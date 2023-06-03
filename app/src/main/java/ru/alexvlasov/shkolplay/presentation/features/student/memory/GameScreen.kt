package ru.alexvlasov.shkolplay.presentation.features.student.memory

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.alexvlasov.shkolplay.domain.model.CardData
import ru.alexvlasov.shkolplay.domain.model.CardColor
import ru.alexvlasov.shkolplay.presentation.navigation.NavigationItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class, ExperimentalComposeUiApi::class)
@Composable
fun GameScreen(
    navController: NavController,
    viewModel: MemoryGameViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Уровень ${viewModel.gameLevel}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        if (viewModel.gameFinished) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = "Ты молодец!",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Клики: ${viewModel.flips}",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Очки: ${viewModel.points}",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = {
                        navController.navigate(NavigationItem.MemoryGame.route) {
                            popUpTo(NavigationItem.Games.route)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Дальше",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
                Button(
                    onClick = {
                        navController.navigateUp()
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Меню",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }
        } else {
            Row(Modifier.padding(16.dp)) {
                Text(
                    text = "Клики: ${viewModel.flips}",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Очки: ${viewModel.points}",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                )
            }
            val id = viewModel.idOfFaceUpCard
            val cards = viewModel.cards
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                userScrollEnabled = false
            ) {

                items(cards) { item ->
                    MemoryCard(item, viewModel)
                }
            }
        }
    }
    LaunchedEffect(true) {
        viewModel.showCards()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryCard(item: CardData, viewModel: MemoryGameViewModel) {

    Column(Modifier.defaultMinSize(minHeight = 104.dp)) {
        AnimatedVisibility(visible = !item.isMatched) {
            Card(
                Modifier
                    .padding(2.dp)
                    .clickable(enabled = viewModel.cardsEnabled) {
                        viewModel.chooseCard(item.identifier)
                    }
                    .fillMaxSize()
                    .defaultMinSize(minHeight = 100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when {
                        item.isFaceUp -> MaterialTheme.colorScheme.surfaceVariant
                        item.color == CardColor.Primary -> MaterialTheme.colorScheme.primaryContainer
                        item.color == CardColor.Secondary -> MaterialTheme.colorScheme.inversePrimary
                        item.color == CardColor.Tertiary -> MaterialTheme.colorScheme.tertiaryContainer
                        else -> MaterialTheme.colorScheme.background
                    }
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .defaultMinSize(minHeight = 100.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        text = if (item.isFaceUp) item.emoji else "",
                        fontSize = 40.sp,
                    )

                }
            }
        }
    }
}