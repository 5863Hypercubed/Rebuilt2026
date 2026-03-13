package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Shooter.HoodSub;
import frc.robot.subsystems.Shooter.ShooterSub;
import frc.robot.subsystems.hopper.SerializerSub;
// import frc.robot.util.ShotCalc;

public class ShooterStructure extends SubsystemBase {

  private ShooterSub shooter;
  private HoodSub hood;
  private VisionSub vision;
  private SerializerSub serializer;

  private double targetRPM = 0;
  private double targetAngle = 0;

  public enum ShooterState {
    IDLE,
    SPINNINGUP,
    READY,
    SHOOTING
  }

  private ShooterState state = ShooterState.IDLE;

  public ShooterStructure(
      ShooterSub shooter, HoodSub hood, VisionSub vision, SerializerSub serializer) {
    this.shooter = shooter;
    this.hood = hood;
    this.vision = vision;
    this.serializer = serializer;
  }

  public void requestIDLE() {
    state = ShooterState.IDLE;
  }

  public void requestVisionShot() {
    state = ShooterState.SPINNINGUP;
  }

  public boolean isReady() {
    return state == ShooterState.READY;
  }

  @Override
  public void periodic() {
    switch (state) {
      case IDLE -> handleIdle();
      case SPINNINGUP -> handleSpinUp();
      case READY -> handleReady();
      case SHOOTING -> handleShoot();
    }
  }

  private void handleIdle() {
    shooter.stop();
    serializer.stop();
  }

  private void handleSpinUp() {
    if (!vision.hasTarget()) {
      state = ShooterState.IDLE;
      return;
    }
    // targetRPM = ShotCalc.rpmFromDistance(distance);
    // targetAngle = ShotCalc.hoodFromDistance(distance);
    double distance = vision.getDistanceInches();
    shooter.setRPMFromDistance(distance);
    hood.setAngleFromDistance(distance);

    if (shooter.atRPM(distance)) {
      state = ShooterState.READY;
    }
  }

  private void handleReady() {
    double distance = vision.getDistanceInches();
    shooter.setRPMFromDistance(distance);
    hood.setAngle(distance);

    serializer.stop();
  }

  private void handleShoot() {
    double distance = vision.getDistanceInches();
    shooter.setRPMFromDistance(distance);
    hood.setAngle(distance);
    serializer.autofeed();
  }

  public void shoot() {
    if (state == ShooterState.READY) {
      state = ShooterState.SHOOTING;
    }
  }

  public void stopShooting() {
    if (state == ShooterState.SHOOTING) {
      state = ShooterState.READY;
    }
  }
}
