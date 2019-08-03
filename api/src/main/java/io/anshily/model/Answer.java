package io.anshily.model;

import javax.persistence.*;

@Table(name = "sw_answer")
public class Answer {
    @Id
    private Integer question_item_id;

    /**
     * 答案对应的问题id
     */
    private Integer question_bank_id;

    /**
     * 答案内容
     */
    private String name;

    /**
     * 正确性（1：正确，2：错误）
     */
    private Integer result;

    /**
     * 选项（1,2，3,4）
     */
    private Integer index_number;

    /**
     * ABCD四个选项
     */
    private String index_letter;

    /**
     * @return question_item_id
     */
    public Integer getQuestion_item_id() {
        return question_item_id;
    }

    /**
     * @param question_item_id
     */
    public void setQuestion_item_id(Integer question_item_id) {
        this.question_item_id = question_item_id;
    }

    /**
     * 获取答案对应的问题id
     *
     * @return question_bank_id - 答案对应的问题id
     */
    public Integer getQuestion_bank_id() {
        return question_bank_id;
    }

    /**
     * 设置答案对应的问题id
     *
     * @param question_bank_id 答案对应的问题id
     */
    public void setQuestion_bank_id(Integer question_bank_id) {
        this.question_bank_id = question_bank_id;
    }

    /**
     * 获取答案内容
     *
     * @return name - 答案内容
     */
    public String getName() {
        return name;
    }

    /**
     * 设置答案内容
     *
     * @param name 答案内容
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取正确性（1：正确，2：错误）
     *
     * @return result - 正确性（1：正确，2：错误）
     */
    public Integer getResult() {
        return result;
    }

    /**
     * 设置正确性（1：正确，2：错误）
     *
     * @param result 正确性（1：正确，2：错误）
     */
    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getIndex_number() {
        return index_number;
    }

    public void setIndex_number(Integer index_number) {
        this.index_number = index_number;
    }


    /**
     * 获取ABCD四个选项
     *
     * @return index_letter - ABCD四个选项
     */
    public String getIndex_letter() {
        return index_letter;
    }

    /**
     * 设置ABCD四个选项
     *
     * @param index_letter ABCD四个选项
     */
    public void setIndex_letter(String index_letter) {
        this.index_letter = index_letter;
    }
}