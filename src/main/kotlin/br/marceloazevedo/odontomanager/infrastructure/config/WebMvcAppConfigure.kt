package br.marceloazevedo.odontomanager.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class WebMvcAppConfigure {
    @Bean
    fun addCorsMappings(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.applyPermitDefaultValues()
        configuration.allowedOrigins = listOf("http://localhost:3000")
        configuration.allowedHeaders = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
