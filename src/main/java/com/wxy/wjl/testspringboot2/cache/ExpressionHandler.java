package com.wxy.wjl.testspringboot2.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 表达式处理器
 * @author chenjunyi
 * Created by chenjunyi on 2020/1/9
 */
@Slf4j
public class ExpressionHandler {

    /** 缓存初始容量 */
    private static final int                                  INIT_CAPACITY = 100;

    /** 缓存最大容量 */
    private static final int                                  MAX_CAPACITY  = 1000;

    /** 缓存自动刷新时间（秒） */
    private static final int                                  REFRESH_TIME  = 900;

    /** 使用guava-cache缓存session，减轻压力 */
    private static LoadingCache<String, Optional<Expression>> expressionCache;

    static {
        try {
            AviatorEvaluator.addStaticFunctions("mr", CustomizeExpression.class);
            expressionCache = CacheBuilder.newBuilder().initialCapacity(INIT_CAPACITY)
                .maximumSize(MAX_CAPACITY).expireAfterAccess(REFRESH_TIME, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Optional<Expression>>() {
                    @Override
                    public Optional<Expression> load(String expr) {
                        Expression expression = AviatorEvaluator.compile(expr);
                        return Optional.ofNullable(expression);
                    }
                });
        } catch (Exception e) {
            log.error("load mr expression error", e);
        }
    }

    private ExpressionHandler() {

    }

    /**
     * 执行表达式
     * @param expr   待执行的表达式
     * @param params 表达式执行参数
     * @return 表达式执行结果
     */
    public static Object execute(String expr, Map<String, Object> params) {
        try {
            Optional<Expression> optional = expressionCache.get(expr);
            Expression expression = optional.orElse(null);
            if (expression == null) {
                return null;
            }
            return expression.execute(params);
        } catch (Exception e) {
            log.error("expression [{}] execute error", expr, e);
            return null;
        }
    }

}
