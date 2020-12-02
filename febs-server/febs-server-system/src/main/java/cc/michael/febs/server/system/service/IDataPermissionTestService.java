package cc.michael.febs.server.system.service;

import cc.michael.febs.common.core.entity.QueryRequest;
import cc.michael.febs.common.core.entity.system.DataPermissionTest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * @author michael
 */
public interface IDataPermissionTestService extends IService<DataPermissionTest> {
    /**
     * 查询（分页）
     *
     * @param request            QueryRequest
     * @param dataPermissionTest dataPermissionTest
     * @return IPage<DataPermissionTest>
     */
    IPage<DataPermissionTest> findDataPermissionTests(QueryRequest request, DataPermissionTest dataPermissionTest);
}
