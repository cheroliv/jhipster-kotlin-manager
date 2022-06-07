package ceelo.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Properties specific to Ceelo.
 *
 * Properties are configured in the `application.yml` file.
 * See [tech.jhipster.config.JHipsterProperties] for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
class ApplicationProperties
