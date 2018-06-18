package jp.ac.kanazawait.ep.mmotoki.car;

import jp.ac.kanazawait.ep.mmotoki.abst.AbstCar;
import jp.ac.kanazawait.ep.mmotoki.driver.SimpleDriver;
import jp.ac.kanazawait.ep.mmotoki.navigator.SimpleLeftEdgeTracer;
import jp.ac.kanazawait.ep.mmotoki.util.CheckerColorSensor;
import jp.ac.kanazawait.ep.mmotoki.util.ColorListener;
import jp.ac.kanazawait.ep.mmotoki.util.EV3Logger;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.lcd.LCD;
import lejos.robotics.Color;

public class MotokiCarWithListener extends AbstCar implements KeyListener, ColorListener {
//public class ButtonEventCar extends AbstCar {

	/**
	 * 動作継続条件
	 */
	private boolean isActive = false;

	public MotokiCarWithListener() {
		this.checkerColorSensor = CheckerColorSensor.getInstance();
		this.driver = new SimpleDriver("B", "C");
		this.navigator = new SimpleLeftEdgeTracer();
		// ログ設定
		this.logger = EV3Logger.getInstance();
		this.logger.setChecker(checkerColorSensor);
		this.logger.setDriver(driver);
		// listener登録
		Button.ESCAPE.addKeyListener(this);
		this.checkerColorSensor.addColorListener(this);
	}

	@Override
	public void run() {
		this.start();
		this.isActive = true;
		while(this.isActive) {
//		while(!Button.ESCAPE.isDown() && this.checkerColorSensor.getColorId() != Color.RED) {
			this.navigator.decision(this.checkerColorSensor, this.driver);
		}
		this.stop();
	}

	public static void main(String[] args) {
		LCD.drawString("ButtonEventCar", 0, 0);
		MotokiCarWithListener car = new MotokiCarWithListener();
		car.run();
	}


	// キーが押された時の動作
	@Override
	public void keyPressed(Key k) {
		// ESCキーが押された時の動作
		if(k == Button.ESCAPE) {
			this.isActive = false;
			this.driver.stop();
		}
	}

	// キーが離された時の動作
	@Override
	public void keyReleased(Key k) {
		// 必要ないので，何も記述しない
	}

	// 色の通知を受けた時の動作
	@Override
	public void colorDetected(int colorid) {
		if(colorid == Color.RED) {
			this.isActive = false;
			this.driver.stop();
		}
	}


}
