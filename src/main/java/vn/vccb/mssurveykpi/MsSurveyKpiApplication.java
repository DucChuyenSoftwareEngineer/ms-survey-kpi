package vn.vccb.mssurveykpi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import vn.vccb.mssurveykpi.util.MessageUtil;

import java.util.TimeZone;

@EnableSwagger2
@SpringBootApplication
public class MsSurveyKpiApplication {

//    @Autowired
//    private TypeResolver typeResolver;

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        SpringApplication.run(MsSurveyKpiApplication.class, args);
    }


    @Bean
    public CommandLineRunner run(ApplicationContext context) {
        return args -> {
            MessageUtil.setMessageSource(context.getBean(MessageSource.class));
        };
    }
}
