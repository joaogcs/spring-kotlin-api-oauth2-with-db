package com.example.springkotlinuseroauth2postgresql.configs

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.*
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(SpringDocConfig::class)
class OpenApiConfig(
    private val springDocConfig: SpringDocConfig,
) {
    companion object {
        const val SECURITY_REQUIREMENT = "Password Flow"
    }

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .components(getComponents())
            .addSecurityItem(SecurityRequirement().addList(SECURITY_REQUIREMENT))
    }

    private fun getComponents(): Components? {
        val passwordFlowScheme = SecurityScheme()
            .type(SecurityScheme.Type.OAUTH2)
            .flows(
                OAuthFlows()
                    .password(
                        OAuthFlow()
                            .tokenUrl(springDocConfig.tokenUrl)
                            .scopes(getScopes())
                    )
            )
        return Components()
            .securitySchemes(mapOf(Pair(SECURITY_REQUIREMENT, passwordFlowScheme)))
    }

    private fun getScopes(): Scopes {
        val scopes = Scopes()
        springDocConfig.scopes.forEach { scope ->
            scopes.addString(scope.name, scope.item)
        }
        return scopes
    }
}

@ConstructorBinding
@ConfigurationProperties("springdoc.configs")
class SpringDocConfig(
    val tokenUrl: String,
    val scopes: List<Scope>,
) {
    class Scope(
        val name: String,
        val item: String,
    )
}