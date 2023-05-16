package com.smart.agriculture.common.utils;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <h3>mpgenerator</h3>
 * <p></p>
 *
 **/
public class CodeGenerator {
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
//        String [] tableNames = new String[]{"pro_sta_score"};
        String[] tableNames = scanner("表名，多个英文逗号分割").split(",");

        String [] modules = new String[]{"agriculture_pojo", "agriculture_mapper", "agriculture_service", "agriculture_controller"};//项目模块名，需自定义
        for (String module : modules) {
            moduleGenerator(module,tableNames);
        }
    }

    private static void moduleGenerator(String module,String [] tableNames){

        GlobalConfig globalConfig = getGlobalConfig(module);// 全局配置

        DataSourceConfig dataSourceConfig = getDataSourceConfig();// 数据源配置

        PackageConfig packageConfig = getPackageConfig(module);// 包配置

        StrategyConfig strategyConfig = getStrategyConfig(tableNames);// 策略配置

        TemplateConfig templateConfig = getTemplateConfig(module);// 配置模板

        InjectionConfig cfgConfig = getCfgConfig(); //自定义设置

        new AutoGenerator()
                .setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setPackageInfo(packageConfig)
                .setStrategy(strategyConfig)
                .setTemplate(templateConfig)
                .setCfg(cfgConfig)
                .execute();
    }

    private static InjectionConfig getCfgConfig() {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        String projectPath = System.getProperty("user.dir");

        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
         String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return new File("agriculture_mapper").getAbsolutePath()+ "/src/main/resources/mapper/" + tableInfo.getEntityName()
                        + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    private static TemplateConfig getTemplateConfig(String module) {
        TemplateConfig templateConfig = new TemplateConfig();
        if ("agriculture_pojo".equals(module)){
            templateConfig.setEntity(new TemplateConfig().getEntity(false))
                    .setMapper(null)//mapper模板
                    .setXml(null)
                    .setService(null)
                    .setServiceImpl(null)
                    .setController(null);//service模块不生成controller代码
        } else if ("agriculture_mapper".equals(module)){
                     templateConfig.setEntity(null)
                    .setMapper(new TemplateConfig().getMapper())
                    .setXml(null)
                    .setService(null)
                    .setServiceImpl(null)
                    .setController(null);
        } else if ("agriculture_service".equals(module)){
            templateConfig.setEntity(null)
                    .setMapper(null)
                    .setXml(null)
                    .setService(new TemplateConfig().getService())
                    .setServiceImpl(new TemplateConfig().getServiceImpl())
                    .setController(null);
        } else if ("agriculture_controller".equals(module)){//web模块只生成controller代码
            templateConfig.setEntity(null)
                    .setMapper(null)
                    .setXml(null)
                    .setService(null)
                    .setServiceImpl(null)
                    .setController(new TemplateConfig().getController());
        } else {
            throw new IllegalArgumentException("参数匹配错误，请检查");
        }
        return templateConfig;
    }

    private static StrategyConfig getStrategyConfig(String[] tableNames) {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)//驼峰命名
                .setEntityLombokModel(true) //lombok
                .setRestControllerStyle(true) //restful
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setSuperEntityClass("com.smart.agriculture.Do.BaseDo") //继承父类实体类
                .setSuperEntityColumns(new String[]{"id","create_time", "update_time", "is_del"})
                .setInclude(tableNames);
        return strategyConfig;
    }

    private static PackageConfig getPackageConfig(String module) {
        PackageConfig packageConfig = new PackageConfig();
        String packageName = "com.smart.agriculture";//不同模块 代码生成具体路径自定义指定
        //studio_pojo", "studio_dao", "studio_service", "studio_controller
//        if ("studio_pojo".equals(module)){
//            packageName+=".entity";
//        } else if ("studio_dao".equals(module)){
//            packageName+=".dao";
//        } else if ("studio_service".equals(module)){
//            packageName+=".service";
//        } else if ("studio_controller".equals(module)){
//            packageName+=".controller";
//        }
        packageConfig.setParent(packageName)
                .setEntity("Do")
                .setMapper("mapper")
//                .setXml("mapper")
                .setService("service")
                .setController("controller");
        return packageConfig;
    }

    private static DataSourceConfig getDataSourceConfig() {
        String dbUrl = "jdbc:mysql://127.0.0.1:3307/smart_agriculture?useSSL=false&serverTimezone=GMT%2B8&characterEncoding=utf8&allowPublicKeyRetrieval=true";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setDriverName(Driver.class.getName())
                .setUsername("root")
                .setPassword("kangkang")
                .setUrl(dbUrl);
        return dataSourceConfig;
    }

    private static GlobalConfig getGlobalConfig(String module) {
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOpen(false)//new File(module).getAbsolutePath()得到模块根目录路径，因事Maven项目，代码指定路径自定义调整
                .setOutputDir(new File(module).getAbsolutePath()+"/src/main/java") //生成文件的输出目录
                .setFileOverride(false)//是否覆盖已有文件
                .setActiveRecord(false)
                .setIdType(IdType.ID_WORKER)
                .setAuthor("ylx");  //使用时，更改为自己的昵称
        return globalConfig;
    }
}
