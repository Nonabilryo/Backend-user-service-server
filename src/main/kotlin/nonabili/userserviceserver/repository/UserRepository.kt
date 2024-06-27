package nonabili.userserviceserver.repository

import nonabili.userserviceserver.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository: JpaRepository<User, UUID> {
    fun findUserByName(name: String): User?
    fun findUserById(id: String): User?
    fun findUserByIdx(idx: UUID): User?
}