package io.anshily.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sw_permission")
public class Permission {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 权限名
     */
    private String name;

    /**
     * url
     */
    private String url;

    /**
     * 控制器
     */
    private String controller;

    /**
     * 新增时间
     */
    private Date add_time;

    /**
     * 更新时间
     */
    private Date update_time;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取权限名
     *
     * @return name - 权限名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置权限名
     *
     * @param name 权限名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取url
     *
     * @return url - url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置url
     *
     * @param url url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取控制器
     *
     * @return controller - 控制器
     */
    public String getController() {
        return controller;
    }

    /**
     * 设置控制器
     *
     * @param controller 控制器
     */
    public void setController(String controller) {
        this.controller = controller;
    }

    /**
     * 获取新增时间
     *
     * @return add_time - 新增时间
     */
    public Date getAdd_time() {
        return add_time;
    }

    /**
     * 设置新增时间
     *
     * @param add_time 新增时间
     */
    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 设置更新时间
     *
     * @param update_time 更新时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}