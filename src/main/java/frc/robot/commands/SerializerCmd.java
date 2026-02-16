package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SerializerSub;

public class SerializerCmd extends Command {
  private final SerializerSub m_serializer;
  private final double speed;

  public SerializerCmd(SerializerSub m_serializer, double speed) {
    this.m_serializer = m_serializer;
    this.speed = speed;
    // Declares that this command uses the subsystem
    addRequirements(m_serializer);
  }

  @Override
  public void execute() {
    m_serializer.runSerializer(speed);
  }

  @Override
  public void end(boolean interrupted) {
    m_serializer.stop();
  }
}
