package com.fangdd.tp.doclet;

import com.sun.tools.javadoc.Main;

import java.io.File;
import java.io.IOException;

/**
 * @auth ycoe
 * @date 18/1/5
 */
public class TpDocletTest {
    /**
     * maven仓库地址
     */
    private static final String mavenRepositoryPath = "/Users/ycoe/Software/maven/repository/";

    /**
     * 需要引入的包
     */
    private static final String[] jars = new String[]{
            "com/alibaba/dubbo/2.8.4/dubbo-2.8.4.jar",
            "org/springframework/boot/spring-boot/1.5.6.RELEASE/spring-boot-1.5.6.RELEASE.jar",
            "org/springframework/boot/spring-boot-autoconfigure/1.5.6.RELEASE/spring-boot-autoconfigure-1.5.6.RELEASE.jar",
            "org/springframework/spring-web/4.3.10.RELEASE/spring-web-4.3.10.RELEASE.jar",
            "com/alibaba/fastjson/1.2.29/fastjson-1.2.29.jar",
            "org/springframework/spring-context/3.2.9.RELEASE/spring-context-3.2.9.RELEASE.jar",
            "org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar",
            "org/springframework/spring-beans/4.3.10.RELEASE/spring-beans-4.3.10.RELEASE.jar",
            "org/mongodb/mongo-java-driver/3.4.2/mongo-java-driver-3.4.2.jar",
            "com/google/guava/guava/19.0/guava-19.0.jar",
            "javax/validation/validation-api/1.1.0.Final/validation-api-1.1.0.Final.jar",
            "com/fangdd/traffic/common-mongodb/2.3-SNAPSHOT/common-mongodb-2.3-SNAPSHOT.jar",
            "com/fasterxml/jackson/core/jackson-annotations/2.8.4/jackson-annotations-2.8.4.jar"
    };
    private static String projectPath;

    public static void main(String[] args) throws IOException {
        projectPath = System.getProperty("user.dir");

        String projectSrcDirs = getProjectSrcDirs();
        String[] docArgs = new String[]{
//                "-public",
//                "-d",
//                "/Users/ycoe/Projects/fdd/tp/tp-doc/tp-demo-server/target/docs",
                "com.fangdd.tp.controller.api",
//                "com.fangdd.tp.service.impl.test",
                "-subpackages",
                projectPath + "/server/target/tp-server.jar",
                "-cp",
                getLibs(),
                "-sourcepath",
                projectSrcDirs
        };

        Main.execute("myJavadoc", TpDoclet.class.getName(), docArgs);
    }

    private static String getLibs() {
        StringBuilder sb = new StringBuilder();
        for (String jar : jars) {
            if (sb.length() > 0) {
                sb.append(":");
            }
            sb.append(mavenRepositoryPath + jar);
        }
        return sb.toString();
    }


    public static String getProjectSrcDirs() {
        String projectPath = System.getProperty("user.dir");

        StringBuilder srcPath = new StringBuilder();
        File projectDir = new File(projectPath);
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