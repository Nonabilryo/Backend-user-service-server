package nonabili.userserviceserver.entity

import jakarta.persistence.*
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val idx: UUID = UUID.randomUUID(),
    val name: String,
    val id: String,
    val password: String,
    val email: String,
    val tell: String? = null,
    val adress: String = "전국",
    val signed: Date = Date(),
    @OneToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "profile_idx")
    val image: Image? = null,
    val description: String = "",
    val role: Role = Role.USER
)
