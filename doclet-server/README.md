# TP-DOC 使用说明文档

TP-DOC是对javadoc的扩展，对代码无任何入侵或污染，通过读取代码注释，自动生成接口文档。目前已实现两类接口文档的生成：

1. spring mvc提供的  `@RestController`注解的类和由`@RequestMapping` / `@GetMapping` / `@PostMapping` / `@PutMapping` / `@PatchMapping` / `@DeleteMapping` 注解的方法

2. @com.alibaba.dubbo.config.annotation.Service注解的Dubbo接口
3. 使用xml配置的dubbo服务接口 （暂时只能支持固定路径的配置文件）

本工具以maven插件方式提供，使用本自动化文档系统只需要：多写注释，多写注释，多写注释！

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
             <groupId>com.fangdd</groupId>
             <artifactId>doclet</artifactId>
             <version>1.2-SNAPSHOT</version>
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
             <!-- package可以在提交代码后由CI自动触发，如果不需要自动触发，可以设置为site，届时需要手工执行：mvn clean site -->
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

创建完成后，会在日志里面打印出文档的地址（PS，截图上的链接地址有变化，以实际显示为准）：
 
 ![](https://static-tp.fangdd.com/xfwf/FotZpF4m8zUD4hiU9K6c4kRMnt44.png)

文档地址默认使用此模块的maven（不包含版本号），即是升级了版本后访问地址还是不变的，查看旧版本的文档可以在历史文档中找到

<br>

## 二、案例

目前接口文档支持两种类型：

1. RestFul接口： [小区筛选结果接口](http://tp-doc.fangdd.net/doc/com.fangdd.cp:m-web-cp-server/cd4ae6f7f3c114ad507ec67b3aad8e50)

2. Dubbo接口：[文章内链、敏感词格式化](http://tp-doc.fangdd.net/doc/com.fangdd.tp:scanengine-dp-server/bcd736748b165a61fdffb728436583b5)

3. 目前接入的项目文档：[项目索引](http://tp-doc.fangdd.net)
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

PS：暂未实现将`@RestController`写在父类的场景

如果添加注释`@disable`，则整个Controller的文档都不会被创建


### 2. 方法

Controller里面的方法，只要被注解为`@RequestMapping` / `@GetMapping` / `@PostMapping` / `@PutMapping` / `@PatchMapping` / `@DeleteMapping`，就会被当成`RestFul`接口处理，生成对应的一篇接口文档(Article)

这些注解的属性：

> `value`与`Controller`里的`@RequestMapping`内的`value`属性一起当成接口路径。如果有多个时，会平铺开
>
> 当`@RequestMapping`属性`method`没有指定值时，文档中会显示成 `GET` / `POST` （这里没有把`PUT` / `DELETE`等显示出来）


### 3. 方法的注释

`@api` 表示指定接口名称，如果没有指定，尝试使用第一行注释，其它注释当成接口注释。如果也没有写注释，则使用方法名称作为接口名称

`@param` 参数的注释，会出现在文档的参数表格里面，如果没有指定则没有注释信息（暂时实现默认值的写法）

`@return` 响应的注释

如果添加注释`@disable`，则本接口的文档都不会被创建


### 4. 方法参数

请求参数的说明可以在方法注释的`@param`里写，但如果请求参数是一个`bean`，则可以在类属性里写更丰富的信息

支持`spring mvc`的参数注解：

`@PathVariable` ：路径参数，必填，使用了正则限制的，在接口调用中还会对参数进行校验

`@RequestBody` ：Post Body参数，非必填，支持复杂结构类，类的注释说明见下文

`@RequestParam`：默认必填，也可以通过属性`required`声明是否必填，使用`defaultValue`声明默认值。如果参数是没有带任何注解，也相当于使用此注解，必填

`@RequestAttribute`：一般是由Filter或拦截器注入的，所以不把它当成是请求参数，不会出现在文档里面

`@RequestHeader`：是由请求头注进来的，暂时也不体现在文档中

自定义注解暂不支持


### 5. 响应体

响应体的说明可以在`@return`里写，同时，如果是个`bean`时也可以在类里面写更多的信息，参考下文`Bean`的说明



### 6. Bean

请求参数与响应体都可以是一个`Bean`，看下面的案例：


``` java
/**
 * 标准响应体，这里可以写些类的说明
 */
public class BaseResponse<T> {
    /**
     * 响应值： 200=正常 其它值表示有异常
     * @demo 200
     */
    private Integer code;

    /**
     * 信息，一般在响应异常时有值
     * @required
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

类属性的注释非常重要，尽量写明白它的含义。另外，属性必需有对应的getter和setter，否则会被show me 
丢弃。

可以使用以下注解标识属性必填，不能为空：

> `@javax.validation.constraints.NotNull`
>
> `@required`
>
> `@org.hibernate.validator.constraints.NotBlank`
>
> `@org.hibernate.validator.constraints.NotEMPTY`

<br>

默认属性是可为空的，也可以使用注解：

> `@javax.validation.constraints.Null`


`@demo` 是指明此属性的demo值，让生成的文档更直观

如果`Bean`包含泛型，为生成完整的文档，强烈建议在定义里定义好泛型类型！

如果是`java.util.Date`类型时，如果有指定`@demo`值，则直接使用指定的值；
否则，如果有使用注解`org.springframework.format.annotation.DateTimeFormat`则会使用当前时间根据注解配置的格式转换成字符串显示；
如果也没有使用注解，则使用`new Date().toString()`的值显示
建议：不要使用`java.util.Date`类型！可以使用时间戳类型。

枚举类型，使用`@demo`值，未指定时，为空。




```java
/**
 * DEMO接口
 * @chapter Demo接口
 * @section Demo1
 * @c1 10
 * @c2 10
 */
@RestController
@RequestMapping("/demo")
public class DemoApiController {
    /**
     * 接口1
     * 这里注释的第一行会被当成是接口的名称，第二行被当成是接口说明
     * @c3 10
     * @return
     */
    @RequestMapping(value = "/api1")
    public BaseResponse api1() {
        return BaseResponse.success();
    }

    /**
     * 这里的文字会被当成接口注释 
     * @api 接口2
     * @return 返回热门标签
     */
    @RequestMapping(value = "/api2")
    public BaseResponse<List<Tag>> api2() {
        return BaseResponse.success();
    }
}
```

上面代码中，响应体`BaseResponse`是带泛型的，所以在写接口时，`api1()`是不推荐的写法，因为没有指定响应体内容，生成的文档时也没办法去识别它的响应体结构

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


### 7. 排序

|注释标签|范围|说明|DEMO |
|:--|:--|:--|:--|
| @c1 | 类 | 一级分类，即 @chapter 排序 | @c1 10 |
| @c2 | 类 | 二级分类，即 @section 排序 | @c2 10 |
| @c3 | 方法 | 接口排序，即 @api 排序 | @c3 10 |

排序值越小越前

<br>

## 四、Dubbo文档

dubbo文档的生成规则与RestFul的完全一致，请参考上面RestFul说明

注意，dubbo接口的文档以接口的注释为准，实现类的注释暂未采集！

### 1. 注解方式声明
@com.alibaba.dubbo.config.annotation.Service注解的Dubbo接口，文档的说明以接口的注释为准，实现里的注释暂时未采用，后继将使用上

### 2. 配置方式声明

目前仅支持配置文件名且目录为：`/src/main/resources/applicationContext-dubbo.xml`

<br>

## 六、Markdown文档

系统支持自定义markdown文档，markdown文档必须是在启动项目下，以`.md`为后缀。

默认，需要一个`REDME.md`文件，用于在项目文档根目录中显示（参考github的项目首页）

另外，如果文件名跟分类名一致时，会将文档的链接加在分类上，比如有个分类目录为：`公共接口` > `相册接口`

然后创建两个文件：`公共接口.md` 和 `公共接口.相册接口.md`，这时，生成的目录结构会显示成下图那样，是带链接的。

![](https://fs.fangdd.com/customer/fufeixiazai/FoZA1oYbWJym-SbCgJ0cNMzBhJed.png
)




