package di

import data.repository.AuthRepositoryImpl
import data.repository.CategoryRepositoryImpl
import data.repository.DealRepositoryImpl
import data.repository.DealVoteRepositoryImpl
import data.repository.TagRepositoryImpl
import domain.repository.AuthRepository
import domain.repository.CategoryRepository
import domain.repository.DealRepository
import domain.repository.DealVoteRepository
import domain.repository.TagRepository
import org.koin.dsl.module
import ui.account.auth.login.LoginViewModel
import ui.account.auth.registration.viewModel.RegistrationViewModel
import ui.account.wish_list.WishListViewModel
import ui.category.viewModel.CategoryViewModel
import ui.deals.ViewModel.DealsViewModel
import ui.home.tags.CreateTagViewModel
import ui.home.tags.TagsViewModel
import ui.notification.NotificationViewModel
import ui.search.SearchScreenViewModel
import useCase.AuthUseCase
import useCase.CategoryUseCase
import useCase.DealsUseCase
import useCase.ImageUploadUseCase
import useCase.TagsUseCase

private var domainModule = module {
    factory { DealsUseCase() }
    factory { AuthUseCase() }
    factory { CategoryUseCase() }
    factory { TagsUseCase() }
    factory { ImageUploadUseCase() }
}

private var presentationModule = module {
    single<DealRepository> { DealRepositoryImpl() }
    single { DealsViewModel() }
    single<AuthRepository> { AuthRepositoryImpl() }
    single<CategoryRepository> { CategoryRepositoryImpl() }
    single<TagRepository> { TagRepositoryImpl() }
    single<DealVoteRepository> { DealVoteRepositoryImpl() }
    single { RegistrationViewModel() }
    single { LoginViewModel() }
    single { CategoryViewModel() }
    single { SearchScreenViewModel() }
    single { TagsViewModel() }
    single { CreateTagViewModel() }
    single { WishListViewModel() }
    single { NotificationViewModel() }
}


private fun getAllModules() = listOf(
    domainModule, presentationModule
)

fun getSharedModules() = getAllModules()