package online.yangxiao.hyxtrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard
@SpringBootApplication
@EnableCircuitBreaker
public class HystrixDashBoardServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashBoardServerApplication.class, args);
    }

}
