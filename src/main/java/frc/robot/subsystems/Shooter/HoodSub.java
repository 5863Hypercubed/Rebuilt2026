package frc.robot.subsystems.Shooter;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class HoodSub extends SubsystemBase {

  private TalonFX hoodMotor;
  private double hoodPos;

  //// gear ratio
  public HoodSub() {
    hoodMotor = new TalonFX(Constants.HoodConstants.hoodID);
    hoodPos = hoodMotor.getPosition().getValueAsDouble();
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

    // Encoders
    configEncoders();
  }

  private void configEncoders() {
    hoodMotor.setPosition(0);
  }

  public void setAngle(double deg) {
    PositionVoltage m_position = new PositionVoltage(0);

    double rot = deg / Constants.HoodConstants.DEGREES_PER_ROT;
    hoodMotor.setControl(
        m_position.withPosition(
            MathUtil.clamp(
                rot, Constants.HoodConstants.minAngle, Constants.HoodConstants.maxAngle)));
  }

  public double getAngle() {
    return hoodPos * Constants.HoodConstants.DEGREES_PER_ROT;
  }

  public void manualHood(double speed) {
    hoodMotor.set(speed);
  }

  public void stop() {
    hoodMotor.stopMotor();
  }

  @Override
  public void periodic() {
    
  }
}
