package config.security;

import entities.user.User;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import support.holders.SessionsHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

/**
 * Created by ANykytenko on 8/10/2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        sessionsHolder().setAuthenticationManagerBuilder(auth);
        sessionsHolder().update();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().maximumSessions(100).sessionRegistry(sessionRegistry());
        http.authorizeRequests()
                .antMatchers("/Challenge/Close/**").access("hasRole('ROLE_" + User.Role.ADMIN.name() + "')")
                .antMatchers("/Challenge/**").access("hasRole('ROLE_" + User.Role.ADMIN.name() + "') or hasRole('ROLE_" + User.Role.USER.name() + "')")
                .antMatchers("/Pages/Challenges").access("hasRole('ROLE_" + User.Role.ADMIN.name() + "') or hasRole('ROLE_" + User.Role.USER.name() + "')")
                .antMatchers("/Pages/Ranks").access("hasRole('ROLE_" + User.Role.ADMIN.name() + "') or hasRole('ROLE_" + User.Role.USER.name() + "')")
                .antMatchers("/").access("hasRole('ROLE_" + User.Role.ADMIN.name() + "') or hasRole('ROLE_" + User.Role.USER.name() + "')")
                .and().formLogin().defaultSuccessUrl("/", false)
                .and().logout().logoutSuccessUrl("/Pages/logout");
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SessionsHolder sessionsHolder() {
        return new SessionsHolder();
    }

    @Bean
    public ApplicationListener applicationListener() {
        return new ApplicationListener() {
            public void onApplicationEvent(ApplicationEvent applicationEvent) {
                if (applicationEvent instanceof InteractiveAuthenticationSuccessEvent ||
                        applicationEvent instanceof SessionDestroyedEvent) {
                    notifyClients();
                }
            }
        };
    }

    private void notifyClients() {
        messagingTemplate.convertAndSend("/UpdateEvent/All", "{'status' : 'ok'}");
    }

}
