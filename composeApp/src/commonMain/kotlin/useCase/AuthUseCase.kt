package useCase

import data.model.RegisterModel
import domain.repository.AuthRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthUseCase : KoinComponent {
    private val repository: AuthRepository by inject()

    suspend fun register(registerModel: RegisterModel, onSuccess: () -> Unit) {
        try {
            repository.register(registerModel = registerModel).let { isSuccess ->
                if (isSuccess) {
                    onSuccess()
                } else {
                    throw Exception("Oh, something went wrong!")
                }
            }
        } catch (e: Exception) {
            throw Exception("Error doRegistration: ${e.message}")
        }
    }

    suspend fun login(email: String, password: String, onSuccess: () -> Unit) {
        try {
            repository.login(email, password).let { isSuccess ->
                if (isSuccess) {
                    onSuccess()
                } else {
                    throw Exception("Oh, something went wrong!")
                }
            }
        } catch (e: Exception) {
            throw Exception("Error wit login ${e.message}")
        }
    }
}