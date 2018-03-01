<#include "./common/header.ftl" />
<style>
    nav{
    }
    nav>ul{
        list-style-type: none;
        margin: 0;
        padding: 0;
    }
    nav>ul>li{
        width: 33%!important;
        height: 150px!important;
        float: left;
        padding: 10px;
    }
    nav>ul>li:hover{
        background: #EEE;
    }
    nav>ul>li>p>.time{
        color: #999;
    }
</style>
<nav role="navigation">
    <ul class="summary">
    <#list docs as doc>
        <li class="book">
            <a href="/doc/${doc.id}/"><h3>${doc.name!doc.artifactId}</h3></a>
            <p>
                ${doc.id + ':' + doc.version!}

            </p>

            <p>${doc.description!}</p>
            <p>
                <span class="time">${doc.docVersion?number_to_datetime}</span>
                <a href="/doc/${doc.id}/history">历史版本</a>
            </p>
        </li>
    </#list>
    </ul>
</nav>
<#include "./common/footer.ftl" />