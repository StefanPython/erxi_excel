package com.tofree.erxi_excel.erxi.domain;

import com.jm.excel.export.excelExportAnnotation;
import com.jm.pojo.BasicModel;


public class test extends BasicModel {
    @excelExportAnnotation(name = "姓名")
    private String name;
    @excelExportAnnotation(name = "密码")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //导出的Excel的表的标题及文件名
    public String exportExcelTitle(){
        return "测试清单";
    }

    @Override
    public String toString() {
        return "test{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
