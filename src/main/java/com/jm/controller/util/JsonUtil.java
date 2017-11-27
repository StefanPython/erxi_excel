package com.jm.controller.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonUtil {
    @Autowired
	private MyObjectMapper objMapper;
    
   public  String getJsonString(Object obj) throws JsonProcessingException{
 	
 		return objMapper.writeValueAsString(obj);
    }
    
   private JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
        return objMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
    }
    
    public List getListObjects(String json,Class pojoClass) throws Exception{
 	   JavaType type=getCollectionType(ArrayList.class, pojoClass);
 		return objMapper.readValue(json,type);
    }
    
    public  List getListObjects(InputStream is,Class pojoClass) throws Exception{
 	   JavaType type=getCollectionType(ArrayList.class, pojoClass);
 		return objMapper.readValue(is,type);	
    }
    
    public   Object toObject(String content,Class clasz) throws Exception{
 	 
 		return objMapper.readValue(content, clasz);
    }
    
    public  Object toObject(InputStream content,Class clasz) throws Exception{
 	 
 		return objMapper.readValue(content, clasz);
    }

	public MyObjectMapper getObjMapper() {
		return objMapper;
	}

	public void setObjMapper(MyObjectMapper objMapper) {
		this.objMapper = objMapper;
	} 
	
    
}
