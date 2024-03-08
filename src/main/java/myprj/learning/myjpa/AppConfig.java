package myprj.learning.myjpa;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan
//@EnableJpaRepositories(basePackages = "myprj.learning.myjpa.repository")
public class AppConfig {


//  // H2 로컬
//    @Bean
//    public DataSource dataSource() {
//        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//        dataSource.setDriverClass(org.h2.Driver.class);
//        dataSource.setUrl("jdbc:h2:tcp://localhost/E:/srcRepo/202312-jpa/h2_database/database/hello");
//        dataSource.setUsername("user");
//        dataSource.setPassword("0000");
//        return dataSource;
//    }
//

//  // MySql 로컬
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/myjpa");
        dataSource.setUsername("root");
        dataSource.setPassword("0000");
        return dataSource;
    }

//  // MySql 원격
//    @Bean
//    public DataSource dataSource() {
//        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
//        dataSource.setUrl("jdbc:mysql://3.39.192.186:3306/myjpa");
//        dataSource.setUsername("root");
//        dataSource.setPassword("0000");
//        return dataSource;
//    }

    @Bean
    public TransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setDataSource(dataSource());
        return tm;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        //
        Properties properties = new Properties();
//        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.id.new_generator_mappings", "true");
        //
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("myprj.learning.myjpa");
        // @Service 랑 @Repository 붙어 있는 클래스들 못 찾아오고 Bean 으로 등록 못함.
        // AppConfig.java 설정 클래스 파일에다가 @ComponentScan 붙여서 억지로 다 스캔해서 Bean 으로 등록하게 만듦.
        // 그러면 무슨 무슨 Exception, 무슨 무슨 Builder, JdbcTemplate 뭐 별의 별 것들 다 긁어와서 Bean 으로 등록함.
        // JPA 관련된 어노테이션만 찾아서 빈으로 등록하게 하고 싶음
        // 그때 emf.setPackagesToScan(); 를 내가 스캔하고 싶은 디렉토리의 가장 상위로 지정해주면 됨.
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaProperties(properties);
        emf.setPersistenceUnitName("hello");
//        emf.setPersistenceXmlLocation("persistence.xml");
        // persistence.xml 에서 등록할 설정 정보들은 모두 AppConfig.java 설정 클래스 안에서 넣어줬기 때문에 불필요함.
        // 그래도 읽어오도록 하고 싶다면 지정하기는 가능.
        //
        return emf;
    }



    // 이미지 등록
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")           // 해당 경로의 요청이 올 때
                .addResourceLocations("classpath:/static/") // classpath 기준으로 'm' 디렉토리 밑에서 제공
                .setCachePeriod(20);                   // 캐싱 지정
    }

}
