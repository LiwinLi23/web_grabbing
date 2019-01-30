/*
 *  @(#)ResultJson.java  1.0   2012-11-22
 *  Copyright (c) 2012, 北京优听信息技术有限公司
 */
package my.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类或接口：ResultJson<br>
 * 功能描述：返回Json结果
 * @author Xu Tieqiang
 * @version 1.0
 * @librarys 
 */
public class ResponseResult {
	private String errcode;
	private String msg;
	private String uptime = "";
	private String vef = "";
	@SuppressWarnings("rawtypes")
	private List<Map> result; //返回值列表
	@SuppressWarnings("rawtypes")
	private List<Map> action; //返回行为列表
	@SuppressWarnings("rawtypes")
	private Map title; //页面标题
	
	public ResponseResult(){
		//this("UnknownError",new ArrayList<Map>(),new ArrayList<Map>());
		//this("UnknownError",null,null,null);
		this("success",null,null,null);
	}
	public ResponseResult(String errname){
		//this(errname,new ArrayList<Map>(),new ArrayList<Map>());
		this(errname,null,null,null);
	}
	
	@SuppressWarnings("rawtypes")
	public ResponseResult(String errname,List<Map> result,List<Map> action,Map title){
		if (errname==null) 
			errname="UnknownErrornull";
		
		this.msg=errname;
		if (errname.equalsIgnoreCase("success"))  this.errcode="1";
		else
		this.errcode="-999"; 
		this.result=result;
		this.action = action;
		this.title = title;
	}

	@SuppressWarnings("rawtypes")
	public List<Map> getResult() {
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public void addMaptoResult(Map result) {
		if (this.result ==null)
			this.result=new ArrayList<Map>();
		this.result.add(result);
	}
	
	@SuppressWarnings("rawtypes")
	public void setResult(List<Map> result) {
		this.result = result;
	}
	public String getErrcode() {
		return errcode;
	}
	public String getMsg() {
		return msg;
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map> getAction() {
		return action;
	}
	@SuppressWarnings("rawtypes")
	public void setAction(List<Map> action) {
		this.action = action;
	}
	
	@SuppressWarnings("rawtypes")
	public Map gettitle() {
		return title;
	}
	@SuppressWarnings("rawtypes")
	public void settitle(Map title) {
		this.title = title;
	}
	
	public int getCount() {
		return (result==null)?0:result.size();
	}
	public void setErrname(String errname) {
		//this.errcode=new ServiceException(errname).getCode();
		if (errname.equalsIgnoreCase("success"))  this.errcode="1";
		else
		this.errcode="-999"; 
		this.msg=errname;
	}
	
	public String getvef() {
		return vef;
	}
	public void setvef(String vef) {
		this.vef = vef;
	}
	
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	
	
	public String toJson(){
		/*
		 * 因性能问题(cpu占用率过高)，弃用Json-lib库，而采用Jackson库
		 * //return JSONObject.fromObject(this).toString(); 
		 */
		return JsonUtil.toJson(this);
	}
	@Override
	public String toString() {
		return "ResponseResult [errcode=" + errcode + ", msg="+ msg + ", result=" + result + ", count=" + this.getCount() + ", uptime=" + uptime + "]";
	}
	
}