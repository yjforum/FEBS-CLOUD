package cc.michael.febs.server.system.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author michael
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:febs-server-system.properties"})
@ConfigurationProperties(prefix = "febs.server.system")
public class FebsServerSystemProperties {
    /**
     * 批量插入当批次可插入的最大值
     */
    private Integer batchInsertMaxNum = 1000;
}
