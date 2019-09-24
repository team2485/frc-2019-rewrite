package frc.team2485.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.team2485.WarlordsLib.motorcontrol.TalonSRXWrapper;
import frc.team2485.robot.subsystems.Drivetrain;

import javax.management.DescriptorRead;

public class RobotContainer {

    private XboxController jack;

    private Drivetrain drivetrain;

    public RobotContainer() {

        drivetrain = new Drivetrain();

        jack = new XboxController(0);

        SmartDashboard.putData(drivetrain);


        configureCommands();
    }

    public void configureCommands() {

        drivetrain.setDefaultCommand(new RunCommand(() -> {
            drivetrain.curvatureDrive(jack.getTriggerAxis(GenericHID.Hand.kRight) - jack.getTriggerAxis(GenericHID.Hand.kLeft), jack.getX(GenericHID.Hand.kLeft), jack.getXButton());
        }));

    }


}
