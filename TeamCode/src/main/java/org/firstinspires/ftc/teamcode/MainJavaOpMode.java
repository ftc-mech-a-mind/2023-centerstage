package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

// TODO: back right should be inverted for strafe

@TeleOp
public class MainJavaOpMode extends LinearOpMode {
    private static final int EXTENDED = 11000;



    private static final int RETRACTED = 0;
    private final int MAX_EXTEND = 2600;
    private final int MIN_EXTEND = -1000;
    private final int MAX_ANGLE = 10000;
    private final int MIN_ANGLE = -15000;
    private final int ELEVATOR_MAX = 10000;
    // initializes float that = 10000
    private final int ELEVATOR_MIN = -10000;

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor extendMotor = hardwareMap.get(DcMotor.class, "Extend Motor");
        DcMotor angleMotor = hardwareMap.get(DcMotor.class, "Angle Motor");
        DcMotor ClimbLeft = hardwareMap.get(DcMotor.class, "ClimbLeftMotor");
        DcMotor ClimbRight = hardwareMap.get(DcMotor.class, "ClimbRightMotor");
//        Servo ShooterServo = hardwareMap.get(Servo.class, "ShooterServo");
        Servo GrabberServo = hardwareMap.get(Servo.class, "GrabberServo");
        Servo GrabberPusherServo = hardwareMap.get(Servo.class, "PusherServo");
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        IMU imu = hardwareMap.get(IMU.class, "imu");

        Drivetrain drivetrain = new Drivetrain(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor, imu);
        Elevator elevator = new Elevator(extendMotor, angleMotor);
        Climb climb = new Climb(ClimbLeft, ClimbRight);
        Grabber grabber = new Grabber(GrabberServo, GrabberPusherServo);
//        Shooter shooter = new Shooter(ShooterServo);

        waitForStart();

        grabber.setPusher(Grabber.MAX_PUSHER_POSITION);

        while (opModeIsActive()) {
            if (isStopRequested()) return;

            // DRIVETRAIN CONTROLS
            double y1 = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;

            double rx = gamepad1.right_stick_x;
            double y2 = -gamepad1.right_stick_y;

            if (gamepad1.options) {
                drivetrain.imuResetYaw();
            }

            int mode = drivetrain.getMode();

            telemetry.addData("imu yaw: ", drivetrain.imuGetYawAngles());

//            if(mode == 0) {
//                drivetrain.mecanumDrive(x, y1, rx);
//                telemetry.addData("Drive Mode: ", "Mecanum Drive");
//            }  else if(mode == 1) {
//                drivetrain.tankDrive(y1, y2);
//                telemetry.addData("Drive Mode: ", "Tank");
//            } else if(mode == 2) {
//                drivetrain.move(y1, x, rx, 1);
//                telemetry.addData("Drive Mode: ", "Field-Centric 100%");
//            } else if (mode == 3) {
//                drivetrain.move(y1, x, rx, 0.5);
//                telemetry.addData("Drive Mode: ", "Field-Centric 50%");
//            } else if(mode == 4){
            if (mode == 0) {
                drivetrain.robotCentricMove(y1, x, rx);
                telemetry.addData("Drive Mode: ", "Robot-Centric");
//            } else if(mode == 5){
            } else if (mode == 1) {
                drivetrain.robotCentricMove(y1, x, rx, 0.5);
                telemetry.addData("Drive Mode: ", "Robot Centric 50%");
//            } else if(mode == 6){
            } else if (mode == 2) {
                drivetrain.robotCentricMove(y1, x, rx, 0.25);
                telemetry.addData("Drive Mode: ", "Robot Centric 25%");
//            } else if(mode == 7) {
//                drivetrain.mecanumDrive(x, y1, rx, 0.5);
//                telemetry.addData("Drive Mode: ", "Mecanum Drive 50%");
//            } else if(mode == 8) {
//                drivetrain.mecanumDrive(x, y1, rx, 0.25);
//                telemetry.addData("Drive Mode: ", "Mecanum Drive 25%");
            }

            if (gamepad1.triangle) {
//                if (drivetrain.getMode() + 1 > 9) {
                if (drivetrain.getMode() + 1 > 3) {
                    drivetrain.setMode(0);
                } else {
                    drivetrain.setMode(drivetrain.getMode() + 1);
                }
                sleep(100);
            }

            //telemetry.addData("Pusher Position", grabber.getPusher());
            //telemetry.addData("Intake Position", grabber.getIntake());

            // GRABBER CONTROLS
            if (gamepad2.a) {
                grabber.setIntake(Grabber.MAX_INTAKE_POSITION);

            } else if (gamepad2.b) {
                grabber.setIntake(Grabber.MIN_INTAKE_POSITION);
            }

            if (gamepad2.x) {
                grabber.setPusher(Grabber.MAX_PUSHER_POSITION);

            } else if (gamepad2.y) {
                grabber.setPusher(Grabber.MIN_PUSHER_POSITION);
            }

            /*
                TODO: CLIMB
                    back to reset encoders
                    dpad up for move up
                    dpad down for move down
                    left bumper to force
             */

            // SHOOTER CONTROLS - not using for 1st competition
//            if (gamepad2.x) {
//                shooter.setPosition(Servo.MAX_POSITION);
//            } else {
//                shooter.setPosition(Servo.MIN_POSITION);
//            }

            // ELEVATOR CONTROLS
            // while the opmode is active make new float that takes the left stick y value
            float extendInput = -gamepad2.left_stick_y;
            float angleInput = -gamepad2.right_stick_y;

            if (extendInput > 0.1)  {
                elevator.setExtension(MAX_EXTEND, 0.45);
            } else if (extendInput < -0.1) {
                elevator.setExtension(MIN_EXTEND, 0.45);
            } else {
                elevator.stopExtend();
            }

            if(gamepad2.start) {
                elevator.resetEncoders();
            }
//
            telemetry.addData("Elevator Extend Position: ", elevator.getExtensionPos());
            telemetry.addData("Elevator Angle Position: ", elevator.getAnglePos());

            if(angleInput > 0.1) {
                elevator.setAngle(MAX_ANGLE, 0.45);
            } else if(angleInput < -0.1) {
                elevator.setAngle(MIN_ANGLE, 0.45);
            } else {
                elevator.stopAngle();
            }

            telemetry.update();
        }
    }
}