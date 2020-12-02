package cc.michael.febs.common.security.starter.annotation;

import cc.michael.febs.common.security.starter.configure.FebsCloudResourceServerConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author michael
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FebsCloudResourceServerConfigure.class)
public @interface EnableFebsCloudResourceServer {
}
