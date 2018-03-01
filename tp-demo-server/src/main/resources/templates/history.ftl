<#include "./common/header.ftl" />
<style>
    #page-div{
        margin: 10px;
        position: relative;
    }
    .pager{
        display: inline;
        position: absolute;
        right: 10px;
    }
    .pager>.page{
        padding: 2px 10px;
        border: 1px solid #999;
        margin: 1px;
    }
</style>
<a href="/">返回</a>
<h1>${doc.name!doc.artifactId} 历史版本列表</h1>
<div class="markdown-section">
    <table>
        <thead>
        <tr>
            <td>版本号</td>
            <td>说明</td>
            <td>文档创建时间</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <#list docLogList as item>
        <tr>
            <td>${doc.id}:${item.version!}</td>
            <td>${item.description!}</td>
            <td>${item.docVersion?number_to_datetime}</td>
            <td>
                <a href="/doc/${doc.id}/?version=${item.docVersion?c}">查看</a>
                <#if doc.docVersion != item.docVersion>
                    | <a class="del-link" version="${item.docVersion?c}" href="javascript:;">删除</a>
                </#if>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
<div id="page-div">
    <#assign pageCount=((total + pageSize - 1) / pageSize)?int />
    共${total}条记录 当前分页：${pageNo} / ${pageCount}
    <div class="pager">
        <#list 1..pageCount as c>
            <a href="/doc/${doc.id}/history?pageNo=${c}&pageSize=${pageSize}" class="page"> ${c} </a>
        </#list>

    </div>
</div>
<script src="/js/jquery.min.js"></script>
<script>
    var DOC_ID = '${doc.id}';
    $(function () {
        $('.del-link').click(function () {
            var version = $(this).attr('version');

            if(confirm('删除本历史版本文档不可恢复，是否继续？')) {
                $.ajax({
                    url: '/api/doc/' + DOC_ID + "/?version=" + version,
                    method: 'DELETE',
                    contentType: 'application/json; charset=utf-8'
                }).done(function (result) {
                    console.log(result);
                    if(result === '成功') {
                        alert('删除成功！');
                        window.location.reload();
                    } else {
                        alert(result);
                    }
                }).fail(function (e) {
                    console.error(e);
                    alert('发生未知错误');
                });
            }
        });
    });
</script>
</body>
</html>