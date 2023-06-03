package ru.alexvlasov.shkolplay.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexvlasov.shkolplay.domain.model.User
import ru.alexvlasov.shkolplay.domain.model.UserRole
import java.util.*

// Иниициализация сущности и одна из трёх компонент Room. Entity - сущность, таблица в базе данных.
// Есть имя, первичный ключ, колонки и инициализатор (конструктор)

@Entity(tableName = "users_table")
class UserEntity(
    @PrimaryKey
    @ColumnInfo(name="user_id")
    var uid: UUID,
    @ColumnInfo(name="email")
    var email: String,
    @ColumnInfo(name="name")
    var name: String,
    @ColumnInfo(name="surname")
    var surname: String,
    @ColumnInfo(name="role")
    var role: UserRole,
) {
    fun toModel(): User = User(
        uid,
        email,
        name,
        surname,
        role
    )

    constructor(model: User) : this(
        model.uid!!,
        model.email,
        model.name,
        model.surname,
        model.role,
    )
}
