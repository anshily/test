package io.anshily.service.impl;

import io.anshily.dao.QuestionTypeMapper;
import io.anshily.model.QuestionType;
import io.anshily.service.QuestionTypeService;
import io.anshily.base.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by anshi on 2019/08/01.
 */
@Service
@Transactional
public class QuestionTypeServiceImpl extends AbstractService<QuestionType> implements QuestionTypeService {
    @Resource
    private QuestionTypeMapper swQuestionTypeMapper;

}
