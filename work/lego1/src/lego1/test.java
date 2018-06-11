package lego1;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;

public class test {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		TextLCD lcd = ev3.getTextLCD();
		Keys keys = ev3.getKeys();
		lcd.drawString("Hello World", 0, 0);
		lcd.drawString("Press any key", 0, 1);
		keys.waitForAnyPress();
		}


}
