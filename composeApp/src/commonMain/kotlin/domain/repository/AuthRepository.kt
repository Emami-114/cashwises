package domain.repository

import data.model.RegisterModel

interface AuthRepository {
    suspend fun register(registerModel: RegisterModel): Boolean

    suspend fun login(email: String, password: String): Boolean

    suspend fun passwordForget(email: String): Boolean
    suspend fun logout()
}