package frc.robot.subsystems.Shooter;

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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
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

  public ShooterSub() {
    // Leader
    shooterLMotor = new SparkMax(Constants.ShooterConstants.shooterLID, MotorType.kBrushless);
    SparkMaxConfig shooterLConfig = new SparkMaxConfig();
    Lpid = shooterLMotor.getClosedLoopController();

    shooterLConfig
      .inverted(false)
      .idleMode(IdleMode.kCoast);
    shooterLConfig
      .smartCurrentLimit(40)
      .voltageCompensation(12);
    shooterLConfig.closedLoop
      .p(Constants.ShooterConstants.kP)
      .i(Constants.ShooterConstants.kI)
      .d(Constants.ShooterConstants.kD);

    shooterLMotor.configure(
        shooterLConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Follower
    shooterFMotor = new SparkMax(Constants.ShooterConstants.shooterFID, MotorType.kBrushless);
    SparkMaxConfig shooterFConfig = new SparkMaxConfig();

    shooterFConfig
      .inverted(false)
      .idleMode(IdleMode.kCoast)
      .follow(shooterLMotor);
    shooterFConfig
      .smartCurrentLimit(40)
      .voltageCompensation(12);
    Fpid = shooterFMotor.getClosedLoopController();

    shooterFMotor.configure(
        shooterFConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Encoders
    configEncoders();
  }

  private void configEncoders() {
    shooterLEncoder = shooterLMotor.getEncoder();
    shooterLEncoder.setPosition(0);

    shooterFEncoder = shooterFMotor.getEncoder();
    shooterFEncoder.setPosition(0);
  }

  public void setRPM(double RPM) {
    double RPS = RPM / 60;
    double radPerSec = RPS * 2 * Math.PI;

    double ffVolts = sFF.calculate(radPerSec);
    double arbFF = ffVolts / 12.0;

    Lpid.setSetpoint(
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

  @Override
  public void periodic() {}
}
