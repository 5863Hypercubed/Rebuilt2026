// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SerializerSub extends SubsystemBase {
  // CANID is a placeholder, needs to be set
  private final SparkMax motor;

  public SerializerSub() {
    motor = new SparkMax(Constants.SerializerConstands.serializerID, MotorType.kBrushless);
    SparkMaxConfig configuration = new SparkMaxConfig();

    configuration.inverted(false).idleMode(IdleMode.kCoast);

    motor.configure(configuration, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void runSerializer(double speed) {
    motor.set(speed);
  }

  public void stop() {
    motor.set(0);
  }
}
