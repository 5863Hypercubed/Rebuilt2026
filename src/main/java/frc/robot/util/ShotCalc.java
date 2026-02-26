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
    hoodMap.put(0.0, 0.0);
    hoodMap.put(0.0, 0.0);
    hoodMap.put(0.0, 0.0);

    rpmMap.put(0.3, 1000.0);
    rpmMap.put(0.8, 2000.0);
    rpmMap.put(1.5, 3000.0);
  }

  public static double rpmFromDistance(double distance) {
    return rpmMap.get(distance);
  }

  public static double hoodFromDistance(double distance) {
    return hoodMap.get(distance);
  }
}
