package com.mine.gublog.utils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 反射工具类
 * (代码生成工具类)
 */
public class ClassUtil {
    private ClassUtil() {
    }

    /**
     * 类型赋值转换, 字段名需相同
     * 考虑到性能问题，只在开发阶段用来生成代码
     *
     * @param src
     * @param dstClass
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T, E> E transfer(T src, Class<E> dstClass) {
        E dst = null;
        Class srcClass = src.getClass();
        Field[] srcFields = srcClass.getDeclaredFields();
        Set<String> srcFieldSet = new HashSet<String>();
        for (Field field : srcFields) {
            srcFieldSet.add(field.getName());
        }
        try {
            dst = dstClass.newInstance();
            Field[] fields = dstClass.getDeclaredFields();
            for (Field field : fields) {
                if (srcFieldSet.contains(field.getName())) {
                    PropertyDescriptor readPd = new PropertyDescriptor(field.getName(), srcClass);
                    Method readMethod = readPd.getReadMethod();
                    Object value = readMethod.invoke(src);
//                    if (value != null) {
                    PropertyDescriptor writePd = new PropertyDescriptor(field.getName(), dstClass);
                    Method writeMethod = writePd.getWriteMethod();
                    writeMethod.invoke(dst, value);
//                    }
                    System.out.println(StringUtil.uncapitalize(dstClass.getSimpleName()) + "." + writeMethod.getName() + "(" + StringUtil.uncapitalize(srcClass.getSimpleName()) + "." + readMethod.getName() + "());");
                }
            }
        } catch (InstantiationException | IllegalAccessException | IntrospectionException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return dst;
    }

    public static void foreachField(Class clazz, Consumer function){
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            function.accept(field);
        }
    }

    private static Map<String, String> typeMap = new HashMap<String, String>();

    static {
        typeMap.put("String", "VARCHAR");
        typeMap.put("Long", "BIGINT");
        typeMap.put("Byte", "TINYINT");
        typeMap.put("Date", "TIMESTAMP");
    }

    /**
     * Mapper文件 ResultMap, ColumnList 生成
     *
     * @param clazz
     * @return
     */
    public static Document generateResultMap(Class clazz) {
        Document document = DocumentHelper.createDocument();
        // DOCTYPE
        document.setXMLEncoding("UTF-8");
        document.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd", null);
        // mapper
        Element mapper = document.addElement("mapper")
                .addAttribute("namespace", "Mapper");
        // resultMap
        Element resultMap = mapper.addElement("resultMap")
                .addAttribute("id", clazz.getSimpleName() + "ResultMap")
                .addAttribute("type", clazz.getName());
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder columnListStr = new StringBuilder();
        for (Field field : fields) {
            columnListStr.append(field.getName()).append(", ");
            if ("id".equals(field.getName())) {
                Element result = resultMap.addElement("id")
                        .addAttribute("column", StringUtil.fieldnameCovert(field.getName()))
                        .addAttribute("jdbcType", typeMap.get(field.getType().getSimpleName()))
                        .addAttribute("property", field.getName());
            } else {
                Element result = resultMap.addElement("result")
                        .addAttribute("column", StringUtil.fieldnameCovert(field.getName()))
                        .addAttribute("jdbcType", typeMap.get(field.getType().getSimpleName()))
                        .addAttribute("property", field.getName());
            }
        }
        // sql
        int length = columnListStr.length();
        Element sql = mapper.addElement("sql")
                .addAttribute("id", clazz.getSimpleName() + "ColumnList")
                .addText(columnListStr.delete(length > 0 ? length - 2 : 0, length).toString());
        return document;
    }

    /**
     * 格式化输出xml
     *
     * @param document
     * @param outputStream
     */
    public static void outputXml(Document document, OutputStream outputStream) {
        OutputFormat xmlFormat = new OutputFormat();
        // 设置换行 为false时输出的xml不分行
        xmlFormat.setNewlines(true);
        // 生成缩进
        xmlFormat.setIndent(true);
        // 指定缩进
        xmlFormat.setIndent("    ");
        //设置文件编码
        xmlFormat.setEncoding("UTF-8");

        XMLWriter xmlWriter = null;
        try {
            xmlWriter = new XMLWriter(outputStream, xmlFormat);
            xmlWriter.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (xmlWriter != null) {
                try {
                    xmlWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
