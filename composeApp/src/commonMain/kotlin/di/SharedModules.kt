package di

import data.repository.AuthRepositoryImpl
import data.repository.CategoryRepositoryImpl
import data.repository.DealRepositoryImpl
import domain.repository.AuthRepository
import domain.repository.CategoryRepository
import domain.repository.DealRepository
import org.koin.dsl.module
import ui.account.auth.login.LoginViewModel
import ui.account.auth.registration.viewModel.RegistrationViewModel
import ui.category.viewModel.CategoryViewModel
import ui.deals.ViewModel.DealsViewModel
import ui.search.SearchScreenViewModel
import useCase.AuthUseCase
import useCase.CategoryUseCase
import useCase.DealsUseCase

private var domainModule = module {
    factory { DealsUseCase() }
    factory { AuthUseCase() }
    factory { CategoryUseCase() }
}

private var presentationModule = module {
    single<DealRepository> { DealRepositoryImpl() }
    single { DealsViewModel() }
    single<AuthRepository> { AuthRepositoryImpl() }
    single<CategoryRepository> { CategoryRepositoryImpl() }
    single { RegistrationViewModel() }
    single { LoginViewModel() }
    single { CategoryViewModel() }
    single { SearchScreenViewModel() }
}


private fun getAllModules() = listOf(
    domainModule, presentationModule
)

fun getSharedModules() = getAllModules()