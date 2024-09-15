package tech.willeei.admin.catalogo.infrastructure.configuration;

import com.google.cloud.storage.Storage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tech.willeei.admin.catalogo.infrastructure.configuration.properties.GoogleStorageProperties;
import tech.willeei.admin.catalogo.infrastructure.services.StorageService;
import tech.willeei.admin.catalogo.infrastructure.services.impl.GoogleCloudStorageService;
import tech.willeei.admin.catalogo.infrastructure.services.local.InMemoryStorageService;

@Configuration
public class StorageConfig {

    @Bean(name = "storageService")
    @Profile({"development", "production"})
    public StorageService gcStorageService(
            final GoogleStorageProperties props,
            final Storage storage
    ) {
        return new GoogleCloudStorageService(props.getBucket(), storage);
    }

    @Bean(name = "storageService")
    @ConditionalOnMissingBean
    public StorageService inMemoryStorageService() {
        return new InMemoryStorageService();
    }
}
