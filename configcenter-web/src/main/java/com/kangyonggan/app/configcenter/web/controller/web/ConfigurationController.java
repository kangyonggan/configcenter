package com.kangyonggan.app.configcenter.web.controller.web;

import com.kangyonggan.app.configcenter.biz.service.ConfigurationService;
import com.kangyonggan.app.configcenter.model.vo.Configuration;
import com.kangyonggan.app.configcenter.web.controller.BaseController;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/1/25
 */
@RestController
@RequestMapping("configuration")
@Log4j2
public class ConfigurationController extends BaseController {

    @Autowired
    private ConfigurationService configurationService;

    /**
     * 获取系统配置
     *
     * @param proj
     * @param env
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Object> getConfigs(@RequestParam("proj") String proj, @RequestParam("env") String env) {
        Map<String, Object> resultMap = getResultMap();

        try {
            List<Configuration> configurations = configurationService.findProjectConfigurations(proj, env);
            resultMap.put("configs", configurations);
        } catch (Exception e) {
            log.error("获取项目配置异常", e);
            setResultMapFailure(resultMap);
        }
        return resultMap;
    }
}
