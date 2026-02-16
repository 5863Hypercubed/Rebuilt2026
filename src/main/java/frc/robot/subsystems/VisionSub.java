package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LimelightInfo;

public class VisionSub extends SubsystemBase {
  public double getDistanceInches() {
    double ty = LimelightInfo.getTY("limelight");

    double angle = Constants.LimeLightConstants.LIMELIGHT_ANGLE + ty;

    return (Constants.LimeLightConstants.TARGET_HEIGHT
            - Constants.LimeLightConstants.LIMELIGHT_HEIGHT)
        / Math.tan(Math.toRadians(angle));
  }

  public boolean hasTarget() {
    boolean tv = LimelightInfo.getTV("limelight");

    if (tv) {
      return true;
    } else {
      return false;
    }
  }
}
