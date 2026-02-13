// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.ShotCalc;

public class Flywheel extends SubsystemBase {
  public ShotCalc m_shooter;
  public SparkMax l_motor;
  public SparkMax f_motor;
  public RelativeEncoder l_encoder;
  public RelativeEncoder f_encoder;
  public SparkClosedLoopController pidController;
  /** Creates a new Flywheel. */
  public Flywheel() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
