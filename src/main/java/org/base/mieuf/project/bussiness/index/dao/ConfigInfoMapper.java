package org.base.mieuf.project.bussiness.index.dao;

import org.base.mieuf.project.bussiness.index.bean.ConfigInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ConfigInfo record);

    int insertSelective(ConfigInfo record);

    ConfigInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ConfigInfo record);

    int updateByPrimaryKey(ConfigInfo record);

    List<ConfigInfo> selectAllsuffix1();
}