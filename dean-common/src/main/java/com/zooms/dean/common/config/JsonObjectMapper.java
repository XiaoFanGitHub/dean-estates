package com.zooms.dean.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 *
 * @author linfeng
 */
public class JsonObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = 4881978614323035078L;

    public JsonObjectMapper() {

        // 不允许单引号
        this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, false);
        // 字段和值都加引号
        this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        this.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
        /*
         * 默认：false，底层的数据流(二进制数据持久化，如：图片，视频等)全部被output，若读取一个位置的字段，则抛出异常
         * true时，则忽略未定义
         */
        this.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
        // 数字也加引号
//        this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
//        this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
        // 空值处理为空串
        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
                if (value instanceof Number) {
                    gen.writeNumber(0);
                } else {
                    gen.writeString("");
                }
            }
        });

    }
}
