package jp.ac.kanazawait.ep.mmotoki.util;

/**
 * 色を検出したときに通知するためのインタフェース
 * ColorListenerインタフェースと対になる．
 * 実装例：
 * 	private ArrayList<ColorListener> listeners = null;

	@Override
	public void addColorListener(ColorListener listener) {
		if(this.listeners == null) {
			this.listeners = new ArrayList<ColorListener>();
		}
		this.listeners.add(listener);
	}

	@Override
	public void callListeners() {
		// TODO: coloridの取得と通知するかどうかの場合分け
		for(ColorListener listener : this.listeners) {
			listener.colorDetected(colorid);
		}
	}
 * @author mmotoki
 *
 */
public interface ColorFinder {

	/**
	 * リスナーをリスナーリストに追加するメソッド
	 * @param listener
	 */
	public void addColorListener (ColorListener listener);

	/**
	 * リスナーにイベントを通知するメソッド．
	 * 登録されている各リスナーのcolorDetectedメソッドを呼び出す．
	 */
	public void callListeners();


}
