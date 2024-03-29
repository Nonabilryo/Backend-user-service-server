package nonabili.userserviceserver.service

import ch.qos.logback.core.status.ErrorStatus
import nonabili.userserviceserver.repository.UserRepository
import nonabili.userserviceserver.util.error.CustomError
import nonabili.userserviceserver.util.error.ErrorState
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {
    fun getUserInfo(userName: String) {
        val user = userRepository.findUserByName(userName) ?: throw CustomError(ErrorState.NOT_FOUND_USERNAME)
        return
    }
}