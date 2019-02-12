package hu.robnn.reportserver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ReportServerConfiguration {

    @Bean
    Properties properties() throws IOException {
        InputStream inputStream = getClass().getClassLoader()
            .getResourceAsStream("config.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

    @Bean
    DataSource dataSource() {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        try {
            pgSimpleDataSource.setUser(properties().getProperty("database.username"));
            pgSimpleDataSource.setPassword(properties().getProperty("database.password"));
            pgSimpleDataSource
                .setPortNumber(Integer.parseInt(properties().getProperty("database.port")));
            pgSimpleDataSource.setServerName(properties().getProperty("database.connection.uri"));
            pgSimpleDataSource.setDatabaseName(properties().getProperty("database.name"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pgSimpleDataSource;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("hu");
        try {
            Properties hibernateProperties = new Properties();
            hibernateProperties
                .put("hibernate.dialect", properties().getProperty("hibernate.dialect"));
            hibernateProperties.put("hibernate.temp.use_jdbc_metadata_defaults", false);
            hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
            entityManagerFactoryBean.setJpaProperties(hibernateProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entityManagerFactoryBean;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
