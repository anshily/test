package io.anshily.controller;
import io.anshily.base.core.Result;
import io.anshily.base.core.ResultGenerator;
import io.anshily.model.QuestionType;
import io.anshily.service.QuestionTypeService;
import io.anshily.base.core.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by anshi on 2019/08/01.
*/
@RestController
@RequestMapping("/question/type")
public class QuestionTypeController {
    @Resource
    private QuestionTypeService questionTypeService;

    @PostMapping("/add")
    public Result add(@RequestBody QuestionType questionType) {
        questionTypeService.save(questionType);
        return ResultGenerator.successResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Integer id) {
        questionTypeService.deleteById(id);
        return ResultGenerator.successResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody QuestionType questionType) {
        questionTypeService.update(questionType);
        return ResultGenerator.successResult();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        QuestionType questionType = questionTypeService.findById(id);
        return ResultGenerator.successResult(questionType);
    }

    @GetMapping("/list")
    public Result list(PageBean<QuestionType> page) {
        PageHelper.startPage(page.getPageNum(),page.getSize());
        List<QuestionType> list = questionTypeService.findAll();
        page.setList(list);
        return ResultGenerator.successResult(page);
    }

    @PostMapping("/custom/list")
    public Result customList(@RequestBody QuestionType questionType) {
        PageBean<QuestionType> page = new PageBean<QuestionType>();
    //        PageHelper.startPage(page.getPageNum(),page.getSize());
    Condition condition = new Condition(QuestionType.class);
    Example.Criteria criteria = condition.createCriteria();
    criteria.andLike("id","%1%");
    List<QuestionType> list = questionTypeService.findByCondition(condition);
        page.setList(list);
        return ResultGenerator.successResult(page);
    }
}
