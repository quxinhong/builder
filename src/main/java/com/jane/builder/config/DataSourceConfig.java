package com.jane.builder.config;
/*
 * package com.jane.editor.config;
 * 
 * import javax.sql.DataSource;
 * 
 * import org.springframework.beans.factory.annotation.Qualifier; import
 * org.springframework.boot.context.properties.ConfigurationProperties; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.context.annotation.Primary; import
 * org.springframework.jdbc.datasource.DataSourceTransactionManager;
 * 
 * import com.alibaba.druid.pool.DruidDataSource; import
 * com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
 * 
 * @Configuration public class DataSourceConfig {
 * 
 * @Primary
 * 
 * @ConfigurationProperties(prefix = "spring.datasource")
 * 
 * @Bean(name = "primaryDataSource")
 * 
 * @Qualifier("primaryDataSource") public DataSource primaryDataSource() {
 * DruidDataSource dataSource = DruidDataSourceBuilder.create().build(); return
 * dataSource; }
 * 
 * @Primary
 * 
 * @Bean(name = "transactionManager") public DataSourceTransactionManager
 * transactionManager(@Qualifier("primaryDataSource") DataSource
 * primaryDataSource) { return new
 * DataSourceTransactionManager(primaryDataSource); }
 * 
 * @Bean(name = "secondaryDataSource")
 * 
 * @Qualifier("secondaryDataSource")
 * 
 * @ConfigurationProperties(prefix="customer.datasource.db0") public DataSource
 * secondaryDataSource() { DruidDataSource dataSource =
 * DruidDataSourceBuilder.create().build(); return dataSource; } }
 */