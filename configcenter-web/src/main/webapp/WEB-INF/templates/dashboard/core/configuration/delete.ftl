<#if configuration.isDeleted == 1>
<a href="javascript:" data-role="configuration-delete" title="恢复配置"
   data-url="${ctx}/dashboard/core/configuration/${configuration.id}/undelete">
    <span class="label label-danger arrowed-in">已删除</span>
</a>
<#else>
<a href="javascript:" data-role="configuration-delete" title="删除配置"
   data-url="${ctx}/dashboard/core/configuration/${configuration.id}/delete">
    <span class="label label-success arrowed-in">未删除</span>
</a>
</#if>