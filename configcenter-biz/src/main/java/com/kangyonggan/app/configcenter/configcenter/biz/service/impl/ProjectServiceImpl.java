package com.kangyonggan.app.configcenter.configcenter.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.configcenter.configcenter.biz.service.ProjectService;
import com.kangyonggan.app.configcenter.configcenter.biz.util.StringUtil;
import com.kangyonggan.app.configcenter.configcenter.mapper.ProjectMapper;
import com.kangyonggan.app.configcenter.configcenter.model.annotation.CacheDeleteAll;
import com.kangyonggan.app.configcenter.configcenter.model.annotation.CacheGetOrSave;
import com.kangyonggan.app.configcenter.configcenter.model.annotation.LogTime;
import com.kangyonggan.app.configcenter.configcenter.model.constants.AppConstants;
import com.kangyonggan.app.configcenter.configcenter.model.vo.Project;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/25
 */
@Service
public class ProjectServiceImpl extends BaseService<Project> implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    @LogTime
    public List<Project> searchProjects(int pageNum, String name) {
        Example example = new Example(Project.class);

        if (StringUtils.isNotEmpty(name)) {
            example.createCriteria().andLike("name", StringUtil.toLikeString(name));
        }

        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return super.selectByExample(example);
    }

    @Override
    @LogTime
    public void saveProject(Project project) {
        super.insertSelective(project);
    }

    @Override
    @LogTime
    @CacheGetOrSave("project:id:{0}")
    public Project findProjectById(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    @LogTime
    @CacheDeleteAll("project")
    public void updateProject(Project project) {
        super.updateByPrimaryKeySelective(project);
    }

    @Override
    @LogTime
    @CacheDeleteAll("project")
    public void deleteProject(Long id) {
        super.deleteByPrimaryKey(id);
    }

    @Override
    @LogTime
    public boolean existsProjectCode(String code) {
        Project project = new Project();
        project.setCode(code);

        return projectMapper.selectCount(project) == 1;
    }

    @Override
    @LogTime
    @CacheGetOrSave("project:all")
    public List<Project> findAllProjects() {
        Project project = new Project();
        project.setIsDeleted(AppConstants.IS_DELETED_NO);

        return super.select(project);
    }
}
