package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SerializerSub;

public class SerializerCmd extends Command {
  private final SerializerSub m_subsystem;
  private final double m_speed;

  public SerializerCmd(SerializerSub subsystem, double speed) {
    m_subsystem = subsystem;
    m_speed = speed;
    // Declares that this command uses the subsystem
    addRequirements(m_subsystem);
  }

  @Override
  public void execute() {
    m_subsystem.runSerializer(m_speed);
  }

  @Override
  public void end(boolean interrupted) {
    m_subsystem.stop();
  }
}
