package com.zooms.dean.auth.client;


import com.zooms.dean.auth.common.bean.AppItem;
import com.zooms.dean.common.tool.JsonUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.Scanner;

/**
 * @author linfeng
 * @since 2018.04.04
 */
@Component
public class JsonConfig {

    private String data = "classpath:data.json";

    private static AppItem appItem;

    /**
     * 读取文件类容为字符串
     *
     * @param file
     * @return
     */
    private String jsonRead(File file) {

        StringBuilder buffer = new StringBuilder();
        try (Scanner scanner = new Scanner(file, "utf-8")) {

            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (Exception ignored) {

        }
        return buffer.toString();

    }

    public AppItem getData() {
        if (appItem == null) {
            try {
                File file = ResourceUtils.getFile(data);
                if (!file.exists()) {
                    return null;
                }
                appItem = JsonUtils.getInstance().fromJson(jsonRead(file), AppItem.class);
                return appItem;
            } catch (Exception e) {
                return null;
            }
        } else {
            return appItem;
        }
    }

}
