package io.anshily.service.impl;

import io.anshily.dao.RoleMapper;
import io.anshily.model.Role;
import io.anshily.service.RoleService;
import io.anshily.base.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by anshi on 2019/08/02.
 */
@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {
    @Resource
    private RoleMapper swRoleMapper;

}
