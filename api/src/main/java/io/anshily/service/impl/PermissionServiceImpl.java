package io.anshily.service.impl;

import io.anshily.dao.PermissionMapper;
import io.anshily.model.Permission;
import io.anshily.service.PermissionService;
import io.anshily.base.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by anshi on 2019/08/02.
 */
@Service
@Transactional
public class PermissionServiceImpl extends AbstractService<Permission> implements PermissionService {
    @Resource
    private PermissionMapper swPermissionMapper;

}
