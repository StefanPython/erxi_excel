package com.jm.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class jsonResult {
	private boolean success=true;
	private String msg;
	private long total;
	private int pageSize;
	private List rows=new ArrayList(0);
	
//	public jsonResult(boolean success, String msg, long total, int pageSize, List datas) {
//		super();
//		this.success = success;
//		this.msg = msg;
//		this.total = total;
//		this.pageSize = pageSize;
//		this.rows = datas;
//	}

	public void setErrMsg(String msg){
		this.success=false;
		this.msg=msg;
	}
	
   public void add(List datas){
	   if(datas!=null){
	   this.rows.addAll(datas);
	   this.total=this.rows.size();
	   }
   }
	
	public jsonResult(String msg) {
		super();
		this.msg = msg;
	}



	public jsonResult(List datas) {
		super();
		this.rows = datas;
		if(datas!=null)
		this.total=datas.size();
	}

 

	public jsonResult(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "jsonResult [success=" + success + ", msg=" + msg + ", total=" + total + ", pageSize=" + pageSize
				+ ", rows=" + rows + "]";
	}
	
	
	
	
	
	
}
