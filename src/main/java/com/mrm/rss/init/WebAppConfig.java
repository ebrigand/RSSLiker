package com.mrm.rss.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * Configuration for Spring Web
 * 
 * @author ebrigand
 * 
 */
@Configuration
@EnableWebMvc
@EnableScheduling
@ImportResource("classpath*:config.xml")
@ComponentScan("com.mrm.rss")
@PropertySource("classpath:rssliker.properties")
public class WebAppConfig extends WebMvcConfigurerAdapter {

  @Resource
  private Environment env;

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.favorPathExtension(true).useJaf(false).ignoreAcceptHeader(true).mediaType("html", MediaType.TEXT_HTML).mediaType("json", MediaType.APPLICATION_JSON)
        .defaultContentType(MediaType.TEXT_HTML);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {

    List<ViewResolver> resolvers = new ArrayList<ViewResolver>();

    InternalResourceViewResolver r1 = new InternalResourceViewResolver();
    r1.setPrefix("/WEB-INF/pages/");
    r1.setSuffix(".jsp");
    r1.setViewClass(JstlView.class);
    resolvers.add(r1);

    JsonViewResolver r2 = new JsonViewResolver();
    resolvers.add(r2);

    ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
    resolver.setViewResolvers(resolvers);
    resolver.setContentNegotiationManager(manager);
    return resolver;

  }

  /**
   * View resolver for returning JSON in a view-based system. Always returns a
   * {@link MappingJacksonJsonView}.
   */
  public class JsonViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
      MappingJacksonJsonView view = new MappingJacksonJsonView();
      view.setPrettyPrint(true);
      return view;
    }
  }

}
