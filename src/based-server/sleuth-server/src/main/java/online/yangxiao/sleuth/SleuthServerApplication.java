package online.yangxiao.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

//spring-boot 2.x 不需要手动创建server
//使用java -jar zipkin-server-2.10.1-exec.jar localhost:9411
@SpringBootApplication
@EnableZipkinServer
public class SleuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SleuthServerApplication.class, args);
    }

}
