# TP-DOC 使用说明文档

TP-DOC是在公司spring boot种子项目上，依赖代码注释，自动生成接口文档。目前已实现两类接口文档的生成：

1. spring mvc提供的  `@ResController`注解的类和由`@RequestMapping` / `@GetMapping` / `@PostMapping` 注解的方法

2. @com.alibaba.dubbo.config.annotation.Service注解的Dubbo接口

本工具以maven插件方式提供，除了限制了特定的注解外，没有任何代码入侵，所有信息都不是必填。

接口文档上面的信息都是通过java本身或代码注释中提取。所以使用本自动化文档系统只需要：多写注释，多写注释，多写注释！

<br>

文档结构分为三级：

> 章节(Chapter): 一级分类
>
> 模块(Section)：二级分类
>
> 文章(Article)：接口文档
>
> ![](https://static-tp.fangdd.com/xfwf/FhDxYseBSEA6ebh2w-yR54x132rm.png)

## 一、如何使用

在服务入口模块的pom.xml文件`build` &gt; `plugins` 内添加以下代码
 
 ``` xml
 <plugin>
     <groupId>org.apache.maven.plugins</groupId>
     <artifactId>maven-javadoc-plugin</artifactId>
     <version>2.10.3</version>
     <configuration>
         <doclet>com.fangdd.tp.doclet.TpDoclet</doclet>
         <docletArtifact>
             <!-- 因发布系统无法拉取到SNAPSHOT包，所以这里改成了正式包 -->
             <groupId>com.fangdd</groupId>
             <artifactId>doclet</artifactId>
             <version>1.1</version>
         </docletArtifact>
         <sourcepath>
             <!-- 指定源码路径，如果多个模块，需要包含进去 -->
             ${project.basedir}/src/main/java:${project.basedir}/../m-web-cp-api/src/main/java
         </sourcepath>
         <useStandardDocletOptions>false</useStandardDocletOptions>

         <additionalJOptions>
             <additionalJOption>-J-Dbasedir=${project.basedir}</additionalJOption>
         </additionalJOptions>
     </configuration>
     <executions>
         <execution>
             <id>attach-javadocs</id>
             <!-- package可以在提交代码后由CI自动触发，如果不需要自动触发，可以设置为site，届时需要手工执行：mvn clean package -->
             <phase>package</phase>
             <goals>
                 <goal>javadoc</goal>
             </goals>
         </execution>
     </executions>
 </plugin>
 ```

引入的新构建模块插件需要注意：

> 1. `plugin` &gt; `configuration` &gt; `sourcepath` 需要按项目实际情况调整，将需要扫描的源码路径添加进来即可
> 
> 2. 文档服务器需要在`10.0.1.86`，所以需要内网环境才能正确生成文档
>
> 3. `plugin`配置需要添加到打包部署包的模块上
>
> 4. maven的勾子可设置为`package`，代码提交时会由CI触发，即每次代码提交都会更新文档，如果不需要自动触发，可配置为`site`，然后手工执行：mvn clean site，手工更新文档

创建完成后，会在日志里面打印出文档的地址：
 
 ![](https://static-tp.fangdd.com/xfwf/FotZpF4m8zUD4hiU9K6c4kRMnt44.png)

文档地址默认使用此模块的maven（不包含版本号），即是升级了版本后访问地址还是不变的，查看旧版本的文档可以在历史文档中找到

<br>

## 二、案例

目前接口文档支持两种类型：

1. RestFul接口： [批量获取多个广告位接口](http://10.0.1.86:17010/doc/com.fangdd.cp:m-web-cp-server/?code=com.fangdd.cp.m.controller.AdvertApiController.getEsfAdvertMap)

2. Dubbo接口：[通过ID获取文章](http://10.0.1.86:17010/doc/com.fangdd.cp:article-ctc-cp-server/?code=com.fangdd.cp.ctc.article.service.ArticleService.queryArticleById)

3. 目前接入的项目文档：[项目索引](http://10.0.1.86:17010/index.html)
<br>


## 三、RestFul文档

写文档其它就是写java注释！看下面的一段代码


```java
/**
 * 小区列表接口
 *
 * @chapter 小区
 * @section 小区推荐
 */
@RestController
@RequestMapping("/cell")
public class CellApiController {
    /**
     * 相似小区列表
     * 从数据团队提供的接口里获取到小区ID，再通过搜索接口，获取小区详情
     *
     * @param request 请求参数
     * @return 小区列表
     */
    @RequestMapping(value = "/similar", method = RequestMethod.POST)
    public BaseResponse<List<CellInfoDto>> getSimilarCellList(@RequestBody SimilarCellReqDto request) {
        //...
    }
}
```



### 1. Controller注释

只在被注解为`@RestController`的类才会被当成`RestFul`接口进行解析，否则丢弃

类注释里面： 

`@chapter` 表示指定章节（Chapter），默认为`接口文档`

`@section` 表示指定模块（Section），默认为当前类第一行注释，如果没有写注释，则为类全名。如果未指定`@section`但有多行注释时，会取第一行注释为模块名，其它为模块注释


### 2. 方法

Controller里面的方法，只要被注解为`@RequestMapping`，就会被当成`RestFul`接口处理，生成对应的一篇接口文档(Article)

`@RequestMapping`属性：

> `value`与`Controller`里的`@RequestMapping`内的`value`属性一起当成接口路径。如果有多个时，会平铺开
>
> `method`当成`RestFul`方法，如果没有指定时，文档中会显示成 `GET` / `POST`

### 3. 方法注释

`@api` 表示指定接口名称，如果没有指定，尝试使用第一行注释，其它注释当成接口注释。如果也没有写注释，则使用方法名称作为接口名称

`@param` 参数的注释，会出现在文档的参数表格里面，如果没有指定则没有注释信息

`@return` 响应的注释

### 4. 方法参数和响应体

请求参数的说明可以在方法注释的`@param`里写，但如果请求参数是一个`bean`，则可以在类属性里写更丰富的信息

响应体的说明可以在`@return`里写，同时，如果是个`bean`时也可以在类里面写更多的信息



### 5. Bean

请求参数与响应体都可以是一个`Bean`，看下面的案例：


```java
/**
 * 标准响应体
 */
public class BaseResponse<T> {
    /**
     * 响应值： 200=正常 其它值表示有异常
     * @demo 200
     */
    private Integer code;

    /**
     * 信息，一般在响应异常时有值
     * @demo 用户名不能为空
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;
    
    //getter和setter
}
```

类属性的注释非常重要，应该写明白它的含义

`@demo` 是指明此属性可能的值，让生成的文档更直观

如果`Bean`包含泛型，为生成完整的文档，强烈建议在定义里定义好泛型类型！

```java
/**
 * DEMO接口
 */
@RestController
@RequestMapping("/demo")
public class DemoApiController {
    /**
     * 接口1
     * @return
     */
    @RequestMapping(value = "/api1")
    public BaseResponse api1() {
        return BaseResponse.success();
    }

    /**
     * 接口2
     * @return 返回热门标签
     */
    @RequestMapping(value = "/api2")
    public BaseResponse<List<Tag>> api2() {
        return BaseResponse.success();
    }
}
```

上面demo中，响应体`BaseResponse`是带泛型的，所以在写接口时，`api1()`是不推荐的写法，因为没有指定响应体内容，生成的文档时也没办法去识别它的内容

`api2()`是一种推荐的写法，指定了泛型，这样在生成文档时，就可以根据泛型的类型继续深挖它的结构，最后生成出来的文档会非常明了

如果`Bean`属性出现了循环引用的情况下，生成的文档会使用下面的方式表达，比如

```java
public class User<T> {
    /**
     * 上级用户
     */
    private User<T> parent; //这里也使用了当前类
    
}
```

会生成以下文档：

![](https://static-tp.fangdd.com/xfwf/Fvxk4XXhzWjfcrK3pT3HO--6maYq.png)

<br>

## 四、Dubbo文档

@com.alibaba.dubbo.config.annotation.Service注解的Dubbo接口，文档的说明以接口的注释为准，实现里的注释暂时未采用，后继将使用上

dubbo文档的生成规则与RestFul的完全一致，请参考上面RestFul说明


<br>
## 五、已知问题

1. FastJson的注解的格式化还不支持
2. @NotNull 此类注解还不支持
3. dubbo使用xml声明的服务还不支持
4. 历史版本文档暂时还未开发


## Next Step


1.  dubbo使用xml声明的服务支持
2.  历史版本文档查看


