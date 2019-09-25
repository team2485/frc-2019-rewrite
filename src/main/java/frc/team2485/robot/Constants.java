package frc.team2485.robot;

public class Constants {

    /**
     * WPILib Button enum is public in next release; private currently
     */
    public enum Button {
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

        Button(int value) {
            this.value = value;
        }
    }


    public static final double LIFT_SOLENOID_IN_PORT = 0;
    public static final double LIFT_SOLENOID_OUT_PORT = 4;
    public static final double SLIDE_SOLENOID_IN_PORT = 1;
    public static final double SLIDE_SOLENOID_OUT_PORT = 5;

    public static final double HATCH_INTAKE_ROLLERS_MOTOR_PORT = 10;
}
