package cn.tedu.storage;

import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author Haitao
 * @date 2020/12/14 11:02
 */
@Configuration
public class DSAutoConfiguration {
    // 创建原始 DataSource 对象
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(){
//        return new DruidDataSource();
        return new HikariDataSource();
    }

    // 创建 Seata 的 DataSource Proxy 对象
    @Bean
    @Primary  //首选此对象为注入对象
    public DataSourceProxy dataSourceProxy(DataSource dataSource){
        return new DataSourceProxy(dataSource);
    }

}
