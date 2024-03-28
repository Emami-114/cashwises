package di

import data.repository.AuthRepositoryImpl
import data.repository.DealRepositoryImpl
import domain.repository.AuthRepository
import domain.repository.DealRepository
import org.koin.dsl.module
import ui.account.auth.login.LoginViewModel
import ui.account.auth.registration.viewModel.RegistrationViewModel
import ui.deals.ViewModel.DealsViewModel
import useCase.AuthUseCase
import useCase.DealsUseCase

private var domainModule = module {
    factory { DealsUseCase() }
    factory { AuthUseCase() }
}

private var presentationModule = module {
    single<DealRepository> { DealRepositoryImpl() }
    single { DealsViewModel() }
    single<AuthRepository> { AuthRepositoryImpl() }
    single { RegistrationViewModel() }
    single { LoginViewModel() }
}

private fun getAllModules() = listOf(
    domainModule, presentationModule
)

fun getSharedModules() = getAllModules()