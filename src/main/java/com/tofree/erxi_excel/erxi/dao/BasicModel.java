package com.tofree.erxi_excel.erxi.dao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jm.controller.util.JsonUtil;
import com.jm.controller.util.MyObjectMapper;
import com.jm.util.ReflectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BasicModel implements Serializable{
private static MyObjectMapper objMap;
private static JsonUtil jsonUtil;
private Integer departmentId;
static{
	synchronized (BasicModel.class) {
		objMap=new MyObjectMapper();
		jsonUtil=new JsonUtil();
		jsonUtil.setObjMapper(objMap);
	}
}
/*protected static final   String[] types = {"java.lang.Integer",  
        "java.lang.Double",  
        "java.lang.Float",  
        "java.lang.Long",  
        "java.lang.Short",  
        "java.lang.Byte",  
        "java.lang.Boolean",  
        "java.lang.Character",  
        "java.lang.String",
        "java.util.Date",
        "int","double","long","short","byte","boolean","char","float"}; */	
	
 protected Integer Id;
 
 
 
public Integer getId() {
return Id;
}
public void setId(Integer id) {
this.Id = id;
}public boolean equals(Object obj) {
 if(this.getClass().getName().equals(obj.getClass().getName())){
return this.hashCode()==obj.hashCode();
}
return false;
}
public int hashCode() {
int basic=this.getClass().hashCode()*1000;
if(Id==null){
return -basic;
}else{
return basic+Id;
}
}
//导出的Excel的表的标题及文件名
public String exportExcelTitle(){
	return null;
}

//导入的Excel的表的标题及文件名
public String importExcelTitle(){
	return null;
}

//返回excel导入的字段名
@JsonIgnore
public String[] getImportExcelDataField(){
	return null;
}

//把map对象转换成pojo对象
public static BasicModel createModel(Map<String,String> fieldValues,Class pojoClass) throws Exception{
	String temp=jsonFormat(fieldValues, pojoClass);
	  if(temp==null)
		  return null;
	return (BasicModel)jsonUtil.toObject(temp, pojoClass);
}

private static String jsonFormat(Map<String,String> fieldValues,Class pojoClass) throws Exception{
	 if(fieldValues==null||fieldValues.size()==0)
		 return null;
	StringBuffer sb=new StringBuffer();
	List<String> contents=new ArrayList<String>(0);
	sb.append("{");
	String type=null;
	for(Entry<String, String> kv:fieldValues.entrySet()){
		type=ReflectUtils.getFieldTypeName(pojoClass,kv.getKey());
		//System.out.println(type);
		 if("String".equals(type)){
			 contents.add("\""+kv.getKey()+"\":\""+kv.getValue()+"\"");
		 }else if("Date".equals(type)){
			 String value=kv.getValue().replace(".", "-");
			 value=value.replace("/", "-");
			 value=value.replace(" ", "-");
			 contents.add("\""+kv.getKey()+"\":\""+value+"\"");
		 }else{
			 contents.add("\""+kv.getKey()+"\":"+kv.getValue());
		 }
	}
	if(contents.size()>0){
	int i=0;
	for(;i<contents.size()-1;i++)
		sb.append(contents.get(i)+",");
	sb.append(contents.get(i)+"}");
	//System.out.println(sb.toString());
	return sb.toString();
	}else{
		return null;
	}
	
}

public final Integer getDepartmentId() {
	return departmentId;
}
public final void setDepartmentId(Integer departmentId) {
	this.departmentId = departmentId;
}



}