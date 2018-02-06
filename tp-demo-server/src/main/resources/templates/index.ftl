<#include "./common/header.ftl" />
<nav role="navigation">
    <ul class="summary">
    <#list docs as doc>
        <li class="book">
            <a href="/doc/${doc.id}/">
            <h3>${doc.name!doc.artifactId}</h3>
                <span>${doc.id + ':' + doc.version!}</span>
                <span>[更新时间：${doc.docVersion?number_to_datetime}]</span>
            </a>
            <p>${doc.description!}</p>
        </li>
    </#list>
    </ul>
</nav>
<#include "./common/footer.ftl" />