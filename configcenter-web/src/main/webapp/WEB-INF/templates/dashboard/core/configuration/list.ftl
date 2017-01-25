<#assign ctx="${(rca.contextPath)!''}">
<#assign projCode = RequestParameters.projCode!'' />
<#assign environment = RequestParameters.environment!'' />
<#assign name = RequestParameters.name!'' />

<div class="page-header">
    <h1>
        配置列表
        <small class="pull-right">
            <a href="${ctx}/dashboard/core/configuration/create" class="btn btn-sm btn-inverse" data-toggle="modal" data-target="#myModal"
               data-backdrop="static">添加</a>
        </small>
    </h1>
</div>

<div class="space-10"></div>

<form class="form-inline" method="get">
    <div class="form-group">
        <select name="projCode" class="form-control">
            <option value="">所有项目</option>
            <#list projects as proj>
                <option value="${proj.code}" <#if projCode==proj.code>selected</#if>>${proj.name}</option>
            </#list>
        </select>
    </div>
    <div class="form-group">
        <select name="environment" class="form-control">
            <option value="">所有环境</option>
            <#list environments as e>
                <option value="${e.code}" <#if environment==e.code>selected</#if>>${e.value}</option>
            </#list>
        </select>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="name" value="${name}" placeholder="名"
               autocomplete="off"/>
    </div>

    <button class="btn btn-sm btn-inverse" data-toggle="search-submit">
        搜索
        <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
    </button>
</form>

<div class="space-10"></div>

<table id="configuration-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>项目</th>
        <th>环境</th>
        <th>名</th>
        <th>值</th>
        <th>描述</th>
        <th>逻辑删除</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?size gt 0>
        <#list page.list as configuration>
            <#include "table-tr.ftl"/>
        </#list>
    <#else>
    <tr>
        <td colspan="20">
            <div class="empty">暂无查询记录</div>
        </td>
    </tr>
    </#if>
    </tbody>
</table>
<@c.pagination url="#core/configuration" param="projCode=${projCode}&environemnt=${environment}&name=${name}"/>

<script src="${ctx}/static/app/js/dashboard/core/configuration/list.js"></script>
