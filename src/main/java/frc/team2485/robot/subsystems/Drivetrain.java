package frc.team2485.robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team2485.WarlordsLib.control.CoupledPIDController;
import frc.team2485.WarlordsLib.motorcontrol.TalonSRXWrapper;

public class Drivetrain extends SubsystemBase {

    private DifferentialDrive drive;

    private SpeedControllerGroup leftSpeedControllers, rightSpeedControllers;

    private TalonSRXWrapper driveLeftTalon1, driveLeftTalon2, driveLeftTalon3, driveLeftTalon4;

    private TalonSRXWrapper driveRightTalon1, driveRightTalon2, driveRightTalon3, driveRightTalon4;

    private CoupledPIDController angleController;

    public Drivetrain() {
        super();

        this.driveLeftTalon1 = new TalonSRXWrapper(12);
        this.driveLeftTalon2 = new TalonSRXWrapper(13);
        this.driveLeftTalon3 = new TalonSRXWrapper(14);
        this.driveLeftTalon4 = new TalonSRXWrapper(15);

        this.driveRightTalon1 = new TalonSRXWrapper(4);
        this.driveRightTalon2 = new TalonSRXWrapper(5);
        this.driveRightTalon3 = new TalonSRXWrapper(6);
        this.driveRightTalon4 = new TalonSRXWrapper(7);

        this.leftSpeedControllers = new SpeedControllerGroup(driveLeftTalon1, driveLeftTalon2, driveLeftTalon3, driveLeftTalon4);
        this.rightSpeedControllers = new SpeedControllerGroup(driveRightTalon1, driveRightTalon2, driveRightTalon3, driveRightTalon4);

        this.drive = new DifferentialDrive(leftSpeedControllers, rightSpeedControllers);

        this.angleController = new CoupledPIDController(0,0,0);

        addChild("Drive", drive);
        addChild("Velocity Controller", angleController);
    }

    public void curvatureDrive(double throttle, double steering, boolean isQuickTurn) {
        drive.curvatureDrive(throttle, steering, isQuickTurn);
    }

    public void setLeft(double pwm) {
        leftSpeedControllers.set(pwm);
    }

    public void setRight(double pwm) {
        rightSpeedControllers.set(pwm);
    }


}
