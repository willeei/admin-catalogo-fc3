package tech.willeei.admin.catalogo.infrastructure.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.willeei.admin.catalogo.infrastructure.configuration.annotations.VideoCreatedQueue;
import tech.willeei.admin.catalogo.infrastructure.configuration.annotations.VideoEncodedQueue;
import tech.willeei.admin.catalogo.infrastructure.configuration.properties.amqp.QueueProperties;

@Configuration
public class AmpqConfig {

    @Bean
    @ConfigurationProperties("amqp.queues.video-created")
    @VideoCreatedQueue
    public QueueProperties videoCreatedQueueProperties() {
        return new QueueProperties();
    }

    @Bean
    @ConfigurationProperties("amqp.queues.video-encoded")
    @VideoEncodedQueue
    public QueueProperties encodedCreatedQueueProperties() {
        return new QueueProperties();
    }

    @Bean
    public String queueName(@VideoCreatedQueue QueueProperties props) {
        return props.getQueue();
    }
}
