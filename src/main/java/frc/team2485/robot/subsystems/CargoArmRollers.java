package frc.team2485.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team2485.WarlordsLib.motorcontrol.TalonSRXWrapper;
import frc.team2485.robot.Constants;

public class CargoArmRollers extends SubsystemBase {

    private TalonSRXWrapper rollersMotor;

    public CargoArmRollers() {
        super();
        rollersMotor = new TalonSRXWrapper(Constants.CARGO_ARM_ROLLERS_MOTOR_PORT);

        addChild("Cargo Arm Rollers", rollersMotor);
    }

    public void set(double pwm) {
        rollersMotor.set(pwm);
    }

    public void stop() {
        rollersMotor.set(0);
    }

}
