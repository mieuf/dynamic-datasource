package org.base.mieuf.platform.util;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;

/**
 * MybatisGenerator生成代码类型自定义转换
 */
public class MybatisGeneratorTypeResolver extends JavaTypeResolverDefaultImpl {

    /**
     * 将数据库的tinyint映射成Integer
     * LONGVARCHAR映射成String(默认也是)
     */
    public MybatisGeneratorTypeResolver() {
        super();
        super.typeMap.put(Types.TINYINT,
                new JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Integer.class.getName())));
        super.typeMap.put(Types.LONGVARCHAR,
                new JdbcTypeInformation("LONGVARCHAR", new FullyQualifiedJavaType(String.class.getName())));
    }
}
