package cc.michael.febs.common.core.entity;


import cc.michael.febs.common.core.entity.system.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author michael
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends Tree<Menu> {

    private String path;
    private String component;
    private String perms;
    private String icon;
    private String type;
    private Integer orderNum;
}
