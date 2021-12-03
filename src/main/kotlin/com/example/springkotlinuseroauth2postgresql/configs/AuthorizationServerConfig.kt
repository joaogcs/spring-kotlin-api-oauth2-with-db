package com.example.springkotlinuseroauth2postgresql.configs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.validation.annotation.Validated
import javax.sql.DataSource
import javax.validation.constraints.NotEmpty

@Configuration
@EnableConfigurationProperties(AuthenticationConfig::class)
@EnableAuthorizationServer
@EnableResourceServer
class AuthorizationServerConfiguration(
    private val userDetailsService: UserDetailsService,
    private val authenticationManager: AuthenticationManager,
    private val authenticationConfig: AuthenticationConfig,
    private val dataSource: DataSource,
    private val passwordEncoder: PasswordEncoder
) : AuthorizationServerConfigurerAdapter() {

    companion object {
        const val KEY_ACCESS = "permitAll()"
        const val TOKEN_ACCESS = "permitAll()"
    }

    @Bean
    fun tokenStore(): TokenStore? {
        return JdbcTokenStore(dataSource)
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
            .withClient(authenticationConfig.clientId)
            .scopes(*authenticationConfig.scopes.toTypedArray())
            .secret(passwordEncoder.encode(authenticationConfig.clientSecret))
            .authorizedGrantTypes(*authenticationConfig.authorizedGrantTypes.toTypedArray())
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.userDetailsService(userDetailsService)
        endpoints.authenticationManager(authenticationManager)
        endpoints.tokenStore(tokenStore())
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security
            .tokenKeyAccess(KEY_ACCESS)
            .checkTokenAccess(TOKEN_ACCESS)
            .allowFormAuthenticationForClients()
    }
}

@Validated
@ConstructorBinding
@ConfigurationProperties("authentication")
class AuthenticationConfig(
    @field:NotEmpty
    val clientId: String,
    @field:NotEmpty
    val clientSecret: String,
    @field:NotEmpty
    val scopes: List<String>,
    @field:NotEmpty
    val authorizedGrantTypes: List<String>
)