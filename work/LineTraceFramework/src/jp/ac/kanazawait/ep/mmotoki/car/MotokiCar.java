package jp.ac.kanazawait.ep.mmotoki.car;

import jp.ac.kanazawait.ep.mmotoki.abst.AbstCar;
import jp.ac.kanazawait.ep.mmotoki.driver.SimpleDriver;
import jp.ac.kanazawait.ep.mmotoki.navigator.SimpleLeftEdgeTracer;
import jp.ac.kanazawait.ep.mmotoki.util.CheckerColorSensor;
import lejos.hardware.Button;
import lejos.robotics.Color;

public class MotokiCar extends AbstCar {

	public MotokiCar() {
		this.checkerColorSensor = CheckerColorSensor.getInstance();
		this.driver = new SimpleDriver("B", "C");
		this.navigator = new SimpleLeftEdgeTracer();
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
		MotokiCar car = new MotokiCar();
		car.run();
	}

}
