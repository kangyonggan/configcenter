<#assign ctx="${(rca.contextPath)!''}">

<tr id="configuration-${configuration.id}">
    <td><#include "proj.ftl"/></td>
    <td><#include "env.ftl"/></td>
    <td>${configuration.name}</td>
    <td>${configuration.value}</td>
    <td>${configuration.description}</td>
    <td><#include "delete.ftl"></td>
    <td><@c.relative_date datetime=configuration.createdTime/></td>
    <td>
        <div class="btn-group">
            <a href="${ctx}/dashboard/core/configuration/${configuration.id}/edit" data-toggle="modal"
               data-target="#myModal"
               data-backdrop="static" class="btn btn-inverse btn-xs">编辑</a>

        <#if configuration.isDeleted == 1>
            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="javascript:" data-role="configuration-remove" title="删除配置"
                       data-url="${ctx}/dashboard/core/configuration/${configuration.id}/remove">
                        物理删除
                    </a>
                </li>
            </ul>
        </#if>
        </div>
    </td>
</tr>