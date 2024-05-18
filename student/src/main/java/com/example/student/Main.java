package com.example.student;


import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class Main {
    public static void main(String[] args) {
        //创建generator对象
        AutoGenerator autoGenerator = new AutoGenerator();

        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/student");
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("460751973");
        autoGenerator.setDataSource(dataSourceConfig);


        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        //globalConfig.setOutputDir("F:/generator/src/main/java");
        globalConfig.setOutputDir("D:/IDE/student/src/main/java"); //项目生成的路径
        globalConfig.setAuthor("wang");               // 设置作者
        globalConfig.setOpen(false);                  //创建好后是否打开文件夹
        globalConfig.setFileOverride(true);           // 是否覆盖原来生成的代码，默认为false
        globalConfig.setDateType(DateType.ONLY_DATE);  //关于日期类型的设置
        globalConfig.setControllerName("%sController"); // 自定义文件命名，注意 %s 会自动填充表实体属性！
        globalConfig.setServiceName("%sService");        // 默认service接口名IXXXService 自定义指定之后就不会用I开头了
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");
        autoGenerator.setGlobalConfig(globalConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.example.student");            //设置包名
        packageConfig.setModuleName(null);            //设置模块名
        packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setMapper("mapper");
        packageConfig.setEntity("entity");
        packageConfig.setXml("mapper.xml");
        autoGenerator.setPackageInfo(packageConfig);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityLombokModel(true);                      //添加Lombok注解
        // strategy.setTablePrefix(new String[] { "sys_" });      // 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);     // 表名生成策略(下划线转驼峰)
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); //列名生成策略（下划线转驼峰）
        //strategy.setInclude("user");                              // 需要生成的数据表的名称（如果不写这一行，将默认创建这个数据库中所有的表）
        autoGenerator.setStrategy(strategy);

        // 执行生成
        autoGenerator.execute();
    }
}
