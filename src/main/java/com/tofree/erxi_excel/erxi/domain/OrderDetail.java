package com.tofree.erxi_excel.erxi.domain;

import com.jm.excel.export.excelExportAnnotation;
import com.jm.pojo.BasicModel;

public class OrderDetail extends BasicModel {
    @excelExportAnnotation(name = "订单编号")
    private String OrderNumber;//订单编号
    @excelExportAnnotation(name = "主办方")
    private String CreatedByUser;//主办方
    @excelExportAnnotation(name = "活动标题")
    private String title;//活动标题
    @excelExportAnnotation(name = "套餐")
    private String OrderName;//套餐
    @excelExportAnnotation(name = "单价")
    private String Price;//单价
    @excelExportAnnotation(name = "活动开始时间")
    private String StartDate;//活动开始时间
    @excelExportAnnotation(name = "下单人昵称")
    private String NickName;//下单人昵称
    @excelExportAnnotation(name = "注册时间")
    private String RegistTime;//注册时间
    @excelExportAnnotation(name = "联系人")
    private String ContactName;//联系人
    @excelExportAnnotation(name = "联系电话")
    private String Mobile;//联系电话
    @excelExportAnnotation(name = "数量")
    private String Quantity;//数量
    @excelExportAnnotation(name = "订单创建时间")
    private String OrderCreatedTime;//订单创建时间
    @excelExportAnnotation(name = "运行平台")
    private String Platform;//运行平台
    @excelExportAnnotation(name = "支付方法")
    private String PayMethod;//支付方法
    //导出的Excel的表的标题及文件名
    public String exportExcelTitle(){
        return "订单套餐明细";
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getCreatedByUser() {
        return CreatedByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        CreatedByUser = createdByUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrderName() {
        return OrderName;
    }

    public void setOrderName(String orderName) {
        OrderName = orderName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getRegistTime() {
        return RegistTime;
    }

    public void setRegistTime(String registTime) {
        RegistTime = registTime;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getOrderCreatedTime() {
        return OrderCreatedTime;
    }

    public void setOrderCreatedTime(String orderCreatedTime) {
        OrderCreatedTime = orderCreatedTime;
    }

    public String getPlatform() {
        return Platform;
    }

    public void setPlatform(String platform) {
        Platform = platform;
    }

    public String getPayMethod() {
        return PayMethod;
    }

    public void setPayMethod(String payMethod) {
        PayMethod = payMethod;
    }
    /*T1.OrderNumber 订单编号,
    T2.CreatedByUser 主办方,
    T2.title 活动标题,
    T5.Name 套餐,
    t4.Price 单价,
    CONVERT (DATE, t2.StartDate, 101) 活动开始时间,
    t3.NickName 下单人昵称,
    dateadd(hh, 8, t3.CreatedTime) 注册时间,
    t1.Name 联系人,
    t1.Mobile 联系电话,
    t4.Quantity 数量,
    dateadd(hh, 8, T1.CreatedTime) 订单创建时间,
    T1.Platform 运行平台,
    T1.PayMethod 支付方法
*/



}
