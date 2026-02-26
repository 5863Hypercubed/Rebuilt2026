// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * This class defines the runtime mode used by AdvantageKit. The mode is always "real" when running
 * on a roboRIO. Change the value of "simMode" to switch between "sim" (physics sim) and "replay"
 * (log replay from a file).
 */
public final class Constants {
  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }

  public static class SerializerConstants {
    public static final int serializerID = 22;
  }

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class IndexerConstants {
    public static final int indexerID = 18;
  }

  public static class SlapdownConstants {
    public static final int slapdownID = 20; // Placeholder
    public static final int kP = 1; // Placeholder
    public static final int kI = 0; // Placeholder
    public static final int kD = 0; // Placeholder

    public static final int intakeID = 21;
  }

  public static class ShooterConstants {

    public static final int shooterLID = 15; // Right Motor
    public static final int shooterFID = 16; // Left Motor
    public static final double kP = 1.5;
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double kS = 0;
    public static final double kV = 0;
    public static final double kA = 0;
    public static final double RPM_TOLERANCE = 0;

    // get more accurate gear ratio
    public static final double gearRatio = 1 / 1.2;
    public static final double DEGREES_PER_ROT = 360 / gearRatio;

    public static final double max_RPM = 0;
    public static final double targetRPM = 0;
    public static final double shotslope = 0;
  }

  public static class HoodConstants {

    public static final int hoodID = 17;
    public static final double kP = 0;
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double kFF = 0;
    public static final double RPM_TOLERANCE = 0;

    // Encoder -> degrees
    public static final double gearRatio = 24 / 14;
    public static final double DEGREES_PER_ROT = 360 / gearRatio;

    // from horizontal
    public static final double minAngle = 13;
    public static final double maxAngle = 42;
    public static final double hoodslope = 0;
  }

  public static class LimeLightConstants {
    // Limelight
    public static final double LIMELIGHT_HEIGHT = 0;
    public static final double LIMELIGHT_ANGLE = 0;

    // Hub
    public static final double TARGET_HEIGHT = 0;
    public static final double HUB_APRILTAG_HEIGHT = 0;
  }
}
