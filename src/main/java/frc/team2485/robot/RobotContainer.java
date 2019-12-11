package frc.team2485.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.team2485.WarlordsLib.commands.IfCommand;
import frc.team2485.WarlordsLib.robotConfigs.RobotConfigurator;
import frc.team2485.robot.commands.SetDrivetrainAngle;
import frc.team2485.robot.subsystems.CargoRollers;
import frc.team2485.robot.subsystems.Drivetrain;
import frc.team2485.robot.subsystems.HatchIntake;
import frc.team2485.robot.subsystems.HatchIntakeRollers;

import java.time.Instant;

public class RobotContainer {

    private XboxController jack;
    private XboxController suraj;

    private Drivetrain drivetrain;
    private HatchIntake hatchIntake;
    private HatchIntakeRollers hatchIntakeRollers;
    private CargoRollers cargoRollers;

    public RobotContainer() {

        drivetrain = new Drivetrain();
        hatchIntake = new HatchIntake();
        hatchIntakeRollers = new HatchIntakeRollers();
        cargoRollers = new CargoRollers();

        jack = new XboxController(0);
        suraj = new XboxController(1);

        SmartDashboard.putData(drivetrain);
        SmartDashboard.putData(hatchIntake);

        configureCommands();
    }

    public void configureCommands() {

        drivetrain.setDefaultCommand(new RunCommand(() ->
            drivetrain.curvatureDrive(jack.getTriggerAxis(GenericHID.Hand.kRight) - jack.getTriggerAxis(GenericHID.Hand.kLeft), jack.getX(GenericHID.Hand.kLeft), jack.getXButton())
                , drivetrain));

        /*Hatch Controls
        Jack left bumper lifts up intake and slides it out, ready for hatch intake.
        Jack A sets rollers to intake when pressed and stops intaking when released.
        Jack right bumper stows hatch intake.


         */

        new JoystickButton(jack, Constants.Button.kBumperLeft.value)
                .whenPressed(new SequentialCommandGroup(
                        new InstantCommand(hatchIntake::lift),
                        new WaitCommand(0.2),
                        new InstantCommand(hatchIntake::slideOut)));


        new JoystickButton(jack, Constants.Button.kBumperRight.value)
                .whenPressed(new InstantCommand(hatchIntake::slideIn)
                    .andThen(new InstantCommand(hatchIntake::stow)));

        new JoystickButton(jack, Constants.Button.kA.value)
                .whenPressed(new InstantCommand(()->hatchIntakeRollers.setRollers(0.8)))
                .whenReleased(new InstantCommand(hatchIntakeRollers::stopRollers));

        Shuffleboard.getTab("Drivetrain").add("SetDrivetrainAngle", new SetDrivetrainAngle(drivetrain, Math.PI/2));

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
        new JoystickButton(suraj, Constants.Button.kBumperLeft.value)
                .whenPressed(new SequentialCommandGroup(new InstantCommand(hatchIntake::slideIn),
                                                        new InstantCommand(hatchIntake::stow),
                                                        new InstantCommand(()->cargoRollers.setRollersManual(0.5)),
                                                        new WaitUntilCommand(cargoRollers::isSpiking),
                                                        new ParallelRaceGroup(new WaitUntilCommand(()->!cargoRollers.isSpiking()), new WaitCommand(500)),
                                                        new ConditionalCommand(new InstantCommand(()->cargoRollers.setRollersManual(0.2)), null, cargoRollers::isSpiking)));


        //cargo outake
        new Trigger(() -> {
            return suraj.getTriggerAxis(GenericHID.Hand.kLeft) > 0.2;
        }).whenActive(new InstantCommand(() -> {
            cargoRollers.setRollersManual(-0.4);
        }));

        new Trigger(() -> {
            return suraj.getTriggerAxis(GenericHID.Hand.kLeft) > 0.2;
        }).whenInactive(new InstantCommand(() -> {
            cargoRollers.setRollersManual(0);
        }));



    }

    public Command getAutonomousCommand() {
        return new SetDrivetrainAngle(drivetrain, Math.PI/2);
    }


}
