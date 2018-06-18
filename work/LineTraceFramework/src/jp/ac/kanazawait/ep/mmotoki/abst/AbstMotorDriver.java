package jp.ac.kanazawait.ep.mmotoki.abst;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.RegulatedMotor;


/**
 * モーターを
 * @author mmotoki
 *
 */
public abstract class AbstMotorDriver {
	/**
	 * 左車輪駆動モーター
	 */
	RegulatedMotor wheelLeft;
	/**
	 * 右車輪駆動モーター
	 */
	RegulatedMotor wheelRight;

	/**
	 * 直進状態に設定
	 */
	public abstract void goStraight();

	/**
	 * 直進状態に設定
	 */
	public void goStraight(int speed)
	{
		this.setSpeed(speed);
	}

	/**
	 * 左に曲がる状態に設定
	 */
	public abstract void turnLeft();

	/**
	 * 右に曲がる状態に設定
	 */
	public abstract void turnRight();

	/**
	 * 左にきつく曲がる状態に設定
	 */
	public abstract void turnLeftSharply();

	/**
	 * 右にきつく曲がる状態に設定
	 */
	public abstract void turnRightSharply();

	/**
	 * 左にゆるく曲がる状態に設定
	 */
	public abstract void turnLeftGently();

	/**
	 * 右にゆるく曲がる状態に設定
	 */
	public abstract void turnRightGently();

	/**
	 * スピードを上げる
	 */
	public abstract void increaseSpeed();

	/**
	 * スピードを下げる
	 */
	public abstract void decreaseSpeed();

	/**
	 * 前進開始
	 * 後退している時に後退させたいときは，一度stop()せよ
	 */
	public void forward() {
		if(!this.wheelLeft.isMoving()) {
			this.wheelLeft.forward();
		}
		if(!this.wheelRight.isMoving()) {
			this.wheelRight.forward();
		}
	}

	/**
	 * 後退開始
	 * 前進している時に後退させたいときは，一度stop()せよ
	 */
	public void backward() {
		if(!this.wheelLeft.isMoving()) {
			this.wheelLeft.backward();
		}
		if(!this.wheelRight.isMoving()) {
			this.wheelRight.backward();
		}
	}

	/**
	 * 停止
	 */
	public void stop() {
		this.wheelLeft.stop(true);
		this.wheelRight.stop(true);
	}

	/**
	 * モーターが動いているかチェックする
	 * @return	どちらかでもモーターが動いていれば true，そうでなければ false
	 */
	public boolean isMoving() {
		return this.wheelLeft.isMoving() || this.wheelRight.isMoving();
	}

	/**
	 * モーターが停止いるかチェックする
	 * @return	両方のモーターが停止していれば true，そうでなければ false
	 */
	public boolean isStalled() {
		return this.wheelLeft.isStalled() && this.wheelRight.isStalled();
	}

	/**
	 * ホイールを一定角度だけ回転
	 * @param angle	回転角度 (度)
	 */
	public void rotate(int angle) {
		this.wheelLeft.rotate(angle, true);
		this.wheelRight.rotate(angle);
	}

	// 20180430追加
	/**
	 * ホイールを一定角度だけ回転
	 * @param angle	回転角度 (度)
	 */
	public void rotate(int angleLeft, int angleRight) {
		this.wheelLeft.rotate(angleLeft, true);
		this.wheelRight.rotate(angleRight);
	}

	/**
	 * 左右のモーターに同じスピードを設定
	 * @param speed		設定するスピード (度/秒)
	 */
	public void setSpeed(int speed) {
		this.setSpeed(speed, speed);
	}

	/**
	 * 左右のモーターごとにスピードを設定
	 * @param speedLeft	左車輪モーターの回転速度 (度/秒)
	 * @param speedRight	左車輪モーターの回転速度 (度/秒)
	 */
	public void setSpeed(int speedLeft, int speedRight) {
		this.wheelLeft.setSpeed(speedLeft);
		this.wheelRight.setSpeed(speedRight);
	}

	/**
	 * モーターの回転速度を速い方に合わせる
	 */
	public void AdjustToFasterSpeed() {
		int speedLeft = this.wheelLeft.getSpeed();
		int speedRight = this.wheelRight.getSpeed();
		if(speedLeft < speedRight) {
			this.wheelLeft.setSpeed(speedRight);
		} else if(speedLeft > speedRight) {
			this.wheelRight.setSpeed(speedLeft);
		}
	}

