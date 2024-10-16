package tech.willeei.admin.catalogo.infrastructure.configuration;

import com.google.cloud.storage.Storage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tech.willeei.admin.catalogo.infrastructure.configuration.properties.google.GoogleStorageProperties;
import tech.willeei.admin.catalogo.infrastructure.configuration.properties.storage.StorageProperties;
import tech.willeei.admin.catalogo.infrastructure.services.StorageService;
import tech.willeei.admin.catalogo.infrastructure.services.impl.GoogleCloudStorageService;
import tech.willeei.admin.catalogo.infrastructure.services.local.InMemoryStorageService;

@Configuration
public class StorageConfig {

    @Bean
    @ConfigurationProperties(value = "storage.catalogo-videos")
    public StorageProperties storageProperties() {
        return new StorageProperties();
    }

    @Bean
    @Profile({"development", "test-integration", "test-e2e"})
    public StorageService localStorageAPI() {
        return new InMemoryStorageService();
    }

    @Bean
    @ConditionalOnMissingBean
    public StorageService gcStorageAPI(
            final GoogleStorageProperties props,
            final Storage storage
    ) {
        return new GoogleCloudStorageService(props.getBucket(), storage);
    }
}
