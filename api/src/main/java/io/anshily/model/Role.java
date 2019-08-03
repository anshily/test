package io.anshily.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sw_role")
public class Role {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 新增时间
     */
    private Date add_time;

    /**
     * 修改时间
     */
    private Date update_time;

    /**
     * 角色描述
     */
    private String info;

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
     * 获取角色名
     *
     * @return name - 角色名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名
     *
     * @param name 角色名
     */
    public void setName(String name) {
        this.name = name;
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
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 设置修改时间
     *
     * @param update_time 修改时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * 获取角色描述
     *
     * @return info - 角色描述
     */
    public String getInfo() {
        return info;
    }

    /**
     * 设置角色描述
     *
     * @param info 角色描述
     */
    public void setInfo(String info) {
        this.info = info;
    }
}