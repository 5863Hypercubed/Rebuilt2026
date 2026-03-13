// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climb extends SubsystemBase {
  public TalonFX climbMotor;
  public double climbPos;
  boolean isAtSetPoint;

  public enum climbStates {
    OUT,
    IN
  }

  public climbStates climbState;

  /** Creates a new CLimb. */
  public Climb() {
    climbMotor = new TalonFX(Constants.ClimbConstants.climbID);
    climbPos = climbMotor.getPosition().getValueAsDouble();
    TalonFXConfigurator config = climbMotor.getConfigurator();
    MotorOutputConfigs outputConfig = new MotorOutputConfigs();
    SoftwareLimitSwitchConfigs softLimitConfig = new SoftwareLimitSwitchConfigs();
    CurrentLimitsConfigs currentConfig = new CurrentLimitsConfigs();
    Slot0Configs pidConfig = new Slot0Configs();

    outputConfig.NeutralMode = NeutralModeValue.Brake;

    softLimitConfig.ForwardSoftLimitThreshold = 0;
    softLimitConfig.ForwardSoftLimitEnable = false;
    softLimitConfig.ReverseSoftLimitThreshold = 0;
    softLimitConfig.ReverseSoftLimitEnable = false;

    currentConfig.StatorCurrentLimit = 20;
    currentConfig.StatorCurrentLimitEnable = true;

    pidConfig.kP = Constants.ClimbConstants.kP;
    pidConfig.kI = Constants.ClimbConstants.kI;
    pidConfig.kD = Constants.ClimbConstants.kD;

    configEncoders();
  }

  private void configEncoders() {
    climbMotor.setPosition(0);
  }

  public void move(double speed) {
    climbMotor.set(speed);
  }

  public void stop() {
    climbMotor.stopMotor();
  }

  public void setPosition(double setpoint) {
    PositionVoltage request = new PositionVoltage(setpoint);
    climbMotor.setControl(request);
  }

  public void resetClimbEncoder() {
    climbMotor.setPosition(0);
  }

  public climbStates getState() {
    return climbState;
  }

  public void setState(climbStates state) {
    climbState = state;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("climbPosition", climbPos);

    /*switch (climbState) {
      case OUT:
        setPosition()(0); // Placeholder
        break;

      case IN:
        setPosition(0); // Placeholder
        break;
    }*/
  }
}
