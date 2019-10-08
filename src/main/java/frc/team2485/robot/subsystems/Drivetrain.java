package frc.team2485.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.PIDBase;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team2485.WarlordsLib.control.CoupledPIDController;
import frc.team2485.WarlordsLib.motorcontrol.CTRE_SpeedControllerGroup;
import frc.team2485.WarlordsLib.motorcontrol.TalonSRXWrapper;

public class Drivetrain extends SubsystemBase {

    private DifferentialDrive drive;

    private CTRE_SpeedControllerGroup leftSpeedControllers, rightSpeedControllers;

    private TalonSRXWrapper driveLeftTalon1, driveLeftTalon2, driveLeftTalon3, driveLeftTalon4;

    private TalonSRXWrapper driveRightTalon1, driveRightTalon2, driveRightTalon3, driveRightTalon4;

    private CoupledPIDController angleController;

    private TalonSRX pigeonTalon;

    private PigeonIMU pigeonIMU;

    public Drivetrain() {
        super();

        this.driveLeftTalon1 = new TalonSRXWrapper(12);
        this.driveLeftTalon2 = new TalonSRXWrapper(13);
        this.driveLeftTalon3 = new TalonSRXWrapper(14);
        this.driveLeftTalon4 = new TalonSRXWrapper(15);

        this.driveLeftTalon1.setInverted(true);
        this.driveLeftTalon2.setInverted(true);
        this.driveLeftTalon3.setInverted(true);
        this.driveLeftTalon4.setInverted(true);

        this.driveRightTalon1 = new TalonSRXWrapper(4);
        this.driveRightTalon2 = new TalonSRXWrapper(5);
        this.driveRightTalon3 = new TalonSRXWrapper(6);
        this.driveRightTalon4 = new TalonSRXWrapper(7);

        this.driveRightTalon1.setInverted(true);
        this.driveRightTalon2.setInverted(true);
        this.driveRightTalon3.setInverted(true);
        this.driveRightTalon4.setInverted(true);

        this.leftSpeedControllers = new CTRE_SpeedControllerGroup(driveLeftTalon1, driveLeftTalon2, driveLeftTalon3, driveLeftTalon4);
        this.rightSpeedControllers = new CTRE_SpeedControllerGroup(driveRightTalon1, driveRightTalon2, driveRightTalon3, driveRightTalon4);

        this.drive = new DifferentialDrive(leftSpeedControllers, rightSpeedControllers);

        this.pigeonTalon = new TalonSRX(1);

        this.pigeonIMU = new PigeonIMU(pigeonTalon);

        this.angleController = new CoupledPIDController(0.5,0,0);

        angleController.setPercentTolerance(0.05);

        addChild("Drive", drive);
        addChild("Velocity Controller", angleController);
        addChild("Left Speed Controllers", leftSpeedControllers);
        addChild("Right Speed Controllers", rightSpeedControllers);

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

    public void setAngleSetpoint(double setpoint) {

        angleController.setSetpoint(setpoint);

        angleController.setOutputRange(-10, 10);
    }

    public void calculateAngle() {
        double output = angleController.calculate(Math.PI / 180 * -1 * pigeonIMU.getFusedHeading() );

        SmartDashboard.putNumber("Angle", pigeonIMU.getFusedHeading());
        SmartDashboard.putNumber("Angle Controller Output", output);


        leftSpeedControllers.set(ControlMode.Current, output);
        rightSpeedControllers.set(ControlMode.Current, -output);

    }

    public double getAngle() {
        return pigeonIMU.getFusedHeading();
    }

    public boolean angleOnTarget() {
        return angleController.atSetpoint(0.05, CoupledPIDController.Tolerance.kPercent);
    }

    public void reset() {
        pigeonIMU.setFusedHeading(0, 50);
        pigeonIMU.setYaw(0, 50);
    }

    public void setMaxOutput(double maxOutput) {
        drive.setMaxOutput(maxOutput);
    }


}
