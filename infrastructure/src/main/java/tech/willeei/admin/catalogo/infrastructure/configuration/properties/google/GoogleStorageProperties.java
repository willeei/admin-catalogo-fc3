package tech.willeei.admin.catalogo.infrastructure.configuration.properties.google;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class GoogleStorageProperties implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(GoogleStorageProperties.class);

    private String bucket;
    private int connectionTimeout;
    private int readTimeout;
    private int retryDelay;
    private int retryMaxAttempts;
    private int retryMaxDelay;
    private double retryMultiplier;

    public String getBucket() {
        return bucket;
    }

    public GoogleStorageProperties setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public GoogleStorageProperties setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public GoogleStorageProperties setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public int getRetryDelay() {
        return retryDelay;
    }

    public GoogleStorageProperties setRetryDelay(int retryDelay) {
        this.retryDelay = retryDelay;
        return this;
    }

    public int getRetryMaxAttempts() {
        return retryMaxAttempts;
    }

    public GoogleStorageProperties setRetryMaxAttempts(int retryMaxAttempts) {
        this.retryMaxAttempts = retryMaxAttempts;
        return this;
    }

    public int getRetryMaxDelay() {
        return retryMaxDelay;
    }

    public GoogleStorageProperties setRetryMaxDelay(int retryMaxDelay) {
        this.retryMaxDelay = retryMaxDelay;
        return this;
    }

    public double getRetryMultiplier() {
        return retryMultiplier;
    }

    public GoogleStorageProperties setRetryMultiplier(double retryMultiplier) {
        this.retryMultiplier = retryMultiplier;
        return this;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug(toString());
    }

    @Override
    public String toString() {
        return "GoogleStorageProperties{" +
                "bucket='" + bucket + '\'' +
                ", connectionTimeout=" + connectionTimeout +
                ", readTimeout=" + readTimeout +
                ", retryDelay=" + retryDelay +
                ", retryMaxAttempts=" + retryMaxAttempts +
                ", retryMaxDelay=" + retryMaxDelay +
                ", retryMultiplier=" + retryMultiplier +
                '}';
    }
}
