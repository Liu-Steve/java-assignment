package edu.whu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;

public class CodeGenerator {

    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setDataSource(dataSourceConfig());
        autoGenerator.setGlobalConfig(globalConfig());
        autoGenerator.setPackageInfo(packageConfig());
        autoGenerator.setStrategy(strategyConfig());
        autoGenerator.execute();
    }

    private static DataSourceConfig dataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/order_week_6?serverTimezone=UTC");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("root");
        return dataSourceConfig;
    }

    private static GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir")
                + "/mybatis-plus-code/src/main/java");
        globalConfig.setOpen(false);
        globalConfig.setAuthor("steve-liu");
        globalConfig.setFileOverride(true);
        globalConfig.setMapperName("%sDao");
        globalConfig.setIdType(IdType.AUTO);
        return globalConfig;
    }

    private static PackageConfig packageConfig() {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("edu.whu.week6");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("dao");
        return packageConfig;
    }

    private static StrategyConfig strategyConfig() {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setInclude("product", "supplier");
        strategyConfig.setTablePrefix("");
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setEntityLombokModel(true);
        return strategyConfig;
    }

}