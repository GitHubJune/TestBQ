package com.ibaiqiu.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;  
 
public class DataConnection {  
 
   static int lport = 5353;//本地端口  
   static String rhost = "rm-vy12uinfci570vvt9.mysql.rds.aliyuncs.com";//远程MySQL服务器  
   static int rport = 3306;//远程MySQL服务端口  
 
   public static Session connectSSH() {  
       String user = "root";//SSH连接用户名  
       String password = "W=SQ\"jOBpRV*avOYLJuf";//SSH连接密码  
       String host = "121.199.172.239";//SSH服务器  
       int port = 22;//SSH访问端口  
       Session session = null;
       try {  
           JSch jsch = new JSch();  
           session = jsch.getSession(user, host, port);  
           session.setPassword(password);  
           session.setConfig("StrictHostKeyChecking", "no");  
           session.connect();  
           //System.out.println(session.getServerVersion());//这里打印SSH服务器版本信息  
           int assinged_port = session.setPortForwardingL(lport, rhost, rport);  
           System.out.println("localhost:" + assinged_port + " -> " + rhost + ":" + rport);  
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
       return session;
   }  
     
   /**
    * connect elle tmall
    * @return
    */
   public static Connection connectSQL() {  
       Connection conn = null;  
       try {  
           Class.forName("com.mysql.jdbc.Driver");  
           conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:5353/sys_info_2", "bqiter_14", "omn3it@r_14");  
       } catch (Exception e) {  
           e.printStackTrace();  
       }
       return conn;
   }  
   
   /**
    * connect clarks jd
    * @return
    */
   public static Connection connectClarksSQL() {
	   Connection conn = null;  
       try {  
           Class.forName("com.mysql.jdbc.Driver");  
           conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:5353/ims_clarks", "bqiter_14", "omn3it@r_14");  
       } catch (Exception e) {  
           e.printStackTrace();  
       }
       return conn;
   }
}
