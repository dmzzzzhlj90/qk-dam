package com.qk.dm.dataservice.rest.mapping;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.PathContainer;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringValueResolver;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.handler.MatchableHandlerMapping;
import org.springframework.web.servlet.handler.RequestMatchResult;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.util.ServletRequestPathUtils;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * rest 匹配path handler
 *
 * @author zhudaoming
 */
@Component
public class RestPatchHandlerMapping extends AbstractHandlerMethodMapping<RequestMappingInfo> implements MatchableHandlerMapping, EmbeddedValueResolverAware {
    private static final int MAX_PATTERNS = 1024;
    private final Map<String, PathPattern> pathPatternCache = new ConcurrentHashMap<>();
    private RequestMappingInfo.BuilderConfiguration config = new RequestMappingInfo.BuilderConfiguration();
    private final PathPatternParser parser = new PathPatternParser();

    private StringValueResolver embeddedValueResolver;
    @PostConstruct
    private void init(){
        config.setPatternParser(parser);
        this.setOrder(-1);
    }
    /**
     * 确定请求是否与给定模式匹配。 当 getPatternParser() 返回 null 时使用此方法，这意味着 HandlerMapping 正在使用字符串模式匹配。
     * @param request the current request
     * @param pattern the pattern to match
     * @return 请求匹配的结果，如果没有，则返回 null
     */
    @Override
    public RequestMatchResult match(@NonNull HttpServletRequest request,@NonNull String pattern) {
        PathPattern pathPattern = this.pathPatternCache.computeIfAbsent(pattern, value -> {
            Assert.isTrue(this.pathPatternCache.size() < MAX_PATTERNS, "Max size for pattern cache exceeded.");
            return this.parser.parse(pattern);
        });
        PathContainer path = ServletRequestPathUtils.getParsedRequestPath(request).pathWithinApplication();
        return (pathPattern.matches(path) ? new RequestMatchResult(pathPattern, path) : null);
    }

    /**
     * 给定类型是否是具有处理程序方法的处理handler。
     * 参数：
     * @param beanType  被检查的 bean 的类型
     * @return 如果这是处理程序类型，则为“true”，否则为“false”。
     */
    @Override
    protected boolean isHandler(@NonNull Class<?> beanType) {
        return (AnnotatedElementUtils.hasAnnotation(beanType, Controller.class) ||
                AnnotatedElementUtils.hasAnnotation(beanType, DataServiceMapping.class));
    }

    /**
     * 提供处理程序方法的映射。 不能为其提供映射的方法不是处理handler的方法。
     * @param method he method to provide a mapping for
     * @param handlerType the handler type, possibly a sub-type of the method's declaring class
     * @return the mapping, or null if the method is not mappe
     */
    @Override
    protected RequestMappingInfo getMappingForMethod(@NonNull Method method,@NonNull Class<?> handlerType) {
        RequestMappingInfo info = createRequestMappingInfo(method);
        if (info != null) {
            RequestMappingInfo typeInfo = createRequestMappingInfo(handlerType);
            if (typeInfo != null) {
                info = typeInfo.combine(info);
            }
        }
        return info;
    }

    /**
     * 检查映射是否与当前请求匹配，并返回一个（可能是新的）映射，其中包含与当前请求相关的条件。
     * @param info  the mapping to get a match for
     * @param request the current HTTP servlet request
     * @return the match, or null if the mapping doesn't match
     */
    @Override
    protected RequestMappingInfo getMatchingMapping(@NonNull RequestMappingInfo info,@NonNull HttpServletRequest request) {
        return info.getMatchingCondition(request);
    }

    /**
     * 返回用于匹配映射mappings进行排序的比较器。 返回的比较器应该排序“best”匹配度更高
     * @param request HttpServletRequest
     * @return RequestMappingInfo
     */
    @Override
    @NonNull
    protected Comparator<RequestMappingInfo> getMappingComparator(@NonNull HttpServletRequest request) {
        return (info1, info2) -> info1.compareTo(info2, request);
    }

    @Override
    public void setEmbeddedValueResolver(@NonNull StringValueResolver resolver) {
        this.embeddedValueResolver=resolver;

    }
    @Nullable
    private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
        DataServiceMapping dataServiceMapping = AnnotatedElementUtils.findMergedAnnotation(element, DataServiceMapping.class);
        RequestCondition<?> condition = (element instanceof Class ?
                getCustomTypeCondition((Class<?>) element) : getCustomMethodCondition((Method) element));
        return (dataServiceMapping != null ? createRequestMappingInfo(dataServiceMapping, condition) : null);
    }

    protected RequestMappingInfo createRequestMappingInfo(
            DataServiceMapping dataServiceMapping, @Nullable RequestCondition<?> customCondition) {

        RequestMappingInfo.Builder builder = RequestMappingInfo
                .paths(resolveEmbeddedValuesInPatterns(dataServiceMapping.path()))
                .methods(dataServiceMapping.method())
                .params(dataServiceMapping.params())
                .headers(dataServiceMapping.headers())
                .consumes(dataServiceMapping.consumes())
                .produces(dataServiceMapping.produces())
                .mappingName(dataServiceMapping.name());
        if (customCondition != null) {
            builder.customCondition(customCondition);
        }
        return builder.options(this.config).build();
    }
    protected String[] resolveEmbeddedValuesInPatterns(DataServiceEnum[] patterns) {
        if (this.embeddedValueResolver == null) {
            return (String[])Arrays.stream(patterns).map(Enum::toString).toArray();
        }
        else {
            String[] resolvedPatterns = new String[patterns.length];
            for (int i = 0; i < patterns.length; i++) {
                resolvedPatterns[i] = this.embeddedValueResolver.resolveStringValue(patterns[i].toString());
            }
            return resolvedPatterns;
        }
    }
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        return null;
    }
    @Nullable
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        return null;
    }

    @Override
    public void setOrder(int order) {
        super.setOrder(order);
    }
}
