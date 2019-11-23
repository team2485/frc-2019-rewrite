/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team2485.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.team2485.WarlordsLib.robotConfigs.RobotConfigurator;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    public RobotContainer robotContainer;

    public Command autonomousCommand;

    Compressor compressor = new Compressor();

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        RobotConfigurator.getInstance().loadConfigsFromFile("/home/lvuser/constants.csv");

        robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        RobotConfigurator.getInstance().saveConfigsToFile("/home/lvuser/constants.csv");
    }

    @Override
    public void autonomousInit() {

        compressor.setClosedLoopControl(false);

        autonomousCommand = robotContainer.getAutonomousCommand();

        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }


    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {

        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }

        compressor.setClosedLoopControl(false);
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {

        CommandScheduler.getInstance().cancelAll();
        compressor.setClosedLoopControl(true);
    }

    @Override
    public void testPeriodic() {
    }

}
