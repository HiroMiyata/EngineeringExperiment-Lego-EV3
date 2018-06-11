package lego2;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class RunLego {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		EV3LargeRegulatedMotor wheelLeft = new EV3LargeRegulatedMotor(MotorPort.B);
		EV3LargeRegulatedMotor wheelRight = new EV3LargeRegulatedMotor(MotorPort.C);
		
		
		int speed = 180;
		
		wheelLeft.setSpeed(speed);
		wheelRight.setSpeed(speed);
		
		wheelLeft.forward();
		wheelRight.forward();
		
		
		
		System.out.println("Press any button");
		Button.waitForAnyPress();
		
		wheelLeft.close();
		wheelRight.close();
	}

}
