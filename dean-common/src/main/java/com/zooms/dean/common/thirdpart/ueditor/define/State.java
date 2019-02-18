package com.zooms.dean.common.thirdpart.ueditor.define;

/**
 * 处理状态接口
 * @author hancong03@baidu.com
 *
 */
public interface State {
	
	public boolean isSuccess();

	String getStoreUrl();
	
	public void putInfo(String name, String val);
	
	public void putInfo(String name, long val);
	
	public String toJSONString();

}
