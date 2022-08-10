package view;

import java.awt.Font;

public class TankMopperFont {

	protected static final String FONT_NAME = "Comic Sans MS";

	public static Font getBoldFont(int pointSize) {
		return new Font(FONT_NAME, Font.BOLD, pointSize);
	}

}
