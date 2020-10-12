package com.wxy.wjl.testspringboot2.swagger2;

import com.alibaba.fastjson.JSON;
import com.wxy.wjl.providerapi.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.swagger.schema.ApiModelPropertyPropertyBuilder;

import java.util.List;

/**
 * Swagger2匹配自定义注解
 */
@Component
@Order(1)
@Primary
public class Swagger2ToDict extends ApiModelPropertyPropertyBuilder {

    public Swagger2ToDict(DescriptionResolver descriptions) {
        super(descriptions);
    }
    @Override
    public void apply(ModelPropertyContext context) {
        super.apply(context);
        System.out.println("context"+JSON.toJSONString(context));
        System.out.println("beanPropertyDefinition"+JSON.toJSONString(context.getBeanPropertyDefinition().get()));
        //Optional类 isPresent 方法判断对象是否存在  存在返回true
        if (context.getAnnotatedElement().isPresent()) {
            // get 方法获取该对象
            ApiModelProperty model = AnnotationUtils.getAnnotation(context.getAnnotatedElement().get(), ApiModelProperty.class);
            if(model!=null){
                return;
            }
        }
        Dict dict=context.getBeanPropertyDefinition().get().getField().getAnnotation(Dict.class);
        if(dict!=null){
            if(dict.name()!=null && dict.name().length()>0){
                context.getBuilder().description(dict.name());
            }
        }

    }


}
