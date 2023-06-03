package ru.alexvlasov.shkolplay.presentation.features.student.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.alexvlasov.shkolplay.R
import ru.alexvlasov.shkolplay.presentation.navigation.NavigationItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class, ExperimentalComposeUiApi::class)
@Composable
fun MenuScreen(
    navController: NavController,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                snackbar = { Snackbar(snackbarData = it) })
        },
    ) { p ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues = p)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(Modifier.fillMaxWidth(0.8f)) {
                Image(
                    painter = painterResource(R.mipmap.back_text),
                    contentDescription = stringResource(R.string.logo),
                    modifier = Modifier
                        .fillMaxWidth().padding(vertical = 16.dp)
                )
                Button(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    onClick = {
                        navController.navigate(NavigationItem.MemoryGame.route)
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Память",
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    )
                }
                Button(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    onClick = {
                        navController.navigate(NavigationItem.MathGame.route)
                    },
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Математика",
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    )
                }
                Button(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    enabled = false,
                    onClick = {},
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Скоро",
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    )
                }
            }
        }
    }
}

