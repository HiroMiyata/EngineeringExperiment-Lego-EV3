package jp.ac.kanazawait.ep.mmotoki.abst;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

/***
 * <h1>ログを取得するための抽象クラス</h1>
 *
 * <p>デフォルトでは，10ms毎に最大120秒間，ログの記録を行う．
 * 記録する内容は，カラーセンサーの測定値とモーターの速度設定である</p>
 * <h2>使用方法</h2>
 * <ol>
 * <li>使用する前に，setCheckerメソッドとsetDriverメソッドを実行し，
 *     checkerColorSenserとdriverに適切なインスタンスを設定</li>
 * <li>ログ記録用メモリを確保・初期化するため，initLog()メソッドを実行</li>
 * <li>ログ記録を開始するにはstart()メソッドを実行</li>
 * <li>ログ記録を終了するにはstopThread()methodを実行</li>
 * <li>ログ記録終了後に，writeLog(ファイル名) を実行し，
 *     ログをファイルに書き出す</li>
 * @author mmotoki
 *
 */
public abstract class AbstEV3Logger extends Thread {
	/**
	 * センサーチェック間隔 (ms)
	 * publicなので，値を直接読み書きできる
	 */
	public int interval = 10;

	/**
	 * ログ記録最大時間 (s)
	 * publicなので，値を直接読み書きできる
	 */
	public int logDurationTime = 120;

	/**
	 * ログ記録の際のカラーセンサーチェッカ－
	 * setCheckerメソッドで設定すること
	 */
	protected AbstCheckerColorSensor checkerColorSenser;

	/**
	 * ログ記録の際のドライバー
	 * setDriverメソッドで設定すること
	 */
	protected AbstMotorDriver driver;

	/**
	 * ログ記録スレッドがアクティブかどうか
	 * 停止するためにはこの値をfalseとする
	 */
	protected boolean isActive = false;

	/**
	 * ログ開始時刻
	 */
	protected long starttime;

	/**
	 *
	 */
	protected int maxSize;

	/**
	 * 現在のログの長さ
	 */
	protected int currentLength = 0;

	/**
	 * 時間ログ記録用配列
	 */
	protected long[] dataTime;

	/**
	 * センサーデータログ記録用配列
	 */
	protected float[][] dataColorSensor;

	/**
	 * モーターデータログ記録用配列
	 */
	protected int[][] dataMotor;

	/**
	 * カラーセンサーチェッカースレッドを設定する
	 * @param checkerColorSensor	使用するカラーセンサーチェッカーのインスタンス
	 */
	public void setChecker(AbstCheckerColorSensor checkerColorSensor) {
		this.checkerColorSenser = checkerColorSensor;
	}

	/**
	 * モータードライバーを設定する
	 * @param driver	使用するAbstMotorDriverのサブクラスのインスタンス
	 */
	public void setDriver(AbstMotorDriver driver) {
		this.driver = driver;
	}

	/**
	 * ログ初期化
	 */
	public void initLog() {
		this.maxSize = this.logDurationTime * 1000 / this.interval;
		// 配列確保
		this.allocateArray();
	}

	/**
	 * ログ記録スレッドの内容
	 */
	public void run() {
		// ログ記録開始
		this.isActive = true;
		this.starttime = System.currentTimeMillis();
		while(this.isActive && this.currentLength < this.maxSize) {
			// データ保存
			saveToLog(this.currentLength);
			// データ数インクリメント
			this.currentLength++;
			// ディレイ
			Delay.msDelay(this.interval);
		}
		if(this.isActive) {
			this.isActive = false;
		}
	}

	/**
	 * スレッドの停止
	 */
	public void stopThread() {
		this.isActive = false;

	}

	/**
	 * CSVにログを書き込む
	 * @param filename	ファイル名
	 */
	public void writeToCSV(String filename) {
		File file = new File(filename);
		BufferedWriter bw;
		LCD.drawString("Writing Log:", 0, 4);
		LCD.drawString("/" + currentLength, String.valueOf(currentLength).length(), 5);
		try {
			bw = new BufferedWriter(new FileWriter(file));
			// ヘッダ書き込み
			this.writeHeadder(bw);
			// データ書き込み
			for(int i = 0; i < this.currentLength; i++) {
				LCD.drawInt(i, String.valueOf(currentLength).length(), 0, 5);
				this.writeData(bw, i);
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * maxSize分の配列確保
	 */
	protected abstract void allocateArray();

	/**
	 * currentLength番目の測定値を配列に保存する
	 * @param currentLength
	 */
	protected abstract void saveToLog(final int currentLength);

	/**
	 * CSVにヘッダを書き込む
	 * @param bw	書き込み先のBufferedWriterのインスタンス
	 * @throws IOException
	 */
	protected abstract void writeHeadder(BufferedWriter bw) throws IOException;

	/**
	 * CSVにデータを書き込む
	 * @param bw	書き込み先のBufferedWriterのインスタンス
	 * @param index 書き込むデータのインデックス
	 * @throws IOException
	 */
	protected abstract void writeData(BufferedWriter bw, int index) throws IOException;


}
