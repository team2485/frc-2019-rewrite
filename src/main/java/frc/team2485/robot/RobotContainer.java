package frc.team2485.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.team2485.WarlordsLib.oi.WL_XboxController;
import frc.team2485.WarlordsLib.oi.WL_XboxController.XboxButton;
import frc.team2485.WarlordsLib.oi.WL_XboxController.XboxJoystick;
import frc.team2485.robot.commands.SetDrivetrainAngle;
import frc.team2485.robot.subsystems.CargoRollers;
import frc.team2485.robot.subsystems.Drivetrain;
import frc.team2485.robot.subsystems.HatchIntake;
import frc.team2485.robot.subsystems.HatchIntakeRollers;

public class RobotContainer {

    private WL_XboxController jack;
    private WL_XboxController suraj;

    private Drivetrain drivetrain;
    private HatchIntake hatchIntake;
    private HatchIntakeRollers hatchIntakeRollers;
    private CargoRollers cargoRollers;

    public RobotContainer() {

        drivetrain = new Drivetrain();
        hatchIntake = new HatchIntake();
        hatchIntakeRollers = new HatchIntakeRollers();
        cargoRollers = new CargoRollers();

        jack = new WL_XboxController(0);
        suraj = new WL_XboxController(1);

        SmartDashboard.putData(drivetrain);
        SmartDashboard.putData(hatchIntake);

        configureCommands();
    }

    public void configureCommands() {


        Shuffleboard.getTab("Drivetrain").add("SetDrivetrainAngle", new SetDrivetrainAngle(drivetrain, Math.PI/2));


        drivetrain.setDefaultCommand(new RunCommand(() ->
            drivetrain.curvatureDrive(jack.getTriggerAxis(GenericHID.Hand.kRight) - jack.getTriggerAxis(GenericHID.Hand.kLeft), jack.getX(GenericHID.Hand.kLeft), jack.getXButton())
                , drivetrain));

        jack.getJoystickButton(XboxButton.kBumperLeft)
                .whenPressed(new SequentialCommandGroup(
                        new InstantCommand(hatchIntake::lift),
                        new WaitCommand(0.2),
                        new InstantCommand(hatchIntake::slideOut)));


        jack.getJoystickButton(XboxButton.kBumperRight)
                .whenPressed(new InstantCommand(hatchIntake::slideIn)
                    .andThen(new InstantCommand(hatchIntake::stow)));

        jack.getJoystickButton(XboxButton.kA)
                .whenPressed(new InstantCommand(()->hatchIntakeRollers.setRollers(0.8)))
                .whenReleased(new InstantCommand(hatchIntakeRollers::stopRollers));

//        new Trigger(() -> {
//            return jack.getTriggerAxis(GenericHID.Hand.kRight) > 0.2;
//        }).whenActive(new InstantCommand(() -> {
//            hatchIntake.setRollers(0.5);
//        }));

        /*Cargo Rollers Controls
        Suraj left bumper: press to intake
        Suraj left trigger: press to outtake, release to stop
         */
        //Cargo intake
        suraj.getJoystickButton(XboxButton.kBumperLeft)
                .whenPressed(new SequentialCommandGroup(new InstantCommand(hatchIntake::slideIn),
                                                        new InstantCommand(hatchIntake::stow),
                                                        new InstantCommand(()->cargoRollers.setRollersManual(0.5)),
                                                        new WaitUntilCommand(cargoRollers::isSpiking),
                                                        new ParallelRaceGroup(new WaitUntilCommand(()->!cargoRollers.isSpiking()), new WaitCommand(500)),
                                                        new ConditionalCommand(new InstantCommand(()->cargoRollers.setRollersManual(0.2)), null, cargoRollers::isSpiking)));

        //cargo outake
        suraj.getJoystickAxisButton(XboxJoystick.kTriggerRight, 0.2)
                .whenActive(new InstantCommand(() -> cargoRollers.setRollersManual(-0.4)));

        suraj.getJoystickAxisButton(XboxJoystick.kTriggerLeft, 0.2)
                .whenInactive(new InstantCommand(() -> cargoRollers.setRollersManual(0)));
    }

    public Command getAutonomousCommand() {
        return new SetDrivetrainAngle(drivetrain, Math.PI/2);
    }


}
