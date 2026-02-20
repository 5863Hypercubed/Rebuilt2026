// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Slapdown;
import frc.robot.subsystems.Slapdown.slapdownStates; 

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class SlapdownUp extends Command {
  private final Slapdown m_slapdown;

  public SlapdownUp(Slapdown m_slapdown) {
    this.m_slapdown = m_slapdown;

    addRequirements(m_slapdown);
  }

  @Override
  public void initialize() {
    m_slapdown.setState(slapdownStates.UP);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
