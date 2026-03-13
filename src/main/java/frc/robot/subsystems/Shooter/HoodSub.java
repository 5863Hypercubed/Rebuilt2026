package frc.robot.subsystems.Shooter;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import java.util.function.Supplier;

public class HoodSub extends SubsystemBase {

  private TalonFX hoodMotor;
  private Supplier<Angle> hoodPos;

  private static final InterpolatingDoubleTreeMap hoodAngleMap = new InterpolatingDoubleTreeMap();

  //// gear ratio
  public HoodSub() {
    hoodMotor = new TalonFX(Constants.HoodConstants.hoodID);
    hoodPos = hoodMotor.getPosition().asSupplier();
    TalonFXConfigurator hoodConfig = hoodMotor.getConfigurator();
    MotorOutputConfigs outputConfig = new MotorOutputConfigs();
    SoftwareLimitSwitchConfigs softLimitConfig = new SoftwareLimitSwitchConfigs();
    CurrentLimitsConfigs currentConfig = new CurrentLimitsConfigs();
    Slot0Configs pidConfig = new Slot0Configs();

    outputConfig.NeutralMode = NeutralModeValue.Brake;

    softLimitConfig.ForwardSoftLimitThreshold = 0.5;
    softLimitConfig.ForwardSoftLimitEnable = true;
    softLimitConfig.ReverseSoftLimitThreshold = -1.36;
    softLimitConfig.ReverseSoftLimitEnable = true;

    currentConfig.StatorCurrentLimit = 20;
    currentConfig.StatorCurrentLimitEnable = true;

    pidConfig.kP = Constants.HoodConstants.kP;
    pidConfig.kI = Constants.HoodConstants.kI;
    pidConfig.kD = Constants.HoodConstants.kD;

    hoodConfig.apply(outputConfig);
    hoodConfig.apply(softLimitConfig);
    hoodConfig.apply(currentConfig);
    hoodConfig.apply(pidConfig);

    hoodAngleMap.put(31.375, 0.0);
    hoodAngleMap.put(81.4, 0.0);
    hoodAngleMap.put(140.0, 0.0);
    hoodAngleMap.put(211.9072, 0.0);

    // Encoders
    configEncoders();
  }

  private void configEncoders() {
    hoodMotor.setPosition(0);
  }

  public void setAngle(double deg) {
    double rot = deg / Constants.HoodConstants.DEGREES_PER_ROT;

    double clampedRot =
        MathUtil.clamp(rot, Constants.HoodConstants.minAngle, Constants.HoodConstants.maxAngle);

    double angleRad = Math.toRadians(deg);

    double ff = Constants.HoodConstants.kFF * Math.cos(angleRad);

    PositionVoltage request = new PositionVoltage(clampedRot);
    request.FeedForward = ff;
    request.FeedForward = MathUtil.clamp(request.FeedForward, -0.05, 0.05);

    hoodMotor.setControl(request);
  }

  public double getInterpolatedAngle(double distance) {
    return hoodAngleMap.get(distance);
  }

  public void setAngleFromDistance(double distance) {
    double angle = hoodAngleMap.get(distance);
    setAngle(angle);
  }

  public Supplier<Angle> getAngle() {
    return hoodPos;
  }

  public void manualHood(double speed) {
    hoodMotor.set(speed);
  }

  public void stop() {
    hoodMotor.stopMotor();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("hoodPosition", getAngle().get().in(Degrees));
  }
}
