package di

import data.repository.DealRepositoryImpl
import domain.repository.DealRepository
import org.koin.dsl.module
import ui.deals.DealsViewModel
import useCase.DealsUseCase

private var domainModule = module {
    factory { DealsUseCase() }
}

private var presentationModule = module {
    single<DealRepository> { DealRepositoryImpl() }

    single { DealsViewModel() }
}

private fun getAllModules() = listOf(
    domainModule, presentationModule
)

fun getSharedModules() = getAllModules()