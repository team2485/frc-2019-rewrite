package frc.team2485.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.team2485.WarlordsLib.motorcontrol.TalonSRXWrapper;
import frc.team2485.robot.commands.SetDrivetrainAngle;
import frc.team2485.robot.subsystems.Drivetrain;
import frc.team2485.robot.subsystems.HatchIntake;

import javax.management.DescriptorRead;

public class RobotContainer {

    private XboxController jack;

    private Drivetrain drivetrain;
    private HatchIntake hatchIntake;

    public RobotContainer() {

        drivetrain = new Drivetrain();
        hatchIntake = new HatchIntake();

        jack = new XboxController(0);

        SmartDashboard.putData(drivetrain);
        SmartDashboard.putData(hatchIntake);

        configureCommands();
    }

    public void configureCommands() {

        drivetrain.setDefaultCommand(new RunCommand(() -> {
            drivetrain.curvatureDrive(jack.getTriggerAxis(GenericHID.Hand.kRight) - jack.getTriggerAxis(GenericHID.Hand.kLeft), jack.getX(GenericHID.Hand.kLeft), jack.getXButton());
        }));

//        XboxController.Button.kY.value;
//
        new JoystickButton(jack, Constants.Button.kBumperLeft.value)
                .whenPressed(new InstantCommand(hatchIntake::lift)
                        .andThen(new InstantCommand(hatchIntake::slideOut)));

        new Trigger(() -> {
            return jack.getTriggerAxis(GenericHID.Hand.kRight) > 0.2;
        }).whenActive(new InstantCommand(() -> {
            hatchIntake.setRollers(0.5);
        }));
    }

    public Command getAutonomousCommand() {
        return new SetDrivetrainAngle(drivetrain, Math.PI);
    }


}
