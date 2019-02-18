package com.zooms.dean.common.tool;

import java.io.Serializable;

public class StatusConstant implements Serializable {

	private static final long serialVersionUID = 1L;

	// 显示/隐藏
    public static final String SHOW = "1";
	public static final String HIDE = "0";
	
	// 是/否
	public static final String YES = "1";
	public static final String NO = "0";

    public static final Integer YES_INT = 1;
    public static final Integer NO_INT = 0;

	// 删除标记（0：正常；1：删除；2：审核；）
	public static final String DEL_FLAG = "delFlag";
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	public static final String DEL_FLAG_AUDIT = "2";
    // 是否添加到顶部下拉菜单
	public static final String SHORTCUT_YES = "1";
	public static final String SHORTCUT_NO = "0";

    //0:草稿;1:审核中;2:审核通过;3:回收站
	public static final Integer STATUS_DRAFT = 0;
	public static final Integer STATUS_DOING = 1;
	public static final Integer STATUS_DONE = 2;
	public static final Integer STATUS_RECYCLE = 3;
	
	//显示、隐藏购买按钮
	public static final String HIDE_BUY_HEY = "_HBB";
	public static final String SHOW_BUY_BUTTON = "0";
	public static final String HIDE_BUY_BUTTON = "1";
	
	//审核状态（0：未审核；1：审核通过；2审核驳回）
	public static final int AUDIT_STATUS_WAIT = 0;
	public static final int AUDIT_STATUS_PASS = 1;
	public static final int AUDIT_STATUS_REJECT = 2;
	
	//上下架状态（0:下架；1：上架）
	public static final int OFF_SHELVE = 0;
	public static final int ON_SHELVE = 1;
	
	//课程支付状态（-1：支付失败；0：未支付；1已支付）
	public static final int COURSE_PAY_FAIL = -1;
	public static final int COURSE_PAY_UNPAID = 0;
	public static final int COURSE_PAY_SUCCESS = 1;
	
}