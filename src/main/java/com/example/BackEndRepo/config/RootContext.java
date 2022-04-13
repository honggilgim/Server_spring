package com.example.BackEndRepo.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", maxAge = 3600)
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.example.demo.repository") // JPA 사용을 위한 패키지 탐색
@MapperScan("com.example.demo.mapper") // MyBatis 사용을 위한 패키지 탐색
public class RootContext {

    // DB 정보
    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource datasource = new BasicDataSource();
        datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        datasource.setUrl("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC");
        datasource.setUsername("root");
        datasource.setPassword("1234");

        return datasource;
    }

    // jpa 설정
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPersistenceUnitName("demo"); // persistence.xml의 설정 정의된 이름
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return entityManagerFactory;
    }

    // mybatis 사용을 위한 sessionFactory bean 등록
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());
        sqlSessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml")); // mybatis 설정파일 등록
        return sqlSessionFactory;
    }

    // mybatis 사용을 위한 sqlSession bean 등록
    @Bean
    public SqlSessionTemplate sqlSession(SqlSessionFactoryBean sqlsessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlsessionFactory.getObject());
    }

    // transactional 설정
    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        // MyBatis transactional
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());

        // JPA transactional
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        // Chained transaction manager (MyBatis X JPA)
        ChainedTransactionManager transactionManager = new ChainedTransactionManager(jpaTransactionManager, dataSourceTransactionManager);
        return transactionManager;
    }
}