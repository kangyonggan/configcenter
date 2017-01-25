<#assign ctx="${(rca.contextPath)!''}">

<tr id="project-${project.id}">
    <td>${project.code}</td>
    <td>${project.name}</td>
    <td>${project.pushUrl}</td>
    <td><#include "delete.ftl"></td>
    <td><@c.relative_date datetime=project.createdTime/></td>
    <td>
        <div class="btn-group">
            <a href="${ctx}/dashboard/core/project/${project.id}/edit" data-toggle="modal" data-target="#myModal"
               data-backdrop="static" class="btn btn-inverse btn-xs">编辑</a>

        <#if project.pushUrl != '' || project.isDeleted == 1>
            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <#if project.pushUrl != ''>
                    <li>
                        <a href="${ctx}/dashboard/core/project/${project.id}/push" data-toggle="modal" data-target="#myModal"
                           data-backdrop="static" class="btn btn-inverse btn-xs">推送配置</a>
                    </li>
                </#if>
                <#if project.isDeleted == 1>
                    <li>
                        <a href="javascript:" data-role="project-remove" title="删除项目"
                           data-url="${ctx}/dashboard/core/project/${project.id}/remove">
                            物理删除
                        </a>
                    </li>
                </#if>
            </ul>

        </#if>
        </div>
    </td>
</tr>