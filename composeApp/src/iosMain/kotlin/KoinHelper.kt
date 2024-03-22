import di.getSharedModules
import org.koin.core.context.startKoin


fun doInitKoin() {
    startKoin { modules(getSharedModules()) }
}