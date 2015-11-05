package com.markersdb.util;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


/**
 * Класс для поддержки MyBatis
 * Необходим для инстанцирования SqlSessionFactory
 */
public class MyBatisUtil
{
    private static SqlSessionFactory factory;

    private MyBatisUtil() {
    }

    static
    {
        Reader reader = null; //Resources.get
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            factory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static SqlSessionFactory getSqlSessionFactory()
    {
        return factory;
    }
}