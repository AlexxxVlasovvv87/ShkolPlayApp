package ru.alexvlasov.shkolplay.presentation.features.teacher.rate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.alexvlasov.shkolplay.R
import ru.alexvlasov.shkolplay.domain.model.Achievement
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatesView(
    navController: NavController,
    a: Achievement,
    groupId: UUID?,
    viewModel: RatesViewModel
) {
    var isMenuOpened by remember { mutableStateOf(false) }
    ElevatedCard(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    a.user!!.fullName(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "${a.memoryLevel} Уровень \"Память\"",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Light,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "${a.memoryPoints} очков максимально",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Light,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "${a.mathStreak} задач получено подряд",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Light,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "${a.mathSuccessStreak} задач решено подряд",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Light,
                )
            }
            if (groupId != null)
            Box {
                Icon(
                    Icons.Rounded.MoreVert,
                    stringResource(R.string.more),
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.clickable { isMenuOpened = true }
                )
                DropdownMenu(
                    expanded = isMenuOpened,
                    onDismissRequest = { isMenuOpened = false },
                    modifier = Modifier.height(40.dp)
                ) {
                    DropdownMenuItem(
                        modifier = Modifier.height(40.dp),
                        onClick = {
                            viewModel.deleteFromGroup(groupId, a.user!!.uid!!)
                            isMenuOpened = false
                        },
                        text = { Text(stringResource(R.string.delete), Modifier.height(40.dp)) }
                    )
                }
            }
        }
    }
}
