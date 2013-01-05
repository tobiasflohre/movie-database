package de.codecentric.moviedatabase.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationConfiguration.class);
		rootContext.setDisplayName("Movie database");
		servletContext.addListener(new ContextLoaderListener(rootContext));
		ServletRegistration.Dynamic dispatcher = 
				servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
		dispatcher.setLoadOnStartup(1); 
		dispatcher.addMapping("/");
		
		servletContext.addFilter("corsFilter", CorsFilter.class)
			.addMappingForUrlPatterns(null, false, "/*");
		
		servletContext.addFilter("hiddenHttpMethodFilter", HiddenHttpMethodFilter.class)
			.addMappingForUrlPatterns(null, false, "/*");
	}
}
