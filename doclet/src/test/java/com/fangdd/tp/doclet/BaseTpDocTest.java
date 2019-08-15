package com.fangdd.tp.doclet;

import java.io.File;

/**
 * @author xuwenzhen
 */
public abstract class BaseTpDocTest {
    /**
     * maven仓库地址
     */
    protected static final String mavenRepositoryPath = "/Users/xuwenzhen/Software/apache-maven-3.6.0/repository/";

    /**
     * 获取依赖包
     *
     * @return
     */
    protected abstract String[] getJars();

    protected abstract String getProjectPath();

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


    protected String getProjectSrcDirs() {
        String projectPath = getProjectPath();

        StringBuilder srcPath = new StringBuilder();
        File projectDir = new File(projectPath);

        File rootSrcDir = new File(projectDir.getAbsolutePath() + "/src/main/java");
        if (rootSrcDir.exists()) {
            srcPath.append(rootSrcDir.getAbsolutePath());
        }
        File[] fs = projectDir.listFiles();
        for (File f : fs) {
            if (f.isFile())
                continue;

            File srcDir = new File(f.getAbsolutePath() + "/src/main/java");
            if (!srcDir.exists())
                continue;

            if (srcPath.length() > 0) {
                srcPath.append(":");
            }
            srcPath.append(srcDir.getAbsolutePath());
        }
        return srcPath.toString();
    }
}
