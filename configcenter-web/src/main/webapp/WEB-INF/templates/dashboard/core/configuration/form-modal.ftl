<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="${configuration.id???string('编辑配置', '添加新配置')}" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/core/configuration/${configuration.id???string('update', 'save')}">
    <#if configuration.id??>
        <input type="hidden" name="id" value="${configuration.id}"/>
    </#if>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>系统<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <select class="form-control" name="projCode">
                    <option value="">请选择项目</option>
                    <#list projects as proj>
                        <option value="${proj.code}" <#if configuration.id?? && configuration.projCode==proj.code>selected</#if>>${proj.name}[${proj.code}]</option>
                    </#list>
                </select>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>环境<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <select class="form-control" name="environment">
                    <option value="">请选择环境</option>
                    <#list environments as environment>
                        <option value="${environment.code}" <#if configuration.id?? && configuration.environment==environment.code>selected</#if>>${environment.value}[${environment.code}]</option>
                    </#list>
                </select>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>名<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "configuration.name" 'class="form-control" placeholder="请输入配置的名"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>值<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "configuration.value" 'class="form-control" placeholder="请输入配置的值"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>描述<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@spring.formInput "configuration.description" 'class="form-control" placeholder="请输入配置的描述"'/>
            </div>
        </div>
    </div>
</form>
</@override>

<@override name="modal-footer">
<button class="btn btn-sm" data-dismiss="modal">
    <i class="ace-icon fa fa-times"></i>
    <@spring.message "app.button.cancel"/>
</button>

<button class="btn btn-sm btn-inverse" id="submit" data-loading-text="正在保存..." data-toggle="form-submit"
        data-target="#modal-form">
    <i class="ace-icon fa fa-check"></i>
    <@spring.message "app.button.save"/>
</button>

<script src="${ctx}/static/app/js/dashboard/core/configuration/form-modal.js"></script>
</@override>

<@extends name="../../../modal-layout.ftl"/>