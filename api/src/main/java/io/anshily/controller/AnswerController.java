package io.anshily.controller;
import io.anshily.base.core.Result;
import io.anshily.base.core.ResultGenerator;
import io.anshily.model.Answer;
import io.anshily.service.AnswerService;
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
@RequestMapping("/answer")
public class AnswerController {
    @Resource
    private AnswerService answerService;

    @PostMapping("/add")
    public Result add(@RequestBody Answer answer) {
        answerService.save(answer);
        return ResultGenerator.successResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Integer id) {
        answerService.deleteById(id);
        return ResultGenerator.successResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Answer answer) {
        answerService.update(answer);
        return ResultGenerator.successResult();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Answer answer = answerService.findById(id);
        return ResultGenerator.successResult(answer);
    }

    @GetMapping("/list")
    public Result list(PageBean<Answer> page) {
        PageHelper.startPage(page.getPageNum(),page.getSize());
        List<Answer> list = answerService.findAll();
        page.setList(list);
        return ResultGenerator.successResult(page);
    }

    @PostMapping("/custom/list")
    public Result customList(@RequestBody Answer answer) {
        PageBean<Answer> page = new PageBean<Answer>();
    //        PageHelper.startPage(page.getPageNum(),page.getSize());
    Condition condition = new Condition(Answer.class);
    Example.Criteria criteria = condition.createCriteria();
    criteria.andLike("id","%1%");
    List<Answer> list = answerService.findByCondition(condition);
        page.setList(list);
        return ResultGenerator.successResult(page);
    }
}
