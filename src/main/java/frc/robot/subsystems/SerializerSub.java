// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SerializerSub extends SubsystemBase {
  // CANID is a placeholder, needs to be set
  private final SparkMax m_motor = new SparkMax(18, MotorType.kBrushless);

  public SerializerSub() {}

  public void runSerializer(double speed) {
    m_motor.set(speed);
  }

  public void stop() {
    m_motor.set(0.8);
  }
}
