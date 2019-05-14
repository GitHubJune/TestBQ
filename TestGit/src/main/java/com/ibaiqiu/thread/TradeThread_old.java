package com.ibaiqiu.thread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibaiqiu.demo.BarCode;
import com.ibaiqiu.utils.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TradeThread_old implements Runnable {

	static List<BarCode> list_barcode = new ArrayList<BarCode>();
	static String status = "WAIT_SELLER_SEND_GOODS";
	static String seller_nick = "";
	static String buyer_nick = "";
	static String type = "fixed";
	static String jdp_response = null;
	static String tid = "";
	static String oid = "";
	static int num = 1;
	static boolean isMoreGood = false;
	
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

	private static void getTrade(Connection con) {		
		try {
			long time1 = System.currentTimeMillis();
			String sql = "select * from jdp_tb_trade_Jessica_copy where tid = '221725346132634099'";
			String barcode = "select * from barcode_elle_Jessica limit 100";
			PreparedStatement ps_barcode = con.prepareStatement(barcode);
			
			ResultSet rs_barcode = ps_barcode.executeQuery();
			while(rs_barcode.next()) {
				BarCode bc = new BarCode();
				bc.setBarcode(rs_barcode.getString("barcode"));
				bc.setNum_iid(rs_barcode.getLong("num_iid"));
				list_barcode.add(bc);
			}
			PreparedStatement ps_trade = con.prepareStatement(sql);
			ResultSet rs_trade = ps_trade.executeQuery();
			while (rs_trade.next()) {
				jdp_response = rs_trade.getString("jdp_response");
			}
			rs_barcode.close();
			rs_trade.close();
			long time2 = System.currentTimeMillis();
			System.out.println("base info used time："+(time2-time1));
			
			long time3 = System.currentTimeMillis();
			String insertSQl = "INSERT INTO jdp_tb_trade_Jessica(tid,status,type,buyer_nick,seller_nick,created,modified,"
					+ "jdp_hashcode,jdp_response,jdp_created,jdp_modified) VALUES(?,?,?,?,?,?,?,?,?,?,?)";		
			PreparedStatement ps = con.prepareStatement(insertSQl);
			con.setAutoCommit(false);
			for (int i = 0; i < 10000; i++) {
				tid = trade_tid();
				String new_jdp_response = jdp_response(jdp_response);
				ps.setString(1, tid);ps.setString(2, status);
				ps.setString(3, type);ps.setString(4, buyer_nick);
				ps.setString(5, seller_nick);ps.setTimestamp(6, Utils.sqlDate());
				ps.setTimestamp(7, Utils.sqlDate());
				ps.setString(8, Utils.hashcode());ps.setString(9,new_jdp_response);
				ps.setTimestamp(10, Utils.sqlDate());
				ps.setTimestamp(11, Utils.sqlDate());
				ps.addBatch();
				/*if(i%1000==0){
					ps.executeBatch();
					ps.clearBatch();
				}*/
			}

			ps.executeBatch();
			con.commit();
			long time4 = System.currentTimeMillis();
			System.out.println("insert data used time："+(time4-time3));
		}  catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * update response data
	 * @param jdp_response
	 * @return
	 */
	private static String jdp_response(String jdp_response) {
		JSONObject obj = JSONObject.fromObject(jdp_response);
		JSONObject trade = obj.getJSONObject("trade_fullinfo_get_response").getJSONObject("trade");
		trade.put("tid", tid);
		trade.put("tid_str", tid);
		trade.put("status", status);
		trade.put("pay_time", Utils.date());
		/*update money*/
		trade.put("price", Double.parseDouble(trade.getString("price"))*num);
		trade.put("total_fee", Double.parseDouble(trade.getString("total_fee"))*num);
		trade.put("payment", Double.parseDouble(trade.getString("payment"))*num);
		JSONArray order = trade.getJSONObject("orders").getJSONArray("order");
		if(isMoreGood) {
			trade.remove("num_iid");trade.remove("num");
			packageMoreGoods(order);
		}else {
			int n = (int)(Math.random()*100);
			trade.put("num_iid", list_barcode.get(n).getNum_iid());
			trade.put("num", num);
			JSONObject orderone = (JSONObject) order.get(0);
			orderone.put("oid", tid);
			orderone.put("oid_str", tid);
			orderone.put("outer_sku_id", list_barcode.get(n).getBarcode());
			orderone.put("num_iid", list_barcode.get(n).getNum_iid());
			orderone.put("title", trade.get("seller_nick")+list_barcode.get(n).getBarcode());
			orderone.put("status", status);
			orderone.put("num", num);
			orderone.put("payment", Double.parseDouble(orderone.getString("payment"))*num);
			orderone.put("price", Double.parseDouble(orderone.getString("price"))*num);
			orderone.put("total_fee", Double.parseDouble(orderone.getString("total_fee"))*num);
		}
		buyer_nick = trade.getString("buyer_nick");
		seller_nick = trade.getString("seller_nick");
		return obj.toString();
	}
	

	//more goods
		private static void packageMoreGoods(JSONArray order) {
			JSONObject orderone = order.getJSONObject(0);
			JSONObject ordertwo = order.getJSONObject(0);
			order.add(ordertwo);
			ordertwo = order.getJSONObject(1);
			String trade_oid_1 = trade_tid();
			orderone.put("oid", trade_oid_1);
			orderone.put("oid_str", trade_oid_1);
			int n = (int)(Math.random()*10);
			orderone.put("outer_sku_id", list_barcode.get(n).getBarcode());
			orderone.put("num_iid", list_barcode.get(n).getNum_iid());
//			orderone.put("title", trade.get("seller_nick")+list_barcode.get(n).getBarcode());
			orderone.put("status", status);
			String trade_oid_2 = trade_tid();
			ordertwo.put("oid", trade_oid_2);
			ordertwo.put("oid_str", trade_oid_2);
			int m = (int)(Math.random()*10);
			boolean flag = true;
			while(flag) {
				if(m==n) {
					m = (int)(Math.random()*10);
				}else {
					flag = false;
				}
			}
			ordertwo.put("outer_sku_id", list_barcode.get(m).getBarcode());
			ordertwo.put("num_iid", list_barcode.get(m).getNum_iid());
//			orderone.put("title", trade.get("seller_nick")+list_barcode.get(n).getBarcode());
			ordertwo.put("status", status);
		}
	
	//trade tid
	private static String trade_tid() {
		return Utils.random();
	}
}
