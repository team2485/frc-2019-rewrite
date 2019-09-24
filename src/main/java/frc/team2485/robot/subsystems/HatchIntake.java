package frc.team2485.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team2485.WarlordsLib.motorcontrol.TalonSRXWrapper;

public class HatchIntake extends SubsystemBase {

    private DoubleSolenoid liftSolenoid;
    private DoubleSolenoid slideSolenoid;

    private TalonSRXWrapper rollersMotor;

    public HatchIntake() {
        super();

        liftSolenoid = new DoubleSolenoid(0, 4);
        slideSolenoid = new DoubleSolenoid(1, 5);

        rollersMotor = new TalonSRXWrapper(10);

        addChild("Lift Solenoid", liftSolenoid);
        addChild("Slide Solenoid", slideSolenoid);

        addChild("Hatch Intake Rollers", rollersMotor);
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

    public void setRollers(double pwm) {
        rollersMotor.set(pwm);
    }

    public void stopRollers(double pwm) {
        rollersMotor.set(0);
    }

}
