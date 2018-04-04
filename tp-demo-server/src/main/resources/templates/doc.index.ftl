<#if version??>
<li style="color:red;">当前文档版本：${version?number_to_datetime}</li>
</#if>

<#if markdownDocMap['README']??>
    <li class="chapter" data-level="0" data-path="/doc/${docId}/README.md">
        <a href="/doc/${docId}/README.md">[MD]文档概要</a>
    </li>
</#if>

<#list chapters as chapter>
    <li class="chapter " data-level="${chapter?index}" data-path="/">
    <#if markdownDocMap[chapter.name]??>
        <a href="/doc/${docId}/${chapter.name}.md">[MD]${chapter?index + 1}. ${chapter.name}</a>
    <#else >
        <span href="javascript:;" class="no-link">${chapter?index + 1}. ${chapter.name}</span>
    </#if>
        <ul class="chapter">
            <#list chapter.sections as section>
                <li>
                <#if markdownDocMap[chapter.name + '.' + section.name]??>
                    <a href="/doc/${docId}/${chapter.name}.${section.name}.md" class="no-link">${chapter?index + 1}.${section?index + 1} [MD]${section.name}</a>
                <#else >
                    <span href="javascript:;" class="no-link">${chapter?index + 1}.${section?index + 1} ${section.name}</span>
                </#if>
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