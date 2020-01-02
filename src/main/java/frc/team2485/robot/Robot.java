/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team2485.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.team2485.WarlordsLib.robotConfigs.RobotConfigs;

public class Robot extends TimedRobot {

    public RobotContainer robotContainer;

    public Command autonomousCommand;

    Compressor compressor = new Compressor();

//    public String FILE_PATH = "/home/lvuser/constants.csv";
    public String FILE_PATH = "constants.csv";

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        RobotConfigs.getInstance().loadConfigsFromFile(FILE_PATH);

        robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        RobotConfigs.getInstance().saveConfigsToFile(FILE_PATH);
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

//        CommandScheduler.getInstance().cancelAll();
        compressor.setClosedLoopControl(true);
    }

    @Override
    public void testPeriodic() {
    }

}
