<h2>${liNo!}　[Dubbo] ${api.name}</h2>

<#if api.comment??>
<blockquote>
    <p>${api.comment}</p>
</blockquote>
</#if>

<h3>一、接口定义</h3>
<pre><code class="lang-javascript">${dubboApi?html}</code></pre>

<#if api.requestParams?? && api.requestParams?size &gt; 0>
    <#include "./doc.param.table.ftl" />
</#if>

<h3>二、Dubbo接口实现配置</h3>
<pre><code class="lang-javascript">@com.alibaba.dubbo.config.annotation.Service<#if api.version??>(version="${api.version}")</#if></code></pre>


<h3>三、引用</h3>

<h4>Maven引用</h4>
<pre><code class="lang-markup">&lt;dependency&gt;
    &lt;groupId&gt;${api.artifact.groupId!}&lt;/groupId&gt;
    &lt;artifactId&gt;${api.artifact.artifactId!}&lt;/artifactId&gt;
    &lt;version&gt;${api.artifact.version!}&lt;/version&gt;
&lt;/dependency&gt;</code></pre>

<h4>Gradle引用</h4>
<pre><code class="lang-lua">compile ${api.artifact.groupId!}:${api.artifact.artifactId!}:${api.artifact.version!}</code></pre>

<h3>四、Spring注解方式注入</h3>
<pre><code class="lang-javascript">@com.alibaba.dubbo.config.annotation.Reference(version="${api.version!}")
private ${icoCode};</code></pre>

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

