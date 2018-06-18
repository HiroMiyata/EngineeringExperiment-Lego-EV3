package jp.ac.kanazawait.ep.mmotoki.util;

import java.io.BufferedWriter;
import java.io.IOException;

import jp.ac.kanazawait.ep.mmotoki.abst.AbstEV3Logger;

/***
 * <h1>ログを取得するためのクラス</h1>
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

public class EV3Logger extends AbstEV3Logger {
	/**
	 * Singletonパターンとするための，唯一のインスタンス
	 */
	private static EV3Logger instanceEV3Logger = new EV3Logger();

	/**
	 * インスタンスを取得するクラスメソッド
	 * @return	Singletonパターンによる唯一のインスタンス
	 */
	public static EV3Logger getInstance() {
		return EV3Logger.instanceEV3Logger;
	}

	/**
	 * コンストラクタ
	 * singletonパターンを適用するため，
	 * 他からは呼び出せないprivate属性で宣言
	 */
	private EV3Logger() {
		// no action required
	}


	@Override
	protected void allocateArray() {
		this.dataTime = new long[this.maxSize];
		this.dataColorSensor = new float[this.maxSize][];
		this.dataMotor = new int[this.maxSize][2];
	}

	@Override
	protected void saveToLog(final int currentLength) {
		this.dataTime[this.currentLength] = System.currentTimeMillis();
		this.dataColorSensor[this.currentLength] = this.checkerColorSenser.getAll();
		this.dataMotor[this.currentLength][0] = this.driver.getSpeedLeft();
		this.dataMotor[this.currentLength][1] = this.driver.getSpeedRight();
	}

	@Override
	protected void writeHeadder(BufferedWriter bw) throws IOException{
		bw.write("time, ColorID, Color, Reflected, R, G, B, RGBTotal, speedLeft, speedRight");
		bw.newLine();
	}

	@Override
	protected void writeData(BufferedWriter bw, int index) throws IOException {
		// 経過時間
		bw.write((this.dataTime[index] - this.starttime) + ", ");
		// カラーID
		int colorId = (int) this.dataColorSensor[index][0];
		bw.write(colorId + ", ");
		bw.write(LegoColor.colorId2Name(colorId) + ", ");
		// その他カラーセンサーの値
		for(int j = 1; j < this.dataColorSensor[j].length; j++) {
			bw.write(this.dataColorSensor[index][j] + ", ");
		}
		bw.write((this.dataColorSensor[index][2] + this.dataColorSensor[index][3] + this.dataColorSensor[index][4])
				+ ", ");
		// モーター
		bw.write(this.dataMotor[index][0] + ", " + this.dataMotor[index][1]);

		// １行分の最後の改行
		bw.newLine();
	}

}
