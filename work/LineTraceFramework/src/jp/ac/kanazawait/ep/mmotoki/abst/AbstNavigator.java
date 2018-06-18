package jp.ac.kanazawait.ep.mmotoki.abst;

public abstract class AbstNavigator {
	/**
	 * 走行戦略を具体化するメソッド
	 * @param sensor	センサー
	 * @param driver	走行のための具象クラス
	 */
	public abstract void decision(AbstCheckerColorSensor checkerColorSensor, AbstMotorDriver driver);
}
