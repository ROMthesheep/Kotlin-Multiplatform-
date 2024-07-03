import Data.TitleTopBarTypes
import Navigation.Navigation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    PreComposeApp {
        KoinContext {
            val navigator = rememberNavigator()
            val titleTopBar = getTitleTopAppBar(navigator)
            val colors = getColorsTheme()
            val isNotDashboard = titleTopBar != TitleTopBarTypes.DASHBOARD.value



            AppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = titleTopBar,
                                    fontSize = 25.sp,
                                    color = colors.textColor,
                                )
                            },
                            backgroundColor = colors.backgroundColor,
                            navigationIcon = {
                                IconButton(onClick = {
                                    navigator.popBackStack()
                                }) {
                                    Icon(
                                        modifier = Modifier.padding(start = 16.dp),
                                        imageVector = if (isNotDashboard) Icons.Default.ArrowBackIosNew else Icons.Default.Apps,
                                        tint = colors.textColor,
                                        contentDescription = if (isNotDashboard) "Atras" else "Dashboard icon"
                                    )
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        if (!isNotDashboard) {
                            FloatingActionButton(
                                modifier = Modifier.padding(8.dp),
                                onClick = {
                                    navigator.navigate("/addExpenses")
                                },
                                shape = RoundedCornerShape(50),
                                backgroundColor = colors.addIconColor,
                                contentColor = Color.White
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    tint = Color.White,
                                    contentDescription = "Floating icon"
                                )
                            }
                        }
                    }
                ) {
                    Navigation(navigator = navigator)
                }
            }
        }
    }
}

@Composable
fun getTitleTopAppBar(navigator: Navigator): String {
    var title = TitleTopBarTypes.DASHBOARD
    val navigatorEntryState = navigator.currentEntry.collectAsState(null)
    val isOnAddExpenses = navigatorEntryState.value?.route?.route.equals("/addExpenses/{id}?")
    val isOnEditExpenses = navigatorEntryState.value?.path<Long>("id")

    if (isOnAddExpenses) {
        title = TitleTopBarTypes.ADD
    }

    isOnEditExpenses?.let {
        title = TitleTopBarTypes.EDIT
    }

    return title.value
}