package com.lbd.filesystem.common.configuration;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by luozhanghua on 16/4/22.
 */
@Component
public final class SystemConfigProperties extends AbstractConfigProperties {

    private static SystemConfigProperties confieProperties = new SystemConfigProperties();

    private SystemConfigProperties(){

    }

    @Override
    String getConfigFileName() {
        return "systemConfig.properties";
    }

    @Override
    List<String> getConfigFileNameList() {
        return null;
    }
    
    /**
     * 获得SystemConfigProperties
     * @return
     */
    public static SystemConfigProperties getInstance() {
        return confieProperties;
    }
}
