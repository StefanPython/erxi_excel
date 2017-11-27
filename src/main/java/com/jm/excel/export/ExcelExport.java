package com.jm.excel.export;

import com.jm.pojo.BasicModel;
import com.jm.util.POIExcelUtil;

import java.io.OutputStream;
import java.util.List;

public class ExcelExport {
	//pojo必须不为null，导出数据及表的标题
	public static void exportExcel(OutputStream outputStream, List pojos) throws Exception {
		 List<BasicModel> models=(List<BasicModel>)pojos;
		String title=models.get(0).exportExcelTitle();//导出的标题
		List<String[]> sheetContents=classToXLSSheet.getSheetContent(models);
		if(sheetContents!=null&&sheetContents.size()>0){
			try {
				POIExcelUtil.export(outputStream, title, sheetContents);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("压缩excel出错");
			}
		}
		
		
	}
	
	//导出空表
	public static void exportExcelHeader(OutputStream outputStream, Class pojoClass) throws Exception {
		BasicModel instance=(BasicModel) pojoClass.newInstance();
		String title=instance.exportExcelTitle();//导出的标题
		List<String[]> sheetContents=classToXLSSheet.getSheetHeader(pojoClass);
		if(sheetContents!=null&&sheetContents.size()>0){
			try {
				POIExcelUtil.export(outputStream, title, sheetContents);
			} catch (Exception e) {
				throw e;
			}
		}


	}
	
}
