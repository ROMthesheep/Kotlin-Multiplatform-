import androidx.compose.ui.window.ComposeUIViewController
import dependencyInjection.appModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController { App() }

fun initKoin() {
    startKoin {
        modules(appModule())
    }.koin
}