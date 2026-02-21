package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Shooter.HoodSub;
import frc.robot.subsystems.Shooter.ShooterSub;
import frc.robot.util.ShotCalc;

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
      ShooterSub shooter, HoodSub hood, VisionSub vision, SerializerSub serialzier) {
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

    double distance = vision.getDistanceInches();
    targetRPM = ShotCalc.rpmFromDistance(distance);
    targetAngle = ShotCalc.hoodFromDistance(distance);

    shooter.setRPM(targetRPM);
    hood.setAngle(targetAngle);

    if (shooter.atRPM(targetRPM)) {
      state = ShooterState.READY;
    }
  }

  private void handleReady() {
    shooter.setRPM(targetRPM);
    hood.setAngle(targetAngle);

    serializer.stop();
  }

  private void handleShoot() {
    shooter.setRPM(targetRPM);
    hood.setAngle(targetAngle);
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
