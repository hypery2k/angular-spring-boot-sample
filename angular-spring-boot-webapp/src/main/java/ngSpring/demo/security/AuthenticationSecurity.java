package ngSpring.demo.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Configures global authentication provider, e.g. for password encryption and user details service
 *
 * @author hypery2k
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationProvider")
    AuthenticationProvider authenticationProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider);
    }
}
