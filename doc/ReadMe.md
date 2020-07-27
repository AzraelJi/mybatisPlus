# 模板工程

### 工程结构说明
````
├── pom.xml
├── doc 文档
├── script	脚本文件
│   ├── docker	docker文件    
│   ├── k8s		  k8s文件
│   └── sql    sql 文件
│       ├── ddl    根据日期保存
│       │   ├── 2019-9-5   
│       │   │   └── test.sql
│       │   └── 2019-9-6
│       │       └── test.sql
│       └── dml    根据日期保存
│           ├── 2019-9-5
│           │   └── test.sql
│           └── 2019-9-6
│               └── test.sql
└── src
    ├── main
    │   ├── java
    │   │   ├── annotation	注解
    │   │   ├── aspect	aop类
    │   │   ├── conf	配置类
    │   │   ├── controller	控制器
    │   │   │   ├── base	控制器基类
    │   │   ├── exception	异常类
    │   │   ├── filter	过滤器类
    │   │   ├── generator	代码生成类
    │   │   ├── listener	监听类
    │   │   ├── mapper	dao接口
    │   │   ├── model	模型
    │   │   │   ├── common	通用
    │   │   │   ├── domain	主模型
    │   │   │   ├── query	查询参数
    │   │   │   ├── save	保存参数（添加和修改）
    │   │   │   └── vo	查询结果返回前端包装类
    │   │   ├── service	业务接口
    │   │   │   └── impl	业务接口实现
    │   │   └── util	工具类
    │   └── resources	配置文件和模板
    │       ├── mapper	dao mapper文件
    └── test	测试类
````


### 部分代码建议

- controller 参数不应使用string接收json参数,参数多于三个则封装成对象：实例如下

````
    @PostMapping("/test3")
    public AppResponse foo(@RequestBody @Valid TestQueryParam param) {
        return AppResponse.newResponseEntity("test");
    }

    @PostMapping("/test4")
    public AppResponse bar(@JsonParam @Valid TestQueryParam param) {
        return AppResponse.newResponseEntity("test");
    }
````
- controller层尽量不要写业务逻辑 尽量不要打印日志，若需要输出日志可使用aop拦截器

- 参数验证使用valid：如下

````
  /**
     * 页数
     */
    @NotNull(message = "page 不能为空")
    @Min(value = 1, message = "page最少为1")
    private Integer page;

    /**
     * 每页大小
     */
    @NotNull(message = "pageSize 不能为空")
    @Min(value = 1, message = "pageSize最少为1")
    private Integer pageSize;
````

- dao层不应使用map作为方法参数，推荐使用@Param() 注解 参数多与三个时封装成对象

- 简单curd使用 CodeGenerator 生成 提高开发效率 可自动生成mapper ，service controller 
````
 * 生成的代码结构如下
 * =====================================
 * controller
 * mapper
 * model.domain
 * service
 * =====================================
 * 之后model类需要修改添加 如下注解 @TableName(schema = "dwb")
 * schema为当前表对应的schema
 * =====================================
 * 之后拷贝java目录下面的mapper文件到 resources/mapper 目录下
````
- 复杂查询使用自定义查询,sql模板最好写在xml中便于管理
- 日期类使用LocalDateTime，最好不要使用Date类
- controller层返回类型可直接返回，建议使用 AppResponse
- 异常抛出自定义非检测异常，尽量不要抛出原始异常
- 异常code可根据不同模块指定 常量可保存在 model的common下
- model package中：
   - domain用来保存bean
   - query用来前端查询参数封装
   - save 用来前端添加或者编辑参数封装
   - vo用来返回前端查询结果封装
- mybatis plus 操作可参照 https://mp.baomidou.com/guide/
- 多数据源可使用 dynamic datasource 参照 application.yaml 使用时在service添加注解@DS("db1")
- 若需要对sql性能分析可使用数据库代理驱动com.p6spy.engine.spy.P6SpyDriver 具体配置参照spy.properties
- 全局异常处理可使用spring mvc 提供的 @ControllerAdvice注解 若自定义可参照 ExceptionController
- Controller中通用方法可写在BaseController中，所有的controller继承自BaseController
- 在无法自动注入的地方需要获取容器内的对象可使用SpringHelper.getBean() 
- 在无法自动注入环境变量的地方可使用SpringHelper.getProperty()
- 接口注释参照 BseCameraController
