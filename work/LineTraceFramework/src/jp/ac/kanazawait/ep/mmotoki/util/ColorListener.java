package jp.ac.kanazawait.ep.mmotoki.util;

public interface ColorListener {

	/**
	 * coloridの検出を通知された時に行う動作を実装するメソッド
	 * @param colorid 通知されたカラーID
	 */
	public void colorDetected(int colorid);
}
