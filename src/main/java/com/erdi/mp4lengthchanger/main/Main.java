package com.erdi.mp4lengthchanger.main;

import com.erdi.mp4lengthchanger.LengthChanger;

public class Main {
	public static void main(String[] args) {
		new Thread(new LengthChanger()).start();
	}
}
