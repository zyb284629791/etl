package com.john.etl.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.john.etl.units.EtlException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MabatisGeneratorUtil {


    // 代码生成器
    private static AutoGenerator mpg = new AutoGenerator();
    private static PackageConfig pc = new PackageConfig();
    private static String projectPath = System.getProperty("user.dir");
    private static String author = System.getProperty("os.name");
    private static DataSourceConfig midDataSourceConfig;
    private static DataSourceConfig officialDataSourceConfig;

    static {
        // 数据源配置
        midDataSourceConfig = new DataSourceConfig();
        midDataSourceConfig.setUrl("jdbc:mysql://localhost:3306/etl_mid");
        midDataSourceConfig.setSchemaName("etl_mid");
        midDataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        midDataSourceConfig.setUsername("root");
        midDataSourceConfig.setPassword("root#root");

        // 数据源配置
        officialDataSourceConfig = new DataSourceConfig();
        officialDataSourceConfig.setUrl("jdbc:mysql://localhost:3306/etl_official");
        officialDataSourceConfig.setSchemaName("etl_official");
        officialDataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        officialDataSourceConfig.setUsername("root");
        officialDataSourceConfig.setPassword("root#root");
    }

    /**
     * 反向工程脚手架
     *
     * @param moduleName    模块名称(本级包名)
     * @param tableName     表名
     * @param entityName 上级包名
     * @param tablePrefix   表名前缀(用来在生成entity时删除表名中的前缀)
     */
    public static void generator(String moduleName, String tableName, String entityName, String tablePrefix) {

        mpg.setConfig(null);
        // init globalConfig
        GlobalConfig globalConfig = initGloboConfig();
        if (!StringUtils.isEmpty(entityName)) {
            globalConfig.setEntityName(entityName);
        }
        mpg.setGlobalConfig(globalConfig);

        if (!StringUtils.isEmpty(moduleName)) {
            pc.setModuleName(moduleName);
        }
        // 包配置
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                this.setFileCreate(new IFileCreate() {
                    @Override
                    public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                        return true;
                    }

                    @Override
                    public void checkDir(String filePath) {

                    }
                });
//                HashMap<String,Object> map = new HashMap<>();
//                PackageConfig packageConfig = new PackageConfig();
//                packageConfig.setModuleName(moduleName);
//                map.put("package", packageConfig);
//                this.setMap(map);
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                String schemaName = mpg.getDataSource().getSchemaName();
                String[] schemaNames = schemaName.split("\\_");
                if (schemaNames.length != 2) {
                    throw new EtlException("data source schema name is illegal!");
                }
                return projectPath + "/src/main/resources/mapper/" + schemaNames[1] + "/" + moduleName
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = initStrategy(tableName, tablePrefix);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * @param parentPackage 脚手架生成文件根目录
     * @param dataSource    数据源名称 mid / official
     */
    public static void initBasePackageAndDataSourceAndIdType(String parentPackage,String dataSource,IdType idType) {
        pc.setParent(parentPackage);
        // init data source
        if (!StringUtils.isEmpty(dataSource) && "official".equalsIgnoreCase(dataSource)) {
            mpg.setDataSource(officialDataSourceConfig);
        } else {
            mpg.setDataSource(midDataSourceConfig);
        }
    }

    private static StrategyConfig initStrategy(String tableName, String tablePrefix) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setLogicDeleteFieldName("delete_flag");
//        strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategy.setEntityLombokModel(true);
        // restController
        strategy.setRestControllerStyle(true);
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        strategy.setInclude(tableName);
//        strategy.setSuperEntityColumns("id");
//        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(tablePrefix);
        return strategy;
    }

    private static GlobalConfig initGloboConfig() {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setIdType(IdType.ID_WORKER_STR);
        return gc;
    }
}
