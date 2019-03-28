package com.taoing.system.domain;

import com.taoing.common.annotation.ExportConfig;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class Menu implements Serializable {

    private static final long serialVersionUID = 7368996275919260647L;

    public static final String TYPE_MENU = "0";
    public static final String TYPE_BUTTON = "1";

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "MENU_ID")
    @ExportConfig(value = "编号")
    private Long menuId;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name = "MENU_NAME")
    @ExportConfig(value = "名称")
    private String menuName;

    @Column(name = "URL")
    @ExportConfig(value = "地址")
    private String url;

    @Column(name = "PERMS")
    @ExportConfig(value = "权限标识")
    private String perms;

    @Column(name = "ICON")
    @ExportConfig(value = "图标")
    private String icon;

    @Column(name = "TYPE")
    @ExportConfig(value = "类型", convert = "s:0=菜单,1=按钮")
    private String type;

    @Column(name = "ORDER_NUM")
    private Long orderNum;

    @Column(name = "CREATE_TIME")
    @ExportConfig(
            value = "创建时间",
            convert = "c:com.taoing.common.utils.poi.convert.TimeConvert"
    )
    private Date createTime;

    @Column(name = "MODIFY_TIME")
    private Date modifyTime;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? "" : menuName.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? "" : url.trim();
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms == null ? "" : perms.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? "" : icon.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? "" : type.trim();
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
