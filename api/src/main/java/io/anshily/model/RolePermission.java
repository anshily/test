package io.anshily.model;

import javax.persistence.*;

@Table(name = "sw_role_permission")
public class RolePermission {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色id
     */
    private Integer rid;

    /**
     * 权限id
     */
    private Integer pid;

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
     * 获取角色id
     *
     * @return rid - 角色id
     */
    public Integer getRid() {
        return rid;
    }

    /**
     * 设置角色id
     *
     * @param rid 角色id
     */
    public void setRid(Integer rid) {
        this.rid = rid;
    }

    /**
     * 获取权限id
     *
     * @return pid - 权限id
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置权限id
     *
     * @param pid 权限id
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }
}