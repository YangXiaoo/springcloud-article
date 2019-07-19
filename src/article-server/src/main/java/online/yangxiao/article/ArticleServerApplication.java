package online.yangxiao.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages="online.yangxiao.article.dao")
public class ArticleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleServerApplication.class, args);
    }

}
