// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.ShotCalc;

public class Hood extends SubsystemBase {
  public ShotCalc m_shooter;
  public SparkMax hoodMotor;
  public RelativeEncoder hoodEncoder;
  public SparkClosedLoopController pidController;

  /** Creates a new Hood. */
  public Hood() {
    hoodMotor = new SparkMax(Constants.ShooterConstants.hoodMotorID, MotorType.kBrushless);
    hoodEncoder = hoodMotor.getEncoder();
    pidController = hoodMotor.getClosedLoopController();

    SparkMaxConfig hoodConfig = new SparkMaxConfig();
    hoodConfig
        .inverted(false)
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(15)
        .voltageCompensation(12.0);
    hoodConfig.encoder.positionConversionFactor(360);
    hoodConfig
        .closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .positionWrappingEnabled(true)
        .positionWrappingInputRange(0, 360)
        .pid(
            Constants.ShooterConstants.hoodkP,
            Constants.ShooterConstants.hoodkI,
            Constants.ShooterConstants.hoodkD);
    hoodConfig.softLimit.forwardSoftLimit(360).reverseSoftLimit(0);

    hoodMotor.configure(hoodConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public double getAngle() {
    return hoodEncoder.getPosition();
  }

  public void setToAngle(double distance) {
    double setpoint = m_shooter.hoodFromDistance(distance);
    pidController.setSetpoint(setpoint, ControlType.kPosition);
  }

  public boolean isAtSetpoint() {
    return pidController.isAtSetpoint();
  }

  public void stop() {
    hoodMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
