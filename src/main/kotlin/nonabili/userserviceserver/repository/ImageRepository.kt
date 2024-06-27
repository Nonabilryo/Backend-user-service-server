package nonabili.userserviceserver.repository

import nonabili.userserviceserver.entity.Image
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ImageRepository: JpaRepository<Image, UUID> {
}