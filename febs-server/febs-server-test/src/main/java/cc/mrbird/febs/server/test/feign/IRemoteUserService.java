package cc.michael.febs.server.test.feign;

import cc.michael.febs.common.core.entity.FebsResponse;
import cc.michael.febs.common.core.entity.QueryRequest;
import cc.michael.febs.common.core.entity.constant.FebsServerConstant;
import cc.michael.febs.common.core.entity.system.SystemUser;
import cc.michael.febs.server.test.feign.fallback.RemoteUserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author michael
 */
@FeignClient(value = FebsServerConstant.FEBS_SERVER_SYSTEM, contextId = "userServiceClient", fallbackFactory = RemoteUserServiceFallback.class)
public interface IRemoteUserService {

    /**
     * remote /user endpoint
     *
     * @param queryRequest queryRequest
     * @param user         user
     * @return FebsResponse
     */
    @GetMapping("user")
    FebsResponse userList(@RequestParam("queryRequest") QueryRequest queryRequest, @RequestParam("user") SystemUser user);
}
