package jp.ac.kanazawait.ep.mmotoki.util;

import jp.ac.kanazawait.ep.mmotoki.abst.AbstCheckerColorSensor;
import lejos.hardware.lcd.LCD;

/***
 * <p>カラーセンサーの測定を続けるスレッドのクラス．
 * <strong>センサーは必ず<em>S2ポート</em>に接続</strong>すること．
 * Singletonパターンを使うので，一つしかインスタンスを作成できない．</p>
 * <p>使用法：</p>
 * <ol>
 * <li> getInstance()でインスタンスを取得する
 * <li> <strong>1.で取得したインスタンスに対し，<em>start()</em>を実行</strong>し，測定スレッド開始
 * <li> 測定値を得たいときは，getで始まるメソッドを実行
 * <li> <strong>スレッドの実行を終了するには，<em>stopThread()</em>を実行</strong>
 * </ol>
 * @author mmotoki
 *
 */

public class CheckerColorSensor extends AbstCheckerColorSensor implements ColorFinder {

	/**
	 * Singletonパターンとするための，唯一のインスタンス
	 */
	private static CheckerColorSensor instanceColorSensorChecker = new CheckerColorSensor();

	/**
	 * インスタンスを取得するクラスメソッド
	 * @return 唯一のインスタンス
	 */
	public static CheckerColorSensor getInstance() {
		return CheckerColorSensor.instanceColorSensorChecker;
	}

	/**
	 * コンストラクタ
	 * singletonパターンを適用するため，他からは呼べないprivateで宣言してある
	 */
	private CheckerColorSensor() {
		this.setSensor("S2");
	}

	@Override
	public void showSample(int x, int y) {
		LCD.drawString("ID : " + this.getColorId() + " " + LegoColor.colorId2Name(this.getColorId()), x, y);
		LCD.drawString("Red: 0." + (int) (this.getRed() * 100), x, y+1);
		LCD.drawString("RGB:", x, y+2);
		float[] rgb = this.getRGB();
		LCD.drawString("R:" + (int) (rgb[0] * 10) / 10.0
				+ " G:" + (int) (rgb[1] * 10) / 10.0
				+ " B:" + (int) (rgb[2] * 10) / 10.0,
				x, y+3);
		LCD.drawString("total:" + ((int) ((rgb[0] + rgb[1] + rgb[2]) * 10)) / 10.0, x, y+4);
	}

	@Override
	protected void fetchSample() {
		this.providerRed.fetchSample(this.sampleRed, 0);
		this.providerColorId.fetchSample(this.sampleColorID, 0);
		callListeners();
		this.providerRGB.fetchSample(this.sampleRGB, 0);
	}





}