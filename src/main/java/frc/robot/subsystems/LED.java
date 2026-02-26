package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.Optional;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class LED extends SubsystemBase {
    public static Spark LEDdriverR;
    public static Spark LEDdriverL;
    public static Spark LEDdriverFL;
    Optional<Alliance> ally = DriverStation.getAlliance();

    public LED(){
        LEDdriverR = new Spark(0);
        LEDdriverL = new Spark(1);
        LEDdriverFL = new Spark(2);
    }

    public void set(double light) {
        LEDdriverR.set(light);
        LEDdriverL.set(light);
        LEDdriverFL.set(light);
    }

    /*
    Pulse width, Pattern Type, Color
        //Solid
            1785 0.57 Solid Colors Hot Pink 
            1795 0.59 Solid Colors Dark red  
            1805 0.61 Solid Colors Red 
            1815 0.63 Solid Colors Red Orange 
            1825 0.65 Solid Colors Orange 
            1835 0.67 Solid Colors Gold 
            1845 0.69 Solid Colors Yellow 
            1855 0.71 Solid Colors Lawn Green 
            1865 0.73 Solid Colors Lime 
            1875 0.75 Solid Colors Dark Green 
            1885 0.77 Solid Colors Green 
            1895 0.79 Solid Colors Blue Green 
            1905 0.81 Solid Colors Aqua 
            1915 0.83 Solid Colors Sky Blue 
            1925 0.85 Solid Colors Dark Blue 
            1935 0.87 Solid Colors Blue 
            1945 0.89 Solid Colors Blue Violet 
            1955 0.91 Solid Colors Violet 
            1965 0.93 Solid Colors White 
            1975 0.95 Solid Colors Gray 
            1985 0.97 Solid Colors Dark Gray 
            1995 0.99 Solid Colors Black
    */

    @Override
    public void periodic() {

    if(ally.isPresent()){
        if(ally.get() == Alliance.Red){
            LEDdriverR.set(-0.39);
            LEDdriverL.set(-0.39);
            LEDdriverFL.set(-0.39);
        }
        if(ally.get() == Alliance.Blue){
            LEDdriverR.set(-0.39);
            LEDdriverL.set(-0.39);
            LEDdriverFL.set(-0.39);
        }     
        else{
            LEDdriverR.set(-0.39);
            LEDdriverL.set(-0.39);
            LEDdriverFL.set(-0.39);
            }
        }
    }
}
