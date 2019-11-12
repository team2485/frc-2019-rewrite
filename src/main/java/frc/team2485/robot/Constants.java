package frc.team2485.robot;

public class Constants {

    /**
     * WPILib Button enum is public in next release; private currently
     */
    public enum XboxButton {
        kBumperLeft(5),
        kBumperRight(6),
        kStickLeft(9),
        kStickRight(10),
        kA(1),
        kB(2),
        kX(3),
        kY(4),
        kBack(7),
        kStart(8);

        @SuppressWarnings({"MemberName", "PMD.SingularField"})
        public final int value;

        XboxButton(int value) {
            this.value = value;
        }
    }

    public static final double XBOX_THRESHOLD = 0.2;


    public static final int LIFT_SOLENOID_FORWARD_PORT = 4;
    public static final int LIFT_SOLENOID_REVERSE_PORT = 0;
    public static final int SLIDE_SOLENOID_FORWARD_PORT = 5;
    public static final int SLIDE_SOLENOID_REVERSE_PORT = 1;

    public static final int CARGO_ARM_ROLLERS_MOTOR_PORT = 8;
    public static final int HATCH_INTAKE_ROLLERS_MOTOR_PORT = 10;
}
