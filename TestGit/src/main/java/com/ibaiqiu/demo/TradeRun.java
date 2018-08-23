package com.ibaiqiu.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TradeRun {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 2; i++) {
			exec.execute(new TradeThread());
		}
		exec.shutdown();
		exec.awaitTermination(1, TimeUnit.HOURS);
		long endTime = System.currentTimeMillis();
		System.out.println("总耗时："+(endTime-startTime));
		
		/*Thread th1 = new Thread(new TradeThread());
		Thread th2 = new Thread(new TradeThread());
		th1.start();
		th2.start();*/
	}
}
