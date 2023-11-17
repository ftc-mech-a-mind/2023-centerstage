package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Elevator {
    private DcMotor extendMotor, angleMotor;
    // initializes DC Motor

    public Elevator(DcMotor extendMotor, DcMotor angleMotor) {
        this.extendMotor = extendMotor;
        this.angleMotor = angleMotor;
    }

    public int getExtensionPos() {
        return extendMotor.getCurrentPosition();
    }
    public int getAnglePos() {
        return angleMotor.getCurrentPosition();
    }
    public void setExtension(int targetPosition, double pow) {
        extendMotor.setTargetPosition(targetPosition);
        extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendMotor.setPower(pow);
    }
    public void setAngle(int targetPos, double pow) {
        angleMotor.setTargetPosition(targetPos);
        angleMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        angleMotor.setPower(pow);
    }

}
