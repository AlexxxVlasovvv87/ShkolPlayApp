package ru.alexvlasov.shkolplay.presentation.features.student.math

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class, ExperimentalComposeUiApi::class)
@Composable
fun MathScreen(
    navController: NavController,
    viewModel: MathViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.padding(16.dp)) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Очки: ${viewModel.points}",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Всего: ${viewModel.all}",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Подряд: ${viewModel.streak}",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Лучший: ${viewModel.bestStreak}",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                )
            }
        }
        Spacer(Modifier.weight(0.1f))
        Text(
            text = viewModel.equation,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(32.dp)

        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth().fillMaxHeight(0.5f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            itemsIndexed(viewModel.answers) { i, item ->
                Button(
                    onClick = {
                        viewModel.choose(i)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = item.toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
        Spacer(Modifier.weight(0.1f))
    }
    LaunchedEffect(true) {
        viewModel.new()
    }
}


