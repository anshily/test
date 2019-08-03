package io.anshily.service.impl;

import io.anshily.dao.RolePermissionMapper;
import io.anshily.model.RolePermission;
import io.anshily.service.RolePermissionService;
import io.anshily.base.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by anshi on 2019/08/02.
 */
@Service
@Transactional
public class RolePermissionServiceImpl extends AbstractService<RolePermission> implements RolePermissionService {
    @Resource
    private RolePermissionMapper swRolePermissionMapper;

}
