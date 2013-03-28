package com.xplusz.TestJPA;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Interceptor;
import org.hibernate.cache.HashtableCacheProvider;
import org.hibernate.dialect.PostgreSQLDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JpaConfiguration {

    @Value("#{dataSource}")
    private javax.sql.DataSource dataSource;

    @Bean
    public Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("hibernate.dialect", PostgreSQLDialect.class.getName());
        props.put("hibernate.cache.provider_class", HashtableCacheProvider.class.getName());
        return props;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(false);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(localContainerEntityManagerFactoryBean().getObject());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(this.dataSource);
        lef.setJpaPropertyMap(this.jpaProperties());
        lef.setJpaVendorAdapter(this.jpaVendorAdapter());
        lef.setPackagesToScan("com.xplusz.TestJPA.domain");
        ConfigurableHibernatePersistence persistence = new ConfigurableHibernatePersistence();
        Interceptor interceptor = new HibernateInterceptor();
        persistence.setInterceptor(interceptor);
        lef.setPersistenceProvider(persistence);
        return lef;
    }

}
