<#if version??>
<li style="color:red;">当前文档版本：${version?number_to_datetime}</li>
</#if>
<#list chapters as chapter>
    <li class="chapter " data-level="${chapter?index}" data-path="/">
        <a href="javascript:;" class="no-link">${chapter?index + 1}. ${chapter.name}</a>
        <ul class="chapter">
            <#list chapter.sections as section>
                <li>
                    <a href="javascript:;" class="no-link">${chapter?index + 1}.${section?index + 1} ${section.name}</a>
                    <ul class="articles">
                        <#list section.apis as api>
                            <#if key?? && api.key??>
                                <#if key?? && api.key==key>
                                    <#assign liNo=(chapter?index + 1) + '.' + (section?index + 1) + '.' + (api?index + 1) />
                                </#if>
                                <li class="chapter${(api.key==key)?string(' active', '')}" id="${api.key}">
                                    <a href="/doc/${docId}/?key=${api.key}<#if version??>&version=${version?c}</#if>">
                                    ${chapter?index + 1}.${section?index + 1}.${api?index + 1} [${(api.type == 0)?string('Rest', 'Dubbo')}]${api.name}
                                    </a>
                                </li>
                            <#else >
                                <#if code?? && api.code==code>
                                    <#assign liNo=(chapter?index + 1) + '.' + (section?index + 1) + '.' + (api?index + 1) />
                                </#if>
                                <li class="chapter${(api.code==code)?string(' active', '')}" id="${api.code}">
                                    <a href="/doc/${docId}/?code=${api.code}<#if version??>&version=${version?c}</#if>">
                                    ${chapter?index + 1}.${section?index + 1}.${api?index + 1} [${(api.type == 0)?string('Rest', 'Dubbo')}]${api.name}
                                    </a>
                                </li>
                            </#if>
                        </#list>
                    </ul>
                </li>
            </#list>
        </ul>
    </li>
</#list>