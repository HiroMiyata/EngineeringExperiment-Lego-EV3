package jp.ac.kanazawait.ep.mmotoki.abst;

import java.util.ArrayList;

import jp.ac.kanazawait.ep.mmotoki.util.ColorFinder;
import jp.ac.kanazawait.ep.mmotoki.util.ColorListener;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public abstract class AbstCheckerColorSensor extends Thread implements ColorFinder {

	/**
	 * センサーチェック間隔 (ms)
	 * publicなので，値を直接読み書きできる
	 */
	public int interval = 10;

	/**
	 * センサーで測定した値をLCDに表示するならtrue
	 * publicなので，値を直接読み書きできる
	 */
	public boolean showSample = true;

	/**
	 * 測定したsampleを表示する開始位置のx座標
	 * showSample()で使用する
	 * publicなので，値を直接読み書きできる
	 */
	public int xShowAt = 0;

	/**
	 * 測定したsampleを表示する開始位置のy座標
	 * showSample()で使用する
	 * publicなので，値を直接読み書きできる
	 */
	public int yShowAt = 3;

	/**
	 * 使用するカラーセンサー (S1ポートに接続すること)
	 */
	protected SensorModes sensor;

	/**
	 * ColorIDモードのSampleProvider
	 */
	protected SampleProvider providerColorId;

	/**
	 * RedモードのSampleProvider
	 */
	protected SampleProvider providerRed;

	/**
	 * RGBモードのSampleProvider
	 */
	protected SampleProvider providerRGB;

	/**
	 * ColorIDモードのセンサーの測定値
	 */
	protected float[] sampleColorID;
	/**
	 * ColorIDモードのセンサーの測定値
	 */
	protected float[] sampleRGB;
	/**
	 * Redモードのセンサーの測定値
	 */
	protected float[] sampleRed;

	/**
	 * スレッドがアクティブならtrue
	 */
	protected boolean isActive = false;

	/**
	 * ColorFinderのためのlistenerリスト
	 */
	protected ArrayList<ColorListener> listeners = null;


	/**
	 * カラーIDの取得
	 * @return カラーID
	 */
	public int getColorId() {
		return (int) this.sampleColorID[0];
	}

	/**
	 * Redモードで測定した明るさの取得
	 * @return	Redモードで測定した明るさ (0以上1以下のfloat)
	 */
	public float getRed() {
		return this.sampleRed[0];
	}

	/**
	 * RGBモードで測定した３色の値の取得
	 * @return	RGBモードで測定した３色の値 (0以上1以下のfloatの長さ3の配列)
	 */
	public float[] getRGB() {
		return this.sampleRGB;
	}

	// 20180430 追加
	/**
	 * すべてのセンサーデータの取得
	 * @return [カラーID, Redモードで測定した明るさ，RGBモードのR, RGBモードのG, RGBモードのB]
	 */
	public float[] getAll() {
		float[] data = {this.sampleColorID[0], this.sampleRed[0],
				this.sampleRGB[0], this.sampleRGB[1], this.sampleRGB[2]};
		return data;
	}

	// 20180430追加
	/**
	 * RGBの合計での明るさの取得
	 * @return RGBの合計値
	 */
	public float getIntensity() {
		return this.sampleRGB[0] + this.sampleRGB[1] + this.sampleRGB[2];
	}

	/**
	 * センサー計測スレッドで実行する内容
	 */
	public void run() {
		this.isActive = true;

		while(this.isActive) {
			// 測定
			this.fetchSample();

			// 測定値の表示
			if(this.showSample) {
				this.showSample(this.xShowAt, this.yShowAt);
			}

			Delay.msDelay(this.interval);
		}
	}

	/**
	 * スレッドの停止
	 */
	public void stopThread() {
		this.isActive = false;
	}

	/**
	 * センサーから値を取得するメソッド
	 *
	 */
	protected abstract void fetchSample();

	// 20180430変更
	/**
	 * 測定したsampleをLCDに表示するメソッド
	 * @param x	表示を開始する場所のx座標
	 * @param y	表示を開始する場所のy座標
	 */
	public abstract void showSample(int x, int y);

	/**
	 * センサー，SampleProvider，配列を設定するメソッド
	 * @param port	ポート番号 ("S1"～"S4")
	 */
	protected void setSensor(String port) {
		// センサーの設定
		this.sensor = new EV3ColorSensor(LocalEV3.get().getPort(port));

		// SampleProviderの取得
		this.providerColorId = this.sensor.getMode("ColorID");
		this.providerRed = this.sensor.getMode("Red");
		this.providerRGB = this.sensor.getMode("RGB");

		// sample取得用配列の設定
		this.sampleColorID = new float[this.providerColorId.sampleSize()];
		this.sampleRed = new float[this.providerRed.sampleSize()];
		this.sampleRGB = new float[this.providerRGB.sampleSize()];
	}

	@Override
	public void addColorListener(ColorListener listener) {
		if(this.listeners == null) {
			this.listeners = new ArrayList<ColorListener>();
		}
		this.listeners.add(listener);
	}

	@Override
	public void callListeners() {
		if(listeners != null) {
			int colorid = this.getColorId();
			if(colorid == Color.RED) {
				for(ColorListener listener : this.listeners) {
					listener.colorDetected(colorid);
				}
			}
		}
	}
}
