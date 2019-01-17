package org.base.mieuf.project.bussiness.index.domain;

import org.base.mieuf.project.bussiness.index.IndexService;
import org.base.mieuf.project.bussiness.index.bean.ConfigInfo;
import org.base.mieuf.project.bussiness.index.dao.ConfigInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date 2019/1/16  11:26
 * @Description TODO
 **/
@Service
public class IndexServiceImpl implements IndexService {
    @Resource
    private ConfigInfoMapper configInfoMapper;

    @Override
    public List<ConfigInfo> getAllConfig() {
        return configInfoMapper.selectAllsuffix1();
    }
}
