# 自动化文档说明

## 一、模块说明

本项目分为两部分： doclet插件和文档展示模块

### doclet插件

doclet即是自动化文档模块，由三个模块组件：

doclet-pojo：基类，主要是定义文档的基础信息，由JavaBean组成，是一个公共模块

doclet-render：渲染类，主要实现文档数据的渲染，比如渲染成Markdown / Html

doclet: Maven插件的入口，实现了java源文件的解析与文档元素抽取功能

### 文档展示模块

tp-demo-api：本模块只是作为Dubbo的接口定义模块，不实现任何功能

tp-demo-server：文档的Web展示端，也作为doclet的测试模块。本模块基于spring-boot + freemarker实现的简单网站

## 二、代码调试

doclet插件有两种调试方法

1. 代码调试

`com.fangdd.tp.doclet.TpDocletTest`类里面有一个简单的Doclet类的调试，本方法可以实现断点功能

2. 插件调试

插件调试直接在`tp-doclet/tp-demo-server/pom.xml`里面，但需要将doclet插件打包安装后才能使用，此方法不支持断点

使用插件测试时，请先看下代码：`com.fangdd.tp.doclet.TpDoclet#start()`目前开发环境与发布环境暂未自动化，所以需要调整此方法内的前面几行配置
发布环境默认是生成到`10.0.1.86`文档服务器的`web`模式，而开发环境建议使用本地`127.0.0.1`的'console'模式，即把生成的Markdow打印到控制台

## 三、注意事项

1. 目前发布系统不支持拉此插件的SNAPSHOT包，所以发布时暂时以正式包发布。目前会在将数据提交到文档服务器时添加doclet版本信息，未来，如果版本过低时，需要在文档内做明显升级提示

2. 本项目是内部开源项目，仅限房多多内部使用，未经允许，禁止外部使用及将源码扩散






