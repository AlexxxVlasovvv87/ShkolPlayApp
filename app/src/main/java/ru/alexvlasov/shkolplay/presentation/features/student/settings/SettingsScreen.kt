package ru.alexvlasov.shkolplay.presentation.features.student.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.alexvlasov.shkolplay.domain.model.Difficulty
import ru.alexvlasov.shkolplay.domain.model.Theme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(16.dp, 32.dp, 16.dp, 8.dp),
            text = "Сложность",
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 16.dp)
        ) {
            Difficulty.values().forEach {
                val enabled = it != viewModel.difficulty.collectAsState().value
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (!enabled)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .clickable(enabled = enabled) {
                            viewModel.setDiff(it)
                        }
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp).height(40.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            it.normalName,
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Text(
            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp),
            text = "Тема",
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 16.dp)
        ) {
            Theme.values().forEach {
                val enabled = it != viewModel.theme.collectAsState().value
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor =
                        if (!enabled)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .clickable(enabled = enabled) {
                            viewModel.setTheme(it)
                        }
                ) {
                    Text(
                        it.normalName,
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
    LaunchedEffect(true) {
    }
}


