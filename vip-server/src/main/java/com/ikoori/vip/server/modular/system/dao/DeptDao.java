package com.ikoori.vip.server.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ikoori.vip.server.common.node.ZTreeNode;

/**
 * 部门dao
 *
 * @author fengshuonan
 * @date 2017年2月17日20:28:58
 */
public interface DeptDao {

    /**
     * 获取ztree的节点列表
     *
     * @return
     * @date 2017年2月17日 下午8:28:43
     */
    List<ZTreeNode> tree();

    List<Map<String, Object>> list(@Param("condition") String condition);
}
