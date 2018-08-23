package com.ibaiqiu.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

public class TradeThread implements Runnable {

	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Connection con = Utils.getConn();
		getTrade(con);
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static List<String> list_barcode = new ArrayList<>();
	static String status = "WAIT_SELLER_SEND_GOODS";
	static String seller_nick = "";
	static String buyer_nick = "";
	static String type = "fixed";
	static String jdp_response = null;
	private static void getTrade(Connection con) {		
		try {
			long time1 = System.currentTimeMillis();
			String sql = "select * from jdp_tb_trade order by jdp_modified desc limit 1";
			String barcode = "select * from barcode limit 100";
			PreparedStatement ps_barcode = con.prepareStatement(barcode);
			
			ResultSet rs_barcode = ps_barcode.executeQuery();
			while(rs_barcode.next()) {
				list_barcode.add(rs_barcode.getString("barcode"));
			}
			PreparedStatement ps_trade = con.prepareStatement(sql);
			ResultSet rs_trade = ps_trade.executeQuery();
			while (rs_trade.next()) {
				jdp_response = rs_trade.getString("jdp_response");
				
			}
			rs_barcode.close();
			rs_trade.close();
			long time2 = System.currentTimeMillis();
			System.out.println("基础信息耗时："+(time2-time1));
			
			long time3 = System.currentTimeMillis();
			String insertSQl = "INSERT INTO jdp_tb_trade_Jessica(tid,status,type,buyer_nick,seller_nick,created,modified,"
					+ "jdp_hashcode,jdp_response,jdp_created,jdp_modified) VALUES(?,?,?,?,?,?,?,?,?,?,?)";		
			PreparedStatement ps = con.prepareStatement(insertSQl);
			con.setAutoCommit(false);
			for (int i = 0; i < 10000; i++) {
				String new_jdp_response = jdp_response(jdp_response);
				ps.setString(1, tid());ps.setString(2, status);
				ps.setString(3, type);ps.setString(4, buyer_nick);
				ps.setString(5, seller_nick);ps.setTimestamp(6, Utils.sqlDate());
				ps.setTimestamp(7, Utils.sqlDate());
				ps.setString(8, Utils.hashcode());ps.setString(9,new_jdp_response);
				ps.setTimestamp(10, Utils.sqlDate());
				ps.setTimestamp(11, Utils.sqlDate());
				ps.addBatch();
			}

			ps.executeBatch();
			con.commit();
			long time4 = System.currentTimeMillis();
			System.out.println("插入销售信息耗时："+(time4-time3));
		}  catch (SQLException e) {
			// 数据库连接失败异常处理
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * 修改response数据
	 * @param jdp_response
	 * @return
	 */
	private static String jdp_response(String jdp_response) {
		JSONObject obj = JSONObject.fromObject(jdp_response);
		JSONObject trade = obj.getJSONObject("trade_fullinfo_get_response").getJSONObject("trade");
		String trade_tid = trade_tid();
		trade.put("tid", trade_tid);
		trade.put("tid_str", trade_tid);
		trade.put("status", status);
		trade.put("pay_time", Utils.date());
		JSONObject orders = trade.getJSONObject("orders");
//		System.out.println(orders);
		JSONObject orderone = (JSONObject) orders.getJSONArray("order").get(0);
		String trade_oid = trade_tid();
		orderone.put("oid", trade_oid);
		orderone.put("oid_str", trade_oid);
		int n = (int)(Math.random()*100);
		orderone.put("outer_sku_id", list_barcode.get(n));
		orderone.put("title", trade.get("seller_nick")+list_barcode.get(n));
		orderone.put("status", status);
		buyer_nick = trade.getString("buyer_nick");
		seller_nick = trade.getString("seller_nick");
		return obj.toString();
	}
	
	//tid
	private static String tid() {
		return Utils.random();
	}
	
	//交易tid
	private static String trade_tid() {
		return Utils.random();
	}
}
