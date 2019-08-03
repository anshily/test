package io.anshily.service.impl;

import io.anshily.dao.UserRoleMapper;
import io.anshily.model.UserRole;
import io.anshily.service.UserRoleService;
import io.anshily.base.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by anshi on 2019/08/02.
 */
@Service
@Transactional
public class UserRoleServiceImpl extends AbstractService<UserRole> implements UserRoleService {
    @Resource
    private UserRoleMapper swUserRoleMapper;

}
