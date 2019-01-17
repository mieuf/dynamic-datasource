package org.base.mieuf.platform.util;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * MybatisGenerator启动类，通过此启动类才能使用自定义的类型转换器
 */
public class MybatisGenerateApp {

    public static void main(String[] args) {
        List<String> warnings = new ArrayList<>();
        try {
            String configFilePath = System.getProperty("user.dir")
                    .concat("/src/main/resources/mybatis-generator/generatorConfig.xml");
            System.out.println("加载配置文件===" + configFilePath);
            boolean overwrite = true;
            File configFile = new File(configFilePath);
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                    callback, warnings);
            //ProgressCallback progressCallback = new VerboseProgressCallback();
            myBatisGenerator.generate(null);
            //myBatisGenerator.generate(progressCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
