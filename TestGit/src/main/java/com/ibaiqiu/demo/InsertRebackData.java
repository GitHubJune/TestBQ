package com.ibaiqiu.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ibaiqiu.utils.Utils;

public class InsertRebackData {
	/**退货入仓单
	 * `reback_id` int(11) NOT NULL AUTO_INCREMENT,
	  `reback_sn` varchar(30) NOT NULL DEFAULT '' COMMENT 'oms退货入仓单号',
	  `wms_reback_sn` varchar(30) DEFAULT '' COMMENT 'wms退货入仓单号',
	  `deal_code` varchar(50) DEFAULT '' COMMENT '平台订单号',
	  `sd_id` int(11) DEFAULT '0' COMMENT '店铺id',
	  `return_warehouse` int(11) DEFAULT '0' COMMENT '退回仓库，关联sys_stores标id',
	  `return_match` tinyint(1) DEFAULT '0' COMMENT '退货匹配，0-未匹配，1-已匹配',
	  `return_sn` varchar(30) DEFAULT '' COMMENT '匹配退货单号',
	  `order_return_goods_id` int(11) DEFAULT '0' COMMENT '匹配退货单商品表id',
	  `goods_barcode` varchar(50) DEFAULT '' COMMENT '商品sku',
	  `reback_goods_num` smallint(5) DEFAULT '0' COMMENT '入仓数量',
	  `invoice_no` varchar(30) DEFAULT '' COMMENT '退货运单号',
	  `shipping_name` varchar(100) DEFAULT '' COMMENT '物流公司名称',
	  `consignee` varchar(255) DEFAULT '' COMMENT '退货人',
	  `mobile` varchar(255) DEFAULT '' COMMENT '退货人手机号',
	  `reback_time` datetime DEFAULT NULL COMMENT '入仓时间',
	  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  	  `updated_at` timestamp NULL DEFAULT '0000-00-00 00:00:00',
	  `order_sn` varchar(64) DEFAULT '''''' COMMENT '订单号'
	 */
	private static void packageSQL() {
		Connection con = Utils.getConn();
		String sql = "INSERT INTO order_reback_warehouse(reback_id,reback_sn,wms_reback_sn,deal_code,sd_id,return_warehouse,"
				+ "return_match,return_sn,order_return_goods_id,goods_barcode,reback_goods_num,invoice_no,"
				+ "shipping_name,consignee,mobile,reback_time,order_sn) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Utils.IntID());ps.setString(2, Utils.random());
			ps.setString(3, Utils.random());ps.setString(4, Utils.random());
			ps.setInt(5, 1);ps.setInt(6, 1001);ps.setInt(7, 1);
			ps.setString(8, Utils.random());ps.setInt(9, 1);
			ps.setString(10, "6941288833895");ps.setInt(11, 1);
			ps.setString(12, Utils.random());ps.setString(13, "顺丰快递");
			//$175$X3iyQ5rM2//rUUS2xqxyTg==$1$
			//~mvtuVoQ6uJ8hnX+nUJejvA==~1~
			ps.setString(14, "~mvtuVoQ6uJ8hnX+nUJejvA==~1~");ps.setString(15, "$175$X3iyQ5rM2//rUUS2xqxyTg==$1$");
			ps.setTimestamp(16, Utils.sqlDate());ps.setString(17,  Utils.random());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			packageSQL();
		}
		System.out.println("SUCCESS!!!");
	}
}
