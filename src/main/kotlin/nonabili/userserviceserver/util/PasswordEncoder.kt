package nonabili.userserviceserver.util

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class PasswordEncoder {
    @Bean
    fun customPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}