package frc.team2485.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team2485.WarlordsLib.control.CoupledPIDController;

public class Drivetrain extends SubsystemBase {

    private DifferentialDrive drive;

    private WPI_TalonSRX driveLeftTalonMaster, driveLeftTalon2, driveLeftTalon3, driveLeftTalon4;

    private WPI_TalonSRX driveRightTalonMaster, driveRightTalon2, driveRightTalon3, driveRightTalon4;

    private CoupledPIDController angleController;

    private TalonSRX pigeonTalon;

    private PigeonIMU pigeonIMU;

    public Drivetrain() {
        super();

        this.driveLeftTalonMaster = new WPI_TalonSRX(12);
        this.driveLeftTalon2 = new WPI_TalonSRX(13);
        this.driveLeftTalon3 = new WPI_TalonSRX(14);
        this.driveLeftTalon4 = new WPI_TalonSRX(15);

        this.driveLeftTalonMaster.setInverted(true);
        this.driveLeftTalon2.setInverted(true);
        this.driveLeftTalon3.setInverted(true);
        this.driveLeftTalon4.setInverted(true);

        this.driveLeftTalon2.follow(driveLeftTalonMaster);
        this.driveLeftTalon3.follow(driveLeftTalonMaster);
        this.driveLeftTalon4.follow(driveLeftTalonMaster);

        this.driveRightTalonMaster = new WPI_TalonSRX(4);
        this.driveRightTalon2 = new WPI_TalonSRX(5);
        this.driveRightTalon3 = new WPI_TalonSRX(6);
        this.driveRightTalon4 = new WPI_TalonSRX(7);

        this.driveRightTalonMaster.setInverted(true);
        this.driveRightTalon2.setInverted(true);
        this.driveRightTalon3.setInverted(true);
        this.driveRightTalon4.setInverted(true);

        this.driveRightTalon2.follow(driveRightTalonMaster);
        this.driveRightTalon3.follow(driveRightTalonMaster);
        this.driveRightTalon4.follow(driveRightTalonMaster);

        this.drive = new DifferentialDrive(driveLeftTalonMaster, driveRightTalonMaster);

        this.pigeonTalon = new TalonSRX(1);

        this.pigeonIMU = new PigeonIMU(pigeonTalon);

        this.angleController = new CoupledPIDController(0.5,0,0);

        angleController.setPercentTolerance(0.05);

        addChild("Drive", drive);
        addChild("Velocity Controller", angleController);
        addChild("Left Speed Controllers", driveLeftTalonMaster);
        addChild("Right Speed Controllers", driveRightTalonMaster);

    }

    public void curvatureDrive(double throttle, double steering, boolean isQuickTurn) {
        drive.curvatureDrive(throttle, steering, isQuickTurn);
    }

    public void setLeft(double pwm) {
        driveLeftTalonMaster.set(pwm);
    }

    public void setRight(double pwm) {
        driveRightTalonMaster.set(pwm);
    }

    public void setAngleSetpoint(double setpoint) {

        angleController.setSetpoint(setpoint);

        angleController.setOutputRange(-10, 10);
    }

    public void calculateAngle() {
        double output = angleController.calculate(Math.PI / 180 * -1 * pigeonIMU.getFusedHeading() );

        SmartDashboard.putNumber("Angle", pigeonIMU.getFusedHeading());
        SmartDashboard.putNumber("Angle Controller Output", output);


        driveLeftTalonMaster.set(ControlMode.Current, output);
        driveRightTalonMaster.set(ControlMode.Current, -output);

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
