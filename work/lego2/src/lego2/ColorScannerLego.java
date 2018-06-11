package lego2;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class ColorScannerLego {
	public static void main(String[] args) {
		TextLCD lcd = LocalEV3.get().getTextLCD();
		/**
		 * ColorIDと名前の対応
		 */
		String[] colorNames = {
				"RED", "GREEN", "BLUE", "YELLOW", "MAGENTA",
				"ORANGE", "WHITE", "BLACK", "PINK", "GRAY",
				"LIGHT_GRAY", "DARK_GRAY", "CYAN", "BROWN"};

		/**
		 * カラーセンサーのインスタンス．
		 * 本当はSensorModesインターフェースのフィールドで受ければ十分．
		 * しかし，staticメソッド内でインスタンスを作ったため，
		 * 最後にcloseする必要があり，実体のクラスのフィールドとした．
		 * (SensorModesインターフェースはcloseメソッドを持たないので)
		 */
		// SensorModes colorSensor = new EV3ColorSensor(SensorPort.S2);
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
		SampleProvider colorRGB = colorSensor.getMode("RGB");
		SampleProvider colorID = colorSensor.getMode("ColorID");
		SampleProvider colorRed = colorSensor.getMode("Red");
		float[] sampleRGB = new float[colorRGB.sampleSize()];
		float[] sampleID = new float[colorID.sampleSize()];
		float[] sampleRed = new float[colorRed.sampleSize()];
		// ESCAPEが押されていない間，続ける
		while(!Button.ESCAPE.isDown()) {
			lcd.drawString("R :", 0, 0);
			lcd.drawString("G :", 0, 1);
			lcd.drawString("B :", 0, 2);
			lcd.drawString("ID:", 0, 3);
			lcd.drawString("Red:", 0, 4);
			// RGB測定値の取得
			colorRGB.fetchSample(sampleRGB, 0);
			// カラーIDの取得
			colorID.fetchSample(sampleID, 0);
			// 輝度の取得
			colorRed.fetchSample(sampleRed, 0);
			lcd.drawString(Float.toString(sampleRGB[0]), 4, 0);
			lcd.drawString(Float.toString(sampleRGB[1]), 4, 1);
			lcd.drawString(Float.toString(sampleRGB[2]), 4, 2);
			lcd.drawInt((int)sampleID[0], 2, 3, 3);
			if(sampleID[0] >= 0 && sampleID[0] < colorNames.length)
				lcd.drawString(colorNames[(int)sampleID[0]], 6, 3);
			else
				lcd.drawString("NONE", 6, 3);
			lcd.drawString(Float.toString(sampleRed[0]), 4, 4);
		}
		colorSensor.close();
	}

}
