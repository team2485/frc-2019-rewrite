package frc.team2485.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HatchIntakeRollers extends SubsystemBase {
    private WPI_TalonSRX rollersMotor;

    public HatchIntakeRollers(){
        super();
        rollersMotor = new WPI_TalonSRX(10);
        addChild("Hatch Intake Rollers", rollersMotor);
    }
    public void setRollers(double pwm) {
        rollersMotor.set(pwm);
    }

    public void stopRollers() {
        rollersMotor.set(0);
    }
}
