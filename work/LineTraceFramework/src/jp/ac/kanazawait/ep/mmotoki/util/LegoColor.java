package jp.ac.kanazawait.ep.mmotoki.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import lejos.robotics.Color;

public class LegoColor {
	/**
	 * カラーIDと色の名前の対応を保存するHashMap
	 * staticフィールドなので，このクラスのインスタンスを作成しなくても使用可能
	 */
	public static final Map<Integer, String> colorNames = getColorName();

	/**
	 * カラーIDから色の名前を取得するメソッド
	 * クラスメソッドなので，このクラスのインスタンスを作成しなくても使用可能
	 * @param id	int: カラーID
	 * @return		String: 対応する色の名前
	 */
	public static String colorId2Name(int id) {
		return LegoColor.colorNames.get(id) == null ? "NULL" : LegoColor.colorNames.get(id);
	}

	/**
	 * lejos.robotics.Colorクラスのstaticフィールドとして定義されているカラーIDと色の名前の対応表を作成するメソッド．
	 * @return	キーがカラーID，値が色の名前であるHashMapオブジェクト
	 */
	private static Map<Integer, String> getColorName() {
		/**
		 * カラーIDと色の名前の対応表
		 */
		HashMap<Integer, String> colorIdMap = new HashMap<>();

		/**
		 * 色の名前の最大長
		 */
		int maxLength = 0;

		// lejos.robotics.Colorクラスで宣言されたフィールドの取得
		Field[] declaredFields = Color.class.getDeclaredFields();

		// 色の名前の最大長の取得
		for(Field field : declaredFields) {
			// staticフィールドだけを取り出す
			if(java.lang.reflect.Modifier.isStatic(field.getModifiers()) ) {
				if(maxLength < field.getName().length())
					maxLength = field.getName().length();
			}
		}

		for(Field field : declaredFields) {
			// staticフィールドだけを取り出す
			if(java.lang.reflect.Modifier.isStatic(field.getModifiers()) ) {
				// 色番号をキーに，フィールド（色）の名前を値にしてHashMapに挿入
				// 色の名前は，最大長に合わせて空白をパディング
				try {
					colorIdMap.put(field.getInt(null), String.format("%-" + Integer.toString(maxLength) + "s", field.getName()));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		return colorIdMap;
	}

}
