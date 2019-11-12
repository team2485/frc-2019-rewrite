package frc.team2485.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team2485.WarlordsLib.motorcontrol.TalonSRXWrapper;
import frc.team2485.robot.Constants;

public class HatchIntake extends SubsystemBase {

    private DoubleSolenoid liftSolenoid;
    private DoubleSolenoid slideSolenoid;


    public HatchIntake() {
        super();

        liftSolenoid = new DoubleSolenoid(Constants.LIFT_SOLENOID_FORWARD_PORT, Constants.LIFT_SOLENOID_REVERSE_PORT);
        slideSolenoid = new DoubleSolenoid(Constants.SLIDE_SOLENOID_FORWARD_PORT, Constants.SLIDE_SOLENOID_REVERSE_PORT);

        addChild("Lift Solenoid", liftSolenoid);
        addChild("Slide Solenoid", slideSolenoid);

    };

    public void lift() {
        liftSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void stow() {
        slideSolenoid.set(DoubleSolenoid.Value.kReverse);
        liftSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void slideOut() {
        slideSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void slideIn() {
        slideSolenoid.set(DoubleSolenoid.Value.kReverse);
    }


}
