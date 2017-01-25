package com.kangyonggan.app.configcenter.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.configcenter.biz.util.StringUtil;
import com.kangyonggan.app.configcenter.biz.service.ConfigurationService;
import com.kangyonggan.app.configcenter.model.annotation.CacheDeleteAll;
import com.kangyonggan.app.configcenter.model.annotation.CacheGetOrSave;
import com.kangyonggan.app.configcenter.model.annotation.LogTime;
import com.kangyonggan.app.configcenter.model.constants.AppConstants;
import com.kangyonggan.app.configcenter.model.vo.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/25
 */
@Service
public class ConfigurationServiceImpl extends BaseService<Configuration> implements ConfigurationService {

    @Override
    @LogTime
    public List<Configuration> searchConfigurations(int pageNum, String projCode, String environment, String name) {
        Example example = new Example(Configuration.class);

        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(projCode)) {
            criteria.andEqualTo("projCode", projCode);
        }

        if (StringUtils.isNotEmpty(environment)) {
            criteria.andEqualTo("environment", environment);
        }

        if (StringUtils.isNotEmpty(name)) {
            criteria.andLike("name", StringUtil.toLikeString(name));
        }

        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return super.selectByExample(example);
    }

    @Override
    @LogTime
    public void saveConfiguration(Configuration configuration) {
        super.insertSelective(configuration);
    }

    @Override
    @LogTime
    @CacheGetOrSave("configuration:id:{0}")
    public Configuration findConfigurationById(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    @LogTime
    @CacheDeleteAll("configuration")
    public void updateConfiguration(Configuration configuration) {
        super.updateByPrimaryKeySelective(configuration);
    }

    @Override
    @LogTime
    @CacheDeleteAll("configuration")
    public void deleteConfiguration(Long id) {
        super.deleteByPrimaryKey(id);
    }

    @Override
    @LogTime
    @CacheGetOrSave("configuration:proj:{0}:env:{1}")
    public List<Configuration> findProjectConfigurations(String proj, String env) {
        Configuration configuration = new Configuration();
        configuration.setIsDeleted(AppConstants.IS_DELETED_NO);
        configuration.setProjCode(proj);
        configuration.setEnvironment(env);

        return super.select(configuration);
    }
}
