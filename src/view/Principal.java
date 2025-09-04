package view;

import controller.PingThread;

public class Principal {

	public static void main(String[] args) {
		
		PingThread t1 = new PingThread("UOL", "www.uol.com.br");
		PingThread t2 = new PingThread("Terra", "www.terra.com.br");
		PingThread t3 = new PingThread("Google", "www.google.com.br");
		
		t1.start();
		t2.start();
		t3.start();
	}

}
