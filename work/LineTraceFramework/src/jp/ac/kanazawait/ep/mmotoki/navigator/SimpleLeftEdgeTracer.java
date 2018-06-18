package jp.ac.kanazawait.ep.mmotoki.navigator;

import jp.ac.kanazawait.ep.mmotoki.abst.AbstCheckerColorSensor;
import jp.ac.kanazawait.ep.mmotoki.abst.AbstMotorDriver;
import jp.ac.kanazawait.ep.mmotoki.abst.AbstNavigator;
import lejos.robotics.Color;

public class SimpleLeftEdgeTracer extends AbstNavigator {

	@Override
	public void decision(AbstCheckerColorSensor checkerColorSensor, AbstMotorDriver driver) {
		switch (checkerColorSensor.getColorId()) {
		case Color.WHITE:
			driver.turnRight();
			driver.forward();
			break;
		case Color.BLACK:
			driver.turnLeft();
			driver.forward();
			break;
		default:
			driver.goStraight();
			driver.forward();
			break;
		}
	}

}
