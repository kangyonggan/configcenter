package com.kangyonggan.app.configcenter.configcenter.biz.service;

import com.kangyonggan.app.configcenter.configcenter.model.vo.Configuration;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/25
 */
public interface ConfigurationService {

    /**
     * 搜索配置
     *
     * @param pageNum
     * @param projCode
     * @param environment
     * @param name
     * @return
     */
    List<Configuration> searchConfigurations(int pageNum, String projCode, String environment, String name);

    /**
     * 保存配置
     *
     * @param configuration
     */
    void saveConfiguration(Configuration configuration);

    /**
     * 查找配置
     *
     * @param id
     * @return
     */
    Configuration findConfigurationById(Long id);

    /**
     * 更新配置
     *
     * @param configuration
     */
    void updateConfiguration(Configuration configuration);

    /**
     * 删除配置
     *
     * @param id
     */
    void deleteConfiguration(Long id);

    /**
     * 查找项目配置
     *
     * @param proj
     * @param env
     * @return
     */
    List<Configuration> findProjectConfigurations(String proj, String env);
}
