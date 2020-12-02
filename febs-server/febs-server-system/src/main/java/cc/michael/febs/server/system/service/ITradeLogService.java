package cc.michael.febs.server.system.service;

import cc.michael.febs.common.core.entity.system.TradeLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author michael
 */
public interface ITradeLogService extends IService<TradeLog> {

    /**
     * 打包并派送
     *
     * @param tradeLog 交易日志
     */
    void packageAndSend(TradeLog tradeLog);
}
