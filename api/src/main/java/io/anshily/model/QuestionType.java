package io.anshily.model;

import javax.persistence.*;

@Table(name = "sw_question_type")
public class QuestionType {
    @Id
    private Integer question_type_id;

    /**
     * 问题类型名称（如A类）
     */
    private String name;

    /**
     * 状态（1：可用，0：不可用）
     */
    private Integer statu;

    /**
     * @return question_type_id
     */
    public Integer getQuestion_type_id() {
        return question_type_id;
    }

    /**
     * @param question_type_id
     */
    public void setQuestion_type_id(Integer question_type_id) {
        this.question_type_id = question_type_id;
    }

    /**
     * 获取问题类型名称（如A类）
     *
     * @return name - 问题类型名称（如A类）
     */
    public String getName() {
        return name;
    }

    /**
     * 设置问题类型名称（如A类）
     *
     * @param name 问题类型名称（如A类）
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取状态（1：可用，0：不可用）
     *
     * @return statu - 状态（1：可用，0：不可用）
     */
    public Integer getStatu() {
        return statu;
    }

    /**
     * 设置状态（1：可用，0：不可用）
     *
     * @param statu 状态（1：可用，0：不可用）
     */
    public void setStatu(Integer statu) {
        this.statu = statu;
    }
}