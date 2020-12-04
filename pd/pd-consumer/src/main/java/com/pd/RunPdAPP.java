package com.pd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication				
@MapperScan("com.pd.mapper")	
public class RunPdAPP extends WebMvcConfigurerAdapter 
implements EmbeddedServletContainerCustomizer {
	// import org.springframework.amqp.core.Queue;
	// 封装队列信息的对象
	// rabbitmq 的自动配置类会自动发现这个Queue实例，
	// 根据其中封装的队列信息，在rabbitmq服务器上创建这个队列
	@Bean
	public Queue orderQueue(){
		return new Queue("orderQueue"); //true,false,false
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RunPdAPP.class, args);
	}
	@Override
	public void configureContentNegotiation
	(ContentNegotiationConfigurer configurer) {
		//设置可以用*.html访问json
		configurer.favorPathExtension(false);
	}
	
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setSessionTimeout(60*30);//单位为秒 0永不过期 		
	}
}
