package com.fangdd.tp.doclet;

/**
 * Created by xuwenzhen on 2019/6/12.
 */
public abstract class BaseTpDocTest {
    /**
     * maven仓库地址
     */
    protected static final String mavenRepositoryPath = "/Users/xuwenzhen/Software/apache-maven-3.6.0/repository/";

    /**
     * 获取依赖包
     * @return
     */
    protected abstract String[] getJars();

    protected String getLibs() {
        StringBuilder sb = new StringBuilder();
        for (String jar : getJars()) {
            if (sb.length() > 0) {
                sb.append(":");
            }
            sb.append(mavenRepositoryPath + jar);
        }
        return sb.toString();
    }
}
