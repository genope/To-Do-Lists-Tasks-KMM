package todolist.kmm.di

import todolist.kmm.data_cache.CacheDataImp
import todolist.kmm.data_cache.sqldelight.SharedDatabase
import todolist.kmm.data_remote.RemoteDataImp
import todolist.kmm.data_remote.model.mapper.ApiTaskMapper
import todolist.kmm.domain.IRepository
import todolist.kmm.domain.interactors.GetTasksUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import todolist.kmm.domain.interactors.GetTaskUseCase
import todolist.kmm.domain.interactors.GetTasksFavoritesUseCase
import todolist.kmm.domain.interactors.IsTaskFavoriteUseCase
import todolist.kmm.domain.interactors.SwitchTaskFavoriteUseCase
import todolist.kmm.presentation.ui.features.task_details.TaskDetailViewModel
import todolist.kmm.presentation.ui.features.tasks.TasksViewModel
import todolist.kmm.presentation.ui.features.tasks_favorites.TasksFavoritesViewModel
import todolist.kmm.repository.ICacheData
import todolist.kmm.repository.IRemoteData
import todolist.kmm.repository.RepositoryImp

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            viewModelModule,
            useCasesModule,
            repositoryModule,
            ktorModule,
            sqlDelightModule,
            mapperModule,
            dispatcherModule,
            platformModule()
        )
    }
val viewModelModule = module {
    factory { TasksViewModel(get()) }
    factory { TasksFavoritesViewModel(get()) }
    factory { params -> TaskDetailViewModel(get(),get(),get(),params.get()) }
}
val useCasesModule: Module = module {
    factory { GetTasksUseCase(get(), get()) }
    factory { GetTaskUseCase(get(), get()) }
    factory { GetTasksFavoritesUseCase(get(), get()) }
    factory { IsTaskFavoriteUseCase(get(), get()) }
    factory { SwitchTaskFavoriteUseCase(get(), get()) }
}

val repositoryModule = module {
    single<IRepository> { RepositoryImp(get(), get()) }
    single<ICacheData> { CacheDataImp(get()) }
    single<IRemoteData> { RemoteDataImp(get(), get(), get()) }
}

val mapperModule = module {
    factory { ApiTaskMapper() }
}

val ktorModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    single { "https://apex.oracle.com/pls/apex/internship_space" }
}
val sqlDelightModule = module {
    single { SharedDatabase(get()) }
}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}

fun initKoin() = initKoin {}