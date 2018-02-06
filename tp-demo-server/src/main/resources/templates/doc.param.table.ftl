<#list api.requestParams as param>
    <#if !param.entityName?starts_with("java")>
        <#assign entity=entityMap[param.entityName] />
        <#if entity.primitive?? && !entity.primitive>
        参数 <code>${param.name}</code>
            <#include "./doc.entity.table.ftl" />
        </#if>
    </#if>
</#list>