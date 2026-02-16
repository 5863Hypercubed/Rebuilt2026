package frc.robot.subsystems.Shooter;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class HoodSub extends SubsystemBase {
    //L -> Leader / F -> Follower
    private SparkMax hoodMotor;
    private RelativeEncoder hoodEncoder;
    
    private SparkClosedLoopController hpid;


  public HoodSub() {
    hoodMotor = new SparkMax(Constants.HoodConstants.hoodID, MotorType.kBrushless);
      SparkMaxConfig hoodConfig = new SparkMaxConfig();

      hoodConfig
        .inverted(false)
        .idleMode(IdleMode.kBrake);
      hoodConfig
        .smartCurrentLimit(25)
        .voltageCompensation(12);
      hoodConfig.closedLoop.p(Constants.ShooterConstants.kP);
      hoodConfig.closedLoop.i(Constants.ShooterConstants.kI);
      hoodConfig.closedLoop.d(Constants.ShooterConstants.kD);
      hpid = hoodMotor.getClosedLoopController();

      hoodMotor.configure(hoodConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

      //Encoders
      configEncoders();
    }

    private void configEncoders(){
        hoodEncoder = hoodMotor.getEncoder();
        hoodEncoder.setPosition(0);
    }

    public void setAngle(double deg){
        deg = MathUtil.clamp(
            deg,
            Constants.HoodConstants.minAngle,
            Constants.HoodConstants.maxAngle
        );

        double rot = deg / Constants.HoodConstants.DEGREES_PER_ROT;
        hpid.setSetpoint(rot, ControlType.kPosition);
    }

    public double getAngle(){
        return hoodEncoder.getPosition() * Constants.HoodConstants.DEGREES_PER_ROT;
    }

    public void stop(){
        hoodMotor.stopMotor();
    }

    @Override
    public void periodic() {
    }
}
