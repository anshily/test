package io.anshily.service.impl;

import io.anshily.dao.AnswerMapper;
import io.anshily.model.Answer;
import io.anshily.service.AnswerService;
import io.anshily.base.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by anshi on 2019/08/01.
 */
@Service
@Transactional
public class AnswerServiceImpl extends AbstractService<Answer> implements AnswerService {
    @Resource
    private AnswerMapper swAnswerMapper;

}
