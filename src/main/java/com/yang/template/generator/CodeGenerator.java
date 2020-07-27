package com.yang.template.generator;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * <p>
 * 使用该方法生成的代码结构如下
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
 * </p>
 *
 * @author jjyy
 * @implNote 代码生成工具
 * @since 2019/9/5
 */
public class CodeGenerator {

    private final String PACKAGE_PREFIX = "com.yang.template";
    private final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/cloud-video?useSSL=false";
    private final String USERNAME = "root";
    private final String PASSWORD = "root";
    //    private final String SCHEMA_NAME = "dwb";
    private final String[] TABLE_NAME = {"t_video_media"};


    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        new AutoGenerator()
                .setGlobalConfig(generator.globalConfig())
                .setDataSource(generator.dataSourceConfig())
                .setStrategy(generator.strategyConfig())
                .setPackageInfo(generator.packageConfig())
                .execute();
    }

    /**
     * 全局配置
     */
    private GlobalConfig globalConfig() {
        return new GlobalConfig()
                .setActiveRecord(true)
                .setAuthor("generator")
                .setOutputDir(System.getProperty("user.dir") + "/src/main/java")
                .setFileOverride(true)
                .setIdType(IdType.AUTO)
                .setServiceName("%sService")
                .setBaseResultMap(true)
                .setSwagger2(true)
                .setServiceImplName("%sServiceImpl")
                .setDateType(DateType.TIME_PACK)
                .setBaseColumnList(true);
    }

    /**
     * 数据源配置
     */
    private DataSourceConfig dataSourceConfig() {
        return new DataSourceConfig().setDbType(DbType.MYSQL)
                .setDriverName(DRIVER_NAME)
                .setUrl(JDBC_URL)
                .setUsername(USERNAME)
                .setPassword(PASSWORD);
//                .setSchemaName(SCHEMA_NAME);
    }

    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig() {
        return new StrategyConfig().setCapitalMode(true)
                .setEntityLombokModel(true)
                .setEntitySerialVersionUID(true)
                .setControllerMappingHyphenStyle(true)
                .setEntityBuilderModel(true)
                .setEntityTableFieldAnnotationEnable(true)
                .setRestControllerStyle(true)
                .setSuperControllerClass(this.PACKAGE_PREFIX + ".controller.BaseController")
                .setNaming(NamingStrategy.underline_to_camel)
                .setLogicDeleteFieldName("deletedAt")
                .setInclude(TABLE_NAME)
                .setEntityBooleanColumnRemoveIsPrefix(true);
    }

    /**
     * 包名策略配置
     */
    private PackageConfig packageConfig() {
        return new PackageConfig()
                .setParent(null)
                .setEntity(this.PACKAGE_PREFIX + ".model.domain")
                .setController(this.PACKAGE_PREFIX + ".controller")
                .setService(this.PACKAGE_PREFIX + ".service")
                .setMapper(this.PACKAGE_PREFIX + ".mapper")
                .setServiceImpl(this.PACKAGE_PREFIX + ".service.impl")
                .setXml("mapper");
    }

}
