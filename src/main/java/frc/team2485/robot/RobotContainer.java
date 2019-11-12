package frc.team2485.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.team2485.WarlordsLib.oi.ThresholdHandler;
import frc.team2485.robot.commands.SetDrivetrainAngle;
import frc.team2485.robot.subsystems.CargoArmRollers;
import frc.team2485.robot.subsystems.Drivetrain;
import frc.team2485.robot.subsystems.HatchIntake;
import frc.team2485.robot.subsystems.HatchIntakeRollers;

import javax.print.attribute.standard.JobSheets;

public class RobotContainer {

    private XboxController jack;
    private XboxController suraj;

    private Drivetrain drivetrain;
    private HatchIntake hatchIntake;
    private HatchIntakeRollers hatchIntakeRollers;
    private CargoArmRollers cargoArmRollers;

    public RobotContainer() {

        drivetrain = new Drivetrain();
        hatchIntake = new HatchIntake();
        hatchIntakeRollers = new HatchIntakeRollers();
        cargoArmRollers = new CargoArmRollers();

        jack = new XboxController(0);
        suraj = new XboxController(1);

        SmartDashboard.putData(drivetrain);
        SmartDashboard.putData(hatchIntake);
        SmartDashboard.putData(hatchIntakeRollers);
        SmartDashboard.putData(cargoArmRollers);

        configureCommands();
    }

    public void configureCommands() {

        drivetrain.setDefaultCommand(new RunCommand(() ->
            drivetrain.curvatureDrive(getDrivetrainThrottle(), getDrivetrainSteering(), jack.getXButton())
                , drivetrain));

        //Hatch

        new JoystickButton(suraj, Constants.XboxButton.kStart.value)
                .whenPressed(new SequentialCommandGroup(
                        new InstantCommand(hatchIntake::lift),
                        new WaitCommand(0.2),
                        new InstantCommand(hatchIntake::slideOut)));

        new JoystickButton(suraj, Constants.XboxButton.kBack.value)
                .whenPressed(new InstantCommand(hatchIntake::slideIn)
                    .andThen(new InstantCommand(hatchIntake::stow)));

        new JoystickButton(suraj, Constants.XboxButton.kBumperRight.value)
                .whenPressed(new InstantCommand(()->hatchIntakeRollers.set(-0.8)))
                .whenReleased(new InstantCommand(()->hatchIntakeRollers.setCurrent(1)));

        new Trigger(() -> {
            return suraj.getTriggerAxis(GenericHID.Hand.kRight) > 0.2;
        }).whenActive(new  SequentialCommandGroup(
                new InstantCommand(()->hatchIntakeRollers.set(0.8))
        )).whenInactive(new SequentialCommandGroup(
                new InstantCommand(()->hatchIntakeRollers.set(0)),
                new InstantCommand(hatchIntake::slideIn)
        ));

        //Rollers

        new JoystickButton(suraj, Constants.XboxButton.kBumperLeft.value)
                .whenPressed(new InstantCommand(()->cargoArmRollers.set(-0.9)))
                .whenReleased(new InstantCommand(()->cargoArmRollers.set(-0.2)));

        new Trigger(()-> {
            return suraj.getTriggerAxis(GenericHID.Hand.kLeft) > 0.2;
        }).whenActive(new InstantCommand(()->cargoArmRollers.set(0.8)))
                .whenInactive(new InstantCommand(()->cargoArmRollers.set(0)));
    }

    public Command getAutonomousCommand() {
        return new SetDrivetrainAngle(drivetrain, Math.PI/2);
    }

    private double getDrivetrainThrottle() {
        return ThresholdHandler.deadbandAndScale(jack.getTriggerAxis(GenericHID.Hand.kRight) - jack.getTriggerAxis(GenericHID.Hand.kLeft), Constants.XBOX_THRESHOLD);
    }

    private double getDrivetrainSteering() {
        return ThresholdHandler.deadbandAndScale( jack.getX(GenericHID.Hand.kLeft), Constants.XBOX_THRESHOLD);
    }

}
