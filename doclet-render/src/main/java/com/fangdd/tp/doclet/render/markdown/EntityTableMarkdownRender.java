package com.fangdd.tp.doclet.render.markdown;

import com.fangdd.tp.doclet.pojo.Entity;
import com.fangdd.tp.doclet.pojo.EntityRef;
import com.google.common.base.Strings;

import java.util.List;

/**
 * @author xuwenzhen
 * @date 18/1/15
 */
public class EntityTableMarkdownRender {
    public static String render(Entity entityRef) {
        List<EntityRef> fields = entityRef.getFields();
        if(fields == null || fields.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("| 参数 | 类型 | 是否必填 | Demo | 备注 |\n");
        sb.append("| :-- | :-- | :-- | :-- | :-- |\n");

        for (EntityRef er : fields) {
            sb.append("| ");
            sb.append(er.getName());
            sb.append(" | ");
            sb.append(er.getEntityName());
            sb.append(" | ");
            sb.append(er.isRequired() ? "Y" : "N");
            sb.append(" | ");
            sb.append(getDemo(er));
            sb.append(" | ");
            sb.append(er.getComment());
            sb.append(" |\n");
        }
        return sb.toString();
    }

    private static String getDemo(EntityRef er) {
        String demo = er.getDemo();
        if(!Strings.isNullOrEmpty(demo) && demo.contains("|")) {
            demo = demo.replaceAll("\\|", "\\\\|");
        }
        return demo;
    }
}
