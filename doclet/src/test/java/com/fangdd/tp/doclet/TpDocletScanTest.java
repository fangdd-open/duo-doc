package com.fangdd.tp.doclet;

import com.sun.tools.javadoc.Main;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author ycoe
 * @date 18/1/5
 */
public class TpDocletScanTest extends BaseTpDocTest {
    private static final String BASE_DIR = "/Users/xuwenzhen/Projects/fdd/c/scanengine-dp-service/";

    /**
     * 需要引入的包
     */
    private static final String[] JARS = new String[]{
            "com/alibaba/dubbo/2.8.5/dubbo-2.8.5.jar",
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

    @Test
    public void test() throws IOException {
        projectPath = System.getProperty(DocletConfig.USER_DIR);

        String projectSrcDirs = getProjectSrcDirs();
        System.out.println("扫描目录：" + projectSrcDirs);

        System.setProperty("basedir", getProjectPath());
        String[] docArgs = new String[]{
                "com.fangdd.tp.dict.service.impl",

                "-subpackages",
                projectPath + "/server/target/tp-server.jar",
                "-cp",
                getLibs(),
                "-sourcepath",
                projectSrcDirs
        };

        Main.execute("myJavadoc", TpDoclet.class.getName(), docArgs);
    }

    /**
     * 获取依赖包
     *
     * @return
     */
    @Override
    protected String[] getJars() {
        return JARS;
    }

    @Override
    protected String getProjectPath() {
        return BASE_DIR;
    }
}