<#assign modal_title="推送配置" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/core/project/push">
    <input type="hidden" name="id" value="${id}"/>
    <div class="control-group">
        <select class="form-control" name="env">
            <option value="">请选择推送的环境</option>
            <#list environments as environment>
                <option value="${environment.code}">${environment.value}[${environment.code}]</option>
            </#list>
        </select>
    </div>
</form>
</@override>

<@override name="modal-footer">
<button class="btn btn-sm" data-dismiss="modal">
    <i class="ace-icon fa fa-times"></i>
    <@spring.message "app.button.cancel"/>
</button>

<button class="btn btn-sm btn-inverse" id="submit" data-loading-text="正在保存..." data-toggle="form-submit" data-target="#modal-form">
    <i class="ace-icon fa fa-check"></i>
    <@spring.message "app.button.save"/>
</button>
<script src="${ctx}/static/app/js/dashboard/core/project/push-modal.js"></script>
</@override>

<@extends name="../../../modal-layout.ftl"/>