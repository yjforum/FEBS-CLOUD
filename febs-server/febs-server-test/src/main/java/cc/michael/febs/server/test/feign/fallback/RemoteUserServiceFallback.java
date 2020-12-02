package cc.michael.febs.server.test.feign.fallback;

import cc.michael.febs.common.core.annotation.Fallback;
import cc.michael.febs.server.test.feign.IRemoteUserService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author michael
 */
@Slf4j
@Fallback
public class RemoteUserServiceFallback implements FallbackFactory<IRemoteUserService> {

    @Override
    public IRemoteUserService create(Throwable throwable) {
        return (queryRequest, user) -> {
            log.error("获取用户信息失败", throwable);
            return null;
        };
    }
}