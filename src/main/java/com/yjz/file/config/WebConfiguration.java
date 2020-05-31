package com.yjz.file.config;

import com.yjz.file.constant.FileConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;

/**
 * @Description
 * @Author youjianzhao
 * @Date 2020/5/31 14:55
 * @Version
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.yjz.file")
public class WebConfiguration extends WebMvcConfigurerAdapter {

    /**
     * 上传文件解析器
     *
     * @return
     * @throws IOException
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setUploadTempDir(new FileSystemResource(FileConstant.TEMP_DIR));
        resolver.setMaxInMemorySize(0);
        // 单个文件限制300M
        resolver.setMaxUploadSizePerFile(300 * 1024 * 1024L);
        // 限制1G
        resolver.setMaxUploadSize(1024 * 1024 * 1024L);
        return resolver;
    }

    /**
     * 解决DELETE、PUT参数获取不到
     *
     * @return
     */
    @Bean
    public FormContentFilter formContentFilter() {
        return new FormContentFilter();
    }



    /**
     * 注入RestTemplate
     *
     * @return
     */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
