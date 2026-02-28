package frc.robot.subsystems.Shooter;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkClosedLoopController.ArbFFUnits;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.units.measure.MutAngle;
import edu.wpi.first.units.measure.MutAngularVelocity;
import edu.wpi.first.units.measure.MutVoltage;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;

public class ShooterSub extends SubsystemBase {
  // L -> Leader / F -> Follower
  private SparkMax shooterLMotor;
  private SparkMax shooterFMotor;

  private RelativeEncoder shooterLEncoder;
  private RelativeEncoder shooterFEncoder;

  private SparkClosedLoopController Lpid;
  private SparkClosedLoopController Fpid;

  private final SimpleMotorFeedforward sFF =
      new SimpleMotorFeedforward(ShooterConstants.kS, ShooterConstants.kV, ShooterConstants.kA);

  private final MutVoltage flywheelVoltage = Volts.mutable(0);

  private final MutAngle position = Radians.mutable(0);

  private final MutAngularVelocity velocity = RadiansPerSecond.mutable(0);

  private final SysIdRoutine flywheelRoutine;

  public ShooterSub() {
    // Leader
    shooterLMotor = new SparkMax(Constants.ShooterConstants.shooterLID, MotorType.kBrushless);
    SparkMaxConfig shooterLConfig = new SparkMaxConfig();
    Lpid = shooterLMotor.getClosedLoopController();

    shooterLConfig.inverted(true).idleMode(IdleMode.kCoast);
    shooterLConfig.smartCurrentLimit(40).voltageCompensation(12);
    shooterLConfig
        .closedLoop
        .p(Constants.ShooterConstants.kP)
        .i(Constants.ShooterConstants.kI)
        .d(Constants.ShooterConstants.kD);

    shooterLMotor.configure(
        shooterLConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Follower
    shooterFMotor = new SparkMax(Constants.ShooterConstants.shooterFID, MotorType.kBrushless);
    SparkMaxConfig shooterFConfig = new SparkMaxConfig();

    shooterFConfig.inverted(false).idleMode(IdleMode.kCoast);
    shooterFConfig.smartCurrentLimit(40).voltageCompensation(12);
    Fpid = shooterFMotor.getClosedLoopController();

    shooterFMotor.configure(
        shooterFConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    flywheelRoutine =
      new SysIdRoutine(
          new SysIdRoutine.Config(),
          new SysIdRoutine.Mechanism(
              shooterLMotor::setVoltage,
              log -> {
                log.motor("flywheel")
                    .voltage(
                        flywheelVoltage.mut_replace(
                            shooterLMotor.get() * RobotController.getBatteryVoltage(), Volts))
                    .angularPosition(position.mut_replace(shooterLEncoder.getPosition(), Rotations))
                    .angularVelocity(
                        velocity.mut_replace(
                            shooterLEncoder.getVelocity() / 60, RotationsPerSecond));
              },
              this));
    // Encoders
    configEncoders();
  }

  private void configEncoders() {
    shooterLEncoder = shooterLMotor.getEncoder();
    shooterLEncoder.setPosition(0);

    shooterFEncoder = shooterFMotor.getEncoder();
    shooterFEncoder.setPosition(0);
  }

  public void setspeed(double speed) {
    shooterLMotor.set(speed);
    shooterFMotor.set(speed);
  }

  public void setRPM(double RPM) {
    double RPS = RPM / 60;
    double radPerSec = RPS * 2 * Math.PI;

    double ffVolts = sFF.calculate(radPerSec);
    double arbFF = ffVolts / 12.0;

    Lpid.setSetpoint(
        RPM, ControlType.kVelocity, ClosedLoopSlot.kSlot0, arbFF, ArbFFUnits.kPercentOut);

    Fpid.setSetpoint(
        RPM, ControlType.kVelocity, ClosedLoopSlot.kSlot0, arbFF, ArbFFUnits.kPercentOut);
  }

  public boolean atRPM(double targetRPM) {
    return Math.abs(shooterLEncoder.getVelocity() - targetRPM)
        < Constants.ShooterConstants.RPM_TOLERANCE;
  }

  public double getRPM() {
    return shooterLEncoder.getVelocity();
  }

  public void stop() {
    shooterLMotor.stopMotor();
  }

  public Command sysIdFlywheel(SysIdRoutine.Direction direction) {
    return flywheelRoutine.quasistatic(direction);
  }

  @Override
  public void periodic() {}
}
