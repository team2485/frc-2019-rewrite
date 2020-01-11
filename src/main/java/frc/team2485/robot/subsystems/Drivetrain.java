package frc.team2485.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team2485.WarlordsLib.control.WL_PIDController;
import frc.team2485.WarlordsLib.motorcontrol.WL_TalonSRX;
import frc.team2485.WarlordsLib.robotConfigs.RobotConfigs;

public class Drivetrain extends SubsystemBase {

    private DifferentialDrive drive;

    private WL_TalonSRX driveLeftTalonMaster, driveLeftTalon2, driveLeftTalon3, driveLeftTalon4;

    private WL_TalonSRX driveRightTalonMaster, driveRightTalon2, driveRightTalon3, driveRightTalon4;

    private WL_PIDController angleController;

    private WL_TalonSRX pigeonTalon;

    private PigeonIMU pigeonIMU;

    public Drivetrain() {
        super();

        this.driveLeftTalonMaster = new WL_TalonSRX(12);
        this.driveLeftTalon2 = new WL_TalonSRX(13);
        this.driveLeftTalon3 = new WL_TalonSRX(14);
        this.driveLeftTalon4 = new WL_TalonSRX(15);

        driveLeftTalonMaster.setFollowers(driveLeftTalon2, driveLeftTalon3, driveLeftTalon4);

        this.driveRightTalonMaster = new WL_TalonSRX(4);
        this.driveRightTalon2 = new WL_TalonSRX(5);
        this.driveRightTalon3 = new WL_TalonSRX(6);
        this.driveRightTalon4 = new WL_TalonSRX(7);

        this.driveRightTalonMaster.setFollowers(driveRightTalon2, driveRightTalon3, driveRightTalon4);

        this.drive = new DifferentialDrive(driveLeftTalonMaster, driveRightTalonMaster);

        this.pigeonTalon = new WL_TalonSRX(1);

        this.pigeonIMU = new PigeonIMU(pigeonTalon);

        this.angleController = new WL_PIDController();

        angleController.setTolerance(0.1);

        addChild("Drive", drive);
        addChild("Angle Controller", angleController);
        addChild("Left Speed Controllers", driveLeftTalonMaster);
        addChild("Right Speed Controllers", driveRightTalonMaster);

        RobotConfigs.getInstance().addConfigurable("AngleController", angleController);
    }

    public void curvatureDrive(double throttle, double steering, boolean isQuickTurn) {
        drive.curvatureDrive(throttle, steering, isQuickTurn);
    }

    public void setAngleSetpoint(double setpoint) {

        angleController.setSetpoint(setpoint);

    }

    public void calculateAngle() {
        double output = angleController.calculate(Math.PI / 180  * -1 * pigeonIMU.getFusedHeading() );

        SmartDashboard.putNumber("Angle", pigeonIMU.getFusedHeading());
        SmartDashboard.putNumber("Angle Controller Output", output);




        driveLeftTalonMaster.set(-output);
        driveRightTalonMaster.set(output);
    }

    public double getAngle() {
        return pigeonIMU.getFusedHeading();
    }

    public boolean angleOnTarget() {
        return angleController.atSetpoint();
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
