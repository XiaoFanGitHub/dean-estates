package com.zooms.dean.common.tool;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Properties操作类
 *
 * @author zhaolijin
 */
public class PropertiesTools {
    private static PropertiesTools pu;// 创建对象pu
    private static Hashtable<String, Properties> register = new Hashtable<String, Properties>();
    private static Logger log = Logger.getLogger(PropertiesTools.class);

    private PropertiesTools() {
        super();
    }

    /**
     * 取得PropertiesUtil的一个实例
     */
    public static PropertiesTools getInstance() {
        if (pu == null) {
            pu = new PropertiesTools();
        }
        return pu;
    }

    public static Properties getProperties(String fileName) {
        InputStream is = null;
        Properties p = null;
        try {
            p = (Properties) register.get(fileName);
            if (p == null) {
                try {
                    is = new FileInputStream(fileName);
                } catch (Exception e) {
                    if (fileName.startsWith("/")) {
                        is = PropertiesTools.class
                                .getResourceAsStream(fileName);
                    } else {
                        is = PropertiesTools.class.getResourceAsStream("/"
                                + fileName);
                    }
                }
                if (is == null) {
                    log.info("未找到名称为" + fileName + "的资源！");
                }
                p = new Properties();
                p.load(is);
                register.put(fileName, p);
            }
        } catch (Exception e) {
            log.error("读取properties时异常", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("读取properties关闭流时异常", e);
                }
            }
        }
        return p;
    }

    /**
     * 读取配置文件
     */
    public static Properties getProperties() {
        String fileName = "config.properties";
        return getProperties(fileName);
    }

    public static String getPropertyValue(String strKey) {
        Properties p = getProperties();
        try {
            return p.getProperty(strKey);
        } catch (Exception e) {
            log.error("读取properties时异常", e);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(PropertiesTools
                .getPropertyValue("aliyun_oss.endpoint"));
    }

}
