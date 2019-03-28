package com.taoing.system.domain;

import com.google.common.base.MoreObjects;
import com.taoing.common.annotation.ExportConfig;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_dict")
public class Dict implements Serializable {

    private static final long serialVersionUID = -8857832124964792885L;

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "DICT_ID")
    @ExportConfig(value = "字典ID")
    private Long dictId;

    @Column(name = "KEYY")
    @ExportConfig(value = "字典Key")
    private String keyy;

    @Column(name = "VALUEE")
    @ExportConfig(value = "字典Value")
    private String valuee;

    @Column(name = "TABLE_NAME")
    @ExportConfig(value = "表名")
    private String tableName;

    @Column(name = "FIELD_NAME")
    @ExportConfig(value = "列名")
    private String fieldName;

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public String getKeyy() {
        return keyy;
    }

    public void setKeyy(String keyy) {
        this.keyy = keyy;
    }

    public String getValuee() {
        return valuee;
    }

    public void setValuee(String valuee) {
        this.valuee = valuee;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dictId", dictId)
                .add("keyy", keyy)
                .add("valuee", valuee)
                .add("tableName", tableName)
                .add("fieldName", fieldName)
                .toString();
    }
}

