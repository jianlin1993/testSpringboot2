package com.wxy.wjl.testspringboot2.swagger2;


import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * swagger 生成MD文档
 */
public class SwaggerToMDUtil {


    public static void main(String[] args) throws Exception {
        createMDFromJson();
    }

    /**
     * 启动工程后再执行此方法，会在build/文件夹下生成md文件  访问url的形式
     * @throws Exception
     */
    public static void createMDFromURL() throws Exception{
        Path outputFile = Paths.get("ApiMDDocSwagger/swaggerOnline");
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();
        Swagger2MarkupConverter converter = Swagger2MarkupConverter.from(new URL("http://localhost:8081/v2/api-docs?group=controller"))
                .withConfig(config)
                .build();
        converter.toFile(outputFile);
    }


    /**
     * 此方法是从静态json文件生成MD文档，不需要启动工程
     * @throws Exception
     */
    public static void createMDFromJson() throws Exception{
        String out = "ApiMDDocSwagger/swaggerOffline";

        //生成markdown的配置
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .build();

        //获取swagger.json文件，输出到outputDir中
        Swagger2MarkupConverter converter = Swagger2MarkupConverter.from(Paths.get("src\\main\\resources\\static\\Gateway-openapi.json")).withConfig(config).build();
        converter.toFile(Paths.get(out));
    }

}
