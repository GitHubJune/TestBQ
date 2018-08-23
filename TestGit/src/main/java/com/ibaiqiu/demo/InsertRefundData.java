package com.ibaiqiu.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.json.JSONObject;

public class InsertRefundData {

	/**
	 * 订单状态
	 * WAIT_SELLER_SEND_GOODS 等待卖家发货（仅退款未发货用到）
	 * WAIT_BUYER_CONFIRM_GOODS 等待买家确认收货（仅退款已发货，退货退款用到）
	 * TRADE_FINISHED 交易完成（仅退款已发货，退货退款用到）
	 */
	
	/**
	 * 退单状态
	 * WAIT_SELLER_AGREE 等待卖家同意（仅退款未发货，仅退款已发货用到）
	 * SUCCESS 退款成功（仅退款未发货用到）
	 * WAIT_BUYER_RETURN_GOODS 等待买家退货（退货退款用到）
	 * WAIT_SELLER_CONFIRM_GOODS 等待卖家确认收货（退货退款用到）
	 */
	Utils utils = new Utils();
	//refund_id = 11458703589838401  WAIT_SELLER_AGREE
	//refund_id = 11376380557288006  WAIT_BUYER_RETURN_GOODS
	//refund_id = 10136954845979981  WAIT_SELLER_CONFIRM_GOODS
	
	static String jdp_response = null;
	static String seller_nick = "";
	static String buyer_nick = "";
	static String tid = "201210025405379671";
	static String oid = "201210025405379680";
	static String refund_status = "WAIT_BUYER_RETURN_GOODS";
	private static void packageSQL() {
		Connection con = Utils.getConn();
		try {
			Statement statement = con.createStatement();
			String sql = "select * from jdp_tb_refund_Jessica where refund_id = 11376380557288006";
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()) {
				jdp_response = rs.getString("jdp_response");
			}
			packageResponse();
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void packageResponse() {
		JSONObject obj =JSONObject.fromObject(jdp_response);
		JSONObject refund = obj.getJSONObject("refund_get_response").getJSONObject("refund");
		//refund_id status tid oid good_status has_good_return
		//num refund_fee sku total_fee company_name order_status
		refund.put("refund_id", Utils.random());refund.put("status", refund_status);
		refund.put("tid", tid);refund.put("oid", oid);
//		refund.put("has_good_return", "");refund.put("good_status", "");
		refund.put("alipay_no", "2018081521001001370236458675");refund.put("num", "1");
//		refund.put("sku", "");
		refund.put("price", "1498");refund.put("total_fee", "679");refund.put("refund_fee", "679");
		refund.put("order_status", "WAIT_BUYER_CONFIRM_GOODS");
		refund.put("num_iid", "558304967513");//相当于goods_barcode
		jdp_response = obj.toString();
		seller_nick = refund.getString("seller_nick");
		buyer_nick = refund.getString("buyer_nick");
	}
	
	
	private static void insertData() {
		Connection con = Utils.getConn();
		String sql = "insert into jdp_tb_refund_Jessica(refund_id,seller_nick,buyer_nick,status,created,tid,oid,modified,"
				+ "jdp_hashcode,jdp_response,jdp_created,jdp_modified) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, Utils.random());
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
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		packageSQL();
		insertData();
		System.out.println("SUCCESS!!!");
	}
}
