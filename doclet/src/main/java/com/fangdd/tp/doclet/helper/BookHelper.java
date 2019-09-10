package com.fangdd.tp.doclet.helper;

import com.fangdd.tp.doclet.enums.ApiPositionEnum;
import com.fangdd.tp.doclet.exception.DocletException;
import com.fangdd.tp.doclet.pojo.*;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.MethodDoc;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author xuwenzhen
 * @date 18/1/9
 */
public class BookHelper {
    private static final Map<String, Chapter> CHAPTER_MAP = Maps.newHashMap();
    private static final Map<Chapter, List<Section>> SECTIONS_MAP = Maps.newHashMap();
    private static final Map<String, DubboInfo> INTERFACE_CLASS_MAP = Maps.newHashMap();
    private static final Map<String, String> MARKDOWN_MAP = Maps.newHashMap();
    private static Artifact artifact = null;

    /**
     * 当前处理的api类型
     */
    public static int currentApiType = 0;

    /**
     * 当前类里的@RequestMapping，有可能是父类里的
     */
    public static AnnotationDesc requestMappingAnnotation = null;

    /**
     * 当前分析的接口方法
     */
    private static MethodDoc currentMethod;

    /**
     * 当前分析的接口位置
     */
    private static ApiPositionEnum apiPosition;

    public static Chapter getChapter(String chapterName) {
        Chapter chapter = CHAPTER_MAP.get(chapterName);
        if (chapter != null) {
            return chapter;
        }
        chapter = new Chapter();
        // 默认排序值：100 + 序号
        chapter.setOrder(CHAPTER_MAP.size() + 100);
        chapter.setName(chapterName);
        List<Section> sections = Lists.newArrayList();
        chapter.setSections(sections);
        CHAPTER_MAP.put(chapterName, chapter);
        SECTIONS_MAP.put(chapter, sections);
        return chapter;
    }

    public static Section getSections(Chapter chapter, String sectionName) {
        if (Strings.isNullOrEmpty(sectionName)) {
            throw new DocletException("Book：" + chapter.getName() + "， sectionName不能为空！");
        }
        List<Section> sections = chapter.getSections();
        for (Section section : sections) {
            if (section.getName().equals(sectionName)) {
                return section;
            }
        }

        //没有找到，则新创建
        Section section = new Section();
        // 默认排序值：100 + 序号
        section.setOrder(sections.size() + 100);
        section.setName(sectionName);
        section.setChapter(chapter);

        List<Api> apis = Lists.newArrayList();
        section.setApis(apis);

        sections.add(section);
        return section;
    }

    public static Map<String, Chapter> getBooks() {
        return CHAPTER_MAP;
    }

    public static Api getApi(Section section, String key) {
        if (Strings.isNullOrEmpty(key)) {
            throw new DocletException(section.getChapter().getName() + ">" + section.getName() + "， code不能为空！");
        }
        List<Api> apis = section.getApis();
        for (Api api : apis) {
            if (api.getKey().equals(key)) {
                return api;
            }
        }

        //没有找到，则新创建
        Api api = new Api();
        // 默认排序值：100 + 序号
        api.setOrder(apis.size() + 100);
        api.setKey(key);
        api.setSection(section);

        apis.add(api);
        return api;
    }

    public static void setApiMethod(MethodDoc method) {
        currentMethod = method;
    }

    public static MethodDoc getCurrentMethod() {
        return currentMethod;
    }

    public static void setCurrentMethod(MethodDoc currentMethod) {
        BookHelper.currentMethod = currentMethod;
    }

    public static void setApiPosition(ApiPositionEnum apiPosition) {
        BookHelper.apiPosition = apiPosition;
    }

    public static ApiPositionEnum getApiPosition() {
        return apiPosition;
    }

    public static void setArtifact(Artifact artifact) {
        BookHelper.artifact = artifact;
    }

    public static Artifact getArtifact() {
        return BookHelper.artifact;
    }

    public static void addDubboInterface(String interfaceClass, DubboInfo dubboInfo) {
        INTERFACE_CLASS_MAP.put(interfaceClass, dubboInfo);
    }

    public static Map<String, DubboInfo> getDubboInterfaceSet() {
        return INTERFACE_CLASS_MAP;
    }

    public static DubboInfo getDubboInterface(String interfaceClass) {
        return INTERFACE_CLASS_MAP.get(interfaceClass);
    }

    public static void addMarkdownDoc(String key, String markdown) {
        MARKDOWN_MAP.put(key, markdown);
    }

    public static Map<String, String> getMarkdownDocMap() {
        return MARKDOWN_MAP;
    }

    public static void delSections(Chapter chapter, Section section) {
        chapter.getSections().remove(section);
    }

    public static void delChapter(Chapter chapter) {
        CHAPTER_MAP.remove(chapter.getName());
        SECTIONS_MAP.remove(chapter);
    }

    public static void sort(List<Chapter> chapterList) {
        if (!CollectionUtils.isEmpty(chapterList)) {
            Collections.sort(chapterList);
        }
        for (Chapter chapter : chapterList) {
            sortSections(chapter.getSections());
        }
    }

    private static void sortSections(List<Section> sections) {
        if (!CollectionUtils.isEmpty(sections)) {
            Collections.sort(sections);
        }
        for (Section section : sections) {
            List<Api> apis = section.getApis();
            if (!CollectionUtils.isEmpty(apis)) {
                Collections.sort(apis);
            }
        }
    }
}
