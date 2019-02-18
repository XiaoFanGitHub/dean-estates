package com.zooms.dean.auth.client.mybatis;


import com.zooms.dean.auth.common.TokenPrincipalProvider;
import com.zooms.dean.auth.common.mybatis.CommonAuditingInterceptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * mybatis拦截器 拦截 新增 修改 删除
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Component
public class AuditingInterceptor extends CommonAuditingInterceptor {

    @Autowired
    public AuditingInterceptor(TokenPrincipalProvider tokenPrincipalProvider) {
        super(tokenPrincipalProvider);
    }

}