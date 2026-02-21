// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.test;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Intake;
import frc.robot.commands.SerializerCmd;
import frc.robot.commands.SlapdownDown;
import frc.robot.subsystems.SerializerSub;
import frc.robot.subsystems.Slapdown;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class NoVisionTest extends SequentialCommandGroup {
  /** Creates a new NoVisionTest. */
  public NoVisionTest(Slapdown m_intake, SerializerSub m_serializer) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ParallelDeadlineGroup(
        new WaitCommand(3), 
        new SlapdownDown(m_intake)
      ),
      new ParallelDeadlineGroup(
        new WaitCommand(5),
        new Intake(m_intake, 0.5)
      ),
    
      new ParallelDeadlineGroup(
        new WaitCommand(5), 
        new SerializerCmd(m_serializer, 0.25)
      )
    );
  }
}
