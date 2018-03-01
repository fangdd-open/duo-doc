实体 <code>${entity.name}</code>
<table>
    <thead>
    <tr>
        <th>名称</th>
        <th>类型</th>
        <th style="text-align:center">必填</th>
        <th>DEMO</th>
        <th>备注</th>
    </tr>
    </thead>
    <tbody>
    <#list entity.fields as field>
    <tr>
        <td>${field.name}</td>
        <td>${field.entityName}</td>
        <td style="text-align:center">${field.required?string('Y', 'N')}</td>
        <td>${field.demo!}</td>
        <td>${field.comment}</td>
    </tr>
    </#list>
    </tbody>
</table>