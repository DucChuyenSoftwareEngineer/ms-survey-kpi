package vn.vccb.mssurveykpi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;

@Configuration
public class ApplicationConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource;

        messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:message/message");
            messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());

        return messageSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager;

        transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);

        return transactionManager;
    }
}
