package cc.michael.febs.server.test.feign;

import cc.michael.febs.common.core.entity.constant.FebsServerConstant;
import cc.michael.febs.common.core.entity.system.TradeLog;
import cc.michael.febs.server.test.feign.fallback.RemoteTradeLogServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author michael
 */
@FeignClient(value = FebsServerConstant.FEBS_SERVER_SYSTEM, contextId = "tradeLogServiceClient", fallbackFactory = RemoteTradeLogServiceFallback.class)
public interface IRemoteTradeLogService {

    /**
     * 打包派送
     *
     * @param tradeLog 交易日志
     */
    @PostMapping("package/send")
    void packageAndSend(@RequestBody TradeLog tradeLog);
}
