package tech.willeei.admin.catalogo.infrastructure.configuration;

import com.google.api.gax.retrying.RetrySettings;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.http.HttpTransportOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.threeten.bp.Duration;
import tech.willeei.admin.catalogo.infrastructure.configuration.properties.google.GoogleCloudProperties;
import tech.willeei.admin.catalogo.infrastructure.configuration.properties.google.GoogleStorageProperties;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Configuration
@Profile({"development", "production"})
public class GoogleCloudConfig {

    @Bean
    @ConfigurationProperties("google.cloud")
    public GoogleCloudProperties googleCloudProperties() {
        return new GoogleCloudProperties();
    }

    @Bean
    @ConfigurationProperties("google.cloud.storage.catalogo-videos")
    public GoogleStorageProperties googleStorageProperties() {
        return new GoogleStorageProperties();
    }

    @Bean
    public Credentials credentials(final GoogleCloudProperties props) {
        final var jsonContent = Base64.getDecoder().decode(props.getCredentials());

        try (final var stream = new ByteArrayInputStream(jsonContent)) {
            return GoogleCredentials.fromStream(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public Storage storage(final Credentials credentials, final GoogleStorageProperties storageProperties) {
        final var transportOptions = HttpTransportOptions.newBuilder()
                .setConnectTimeout(storageProperties.getConnectionTimeout())
                .setReadTimeout(storageProperties.getReadTimeout())
                .build();

        final var retrySettings = RetrySettings.newBuilder()
                .setInitialRetryDelay(Duration.ofMillis(storageProperties.getRetryDelay()))
                .setMaxRetryDelay(Duration.ofMillis(storageProperties.getRetryMaxDelay()))
                .setMaxAttempts(storageProperties.getRetryMaxAttempts())
                .setRetryDelayMultiplier(storageProperties.getRetryMultiplier())
                .build();

        final var options = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .setRetrySettings(retrySettings)
                .setTransportOptions(transportOptions)
                .build();

        return options.getService();
    }
}
