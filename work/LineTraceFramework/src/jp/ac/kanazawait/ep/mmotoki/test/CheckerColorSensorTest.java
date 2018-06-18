package jp.ac.kanazawait.ep.mmotoki.test;

import jp.ac.kanazawait.ep.mmotoki.util.CheckerColorSensor;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class CheckerColorSensorTest {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Sound.setVolume(2);
		Sound.buzz();
		CheckerColorSensor checkerColorSensor = CheckerColorSensor.getInstance();
		LCD.drawString("Press any button to stop", 0, 0);
		checkerColorSensor.start();
		Button.waitForAnyPress();
		checkerColorSensor.stopThread();
	}

}
