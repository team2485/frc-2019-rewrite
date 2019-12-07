package frc.team2485.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team2485.WarlordsLib.robotConfigs.RobotConfigurator;

public class CargoRollers extends SubsystemBase {
    private final WPI_TalonSRX cargoRollersTalon = new WPI_TalonSRX(8);

    private boolean intaken;

    public CargoRollers() {
        intaken = false;
    }

    public void setRollersManual(double power) {
        cargoRollersTalon.set(ControlMode.PercentOutput, power);
    }

    public void setIntaken(boolean intaken) {
        this.intaken = intaken;
    }

    public boolean getIntaken() {
        return this.intaken;
    }

    public boolean isSpiking() {return this.cargoRollersTalon.getOutputCurrent() > RobotConfigurator.getInstance().getDouble("Cargo Rollers", "Spike Current", 12);}



}
