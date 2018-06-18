package jp.ac.kanazawait.ep.hironorimiyata;

import jp.ac.kanazawait.ep.mmotoki.abst.AbstCar;
import jp.ac.kanazawait.ep.mmotoki.driver.SimpleDriver;
import jp.ac.kanazawait.ep.mmotoki.navigator.SimpleLeftEdgeTracer;
import jp.ac.kanazawait.ep.mmotoki.util.CheckerColorSensor;
import lejos.hardware.Button;
import lejos.robotics.Color;

public class MiyataCar extends AbstCar {
	public MiyataCar() {
		this.checkerColorSensor = CheckerColorSensor.getInstance();
		this.driver = new SimpleDriver("B", "C");
		this.navigator = new SimpleLeftEdgeTracer();
	}

	@Override
	public void run() {
		this.start();
		int sen = 0;
		while(!Button.ESCAPE.isDown() && this.checkerColorSensor.getColorId() != Color.RED) {
			
			if(sen % 2 == 0 ) {
				this.navigator = new RightEdgeTracer();
			} else {
				this.navigator = new SimpleLeftEdgeTracer();
			}
			
			if(Button.ENTER.isDown()) {
				sen++;
			}
			this.navigator.decision(this.checkerColorSensor, this.driver);
		}
		this.stop();
	}

	public static void main(String[] args) {
		MiyataCar car = new MiyataCar();
		car.run();
	}

	

}
