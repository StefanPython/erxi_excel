package com.jm.util;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelInputUtil {
	//表格的标题和对应列的值
	private static Map<String,String> entityValue = new HashMap<String,String>();
	//表格的中文字段名对应实体类的字段名
	private static Map<String,String> modelKey = new HashMap<String, String>();
	//表格标题数据
	private static String[] titles;
	//属性名
	//private static String[] fieldNames;
	
	/**
	 * 导入Excel文件
	 * @param cls 实体类 (需要符合JavaBean的要求，并且要有set方法)
	 * @param file File文件
	 * @param fileName  文件名
	 * @param args 实体类字段和对应中文名的字符串,该参数从KgzxConstant常量中获取,如果没有则自己添加
	 * @return
	 * @throws Exception
	 * @author Jwenk
	 * @Time 2017-01-11
	 */
	public static  List<Object> inputExcel (Class cls,File file,String fileName,String args) throws Exception{
		
		setModelKey(args.split(","));
		
		//截取文件名的后缀
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		
		List<Object> list = new ArrayList();
		//根据Class文件创建一个对象
		Workbook wb = null;
		
		Object object = cls.newInstance();
		try {
			InputStream stream = new FileInputStream(file);  
			
		    //根据不同版本的Excel文件创建不同的对象
		    if("xls".equals(fileType) || "xlsx".equals(fileType)){
		    	wb = WorkbookFactory.create(stream);
		    }
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Excel文件导入异常");
		}
		//获取第一个Sheet表
		Sheet sheet = wb.getSheetAt(0);
        //获取总表数
		int sheetCount = wb.getNumberOfSheets();
		//获取总行数
		int rowCount  = 0;//wb.getSheetAt(0).getPhysicalNumberOfRows();
		//获取表格的标题栏
		setTitles(sheet);
		
        //遍历所有的Sheet
		for(int n=0;n<sheetCount;n++){
			sheet = wb.getSheetAt(n);
			rowCount = sheet.getPhysicalNumberOfRows();
			for(int i=1;i<=rowCount-1;i++){
				parseExcel(sheet.getRow(i), object);
				list.add(object);
			}
		}
		System.out.println(list);
		if(list.size()<0){
			throw new Exception("导入表为空，请检查");
		}
		
		return list;
	}
	
	/****************私有辅助方法**********************/
	/**
	 * 对Excel数据行的读取
	 * @param row
	 * @param entity
	 * @return
	 * @throws Exception 
	 */
	private static void parseExcel(Row row,Object entity) throws Exception {
		//Map<String,String> sinDTO = new HashMap<String,String>();
		//读取每一行的值
		try {
			//读取该行的每一列数据
			for(int i=0;i<row.getPhysicalNumberOfCells();i++){
				String value = getValue(row.getCell(i));
				entityValue.put(titles[i], value);
			}
			
			for(String key:modelKey.keySet()){
				System.out.println("是否包含地址："+entityValue.containsKey("地址"));
				if(entityValue.containsKey(key)){
					setFieldValueByName(modelKey.get(key), entity, entityValue.get(key));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("数据解析失败");
		}
	}
	
	/**
	 * 读取Excel表格的标题
	 * @param sheet
	 */
	private static void setTitles(Sheet sheet) throws Exception{
		//得到表格的第一行
		int index = sheet.getRow(0).getPhysicalNumberOfCells();
		if(index<0){
			return;
		}
		titles = new String[index];
		//获取表格的第一行数据
		for(int i=0;i<index;i++){
			String value = getValue(sheet.getRow(0).getCell(i));
			titles[i] = value.trim();
		}
	}
	
	/**
	 * 数据格式的转换
	 * @param xssfRow
	 * @return
	 */
	private static String getValue(Cell xssfRow) {
		String value = null;
		if(xssfRow != null){
			switch (xssfRow.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				value= "";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = String.valueOf(xssfRow.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				long number = Math.round(xssfRow.getNumericCellValue());
				value =String.valueOf(number);
				break;
			case Cell.CELL_TYPE_STRING:
				value = xssfRow.getStringCellValue();
				break;
			default:
				break;
			}
		}
		return value == null? null : value.trim();
	}
	
	/**
	 * 根据字段名给对象的字段赋值
	 * @param fieldName
	 * @param o
	 * @return
	 * @throws Exception 
	 */
	private static void setFieldValueByName(String fieldName,Object o,String value) throws Exception{ 
        try {
        	Field field = getFieldByName(fieldName, o.getClass());
        	if(field!=null){
        		field.setAccessible(true);
        		field.set(o, value);
        	}
		} catch (Exception e) {
			throw new Exception(o.getClass().getSimpleName()+"类不存在字段名"+fieldName);
		}
	}
	
	/**
	 * 根据字段名获取字段
	 * @param fieldName
	 * @param clazz
	 * @return
	 */
	private static Field getFieldByName(String fieldName,Class<?> clazz){
		Field[] selfFields = clazz.getDeclaredFields();
		for(Field field:selfFields){
			if(field.getName().equals(fieldName)){
				return field;
			}
		}
		//查看父类是否有该字段，有则返回，没有则返回null
		Class<?> superClazz = clazz.getSuperclass();
		if(superClazz!=null && superClazz!=Object.class){
			return getFieldByName(fieldName, superClazz);
		}
		return null;
	}
	
	/**
	 * 设置表格标题的数据
	 * @param args
	 */
	private static void setModelKey(String[] args){
		if(args.length<=0){
			return;
		}
		titles = new String[args.length];
		for(int i=0;i<args.length;i++){
			String[] data = args[i].split("=");
			modelKey.put(data[1], data[0]);
		}
	}
}
