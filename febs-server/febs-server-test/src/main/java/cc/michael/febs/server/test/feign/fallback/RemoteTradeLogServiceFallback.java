package cc.michael.febs.server.test.feign.fallback;

import cc.michael.febs.server.test.feign.IRemoteTradeLogService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author michael
 */
@Slf4j
@Component
public class RemoteTradeLogServiceFallback implements FallbackFactory<IRemoteTradeLogService> {
    @Override
    public IRemoteTradeLogService create(Throwable throwable) {
        return tradeLog -> log.error("调用失败", throwable);
    }
}
