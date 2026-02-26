// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.hopper;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SlapdownSub extends SubsystemBase {

  public SparkMax slapdownMotor;
  public SparkMax intakeMotor;
  public RelativeEncoder slapdownEncoder;
  public RelativeEncoder intakeEncoder;
  public PIDController slapdownPID;
  boolean isAtSetPoint;

  public static double slapdownPosition = 0;

  public enum slapdownStates {
    UP,
    DOWN
  }

  public slapdownStates slapdownState;

  public SlapdownSub() {
    slapdownState = slapdownStates.UP;
    slapdownMotor = new SparkMax(Constants.SlapdownConstants.slapdownID, MotorType.kBrushless);
    intakeMotor = new SparkMax(Constants.SlapdownConstants.intakeID, MotorType.kBrushless);
    slapdownEncoder = slapdownMotor.getEncoder();
    intakeEncoder = intakeMotor.getEncoder();
    slapdownPID =
        new PIDController(
            Constants.SlapdownConstants.kP,
            Constants.SlapdownConstants.kI,
            Constants.SlapdownConstants.kD);
    SparkMaxConfig slapdownConfig = new SparkMaxConfig();
    SparkMaxConfig intakeConfig = new SparkMaxConfig();

    slapdownPID.setTolerance(0.01);
    slapdownConfig.inverted(false).idleMode(IdleMode.kBrake);

    slapdownConfig
        .softLimit
        .forwardSoftLimit(0) // Placeholder
        .forwardSoftLimitEnabled(false)
        .reverseSoftLimit(0) // Placeholder
        .reverseSoftLimitEnabled(false);

    slapdownConfig
        .smartCurrentLimit(15) // Placholder
        .voltageCompensation(12); // Placeholder

    intakeConfig
        .inverted(false)
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(20)
        .voltageCompensation(8);

    slapdownMotor.configure(
        slapdownConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    intakeMotor.configure(
        intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    slapdownEncoder.setPosition(0);
  }

  public void slapdownMove(double speed) {
    slapdownMotor.set(speed);
  }

  public void slapdownPosition(double setPoint) {
    double speedOutput =
        MathUtil.clamp(slapdownPID.calculate(slapdownEncoder.getPosition(), setPoint), -0.8, 0.8);
    slapdownMove(-speedOutput);
  }

  public void resetSlapdownEncoder() {
    slapdownEncoder.setPosition(0);
  }

  public slapdownStates getState() {
    return slapdownState;
  }

  public void setState(slapdownStates state) {
    slapdownState = state;
  }

  public boolean isAtSetPoint() {
    return slapdownPID.atSetpoint();
  }

  public void stop() {
    intakeMotor.set(0);
  }

  public void slapdownManual(double speed) {
    slapdownMotor.set(speed);
  }

  public void autofeed() {
    intakeMotor.set(0.3);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Slapdown position", slapdownEncoder.getPosition());

    switch (slapdownState) {
      case UP:
        slapdownPosition(0); // Placeholder
        break;

      case DOWN:
        slapdownPosition(0); // Placeholder
        break;
    }
  }
}
