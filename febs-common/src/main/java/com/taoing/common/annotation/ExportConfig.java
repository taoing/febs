package com.taoing.common.annotation;

import org.apache.poi.hssf.util.HSSFColor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel导出项配置
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface ExportConfig {

    /**
     * 表头显示名(如: id字段显示为"编号")默认为字段名
     */
    String value() default "field";

    /**
     * 单元格宽度 默认-1(自动计算)
     */
    short width() default -1;

    /**
     * 将单元格值进行转换后再导出
     * 目前支持以下几种场景
     * 1. 固定的数值转换为字符串值(如: 1代表男, 2代表女)
     * 表达式: "s:1=男,2=女"
     *
     * 2. 数值对应的值需要查询数据库才能进行映射(
     * 实现com.taoing.util.poi.convert.ExpertConvert接口)
     *
     * 默认不启用
     */
    String convert() default "";

    /**
     *
     * @return 当前单元格的字体颜色(默认 HSSFColor.BLACK.index)
     */
    short color() default HSSFColor.BLACK.index;

    /**
     * 将单元格的值替换为当前配置的值:
     * 应用场景:
     * 密码字段导出为: "******"
     */
    String replace() default "";
}
