package cc.michael.febs.server.system.service.impl;

import cc.michael.febs.common.core.entity.system.TradeLog;
import cc.michael.febs.server.system.mapper.TradeLogMapper;
import cc.michael.febs.server.system.service.ITradeLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tracing.TracingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author michael
 */
@Slf4j
@Service("tradeLogService")
public class TradeLogServiceImpl extends ServiceImpl<TradeLogMapper, TradeLog> implements ITradeLogService {

    ConcurrentHashMap<String, Long> hashMap = new ConcurrentHashMap<>();

    @Override
    @LcnTransaction
    public void packageAndSend(TradeLog tradeLog) {
        TradeLog tl = new TradeLog();
        tl.setGoodsId(tradeLog.getGoodsId());
        tl.setGoodsName(tradeLog.getGoodsName());
        tl.setStatus("打包完毕，开始物流配送！");
        tl.setCreateTime(new Date());

        this.save(tl);
        log.info("商品ID为{}，名称为{}的商品打包完毕，开始物流配送", tradeLog.getGoodsId(), tradeLog.getGoodsName());

        hashMap.put(TracingContext.tracing().groupId(), tradeLog.getId());
    }

}
