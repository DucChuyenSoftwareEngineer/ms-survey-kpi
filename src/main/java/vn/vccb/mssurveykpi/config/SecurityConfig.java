package vn.vccb.mssurveykpi.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        String[] lstIgnoreUrl;

        lstIgnoreUrl = new String[]{"/", "/v2/api-docs", "/configuration/ui",
                "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/**"};

        httpSecurity.cors().configurationSource(request -> {
            CorsConfiguration config;
            List<String> lstCorsAllow;

            lstCorsAllow = Collections.singletonList("*");

            config = new CorsConfiguration();
            config.setAllowedOrigins(lstCorsAllow);
            config.setAllowedMethods(lstCorsAllow);
            config.setAllowedHeaders(lstCorsAllow);

            return config;
        }).and().csrf().disable()
                .authorizeRequests()
                .antMatchers(lstIgnoreUrl).permitAll()
                .anyRequest().authenticated()
                // .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //.and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
