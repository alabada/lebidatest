package com.lbd.filesystem.common.configuration;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by luozhanghua on 16/4/22.
 */
abstract class AbstractConfigProperties {

    private Configuration config;

    private Properties properties = new Properties();

    AbstractConfigProperties() {
        try {
        	if (this.getConfigFileNameList() != null) {
        		for (String fileName : this.getConfigFileNameList()) {
                    final InputStream is = this.getClass().getResourceAsStream(fileName);
                    final Properties properties = new Properties();
                    properties.load(is);
                    this.properties.putAll(properties);
                }
        	}
            final PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration(this.getConfigFileName());
            propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
            this.config = propertiesConfiguration;
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    /**
     * 获取配置文件名抽象方法
     * @return
     */
    abstract String getConfigFileName();

    /**
     * 获取全部文件列表
     * @return
     */
    abstract List<String> getConfigFileNameList();

    /**
     * 根据Key属性从配置文件中获取值为String的数据
     * @param key
     * @return
     */
    public String getConfig(String key) {
        return this.config.getString(key);
    }

    /**
     * 根据Key获取List
     * @param key
     * @return
     */
    public List<Object> getList(String key) {
        return this.config.getList(key);
    }

    /**
     * 根据key属性从配置文件中读取值为boolean的数据
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        final String tmp = this.config.getString(key);
        return Boolean.valueOf(tmp);
    }

    /**
     * 根据Key属性从配置文件中读取值为int的数据
     * @param key
     * @return
     */
    public int getInt(String key) {
        final String tmp = this.config.getString(key);
        int result = 0;
        try {
            result = Integer.parseInt(tmp);
        } catch (NumberFormatException e) {
        	e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据key属性从配置文件中读取值为long的数据
     * @param key
     * @return
     */
    public long getLong(String key) {
        final String tmp = this.config.getString(key);
        long result = 0;
        try {
            result = Long.parseLong(tmp);
        } catch (NumberFormatException e) {
        	e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取所有Key
     * @return
     */
    public Iterator<String> getKeys() {
        return this.config.getKeys();
    }


    /**
     * 获取properties
     * @return properties properties
     */
    public Properties getProperties() {
        return this.properties;
    }
}
