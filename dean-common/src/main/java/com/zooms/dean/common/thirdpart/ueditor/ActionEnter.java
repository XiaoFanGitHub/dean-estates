package com.zooms.dean.common.thirdpart.ueditor;

import com.zooms.dean.common.cache.OssFileRedisTemplate;
import com.zooms.dean.common.thirdpart.ueditor.define.ActionMap;
import com.zooms.dean.common.thirdpart.ueditor.define.AppInfo;
import com.zooms.dean.common.thirdpart.ueditor.define.BaseState;
import com.zooms.dean.common.thirdpart.ueditor.define.State;
import com.zooms.dean.common.thirdpart.ueditor.hunter.FileManager;
import com.zooms.dean.common.thirdpart.ueditor.hunter.ImageHunter;
import com.zooms.dean.common.thirdpart.ueditor.upload.Uploader;
import com.zooms.dean.common.tool.AliyunOssTool;
import org.json.JSONException;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.zooms.dean.common.cache.OssFileRedisTemplate.OSS_FILE_CACHE_KEY;
import static com.zooms.dean.common.tool.AliyunOssTool.OSS_TOOL_KEY;


public class ActionEnter {

    private HttpServletRequest request = null;

    private String rootPath = null;
    private String contextPath = null;

    private String actionType = null;

    private static ConfigManager configManager = null;
    private AliyunOssTool ossTool = null;
    private HttpServletResponse response;
    private OssFileRedisTemplate ossFileRedisTemplate;

    public ActionEnter(HttpServletRequest request, HttpServletResponse response, String rootPath, AliyunOssTool ossTool, OssFileRedisTemplate ossFileRedisTemplate) {

        this.request = request;
        this.rootPath = rootPath;
        this.actionType = request.getParameter("action");
        this.contextPath = request.getContextPath();
        if (configManager == null) {
            configManager = ConfigManager.getInstance(this.rootPath, this.contextPath, request.getRequestURI());
        }
        this.ossTool = ossTool;
        this.response = response;
        this.ossFileRedisTemplate = ossFileRedisTemplate;
    }

    public String exec() throws JSONException {

        String callbackName = this.request.getParameter("callback");

        if (callbackName != null) {

            if (!validCallbackName(callbackName)) {
                return new BaseState(false, AppInfo.ILLEGAL).toJSONString();
            }
            this.response.setContentType("text/javascript");
            return callbackName + "(" + this.invoke() + ");";

        } else {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            return this.invoke();
        }

    }

    public String invoke() throws JSONException {

        if (actionType == null || !ActionMap.mapping.containsKey(actionType)) {
            return new BaseState(false, AppInfo.INVALID_ACTION).toJSONString();
        }

        if (configManager == null || !configManager.valid()) {
            return new BaseState(false, AppInfo.CONFIG_ERROR).toJSONString();
        }

        State state = null;

        int actionCode = ActionMap.getType(this.actionType);

        Map<String, Object> conf = null;

        switch (actionCode) {

            case ActionMap.CONFIG:
                return configManager.getAllConfig().toString();

            case ActionMap.UPLOAD_IMAGE:
            case ActionMap.UPLOAD_SCRAWL:
            case ActionMap.UPLOAD_VIDEO:
            case ActionMap.UPLOAD_FILE:
                conf = configManager.getConfig(actionCode);
                conf.put(OSS_TOOL_KEY, ossTool);
                conf.put(OSS_FILE_CACHE_KEY, ossFileRedisTemplate);
                state = new Uploader(request, conf).doExec();
                break;

            case ActionMap.CATCH_IMAGE:
                conf = configManager.getConfig(actionCode);
                conf.put(OSS_TOOL_KEY, ossTool);
                conf.put(OSS_FILE_CACHE_KEY, ossFileRedisTemplate);
                String[] list = this.request.getParameterValues((String) conf.get("fieldName"));
                state = new ImageHunter(conf).capture(list);
                break;

            case ActionMap.LIST_IMAGE:
            case ActionMap.LIST_FILE:
                conf = configManager.getConfig(actionCode);
                conf.put(OSS_TOOL_KEY, ossTool);
                conf.put(OSS_FILE_CACHE_KEY, ossFileRedisTemplate);
                int start = this.getStartIndex();
                state = new FileManager(conf).listFile(start);
                break;

        }

        return state.toJSONString();

    }

    public int getStartIndex() {

        String start = this.request.getParameter("start");

        try {
            return Integer.parseInt(start);
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * callback参数验证
     */
    public boolean validCallbackName(String name) {

        if (name.matches("^[a-zA-Z_]+[\\w0-9_]*$")) {
            return true;
        }

        return false;

    }

}