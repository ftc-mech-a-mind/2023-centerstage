package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Move to Middle Line", group="Robot", preselectTeleOp="MainJavaOpMode")
public class DriveAutoOpMode extends LinearOpMode {
    private static final double kDriveSpeed = 0.6;
    private static final double kTurnSpeed = 0.5;
    private static final double kElevatorExtensionSpeed = 0.3;
    private static final double kElevatorAngleSpeed = 0.3;

    private Drivetrain drive;
    private Elevator elevator;
    private Grabber grabber;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new Drivetrain(
                hardwareMap.dcMotor.get("frontLeftMotor"),
                hardwareMap.dcMotor.get("backLeftMotor"),
                hardwareMap.dcMotor.get("frontRightMotor"),
                hardwareMap.dcMotor.get("backRightMotor"),
                hardwareMap.get(IMU.class, "imu")
        );
        elevator = new Elevator(
                hardwareMap.get(DcMotor.class, "Extend Motor"),
                hardwareMap.get(DcMotor.class, "Angle Motor")
        );
        grabber = new Grabber(
                hardwareMap.get(Servo.class, "GrabberServo"),
                hardwareMap.get(Servo.class, "PusherServo")
        );


        waitForStart();

        grabber.setPusher(Grabber.MAX_PUSHER_POSITION);

        drive.encoderDrive(kDriveSpeed, 1000, 1000, 3);
        drive.encoderDrive(kTurnSpeed, -1000, 1000, 3);

                // about 1000 counts per revolution, 2.66 revolutions to max
        elevator.setAngle(1000, kElevatorAngleSpeed);
        elevator.setExtension(1000, kElevatorExtensionSpeed);

        grabber.setPusher(Grabber.MIN_PUSHER_POSITION);
        grabber.setIntake(Grabber.MIN_INTAKE_POSITION);

        elevator.setExtension(0, kElevatorExtensionSpeed);
        elevator.setAngle(0, kElevatorAngleSpeed);
        grabber.setIntake(Grabber.MAX_INTAKE_POSITION);
    }
}
