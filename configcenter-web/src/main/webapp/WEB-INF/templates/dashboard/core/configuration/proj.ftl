<#list projects as proj>
    <#if proj.code==configuration.projCode>
        ${proj.name}[${proj.code}]
    </#if>
</#list>