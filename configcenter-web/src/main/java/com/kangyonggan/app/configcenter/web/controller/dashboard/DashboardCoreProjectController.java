package com.kangyonggan.app.configcenter.web.controller.dashboard;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.kangyonggan.app.configcenter.biz.service.ConfigurationService;
import com.kangyonggan.app.configcenter.biz.service.DictionaryService;
import com.kangyonggan.app.configcenter.biz.service.ProjectService;
import com.kangyonggan.app.configcenter.biz.util.HttpUtil;
import com.kangyonggan.app.configcenter.model.constants.DictionaryType;
import com.kangyonggan.app.configcenter.model.vo.Configuration;
import com.kangyonggan.app.configcenter.model.vo.Dictionary;
import com.kangyonggan.app.configcenter.model.vo.Project;
import com.kangyonggan.app.configcenter.web.controller.BaseController;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/1/8
 */
@Controller
@RequestMapping("dashboard/core/project")
@Log4j2
public class DashboardCoreProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 项目管理
     *
     * @param pageNum
     * @param name
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("CORE_PROJECT")
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                        @RequestParam(value = "name", required = false, defaultValue = "") String name,
                        Model model) {
        List<Project> projects = projectService.searchProjects(pageNum, name);
        PageInfo<Project> page = new PageInfo(projects);

        model.addAttribute("page", page);
        return getPathList();
    }

    /**
     * 添加项目
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("CORE_PROJECT")
    public String create(Model model) {
        model.addAttribute("project", new Project());
        return getPathFormModal();
    }

    /**
     * 保存项目
     *
     * @param project
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("CORE_PROJECT")
    public Map<String, Object> save(@ModelAttribute("project") @Valid Project project, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            projectService.saveProject(project);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 编辑项目
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("CORE_PROJECT")
    public String create(@PathVariable("id") Long id, Model model) {
        model.addAttribute("project", projectService.findProjectById(id));
        return getPathFormModal();
    }

    /**
     * 更新项目
     *
     * @param project
     * @param result
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("CORE_PROJECT")
    public Map<String, Object> update(@ModelAttribute("project") @Valid Project project, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            projectService.updateProject(project);
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
    @RequiresPermissions("CORE_PROJECT")
    public String delete(@PathVariable("id") Long id, @PathVariable("isDeleted") String isDeleted, Model model) {
        Project project = projectService.findProjectById(id);
        project.setIsDeleted((byte) (isDeleted.equals("delete") ? 1 : 0));
        projectService.updateProject(project);

        model.addAttribute("project", project);
        return getPathTableTr();
    }

    /**
     * 物理删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/remove", method = RequestMethod.GET)
    @RequiresPermissions("CORE_PROJECT")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable("id") Long id) {
        projectService.deleteProject(id);

        return getResultMap();
    }

    /**
     * 推送配置界面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/push", method = RequestMethod.GET)
    @RequiresPermissions("CORE_PROJECT")
    public String push(@PathVariable("id") Long id, Model model) {
        List<Dictionary> environments = dictionaryService.findDictionariesByType(DictionaryType.ENVIRONMENT.getType());

        model.addAttribute("id", id);
        model.addAttribute("environments", environments);
        return getPathRoot() + "/push-modal";
    }

    /**
     * 推送配置
     *
     * @param id
     * @param env
     * @return
     */
    @RequestMapping(value = "push", method = RequestMethod.POST)
    @RequiresPermissions("CORE_PROJECT")
    @ResponseBody
    public Map<String, Object> push(@RequestParam("id") Long id, @RequestParam("env") String env) {
        Map<String, Object> resultMap = getResultMap();
        Project project = projectService.findProjectById(id);

        if (project != null && StringUtils.isNotEmpty(project.getPushUrl())) {
            List<Configuration> configurations = configurationService.findProjectConfigurations(project.getCode(), env);
            String json = JSON.toJSONString(configurations);
            try {
                String data = URLEncoder.encode(json, "UTF-8");
                String result = HttpUtil.sendPost(project.getPushUrl(), "data=" + data);
                if (!"true".equals(result)) {
                    setResultMapFailure(resultMap, "推送失败，请稍后再试！");
                }
            } catch (Exception e) {
                log.error("推送配置失败", e);
                setResultMapFailure(resultMap);
            }
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

}
