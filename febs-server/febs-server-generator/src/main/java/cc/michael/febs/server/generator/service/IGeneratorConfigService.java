package cc.michael.febs.server.generator.service;

import cc.michael.febs.common.core.entity.system.GeneratorConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author michael
 */
public interface IGeneratorConfigService extends IService<GeneratorConfig> {

    /**
     * 查询
     *
     * @return GeneratorConfig
     */
    GeneratorConfig findGeneratorConfig();

    /**
     * 修改
     *
     * @param generatorConfig generatorConfig
     */
    void updateGeneratorConfig(GeneratorConfig generatorConfig);

}
