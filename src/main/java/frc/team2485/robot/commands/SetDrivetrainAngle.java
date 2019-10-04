package frc.team2485.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team2485.robot.subsystems.Drivetrain;

public class SetDrivetrainAngle extends CommandBase {

    Drivetrain drivetrain;

    double setpoint;

    public SetDrivetrainAngle(Drivetrain drivetrain, double setpoint) {
        addRequirements(drivetrain);

        this.drivetrain = drivetrain;

        this.setpoint = setpoint;

        drivetrain.reset();
        drivetrain.setAngleSetpoint(setpoint);
    }

    @Override
    public void execute() {
        drivetrain.calculateAngle();
    }

    @Override
    public boolean isFinished() {
        return drivetrain.angleOnTarget();
    }
}
