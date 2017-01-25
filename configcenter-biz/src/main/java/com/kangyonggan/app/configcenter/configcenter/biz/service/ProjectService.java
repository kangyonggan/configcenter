package com.kangyonggan.app.configcenter.configcenter.biz.service;

import com.kangyonggan.app.configcenter.configcenter.model.vo.Project;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/1/25
 */
public interface ProjectService {

    /**
     * 搜索项目
     *
     * @param pageNum
     * @param name
     * @return
     */
    List<Project> searchProjects(int pageNum, String name);

    /**
     * 保存项目
     *
     * @param project
     */
    void saveProject(Project project);

    /**
     * 查找项目
     *
     * @param id
     * @return
     */
    Project findProjectById(Long id);

    /**
     * 更新项目
     *
     * @param project
     */
    void updateProject(Project project);

    /**
     * 物理删除
     *
     * @param id
     */
    void deleteProject(Long id);

    /**
     * 交易项目代码是否存在
     *
     * @param code
     * @return
     */
    boolean existsProjectCode(String code);

    /**
     * 查找所有项目
     *
     * @return
     */
    List<Project> findAllProjects();

}
