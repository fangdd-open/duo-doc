<#list chapters as chapter>
    <li class="chapter " data-level="${chapter?index}" data-path="/">
        <a href="javascript:;" class="no-link">${chapter?index + 1}. ${chapter.name}</a>
        <ul class="chapter">
            <#list chapter.sections as section>
                <li>
                    <a href="javascript:;" class="no-link">${chapter?index + 1}.${section?index + 1} ${section.name}</a>
                    <ul class="articles">
                        <#list section.apis as api>
                            <#if code?? && api.code==code>
                                <#assign liNo=(chapter?index + 1) + '.' + (section?index + 1) + '.' + (api?index + 1) />
                            </#if>
                            <li class="chapter${(api.code==code)?string(' active', '')}" id="${api.code}">
                                <a href="/doc/${docId}/?code=${api.code}">${chapter?index + 1}.${section?index + 1}.${api?index + 1} ${api.name}</a>
                            </li>
                        </#list>
                    </ul>
                </li>
            </#list>
        </ul>
    </li>
</#list>