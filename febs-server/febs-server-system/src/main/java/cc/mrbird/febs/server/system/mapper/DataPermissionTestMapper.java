package cc.michael.febs.server.system.mapper;

import cc.michael.febs.common.core.entity.system.DataPermissionTest;
import cc.michael.febs.common.datasource.starter.annotation.DataPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author michael
 */
@DataPermission(methods = {"selectPage"})
public interface DataPermissionTestMapper extends BaseMapper<DataPermissionTest> {

}
