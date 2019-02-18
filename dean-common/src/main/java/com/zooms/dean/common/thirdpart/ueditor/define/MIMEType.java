package com.zooms.dean.common.thirdpart.ueditor.define;

import java.util.HashMap;
import java.util.Map;

public class MIMEType {

	public static final String CONTENT_TYPE = "content_type";

	public static final Map<String, String> types = new HashMap<String, String>(){{
		put( "image/gif", ".gif" );
		put( "image/jpeg", ".jpg" );
		put( "image/jpg", ".jpg" );
		put( "image/png", ".png" );
		put( "image/bmp", ".bmp" );
	}};
	
	public static String getSuffix ( String mime ) {
		return MIMEType.types.get( mime );
	}

	public static String getMimeType ( String suffix ) {

		for (Map.Entry entry: MIMEType.types.entrySet()) {
			if (suffix.equals(entry.getValue())) {
				return entry.getKey().toString();
			}
		}
		return "";

	}
}
