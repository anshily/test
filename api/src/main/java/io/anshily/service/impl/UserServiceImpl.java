package io.anshily.service.impl;

import io.anshily.dao.UserMapper;
import io.anshily.model.User;
import io.anshily.service.UserService;
import io.anshily.base.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by anshi on 2019/08/02.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper swUserMapper;

}
