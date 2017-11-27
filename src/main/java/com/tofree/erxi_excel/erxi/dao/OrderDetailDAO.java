package com.tofree.erxi_excel.erxi.dao;


import com.tofree.erxi_excel.erxi.domain.OrderDetail;
import com.tofree.erxi_excel.erxi.domain.OrderMoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {
	private String moneySql="SELECT t1.OrderNumber 订单编号, T2.CreatedByUser 主办方,T2.title 活动标题,CONVERT (DATE, t2.StartDate, 101) 活动开始时间,t3.NickName 下单人昵称,dateadd(hh, 8, t3.CreatedTime) 注册时间,t1.Name 联系人,t1.Mobile 联系电话,T1.Money 支付金额,t4.Quantity 购买数量,dateadd(hh, 8, T1.CreatedTime) 订单创建时间,T1.Platform 运行平台,T1.PayMethod 支付方法 FROM [ErxiActivityOrder] T1 LEFT OUTER JOIN [ErxiActivity] T2 ON T1.ActivityId = T2.id LEFT OUTER JOIN [CommunityUser] t3 ON t1.SessionId = t3.id LEFT OUTER JOIN ( SELECT OrderId,SUM (Quantity) Quantity FROM [ErxiActivityOrderPackage] WHERE Status IN ('Paid', 'Available', 'Used') GROUP BY OrderId ) t4 ON t1.Id = t4.OrderId WHERE PayMethod IS NOT NULL AND t1.CreatedTime BETWEEN dateadd( hh ,- 8,CONVERT (VARCHAR, ?, 101)) AND dateadd(hh ,- 8,CONVERT (VARCHAR, ?, 101) )ORDER BY t1.CreatedTime ASC";
	private String detailSql="SELECT T1.OrderNumber 订单编号,T2.CreatedByUser 主办方,T2.title 活动标题,T5.Name 套餐,t4.Price 单价,CONVERT (DATE, t2.StartDate, 101) 活动开始时间,t3.NickName 下单人昵称,dateadd(hh, 8, t3.CreatedTime) 注册时间,t1.Name 联系人,t1.Mobile 联系电话,t4.Quantity 数量,dateadd(hh, 8, T1.CreatedTime) 订单创建时间,T1.Platform 运行平台,T1.PayMethod 支付方法 FROM [ErxiActivityOrder] T1 LEFT OUTER JOIN [ErxiActivity] T2 ON T1.ActivityId = T2.id LEFT OUTER JOIN [CommunityUser] t3 ON t1.SessionId = t3.id LEFT OUTER JOIN [ErxiActivityOrderPackage] t4 ON t1.Id = t4.OrderId AND T4.Status IN ('Paid', 'Available', 'Used') LEFT OUTER JOIN [ErxiActivityPackage] T5 ON T5.Id = T4.PackageId LEFT OUTER JOIN [ErxiActivitySessionPackage] T6 ON T6.ActivityId = t1.ActivityId AND t6.SessionId = t1.SessionId AND t6.PackageId = t4.PackageId WHERE PayMethod IS NOT NULL AND t1.CreatedTime BETWEEN dateadd(hh ,- 8,CONVERT (VARCHAR, ?, 101)) AND dateadd(hh ,- 8,CONVERT (VARCHAR, ?, 101)) ORDER BY t1.CreatedTime ASC";
    private Logger log = LoggerFactory.getLogger(OrderDetailDAO.class);
	public List<OrderDetail> getOrderDetail(String StartDate, String EndDate){

		List<OrderDetail> ods=new ArrayList<>(0);
		Connection conn=connectionFactory.getConnection();
		PreparedStatement pre=null;
		ResultSet rs=null;

		try {
			pre=conn.prepareStatement(detailSql);
			pre.setString(1, StartDate);
			pre.setString(2, EndDate);
			rs=pre.executeQuery();
            ResultSetMetaData rsd=rs.getMetaData();
            log.info("OrderDetail列数："+rsd.getColumnCount());
			while(rs.next()){
				OrderDetail od=new OrderDetail();
				od.setOrderNumber(rs.getString(1));
				od.setCreatedByUser(rs.getString(2));
				od.setTitle(rs.getString(3));
				od.setOrderName(rs.getString(4));
				od.setPrice(rs.getString(5));
				od.setStartDate(rs.getString(6));
				od.setNickName(rs.getString(7));
				od.setRegistTime(rs.getString(8));
				od.setContactName(rs.getString(9));
				od.setMobile(rs.getString(10));
				od.setQuantity(rs.getString(11));
				od.setOrderCreatedTime(rs.getString(12));
				od.setPlatform(rs.getString(13));
				od.setPayMethod(rs.getString(14));
				ods.add(od);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				pre.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ods;
	}

	public List<OrderMoney> getOrderMoney(String StartDate, String EndDate){
		List<OrderMoney> od2s=new ArrayList<>(0);
		Connection conn=connectionFactory.getConnection();
		PreparedStatement pre=null;
		ResultSet rs=null;
		try {
			pre=conn.prepareStatement(moneySql);
			pre.setString(1, StartDate);
			pre.setString(2, EndDate);
			rs=pre.executeQuery();
            ResultSetMetaData rsd=rs.getMetaData();
            log.info("OrderDetail列数："+rsd.getColumnCount());
			while(rs.next()){
				OrderMoney od2=new OrderMoney();
				od2.setOrderNumber(rs.getString(1));
				od2.setCreatedByUser(rs.getString(2));
				od2.setTitle(rs.getString(3));
				od2.setStartDate(rs.getString(4));
				od2.setNickName(rs.getString(5));
				od2.setRegisterDate(rs.getString(6));
				od2.setName(rs.getString(7));
				od2.setMobile(rs.getString(8));
				od2.setMoney(rs.getString(9));
				od2.setQuantity(rs.getString(10));
				od2.setCreateDate(rs.getString(11));
				od2.setPlatform(rs.getString(12));
				od2.setPayMethod(rs.getString(13));
				od2s.add(od2);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				pre.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return od2s;
	}

	/*public List<OrderMoney> getTest(String StartDate,String EndDate){
		String sql="SELECT * FROM ordermoney where StartDate>=? and StartDate<=?";
		List<OrderMoney> od2s=new ArrayList<OrderMoney>(0);
		Connection conn=connectionFactory.getConnection();
		PreparedStatement pre=null;
		ResultSet rs=null;
		try {
			pre=conn.prepareStatement(sql);
			pre.setString(1, StartDate);
			pre.setString(2, EndDate);
			rs=pre.executeQuery();
			while(rs.next()){
				OrderMoney od2=new OrderMoney();
				od2.setOrderNumber(rs.getString(1));
				od2.setCreatedByUser(rs.getString(2));
				od2.setTitle(rs.getString(3));
				od2.setStartDate(rs.getString(4));
				od2.setNickName(rs.getString(5));
				od2.setRegisterDate(rs.getString(6));
				od2.setName(rs.getString(7));
				od2.setMobile(rs.getString(8));
				od2.setMoney(rs.getString(9));
				od2.setQuantity(rs.getString(10));
				od2.setCreateDate(rs.getString(11));
				od2.setPlatform(rs.getString(12));
				od2.setPayMethod(rs.getString(13));
				od2s.add(od2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				pre.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return od2s;
	}*/


}
