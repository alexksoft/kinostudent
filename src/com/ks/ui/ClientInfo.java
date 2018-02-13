package com.ks.ui;

import java.io.Serializable;
import java.util.TimeZone;

public class ClientInfo implements Serializable {
	public int colorDepth;
	public int desktopWidth;
	public int desktopHeight;
	public int desktopXOffset;
	public int desktopYOffset;
	public int screenHeight;
	public int screenWidth;
	public TimeZone timeZone;
}
