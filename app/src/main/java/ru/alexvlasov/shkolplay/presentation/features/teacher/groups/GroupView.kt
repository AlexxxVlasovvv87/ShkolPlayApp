package ru.alexvlasov.shkolplay.presentation.features.teacher.groups

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
import ru.alexvlasov.shkolplay.domain.model.Group

//Элемент списка группы

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupView(
    navController: NavController,
    t: Group,
    viewModel: GroupsViewModel
) {
    var isMenuOpened by remember { mutableStateOf(false) }
    ElevatedCard(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable {
                navController.navigate("students/${t.groupId}")
            }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    t.groupName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    t.inviteCode,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Light,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Ученики: ${t.students.size}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Light,
                )
            }
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
                            viewModel.delete(t.groupId!!)
                            isMenuOpened = false
                        },
                        text = { Text(stringResource(R.string.delete), Modifier.height(40.dp)) }
                    )
                }
            }
        }
    }
}
