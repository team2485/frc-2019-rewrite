package frc.team2485.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team2485.WarlordsLib.motorcontrol.CTRE_SpeedControllerGroup;
import frc.team2485.WarlordsLib.control.WarlordsPIDController;

public class Drivetrain extends SubsystemBase {

    private DifferentialDrive drive;

    private TalonSRX driveLeftTalonMaster, driveLeftTalon2, driveLeftTalon3, driveLeftTalon4;

    private TalonSRX driveRightTalonMaster, driveRightTalon2, driveRightTalon3, driveRightTalon4;

    private CTRE_SpeedControllerGroup driveLeft, driveRight;

    private WarlordsPIDController angleController;

    private TalonSRX pigeonTalon;

    private PigeonIMU pigeonIMU;

    public Drivetrain() {
        super();

        this.driveLeftTalonMaster = new TalonSRX(12);
        this.driveLeftTalon2 = new TalonSRX(13);
        this.driveLeftTalon3 = new TalonSRX(14);
        this.driveLeftTalon4 = new TalonSRX(15);



        this.driveLeftTalon2.set(ControlMode.Follower,12);
        this.driveLeftTalon3.set(ControlMode.Follower,12);
        this.driveLeftTalon4.set(ControlMode.Follower,12);

        this.driveLeftTalon2.follow(driveLeftTalonMaster);
        this.driveLeftTalon3.follow(driveLeftTalonMaster);
        this.driveLeftTalon4.follow(driveLeftTalonMaster);

        this.driveRightTalonMaster = new TalonSRX(4);
        this.driveRightTalon2 = new TalonSRX(5);
        this.driveRightTalon3 = new TalonSRX(6);
        this.driveRightTalon4 = new TalonSRX(7);

        this.driveRightTalon2.set(ControlMode.Follower,4);
        this.driveRightTalon3.set(ControlMode.Follower,4);
        this.driveRightTalon4.set(ControlMode.Follower,4);

        this.driveRightTalon2.follow(driveRightTalonMaster);
        this.driveRightTalon3.follow(driveRightTalonMaster);
        this.driveRightTalon4.follow(driveRightTalonMaster);

        this.driveLeft = new CTRE_SpeedControllerGroup(driveLeftTalonMaster, driveLeftTalon2, driveLeftTalon3, driveLeftTalon4);

        driveLeft.setInverted(true);

        this.driveRight = new CTRE_SpeedControllerGroup(driveRightTalonMaster, driveRightTalon2, driveRightTalon3, driveRightTalon4);

        this.drive = new DifferentialDrive(driveLeft, driveRight);
        drive.setRightSideInverted(false);

        this.pigeonTalon = new TalonSRX(1);

        this.pigeonIMU = new PigeonIMU(pigeonTalon);

        this.angleController = new WarlordsPIDController("Angle Controller", true);

        angleController.setPercentTolerance(0.05);

        addChild("Drive", drive);
        addChild("Angle Controller", angleController);
        addChild("Left Speed Controllers", driveLeft);
        addChild("Right Speed Controllers", driveRight);

    }

    public void curvatureDrive(double throttle, double steering, boolean isQuickTurn) {
        drive.curvatureDrive(throttle, steering, isQuickTurn);
    }

    public void setAngleSetpoint(double setpoint) {

        angleController.setSetpoint(setpoint);

        angleController.setOutputRange(-10, 10);
    }

    public void calculateAngle() {
        double output = angleController.calculate(Math.PI / 180  * -1 * pigeonIMU.getFusedHeading() );

        SmartDashboard.putNumber("Angle", pigeonIMU.getFusedHeading());
        SmartDashboard.putNumber("Angle Controller Output", output);




        driveRight.set(ControlMode.Current, -output);
        driveLeft.set(ControlMode.Current, output);
    }

    public double getAngle() {
        return pigeonIMU.getFusedHeading();
    }

    public boolean angleOnTarget() {
        return angleController.atSetpoint(0.05, WarlordsPIDController.Tolerance.kPercent);
    }

    public void reset() {
        pigeonIMU.setFusedHeading(0, 50);
        pigeonIMU.setYaw(0, 50);
        angleController.reset();
    }

    public void setMaxOutput(double maxOutput) {
        drive.setMaxOutput(maxOutput);
    }


}
