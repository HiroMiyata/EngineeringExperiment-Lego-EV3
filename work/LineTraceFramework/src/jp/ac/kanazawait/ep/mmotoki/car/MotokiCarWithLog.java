package jp.ac.kanazawait.ep.mmotoki.car;

import jp.ac.kanazawait.ep.mmotoki.abst.AbstCar;
import jp.ac.kanazawait.ep.mmotoki.driver.SimpleDriver;
import jp.ac.kanazawait.ep.mmotoki.navigator.SimpleLeftEdgeTracer;
import jp.ac.kanazawait.ep.mmotoki.util.CheckerColorSensor;
import jp.ac.kanazawait.ep.mmotoki.util.EV3Logger;
import lejos.hardware.Button;
import lejos.robotics.Color;

public class MotokiCarWithLog extends AbstCar {

	public MotokiCarWithLog() {
		this.checkerColorSensor = CheckerColorSensor.getInstance();
		this.driver = new SimpleDriver("B", "C");
		this.navigator = new SimpleLeftEdgeTracer();
		// 20180501追加
		this.logger = EV3Logger.getInstance();
		this.logger.setChecker(checkerColorSensor);
		this.logger.setDriver(driver);
		// Car.java
	}

	@Override
	public void run() {
		this.start();
		while(!Button.ESCAPE.isDown() && this.checkerColorSensor.getColorId() != Color.RED) {
			this.navigator.decision(this.checkerColorSensor, this.driver);
		}
		this.stop();
	}

	public static void main(String[] args) {
		MotokiCarWithLog car = new MotokiCarWithLog();
		car.run();
	}

}