	/**
	 * モーターの回転速度を遅い方に合わせる
	 */
	public void AdjustToSlowerSpeed() {
		int speedLeft = this.wheelLeft.getSpeed();
		int speedRight = this.wheelRight.getSpeed();
		if(speedLeft < speedRight) {
			this.wheelRight.setSpeed(speedLeft);
		} else if(speedLeft > speedRight) {
			this.wheelLeft.setSpeed(speedRight);
		}
	}

	/**
	 * 左右のモーターの回転速度を同じ差分で変化させる
	 * @param diff スピードの差分
	 */
	public void changeSpeedByDiff(int diff) {
		this.changeSpeedByDiff(diff, diff);
	}

	/**
	 * 左右のモーター毎にの回転速度を差分で変化させる
	 * @param diffLeft	左モーターのスピードの差分
	 * @param diffRight	右モーターのスピードの差分
	 */
	public void changeSpeedByDiff(int diffLeft, int diffRight) {
		this.wheelLeft.setSpeed(this.wheelLeft.getSpeed() + diffLeft);
		this.wheelRight.setSpeed(this.wheelRight.getSpeed() + diffRight);
	}

	/**
	 * 左右のモーターの回転速度を同じ倍率で変化させる
	 * @param diff スピードの倍率
	 */
	public void changeSpeedByRatio(double ratio) {
		this.changeSpeedByRatio(ratio, ratio);
	}

	/**
	 * 左右のモーター毎にの回転速度を倍率で変化させる
	 * @param ratioLeft
	 * @param ratioRight
	 */
	public void changeSpeedByRatio(double ratioLeft, double ratioRight) {
		this.wheelLeft.setSpeed((int) (this.getSpeedLeft() * ratioLeft));
		this.wheelRight.setSpeed((int) (this.getSpeedRight() * ratioLeft));
	}

	/**
	 * 左右のモーターの最大速度の遅い方を取得
	 * @return	左右のモーターの最大速度の遅い方 （度/秒)
	 */
	public float getMaxSpeed() {
		float maxSpeedLeft = this.wheelLeft.getMaxSpeed();
		float maxSpeedRight = this.wheelRight.getMaxSpeed();
		return maxSpeedLeft < maxSpeedRight ? maxSpeedLeft : maxSpeedRight;
	}

	/**
	 * 左右のモーターの速いほうの回転速度を取得
	 * @return	左右のモーターの回転速度の速い方 (度/秒)
	 */
	public int getFasterSpeed() {
		int speedLeft = this.getSpeedLeft();
		int speedRight = this.getSpeedRight();
		return speedLeft > speedRight ? speedLeft : speedRight;
	}

	/**
	 * 左右のモーターの遅いほうの回転速度を取得
	 * @return	左右のモーターの回転速度の遅い方 (度/秒)
	 */
	public int getSlowerSpeed() {
		int speedLeft = this.getSpeedLeft();
		int speedRight = this.getSpeedRight();
		return speedLeft < speedRight ? speedLeft : speedRight;
	}

	/**
	 * 左モーターの回転速度取得
	 * @return	左モーターの回転速度 (度/秒)
	 */
	public int getSpeedLeft() {
		return this.wheelLeft.getSpeed();
	}

	/**
	 * 右モーターの回転速度取得
	 * @return	右モーターの回転速度 (度/秒)
	 */
	public int getSpeedRight() {
		return this.wheelRight.getSpeed();
	}

	/**
	 * 直進設定かどうかを調べる
	 * @return	直進ならtrue
	 */
	public boolean isStraight() {
		return wheelLeft.getSpeed() == wheelRight.getSpeed();
	}

	/**
	 * 左に曲がる設定かどうかを調べる
	 * @return	左に曲がる設定ならtrue
	 */
	public boolean isTurnLeft() {
		return wheelLeft.getSpeed() < wheelRight.getSpeed();
	}

	/**
	 * 右に曲がる設定かどうかを調べる
	 * @return	右に曲がる設定ならtrue
	 */
	public boolean isTurnRight() {
		return wheelLeft.getSpeed() > wheelRight.getSpeed();
	}

	/**
	 * モーターを設定する
	 * @param portLeft	左車輪モーターを接続したモーターポート名 "A"～"D"
	 * @param portRight	右車輪モーターを接続したモーターポート名 "A"～"D"
	 */
	public void setMotor(String portLeft, String portRight) {
		wheelLeft = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(portLeft));
		wheelRight = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(portRight));
	}

	/**
	 * モーターを設定する
	 * @param leftMotor	左車輪モーターを接続したEV3LargeReguratedMotorのインスタンス
	 * @param rightMotor	右車輪モーターを接続したEV3LargeReguratedMotorのインスタンス
	 */
	public void setMotor(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor) {
		wheelLeft = leftMotor;
		wheelRight = rightMotor;
	}

}
