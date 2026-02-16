// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  public SparkMax intakeMotor;
  public RelativeEncoder intakeEncoder;

  /** Creates a new Intake. */
  public Intake() {
    intakeMotor = new SparkMax(Constants.IntakeConstants.intakeID, MotorType.kBrushless);
    intakeEncoder = intakeMotor.getEncoder();
    SparkMaxConfig intakeConfig = new SparkMaxConfig();

    intakeConfig
        .inverted(false)
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(15)
        .voltageCompensation(12);

    intakeMotor.configure(
        intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void run(double speed) {
    intakeMotor.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
