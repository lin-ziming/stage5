package cn.tedu.dbinit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
public class DbInitApplication {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(DbInitApplication.class, args);
    }

    /**
     * spring扫描创建完所有对象，完成所有的依赖注入后，会自动执行@PostConstruct方法
     * @throws SQLException
     */
    @PostConstruct
    public void init() throws SQLException {
        exec( "sql/account.sql");
        exec( "sql/storage.sql");
        exec( "sql/order.sql");
        exec( "sql/seata-server.sql");
    }

    private void exec( String sql) throws SQLException {
        ClassPathResource cpr = new ClassPathResource(sql);
        EncodedResource er = new EncodedResource(cpr, "UTF-8");

        ScriptUtils.executeSqlScript(dataSource.getConnection(), er);
    }
}

