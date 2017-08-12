package com.ikoori.vip.server.modular.system.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ikoori.vip.common.persistence.dao.RelationMapper;
import com.ikoori.vip.common.persistence.dao.RoleMapper;
import com.ikoori.vip.common.persistence.model.Relation;
import com.ikoori.vip.common.persistence.model.Role;
import com.ikoori.vip.common.util.Convert;
import com.ikoori.vip.server.modular.system.dao.RoleDao;
import com.ikoori.vip.server.modular.system.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

    @Resource
    RoleMapper roleMapper;

    @Resource
    RoleDao roleDao;

    @Resource
    RelationMapper relationMapper;
    @Override
    @Transactional(readOnly = false)
    public void setAuthority(Integer roleId, String ids) {

        // 删除该角色所有的权限
        this.roleDao.deleteRolesById(roleId);

        // 添加新的权限
        for (Integer id : Convert.toIntArray(ids)) {
            Relation relation = new Relation();
            relation.setRoleid(roleId);
            relation.setMenuid(id);
            this.relationMapper.insert(relation);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void delRoleById(Integer roleId) {
        //删除角色
        this.roleMapper.deleteById(roleId);

        // 删除该角色所有的权限
        this.roleDao.deleteRolesById(roleId);
    }

	@Override
	public Role selectById(Long id) {
		// TODO Auto-generated method stub
		return roleMapper.selectById(id);
	}

}
