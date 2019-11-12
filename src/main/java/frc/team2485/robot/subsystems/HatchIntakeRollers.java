package frc.team2485.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team2485.WarlordsLib.motorcontrol.TalonSRXWrapper;
import frc.team2485.robot.Constants;

public class HatchIntakeRollers extends SubsystemBase {
    private TalonSRXWrapper rollersMotor;

    public HatchIntakeRollers(){
        super();
        rollersMotor = new TalonSRXWrapper(Constants.HATCH_INTAKE_ROLLERS_MOTOR_PORT);

        addChild("Hatch Intake Rollers", rollersMotor);
    }
    public void set(double pwm) {
        rollersMotor.set(pwm);
    }

    public void stop() {
        rollersMotor.set(0);
    }

    public void setCurrent(double current) {
        rollersMotor.set(ControlMode.Current, current);
    }
}
