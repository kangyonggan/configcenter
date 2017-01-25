package com.kangyonggan.app.configcenter.configcenter.web.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.app.configcenter.configcenter.biz.service.ConfigurationService;
import com.kangyonggan.app.configcenter.configcenter.biz.service.DictionaryService;
import com.kangyonggan.app.configcenter.configcenter.biz.service.ProjectService;
import com.kangyonggan.app.configcenter.configcenter.model.constants.DictionaryType;
import com.kangyonggan.app.configcenter.configcenter.model.vo.Configuration;
import com.kangyonggan.app.configcenter.configcenter.model.vo.Dictionary;
import com.kangyonggan.app.configcenter.configcenter.model.vo.Project;
import com.kangyonggan.app.configcenter.configcenter.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/1/8
 */
@Controller
@RequestMapping("dashboard/core/configuration")
public class DashboardCoreConfigurationController extends BaseController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 配置管理
     *
     * @param pageNum
     * @param projCode
     * @param environment
     * @param name
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("CORE_CONFIGURATION")
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                        @RequestParam(value = "projCode", required = false, defaultValue = "") String projCode,
                        @RequestParam(value = "environment", required = false, defaultValue = "") String environment,
                        @RequestParam(value = "name", required = false, defaultValue = "") String name,
                        Model model) {
        List<Configuration> configurations = configurationService.searchConfigurations(pageNum, projCode, environment, name);
        PageInfo<Configuration> page = new PageInfo(configurations);
        List<Project> projects = projectService.findAllProjects();
        List<Dictionary> environments = dictionaryService.findDictionariesByType(DictionaryType.ENVIRONMENT.getType());

        model.addAttribute("page", page);
        model.addAttribute("projects", projects);
        model.addAttribute("environments", environments);
        return getPathList();
    }

    /**
     * 添加配置
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("CORE_CONFIGURATION")
    public String create(Model model) {
        List<Project> projects = projectService.findAllProjects();
        List<Dictionary> environments = dictionaryService.findDictionariesByType(DictionaryType.ENVIRONMENT.getType());

        model.addAttribute("configuration", new Configuration());
        model.addAttribute("projects", projects);
        model.addAttribute("environments", environments);
        return getPathFormModal();
    }

    /**
     * 保存配置
     *
     * @param configuration
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("CORE_CONFIGURATION")
    public Map<String, Object> save(@ModelAttribute("configuration") @Valid Configuration configuration, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            configurationService.saveConfiguration(configuration);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 编辑配置
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("CORE_CONFIGURATION")
    public String create(@PathVariable("id") Long id, Model model) {
        List<Project> projects = projectService.findAllProjects();
        List<Dictionary> environments = dictionaryService.findDictionariesByType(DictionaryType.ENVIRONMENT.getType());

        model.addAttribute("configuration", configurationService.findConfigurationById(id));
        model.addAttribute("projects", projects);
        model.addAttribute("environments", environments);
        return getPathFormModal();
    }

    /**
     * 更新配置
     *
     * @param configuration
     * @param result
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("CORE_CONFIGURATION")
    public Map<String, Object> update(@ModelAttribute("configuration") @Valid Configuration configuration, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            configurationService.updateConfiguration(configuration);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 删除/恢复
     *
     * @param id
     * @param isDeleted
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/{isDeleted:\\bundelete\\b|\\bdelete\\b}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @RequiresPermissions("CORE_CONFIGURATION")
    public String delete(@PathVariable("id") Long id, @PathVariable("isDeleted") String isDeleted, Model model) {
        Configuration configuration = configurationService.findConfigurationById(id);
        configuration.setIsDeleted((byte) (isDeleted.equals("delete") ? 1 : 0));
        configurationService.updateConfiguration(configuration);
        List<Project> projects = projectService.findAllProjects();
        List<Dictionary> environments = dictionaryService.findDictionariesByType(DictionaryType.ENVIRONMENT.getType());

        model.addAttribute("configuration", configuration);
        model.addAttribute("projects", projects);
        model.addAttribute("environments", environments);
        return getPathTableTr();
    }

    /**
     * 物理删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/remove", method = RequestMethod.GET)
    @RequiresPermissions("CORE_CONFIGURATION")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable("id") Long id) {
        configurationService.deleteConfiguration(id);

        return getResultMap();
    }

}
