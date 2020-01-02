package frc.team2485.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HatchIntake extends SubsystemBase {

    private DoubleSolenoid liftSolenoid;
    private DoubleSolenoid slideSolenoid;

    public HatchIntake() {
        super();

        liftSolenoid = new DoubleSolenoid(4, 0);
        slideSolenoid = new DoubleSolenoid(5, 1);


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
