package cc.michael.febs.common.core.entity;

import cc.michael.febs.common.core.entity.system.Dept;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author michael
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends Tree<Dept> {

    private Integer orderNum;
}
