package ${basePackage}.controller;
import ${basePackage}.base.core.Result;
import ${basePackage}.base.core.ResultGenerator;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import ${basePackage}.base.core.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by ${author} on ${date}.
*/
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller {
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @PostMapping("/add")
    public Result add(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
        return ResultGenerator.successResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Integer id) {
${modelNameLowerCamel}Service.deleteById(id);
        return ResultGenerator.successResult();
    }

    @PostMapping("/update")
    public Result update(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        return ResultGenerator.successResult();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.findById(id);
        return ResultGenerator.successResult(${modelNameLowerCamel});
    }

    @GetMapping("/list")
    public Result list(PageBean<${modelNameUpperCamel}> page) {
        PageHelper.startPage(page.getPageNum(),page.getSize());
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findAll();
        page.setList(list);
        return ResultGenerator.successResult(page);
    }

    @PostMapping("/custom/list")
    public Result customList(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        PageBean<${modelNameUpperCamel}> page = new PageBean<${modelNameUpperCamel}>();
    //        PageHelper.startPage(page.getPageNum(),page.getSize());
    Condition condition = new Condition(${modelNameUpperCamel}.class);
    Example.Criteria criteria = condition.createCriteria();
    criteria.andLike("id","%1%");
    List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findByCondition(condition);
        page.setList(list);
        return ResultGenerator.successResult(page);
    }
}
