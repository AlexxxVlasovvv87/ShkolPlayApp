package ru.alexvlasov.shkolplay.domain.model

import java.util.*

//Модель группа

class Group() {
    var groupId: UUID? = null
    var creator: User? = null
    var groupName: String = ""
    var inviteCode: String = ""
    var students: List<User> = listOf()
}
