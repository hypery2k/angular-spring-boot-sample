package ngSpring.demo.security;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 * Enable security authentication for web app
 *
 * @author hypery2k
 */
//tag::thymeleaf-config[]
@Configuration
@EnableWebMvcSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {
//end::thymeleaf-config[]

    // public ressources which are not secured
    private static final String[] UNSECURED_RESOURCE_LIST =
            new String[]{"/docs/*", "/styles/*", "/fonts/*", "/scripts/*", "/images/*", "/index.html", "/views/login.html"};

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(UNSECURED_RESOURCE_LIST);
    }

    //tag::thymeleaf-config[]
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(UNSECURED_RESOURCE_LIST).authenticated();
        http.csrf().disable();
        // use form authentication for web app
        http.authorizeRequests().and().formLogin().loginPage("/login");
        // enable HTTP basic authentication for REST calls
        http.authorizeRequests().antMatchers("/api/**").authenticated().and().httpBasic();
        //end::thymeleaf-config[]
    }
}