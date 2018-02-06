<h2>${liNo!}　[RestFul] ${api.name}</h2>

<#if api.comment??>
<blockquote>
    <p>${api.comment}</p>
</blockquote>
</#if>

<#if (api.paths?size==1)>
    <h3>接口：<code>${api.paths[0]}</code></h3>
<#else >
    <h3>接口：</h3>
    <p>
        <#list api.paths as path><code>${path}</code><#sep ><br></#list>
    </p>
</#if>

<#if api.methods??>
    <#if (api.methods?size==1) >
        <h3>方法：<code>${api.methods[0]}</code></h3>
    <#else>
        <h3>方法：
            <#list api.methods as method><code>${method}</code><#sep > / </#list>
        </h3>
    </#if>
<#else>
    <h3>方法：<code>GET</code> / <code>POST</code></h3>
</#if>

<#if api.requestParams?? && api.requestParams?size &gt; 0>
<h3>请求参数：</h3>
<table>
    <thead>
    <tr>
        <th>名称</th>
        <th>类型</th>
        <th style="text-align:center">来源</th>
        <th style="text-align:center">必填</th>
        <th>备注</th>
    </tr>
    </thead>
    <tbody>
        <#list api.requestParams as param>
        <tr>
            <td>${param.name}</td>
            <td>${param.entityName}</td>
            <td style="text-align:center">--</td>
            <td style="text-align:center">${param.required?string('Y', 'N')}</td>
            <td>${param.comment!}</td>
        </tr>
        </#list>
    </tbody>
</table>

    <#include "./doc.param.table.ftl" />
</#if>

<#if api.response??>
<h3>五、响应</h3>
    <#if api.response.comment??>
    <blockquote>
        <p>${api.response.comment}</p>
    </blockquote>
    </#if>
<p><code>${api.response.entityName?html}</code></p>
<pre><code class="lang-javascript">${response?html}</code></pre>
</#if>


