package com.zooms.dean.common.tool;

import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @Description: IP地址工具类
 * @author zhaolijin
 * @date 2017-07-05
 */
public class IPUtil {
    
    public static String getIpAddress(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  

	/**
	 * getLocalhostIp(获取本机ip地址)
	 * @throws UnknownHostException 
	 * @Exception 异常对象 
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public static String getLocalhostIp() {
		String ip = "";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			return null;
		}
		return ip;
	}
	
	public static List<String> getIpAddrs() throws Exception {
		List<String> IPs = new ArrayList<String>();
		Enumeration<NetworkInterface> allNetInterfaces = null;
		allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
			Enumeration<?> addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				ip = (InetAddress) addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address && ip.getHostAddress().indexOf(".") != -1) {
					IPs.add(ip.getHostAddress());
				}
			}
		}
		return IPs;
	}    		

	/**
	 * 兼容Linux系统
	 * @return
	 */
	public static String getLocalIP() {
		String ip = "";
		try {
			Enumeration<?> e1 = (Enumeration<?>) NetworkInterface
					.getNetworkInterfaces();
			while (e1.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) e1.nextElement();
				Enumeration<?> e2 = ni.getInetAddresses();
				while (e2.hasMoreElements()) {
					InetAddress ia = (InetAddress) e2.nextElement();
					if (ia instanceof Inet6Address)
						continue;
					if (!ia.isLoopbackAddress()) {
						ip = ia.getHostAddress();
						break;
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
			return "";
		}
		return ip;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(IPUtil.getLocalhostIp());
	}

}
