<#list environments as env>
    <#if env.code==configuration.environment>
    ${env.value}[${env.code}]
    </#if>
</#list>