package config.web;

import config.security.SessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by ANykytenko on 8/10/2016.
 */
public class WebInit implements WebApplicationInitializer {

    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addListener(new SessionListener());
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(WebConfig.class);
        ctx.setServletContext(servletContext);
        ServletRegistration.Dynamic dynamic = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        dynamic.addMapping("/");
        dynamic.setLoadOnStartup(1);
    }
}
