package com.ibaiqiu.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ibaiqiu.utils.DataConnection;
import com.ibaiqiu.utils.Utils;
import com.jcraft.jsch.Session;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class InsertTmallRefundDataBatch_compeled {

	/**
	 * order_status
	 * WAIT_SELLER_SEND_GOODS 
	 * WAIT_BUYER_CONFIRM_GOODS 
	 * TRADE_FINISHED 
	 * TRADE_CLOSED 
	 */
	
	/**
	 * refund_status
	 * WAIT_SELLER_AGREE 
	 * SUCCESS 
	 * WAIT_BUYER_RETURN_GOODS 
	 * WAIT_SELLER_CONFIRM_GOODS 
	 */
	
	/**
	 * good_status
	 * BUYER_RECEIVED
	 * BUYER_NOT_RECEIVED
	 * BUYER_RETURNED_GOODS
	 */
	Utils utils = new Utils();
	
	static String jdp_response = null;
	static String seller_nick = "";
	static String buyer_nick = "";
	static String tid = "";
	static String oid = "";
	static String refund_id = "";
	static String refund_status = "WAIT_BUYER_RETURN_GOODS";
	static boolean has_good_return = true;
	static String good_status = "BUYER_RECEIVED";
	static double price = 15;
	private static void BatchRefundData(Connection con) {
		try {
			Statement statement = con.createStatement();
			String sql = "select * from jdp_tb_refund where refund_id = 18574498421065926";
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				jdp_response = rs.getString("jdp_response");
			}
			String tradeSQL = "select * from jdp_tb_trade where tid = '1101117312368726295'";
			PreparedStatement ps_trade = con.prepareStatement(tradeSQL);
			ResultSet rs_trade = ps_trade.executeQuery();
			while(rs_trade.next()) {
				/*------analysis order-----*/
				String trade_response = rs_trade.getString("jdp_response");
				JSONObject obj_trade =JSONObject.fromObject(trade_response);
				
				JSONObject trade = obj_trade.getJSONObject("trade_fullinfo_get_response").getJSONObject("trade");
				JSONArray orders = trade.getJSONObject("orders").getJSONArray("order");
				JSONObject orderone = orders.getJSONObject(0);
				/*-------------------------*/
				tid = trade.getString("tid");
				oid = orderone.getString("oid");
				System.out.println("tid--"+tid);
				seller_nick = trade.getString("seller_nick");
				buyer_nick = trade.getString("buyer_nick");
				JSONObject obj =JSONObject.fromObject(jdp_response);
				JSONObject refund = obj.getJSONObject("refund_get_response").getJSONObject("refund");
				//refund_id status tid oid good_status has_good_return
				//num refund_fee sku total_fee company_name order_status
				refund_id = Utils.random();
				System.out.println("refund_id--"+refund_id);
				refund.put("refund_id", refund_id);refund.put("status", refund_status);
				refund.put("tid", tid);refund.put("oid", oid);
				refund.put("has_good_return", has_good_return);refund.put("good_status", good_status);
				refund.put("alipay_no", trade.getString("alipay_no"));
//				refund.put("num", trade.getInt("num"));
				refund.put("num", 1);
//				refund.put("sku", "");
				refund.put("price", trade.getDouble("price")/orders.size());refund.put("total_fee", price);
				refund.put("refund_fee", price);
//				refund.put("order_status", trade.getString("status"));
				refund.put("order_status", "WAIT_BUYER_CONFIRM_GOODS");
//				refund.put("order_status", "TRADE_CLOSED");
				refund.put("num_iid", orderone.getString("num_iid"));//=goods_barcode
				refund.put("created", Utils.date());
				refund.put("modified", Utils.date());
				jdp_response = obj.toString();
				insertData(con);
			}
			rs.close();
			rs_trade.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static void insertData(Connection con) {
		String sql = "insert into jdp_tb_refund(refund_id,seller_nick,buyer_nick,status,created,tid,oid,modified,"
				+ "jdp_hashcode,jdp_response,jdp_created,jdp_modified) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, refund_id);
			ps.setString(2, seller_nick);ps.setString(3, buyer_nick);
			ps.setString(4, refund_status);
			ps.setTimestamp(5, Utils.sqlDate());
			ps.setString(6, tid);ps.setString(7, oid);
			ps.setTimestamp(8, Utils.sqlDate());
			ps.setString(9, Utils.hashcode());
			ps.setString(10, jdp_response);
			ps.setTimestamp(11, Utils.sqlDate());ps.setTimestamp(12, Utils.sqlDate());
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Session session = DataConnection.connectSSH();
		Connection con = DataConnection.connectSQL();
		BatchRefundData(con);
		try {
			con.close();
			session.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("SUCCESS!!!");
	}
}
