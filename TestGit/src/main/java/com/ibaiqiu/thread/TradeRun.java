package com.ibaiqiu.thread;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.ibaiqiu.utils.DataConnection;
import com.jcraft.jsch.Session;

public class TradeRun {

	public static void main(String[] args) throws InterruptedException {
		Session session = DataConnection.connectSSH();
		Connection con = DataConnection.connectSQL();
		
		
		ExecutorService exec = Executors.newCachedThreadPool();
		long startTime = System.currentTimeMillis();
//		TradeThread.setCon(con);
		for (int i = 0; i < 5; i++) {
			exec.execute(new TradeThread(con));
		}
		exec.shutdown();
		exec.awaitTermination(1, TimeUnit.HOURS);
		
		try {
			con.close();
			session.disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("finally total used time："+(endTime-startTime));
		
		/*Thread th1 = new Thread(new TradeThread());
		Thread th2 = new Thread(new TradeThread());
		th1.start();
		th2.start();*/
	}
}
