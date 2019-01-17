package org.base.mieuf.platform.conf;

import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2019/1/16  10:25
 * @Description druid过滤器 /filter
 **/
@Configuration
public class DruidFilter {

    @Bean
    public FilterRegistrationBean druidStatFilterBean() {
        FilterRegistrationBean druidStatFilterBean=new FilterRegistrationBean(new WebStatFilter());
        List<String> urlPattern=new ArrayList<>();
        urlPattern.add("/*");
        druidStatFilterBean.setUrlPatterns(urlPattern);
        Map<String,String> initParams=new HashMap<>();
        initParams.put("exclusions","*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        druidStatFilterBean.setInitParameters(initParams);
        return druidStatFilterBean;
    }
}
