package cn.tedu.sp06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Sp06RibbonApplication {
    /**
     * 创建 RestTemplate 实例，并存入 spring 容器
     *  @LoadBalanced -
     *  对RestTemplate进行增强，封装RestTemplate，添加负载均衡功能
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        //设置调用超时时间，超时后认为调用失败
        SimpleClientHttpRequestFactory f = new SimpleClientHttpRequestFactory();
        f.setConnectTimeout(1000); //建立连接等待时间
        f.setReadTimeout(1000);    //连接建立后，发送请求后，等待接收响应的时间
        return new RestTemplate(f);

//        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Sp06RibbonApplication.class, args);
    }

}
