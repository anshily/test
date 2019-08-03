package io.anshily.controller;
import io.anshily.base.core.Result;
import io.anshily.base.core.ResultGenerator;
import io.anshily.model.Answer;
import io.anshily.model.Question;
import io.anshily.service.AnswerService;
import io.anshily.service.QuestionService;
import io.anshily.base.core.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by anshi on 2019/08/01.
*/
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Resource
    private QuestionService questionService;
    @Resource
    private AnswerService answerService;

    @PostMapping("/add")
    public Result add(@RequestBody Question question) {
        questionService.save(question);
        return ResultGenerator.successResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Integer id) {
        questionService.deleteById(id);
        return ResultGenerator.successResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Question question) {
        questionService.update(question);
        return ResultGenerator.successResult();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Question question = questionService.findById(id);
        Condition condition = new Condition(Answer.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("question_bank_id", id);
        List<Answer> answers = answerService.findByCondition(condition);
        Map<String,Object> resMap = new HashMap<String,Object>();
        resMap.put("detail", question);
        resMap.put("answer", answers);
        return ResultGenerator.successResult(resMap);
    }

    @GetMapping("/list")
    public Result list(PageBean<Question> page) {
        PageHelper.startPage(page.getPageNum(),page.getSize());
        List<Question> list = questionService.findAll();
        page.setList(list);
        return ResultGenerator.successResult(page);
    }

    @PostMapping("/custom/list")
    public Result customList(@RequestBody Question question) {
        PageBean<Question> page = new PageBean<Question>();
    //        PageHelper.startPage(page.getPageNum(),page.getSize());
    Condition condition = new Condition(Question.class);
    Example.Criteria criteria = condition.createCriteria();
    criteria.andLike("id","%1%");
    List<Question> list = questionService.findByCondition(condition);
        page.setList(list);
        return ResultGenerator.successResult(page);
    }
}
