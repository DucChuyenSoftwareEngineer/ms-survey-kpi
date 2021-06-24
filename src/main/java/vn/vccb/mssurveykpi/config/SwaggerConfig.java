package vn.vccb.mssurveykpi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	@Autowired
	private Environment evn;

	@Bean
	public Docket swaggerSpringfoxDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.pathMapping("/")
				.forCodeGeneration(true)
				.apiInfo(ApiInfo.DEFAULT)
				.groupName("Survey Kpi Api v1.0.0.0")
				.useDefaultResponseMessages(false)
				/*.securitySchemes(Collections.singletonList(apiKey()))e
				.securityContexts(Collections.singletonList(securityContext())*/
				.select()
				.paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("vn.vccb.mssurveykpi.controller"))
				.build();
	}

	// Todo khi nao co jwt thi mo ra

/*	private ApiKey apiKey() {
		String jwtKey;

		jwtKey = evn.getProperty(CommonConstant.JWT_KEY);

		return new ApiKey("JWT", jwtKey, "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.any())
				.build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope;
		AuthorizationScope[] authorizationScopes;

		authorizationScope = new AuthorizationScope("global", "accessEverything");
		authorizationScopes = new AuthorizationScope[]{authorizationScope};

		return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
	}*/
}
