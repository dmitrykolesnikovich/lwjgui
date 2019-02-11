package lwjgui.theme;

import lwjgui.Color;

public abstract class Theme {
	public abstract Color getBackground();
	public abstract Color getPane();
	public abstract Color getSelection();
	public abstract Color getSelectionAlt();
	public abstract Color getSelectionPassive();
	public abstract Color getShadow();
	public abstract Color getText();
	public abstract Color getControl();
	public abstract Color getControlAlt();
	public abstract Color getControlHover();
	public abstract Color getControlOutline();
	
	private static Theme currentTheme = new ThemeWhite();
	
	public static Theme currentTheme() {
		return currentTheme;
	}
	public static void setTheme(Theme theme) {
		currentTheme = theme;
	}
}