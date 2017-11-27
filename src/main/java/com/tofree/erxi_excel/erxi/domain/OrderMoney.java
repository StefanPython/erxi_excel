package com.tofree.erxi_excel.erxi.domain;

import com.jm.excel.export.excelExportAnnotation;
import com.jm.pojo.BasicModel;
import lombok.Data;

@Data
public class OrderMoney extends BasicModel {
    @excelExportAnnotation(name="订单编号")
    private String OrderNumber;
    @excelExportAnnotation(name="主办方")
    private String CreatedByUser;
    @excelExportAnnotation(name="活动标题")
    private String title;
    @excelExportAnnotation(name="活动开始时间")
    private String StartDate;
    @excelExportAnnotation(name="下单人昵称")
    private String NickName;
    @excelExportAnnotation(name="注册时间")
    private String registerDate;
    @excelExportAnnotation(name="联系人")
    private String Name;
    @excelExportAnnotation(name="联系电话")
    private String Mobile;
    @excelExportAnnotation(name="支付金额")
    private String Money;
    @excelExportAnnotation(name="购买数量")
    private String Quantity;
    @excelExportAnnotation(name="订单创建时间")
    private String createDate;
    @excelExportAnnotation(name="运行平台")
    private String Platform;
    @excelExportAnnotation(name="支付方法")
    private String PayMethod;
    //导出的Excel的表的标题及文件名
    public String exportExcelTitle(){
        return "订单金额汇总";
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

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        Money = money;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    @Override
    public String toString() {
        return "OrderMoney{" +
                "OrderNumber='" + OrderNumber + '\'' +
                ", CreatedByUser='" + CreatedByUser + '\'' +
                ", title='" + title + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", NickName='" + NickName + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", Name='" + Name + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Money='" + Money + '\'' +
                ", Quantity='" + Quantity + '\'' +
                ", createDate='" + createDate + '\'' +
                ", Platform='" + Platform + '\'' +
                ", PayMethod='" + PayMethod + '\'' +
                '}';
    }
    /*t1.OrderNumber 订单编号,
	T2.CreatedByUser 主办方,
	T2.title 活动标题,
	CONVERT (DATE, t2.StartDate, 101) 活动开始时间,
	t3.NickName 下单人昵称,
	dateadd(hh, 8, t3.CreatedTime) 注册时间,
	t1.Name 联系人,
	t1.Mobile 联系电话,
	T1.Money 支付金额,
	t4.Quantity 购买数量,
	dateadd(hh, 8, T1.CreatedTime) 订单创建时间,
	T1.Platform 运行平台,
	T1.PayMethod 支付方法*/

}
