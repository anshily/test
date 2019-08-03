package io.anshily.model;

import javax.persistence.*;

@Table(name = "sw_question")
public class Question {
    @Id
    private Integer question_bank_id;

    /**
     * 问题类型id  对应问题类型表
     */
    private Integer question_type_id;

    /**
     * 问题内容
     */
    private String question_title;

    /**
     * 解析内容
     */
    private String explanation;

    /**
     * 问题的父类id
     */
    private Integer parent_id;

    /**
     * 问题标签id
     */
    private Integer question_bank_category_id;

    /**
     * 问题属于那一类型的题目（如A类）
     */
    private String name;

    /**
     * 难度等级（默认为1级）
     */
    private Integer difficulty_degree;

    /**
     * 是否关闭（0：未关闭，1：关闭）
     */
    private Integer isclose;

    /**
     * 是否删除（0：未删除，1：已删除）
     */
    private Integer isdelete;

    /**
     * @return question_bank_id
     */
    public Integer getQuestion_bank_id() {
        return question_bank_id;
    }

    /**
     * @param question_bank_id
     */
    public void setQuestion_bank_id(Integer question_bank_id) {
        this.question_bank_id = question_bank_id;
    }

    /**
     * 获取问题类型id  对应问题类型表
     *
     * @return question_type_id - 问题类型id  对应问题类型表
     */
    public Integer getQuestion_type_id() {
        return question_type_id;
    }

    /**
     * 设置问题类型id  对应问题类型表
     *
     * @param question_type_id 问题类型id  对应问题类型表
     */
    public void setQuestion_type_id(Integer question_type_id) {
        this.question_type_id = question_type_id;
    }

    /**
     * 获取问题内容
     *
     * @return question_title - 问题内容
     */
    public String getQuestion_title() {
        return question_title;
    }

    /**
     * 设置问题内容
     *
     * @param question_title 问题内容
     */
    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    /**
     * 获取解析内容
     *
     * @return explanation - 解析内容
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * 设置解析内容
     *
     * @param explanation 解析内容
     */
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    /**
     * 获取问题的父类id
     *
     * @return parent_id - 问题的父类id
     */
    public Integer getParent_id() {
        return parent_id;
    }

    /**
     * 设置问题的父类id
     *
     * @param parent_id 问题的父类id
     */
    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    /**
     * 获取问题标签id
     *
     * @return question_bank_category_id - 问题标签id
     */
    public Integer getQuestion_bank_category_id() {
        return question_bank_category_id;
    }

    /**
     * 设置问题标签id
     *
     * @param question_bank_category_id 问题标签id
     */
    public void setQuestion_bank_category_id(Integer question_bank_category_id) {
        this.question_bank_category_id = question_bank_category_id;
    }

    /**
     * 获取问题属于那一类型的题目（如A类）
     *
     * @return name - 问题属于那一类型的题目（如A类）
     */
    public String getName() {
        return name;
    }

    /**
     * 设置问题属于那一类型的题目（如A类）
     *
     * @param name 问题属于那一类型的题目（如A类）
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取难度等级（默认为1级）
     *
     * @return difficulty_degree - 难度等级（默认为1级）
     */
    public Integer getDifficulty_degree() {
        return difficulty_degree;
    }

    /**
     * 设置难度等级（默认为1级）
     *
     * @param difficulty_degree 难度等级（默认为1级）
     */
    public void setDifficulty_degree(Integer difficulty_degree) {
        this.difficulty_degree = difficulty_degree;
    }

    /**
     * 获取是否关闭（0：未关闭，1：关闭）
     *
     * @return isclose - 是否关闭（0：未关闭，1：关闭）
     */
    public Integer getIsclose() {
        return isclose;
    }

    /**
     * 设置是否关闭（0：未关闭，1：关闭）
     *
     * @param isclose 是否关闭（0：未关闭，1：关闭）
     */
    public void setIsclose(Integer isclose) {
        this.isclose = isclose;
    }

    /**
     * 获取是否删除（0：未删除，1：已删除）
     *
     * @return iddelete - 是否删除（0：未删除，1：已删除）
     */
    public Integer getIsdelete() {
        return isdelete;
    }

    /**
     * 设置是否删除（0：未删除，1：已删除）
     *
     * @param iddelete 是否删除（0：未删除，1：已删除）
     */
    public void setIddelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
}