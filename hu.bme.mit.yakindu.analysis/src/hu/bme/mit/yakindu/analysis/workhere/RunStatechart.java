package hu.bme.mit.yakindu.analysis.workhere;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;



public class RunStatechart {
	
	public static void main(String[] args) throws IOException {
//		ExampleStatemachine s = new ExampleStatemachine();
//		s.setTimer(new TimerService());
//		RuntimeService.getInstance().registerStatemachine(s, 200);
//		s.init();
//		s.enter();
//		s.runCycle();
//		print(s);
//		s.raiseStart();
//		s.runCycle();
//		System.in.read();
//		s.raiseWhite();
//		s.runCycle();
//		print(s);
//		System.exit(0);
		
		ExampleStatemachine esm = new ExampleStatemachine();
		esm.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(esm, 200);
		esm.init();esm.enter();esm.runCycle();
		while(true) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {String input = br.readLine();
				if (input.equals("exit")) {System.exit(0);}
				else {switch (input) {
						case "start": { esm.raiseStart(); esm.runCycle(); break; } 
						case "white": { esm.raiseWhite(); esm.runCycle(); break; }
						case "black": { esm.raiseBlack(); esm.runCycle(); break; }
						default: break;}}
				print(esm); } catch (IOException e) {e.printStackTrace();}
		}
	}

	public static void print(IExampleStatemachine s) {
		System.out.println("W = " + s.getSCInterface().getWhiteTime());
		System.out.println("B = " + s.getSCInterface().getBlackTime());
	}
}
