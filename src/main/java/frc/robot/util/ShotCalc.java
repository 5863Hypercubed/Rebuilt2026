// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;

/** Add your docs here. */
public class ShotCalc {
  private static final InterpolatingDoubleTreeMap hoodMap = new InterpolatingDoubleTreeMap();
  private static final InterpolatingDoubleTreeMap rpmMap = new InterpolatingDoubleTreeMap();

  static {
    hoodMap.put(null, null);
    hoodMap.put(null, null);
    hoodMap.put(null, null);

    rpmMap.put(null, null);
    rpmMap.put(null, null);
    rpmMap.put(null, null);
  }

  public static double rpmFromDistance(double distance) {
    return rpmMap.get(distance);
  }

  public static double hoodFromDistance(double distance) {
    return hoodMap.get(distance);
  }
}
