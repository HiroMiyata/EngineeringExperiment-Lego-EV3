package jp.ac.kanazawait.ep.mmotoki.abst;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

/**
 * 車全体の抽象クラス
 * 具象クラスでrun()メソッドを実装すること．
 * runメソッド内では start()とstop()を実行すること
 * @author mmotoki
 *
 */
public abstract class AbstCar {
	/**
	 * 使用するセンサーチェッカー
	 */
	protected AbstCheckerColorSensor checkerColorSensor;

	/**
	 * 現在使用しているdriver
	 */
	protected AbstMotorDriver driver;

	/**
	 * 現在使用しているnavigator
	 */
	protected AbstNavigator navigator;

	/**
	 * 使用するLogger
	 */
	protected AbstEV3Logger logger;

	/**
	 * 開始時刻 (ms)
	 * 使用するには，run()の中でstart()を実行すること
	 */
	private long startTime;

	/**
	 * 終了時刻 (ms)
	 * 使用するには，run()の中でstart()を実行すること
	 */
	private long endTime;

	/**
	 * <p>実際に走行させるためのメソッド</p>
	 * <p>具象クラスの中で実装すること</p>
	 * <p>runメソッド内では，<strong><em>最初にstart()を実行し，最後にstop()を実行すること</em></strong></p>
	 */
	public abstract void run();

	/**
	 * 開始準備
	 */
	protected void start() {
		// センサー計測開始
		this.checkerColorSensor.start();

		LCD.drawString("Preparing", 0, 0);

		// loggerが設定されていれば，ログ記録スレッド初期化
		if(this.logger != null) {
			this.logger.initLog();
		}

		// 動作開始待ち
		Sound.buzz();
		LCD.clear();
		LCD.drawString("Press ENTER to start", 0, 0);
		Button.ENTER.waitForPress();

		// loggerが設定されていれば，ログ記録開始
		if(this.logger != null) {
			this.logger.start();
		}

		// 動作開始
		LCD.clear();
		LCD.drawString("Press ESCAPE to stop", 0, 0);
		// 開始時刻記録
		this.startTime = System.currentTimeMillis();
	}

	/**
	 * モーター・センサーの停止
	 */
	protected void stop() {
		// モーター停止
		this.driver.stop();
		// 終了時刻記録
		this.endTime = System.currentTimeMillis();
		// センサー計測スレッド停止
		this.checkerColorSensor.stopThread();
		LCD.clear();
		// 走行時間表示
		LCD.drawString("Time (s) =" + ((double) ((endTime - startTime) / 100) / 10), 0, 0);

		// loggerが設定されていたら，ログ記録スレッド停止・csv書き出し
		if(this.logger != null) {
			this.logger.stopThread();
			this.logger.writeToCSV("log.csv");
		}

		// 動作終了待ち
		LCD.drawString("Press ESC", 0, 1);
		Button.ESCAPE.waitForPress();
	}

}
