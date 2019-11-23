package frc.team2485.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team2485.WarlordsLib.robotConfigs.RobotConfigurator;
import frc.team2485.robot.commands.SetDrivetrainAngle;
import frc.team2485.robot.subsystems.Drivetrain;
import frc.team2485.robot.subsystems.HatchIntake;
import frc.team2485.robot.subsystems.HatchIntakeRollers;

public class RobotContainer {

    private XboxController jack;

    private Drivetrain drivetrain;
    private HatchIntake hatchIntake;
    private HatchIntakeRollers hatchIntakeRollers;

    public RobotContainer() {

        drivetrain = new Drivetrain();
        hatchIntake = new HatchIntake();
        hatchIntakeRollers = new HatchIntakeRollers();

        jack = new XboxController(0);

        SmartDashboard.putData(drivetrain);
        SmartDashboard.putData(hatchIntake);

        configureCommands();
    }

    public void configureCommands() {

        drivetrain.setDefaultCommand(new RunCommand(() ->
            drivetrain.curvatureDrive(jack.getTriggerAxis(GenericHID.Hand.kRight) - jack.getTriggerAxis(GenericHID.Hand.kLeft), jack.getX(GenericHID.Hand.kLeft), jack.getXButton())
                , drivetrain));

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


//        new Trigger(() -> {
//            return jack.getTriggerAxis(GenericHID.Hand.kRight) > 0.2;
//        }).whenActive(new InstantCommand(() -> {
//            hatchIntake.setRollers(0.5);
//        }));
    }

    public Command getAutonomousCommand() {
        return new SetDrivetrainAngle(drivetrain, Math.PI/2);
    }


}
