package com.zealouscoder.grow;

public class ButtonEvent {

	private int keyStatus;
	private int keyCode;

	public ButtonEvent(int status, int code) {
		this.keyStatus = status;
		this.keyCode = code;
	}

	public int getKeyStatus() {
		return keyStatus;
	}

	public int getKeyCode() {
		return keyCode;
	}
	

}
