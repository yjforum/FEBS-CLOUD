package cc.michael.febs.server.system.controller;

import cc.michael.febs.common.core.entity.FebsResponse;
import cc.michael.febs.common.core.entity.system.TradeLog;
import cc.michael.febs.server.system.service.ITradeLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author michael
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final ITradeLogService tradeLogService;

    @PostMapping("package/send")
    public void packageSend(@RequestBody TradeLog tradeLog) {
        this.tradeLogService.packageAndSend(tradeLog);
    }

    @GetMapping("scope/test")
    @PreAuthorize("#oauth2.hasScope('write')")
    public FebsResponse testScope() {
        return new FebsResponse().message("当前client包含write scope");
    }
}
