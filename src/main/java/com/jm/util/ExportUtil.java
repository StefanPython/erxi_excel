package com.jm.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ExportUtil {
	//实体类的字段名（英文）以及对应的value值---实体内容
	private static Map<String,Object> keyValue = new HashMap<String,Object>();
	//表格的中文字段名对应实体类的字段名
	private static Map<String,String> modelKey = new HashMap<String, String>();
	//表格标题数据
	private static String[] titles;
	//属性名
	private static String[] fieldNames;
	
	
	/**
	 * 导出excel文件工具类
	 * @param <T> T要符合JavaBean规范
	 * @param list 网页上的数据集合
	 * @param args 标题数组
	 * @param sheetName 表名
	 * @author Jwenk
	 * @throws IOException 
	 * @time 2016-12-29
	 */
	public static <T> void exportExcel(List<T> list,String[] args,OutputStream out) throws IOException{
		Iterator it = list.iterator();
		//全局参数的初始化
		setModelKey(args);
		//每满100条则从内存中写到硬盘上
		Workbook wb = new SXSSFWorkbook(5000);
		//设置Excel表名
		Sheet sheet=wb.createSheet();
		Row titleRow = sheet.createRow(0);
		//设置水平居中
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		//设置表格每个字段的标题
		Cell titleCell;
		for(int i=0;i<args.length;i++){
			titleCell = titleRow.createCell(i);
			titleCell.setCellType(SXSSFCell.CELL_TYPE_STRING);
			titleCell.setCellValue(titles[i]);
			//设置Excel对应字段的列宽
			sheet.setColumnWidth(i, titles[i].getBytes().length*2*256);
		}
		
		//数据写入
		Cell dataCell;
		int i = 1; //用来记录数据的行数
		while(it.hasNext()){
			Row dataRow = sheet.createRow(i);
			setKeyValue(it.next());
			for(int j=0;j<titles.length;j++){
				dataCell = dataRow.createCell(j);
				dataCell.setCellValue(getValue(keyValue.get(fieldNames[j])));
				//sheet.setColumnWidth(j, getValue(keyValue.get(fieldNames[j])).getBytes().length*1*256);
			}
			i++;
		}
		
		//数据的写出
		try {
			wb.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**************************私有辅助方法***************************/
	
	
	/**
	 * 将对象转换为具体类型的值
	 * @param o 
	 * @return 
	 * @author Jwenk
	 */
	private static String getValue(Object o){
		if(o==null){
			o = "";
		}
		return String.valueOf(o);
	}
	
	/**
	 * 设置表格每个字段标题的数据
	 * @param args
	 */
	private static void setModelKey(String[] args){
		titles = new String[args.length];
		fieldNames = new String[args.length];
		for(int i=0;i<args.length;i++){
			String[] data = args[i].split("=");
			titles[i] = data[1];
			fieldNames[i] = data[0];
			modelKey.put(data[0], data[1]);
		}
	}
	
	/**
	 * 将集合中的数据取出存到map中
	 * @param model
	 */
	private static  void setKeyValue(Object model){
		//System.out.println(model);
		Field[] field = model.getClass().getDeclaredFields();
		for(int i=0;i<field.length;i++){
			String name = field[i].getName();
			keyValue.put(name, getFieldValueByName(name,model));
		}
	}
	
	/**
	 * 辅助方法：通过属性名获取属性值
	 * @param fieldName 属性名
	 * @param o 实体类
	 * @return Object
	 * @author Jwenk
	 */
	private static Object getFieldValueByName(String fieldName,Object o){ 
		Object value = null;
        try {
    		String firstLetter = fieldName.substring(0, 1).toUpperCase(); 
            String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 辅助方法：获取对象的所有属性名
	 * @param model 对象
	 * @return fieldNames 属性名数组
	 * @author Jwenk
	 */
	private static String[] getFieldNames(Object model){
		Field[] field = model.getClass().getDeclaredFields();
		String[] fieldNames = new String[field.length];
		for(int i=0;i<field.length;i++){
			fieldNames[i] = field[i].getName();
		}
		return fieldNames;
	}
}
