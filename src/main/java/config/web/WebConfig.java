package config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import support.mail.CustomMailTransport;
import support.mail.MailTransport;
import support.strings.EnglishStrings;
import support.strings.Strings;

/**
 * Created by ANykytenko on 8/10/2016.
 */
@Configuration
@EnableWebMvc
@ComponentScan({"config", "controllers", "entities.user", "entities.message"})
@Import({WebSocketConfig.class})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("WEB-INF/views/css/").setCachePeriod(1);
        registry.addResourceHandler("/js/**").addResourceLocations("WEB-INF/views/js/").setCachePeriod(1);
        registry.addResourceHandler("/audio/**").addResourceLocations("WEB-INF/views/audio/").setCachePeriod(1);
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public MailTransport mailTransport() {
        return new CustomMailTransport();
    }

    @Bean
    public Strings strings() {
        return new EnglishStrings();
    }
}
