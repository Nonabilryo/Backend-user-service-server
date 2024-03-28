package nonabili.userserviceserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UserServiceServerApplication

fun main(args: Array<String>) {
    runApplication<UserServiceServerApplication>(*args)
}
