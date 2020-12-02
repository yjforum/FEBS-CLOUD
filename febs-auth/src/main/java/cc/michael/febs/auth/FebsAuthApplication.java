package cc.michael.febs.auth;

import cc.michael.febs.common.security.starter.annotation.EnableFebsCloudResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author michael
 */
@SpringBootApplication
@EnableFebsCloudResourceServer
@MapperScan("cc.michael.febs.auth.mapper")
public class FebsAuthApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FebsAuthApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
