package tech.willeei.admin.catalogo.infrastructure.configuration.properties.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class StorageProperties implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(StorageProperties.class);

    private String locationPattern;
    private String filenamePattern;

    public StorageProperties() {
    }

    public String getLocationPattern() {
        return locationPattern;
    }

    public StorageProperties setLocationPattern(final String locationPattern) {
        this.locationPattern = locationPattern;
        return this;
    }

    public String getFilenamePattern() {
        return filenamePattern;
    }

    public StorageProperties setFilenamePattern(final String filenamePattern) {
        this.filenamePattern = filenamePattern;
        return this;
    }

    @Override
    public void afterPropertiesSet() {
        log.debug(toString());
    }

    @Override
    public String toString() {
        return "StorageProperties{" +
                "locationPattern='" + locationPattern + '\'' +
                ", filenamePattern='" + filenamePattern + '\'' +
                '}';
    }
}
