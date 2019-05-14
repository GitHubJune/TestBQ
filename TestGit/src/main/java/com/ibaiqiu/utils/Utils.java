package com.ibaiqiu.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Utils {

	/**
	 * 连接数据库
	 * @return
	 */
	public static Connection getConn() {
		// 声明Connection对象
		Connection con = null;
		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名mydata
		String url = "jdbc:mysql://192.168.0.63:3306/sys_info";
//		String url = "jdbc:mysql://192.168.0.63:3306/ceshi";
		// MySQL配置时的用户名
		String user = "xubs";
		// MySQL配置时的密码
		String password = "xubs";
		try {
			// 加载驱动程序
			Class.forName(driver);
			// 1.getConnection()方法，连接MySQL数据库！！
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// 数据库驱动类异常处理
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			// 数据库连接失败异常处理
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return con;
	}
	
	public static String date() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(System.currentTimeMillis());
		return currentTime;
	}

	public static Timestamp sqlDate() {
		java.util.Date date = new java.util.Date(); // 获取一个Date对象
		Timestamp timeStamp = new Timestamp(date.getTime());
		return timeStamp;
	}

	public static String hashcode() {
		// 7位数
		String jdp_hashcode = String.valueOf((int) ((Math.random() * 9 + 1) * 1000000));
		return jdp_hashcode;
	}

	// 15位随机数
	public static String random() {
		String random = "";
		int a[] = new int[18];
		for (int i = 0; i < a.length; i++) {
			a[i] = (int) (10 * (Math.random()));
			random = random + a[i];
		}
		return "1"+random;
	}
	
	//5位数
	public static int IntID() {
		return (int) ((Math.random() * 9 + 1) * 10000);
	}
	
	public static void main(String[] args) {
//		System.out.println( (int) ((Math.random() * 9 + 1) * 1000));
		/*String random = "";
		int a[] = new int[15];
		for (int i = 0; i < a.length; i++) {
			a[i] = (int) (10 * (Math.random()));
			random = random + a[i];
		}
		System.out.println(random);*/
		for (int i = 0; i < 10; i++) {
			System.out.println(random());
		}
		
	}
}
