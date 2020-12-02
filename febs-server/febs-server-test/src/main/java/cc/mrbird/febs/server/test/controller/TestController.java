package cc.michael.febs.server.test.controller;

import cc.michael.febs.common.core.entity.FebsResponse;
import cc.michael.febs.common.core.entity.QueryRequest;
import cc.michael.febs.common.core.entity.system.SystemUser;
import cc.michael.febs.common.core.entity.system.TradeLog;
import cc.michael.febs.common.core.utils.FebsUtil;
import cc.michael.febs.server.test.feign.IRemoteUserService;
import cc.michael.febs.server.test.service.ITradeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author michael
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TestController {

    private final IRemoteUserService remoteUserService;
    private final ITradeLogService tradeLogService;

    /**
     * 用于演示 Feign调用受保护的远程方法
     */
    @GetMapping("user/list")
    public FebsResponse getRemoteUserList(QueryRequest request, SystemUser user) {
        return remoteUserService.userList(request, user);
    }

    /**
     * 测试分布式事务
     */
    @GetMapping("pay")
    public void orderAndPay(TradeLog tradeLog) {
        this.tradeLogService.orderAndPay(tradeLog);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("user")
    public Map<String, Object> currentUser() {
        Map<String, Object> map = new HashMap<>(5);
        map.put("currentUser", FebsUtil.getCurrentUser());
        map.put("currentUsername", FebsUtil.getCurrentUsername());
        map.put("currentUserAuthority", FebsUtil.getCurrentUserAuthority());
        map.put("currentTokenValue", FebsUtil.getCurrentTokenValue());
        map.put("currentRequestIpAddress", FebsUtil.getHttpServletRequestIpAddress());
        return map;
    }
}
