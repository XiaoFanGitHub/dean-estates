package com.zooms.dean.auth.client.mybatis;

import com.alibaba.druid.sql.SQLUtils;
import com.zooms.dean.auth.common.TokenPrincipal;
import com.zooms.dean.auth.common.TokenPrincipalProvider;
import com.zooms.dean.common.exceptions.DeanAuthException;
import com.zooms.dean.common.mybatis.DataTable;
import com.zooms.dean.common.mybatis.Table;
import com.zooms.dean.common.tool.StringUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

/**
 * 数据权限拦截器
 *
 * @author linfeng
 */
@Intercepts({
        @Signature(method = "query", type = Executor.class, args = {MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class})})
@Component
public class DataPermInterceptor implements Interceptor {

    private static final Logger LOG = LoggerFactory.getLogger(DataPermInterceptor.class);

    private Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);

    private final TokenPrincipalProvider tokenPrincipalProvider;

    @Autowired
    public DataPermInterceptor(TokenPrincipalProvider tokenPrincipalProvider) {
        this.tokenPrincipalProvider = tokenPrincipalProvider;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        if (tokenPrincipalProvider.getTokenPrincipal() == null) {
            return invocation.proceed();
        }
        Map<String, String> tableDomainForce = tokenPrincipalProvider.getTokenPrincipal().getTableAuth();
        if (tableDomainForce.isEmpty()) {
            return invocation.proceed();
        }

        final Object[] queryArgs = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) queryArgs[0];
        Object parameterObject = queryArgs[1];
        String tableName = null;
        if (parameterObject instanceof DataTable) {
            DataTable dataTable = (DataTable) parameterObject;
            tableName = dataTable.getTableName();
        }

        if (StringUtils.isBlank(tableName)) {
            Class<?> classObj;
            try {
                String id = mappedStatement.getId();
                String className = id.substring(0, id.lastIndexOf("."));
                classObj = Class.forName(className);
            } catch (Exception e) {
                return invocation.proceed();
            }

            Table tableSeg = classObj.getAnnotation(Table.class);
            if (tableSeg != null) {
                tableName = tableSeg.value();
            }
        }

        if (StringUtils.isBlank(tableName)) {
            return invocation.proceed();
        }

        if (StringUtils.isBlank(tableDomainForce.get(tableName))) {
            return invocation.proceed();
        }

        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);

        StringWriter out = new StringWriter();
        try {
            Template template = new Template(tableName, new StringReader(tableDomainForce.get(tableName)), configuration);
            Map<String, Object> root = new HashMap<>();
            root.put("user", tokenPrincipalProvider.getTokenPrincipal().getParams());
            template.process(root, out);
        } catch (IOException | TemplateException e) {
            LOG.error(e.getMessage(), e);
        }

        String condition = out.toString();
        if (StringUtils.isNotBlank(condition)) {
            String resultSql = SQLUtils.addCondition(boundSql.getSql(), condition, "mysql");
            BoundSql newBoundSql =
                    new BoundSql(mappedStatement.getConfiguration(), resultSql, boundSql.getParameterMappings(),
                            boundSql.getParameterObject());
            MappedStatement newMappedStatement =
                    copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
            queryArgs[0] = newMappedStatement;
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private String getCondition(TokenPrincipal principal, String source) throws DeanAuthException {

        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("user", principal);

        Stack<String> stack = new Stack<>();
        try {

            source = source.replaceAll("\\s*", "");
            if (!source.startsWith("[") || !source.endsWith("]")) {
                LOG.error("权限domainForce格式错误，需要首尾必须是[]");
                return "1=1";
            }
            source = source.substring(1, source.length() - 1);

            String[] conditions = source.split(";");

            for (int i = conditions.length - 1; i >= 0; i--) {
                String condition = conditions[i];
                if ("and".equalsIgnoreCase(condition) || "or".equalsIgnoreCase(condition.trim())) {
                    stack.push("(" + stack.pop() + " " + conditions[i] + " " + stack.pop() + ")");
                } else {
                    if (!condition.startsWith("(") || !condition.endsWith(")")) {
                        LOG.error("权限domainForce格式错误，例如：[and,(test,=,user.userId),(1,=,1)]");
                        return "1=1";
                    }
                    condition = condition.substring(1, condition.length() - 1);
                    String[] cons = condition.split(",");
                    if (cons.length != 3) {
                        throw new DeanAuthException("权限domainForce格式错误，例如test,=,user.userId");
                    }
                    if (cons[2].trim().startsWith("#")) {
                        String opera = cons[1].trim();
                        if ("in".equalsIgnoreCase(opera) || "not in".equalsIgnoreCase(opera)) {

                        }
                        parser.parseExpression(cons[2]).getValue(context);
                        cons[2] = "'" + parser.parseExpression(cons[2]).getValue(context, String.class) + "'";
                    } else {
                        if (!"null".equalsIgnoreCase(cons[2].trim())) {
                            cons[2] = "'" + cons[2] + "'";
                        }
                    }
                    stack.push(StringUtils.join(cons, " "));
                }
            }
        } catch (ParseException e) {
            throw new DeanAuthException(e.getMessage());
        }

        return stack.pop();
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder =
                new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            builder.keyProperty(ms.getKeyProperties()[0]);
        }
        // setStatementTimeout()
        builder.timeout(ms.getTimeout());

        // setStatementResultMap()
        builder.parameterMap(ms.getParameterMap());

        // setStatementResultMap()
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());

        // setStatementCache()
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    public static class BoundSqlSqlSource implements SqlSource {

        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

}
