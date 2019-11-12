package frc.team2485.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team2485.WarlordsLib.motorcontrol.TalonSRXWrapper;

public class HatchIntakeRollers extends SubsystemBase {
    private TalonSRXWrapper rollersMotor;

    public HatchIntakeRollers(){
        super();
        rollersMotor = new TalonSRXWrapper(10);
        addChild("Hatch Intake Rollers", rollersMotor);
    }
    public void setRollers(double pwm) {
        rollersMotor.set(pwm);
    }

    public void stopRollers() {
        rollersMotor.set(0);
    }
}
