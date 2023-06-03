package ru.alexvlasov.shkolplay.presentation.features.teacher.groups

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.alexvlasov.shkolplay.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDialog(
    viewModel: GroupsViewModel,
    snackbarHostState: SnackbarHostState
) {
    Dialog(
        onDismissRequest = { viewModel.isDialogOpened = false },
        content = {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                ElevatedCard {
                    Column(Modifier.padding(24.dp, 16.dp)) {
                        Text(
                            text = "Создание группы",
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Normal,
                        )
                        OutlinedTextField(
                            value = viewModel.name,
                            onValueChange = { viewModel.name = it },
                            singleLine = true,
                            label = { Text("Название группы") },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodyMedium,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                viewModel.add(
                                    onError = {
                                        viewModel.isDialogOpened = false
                                        snackbarHostState.showSnackbar(it)
                                    },
                                    onSuccess = {
                                        viewModel.isDialogOpened = false
                                        snackbarHostState.showSnackbar("Группа создается")
                                    }
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                        ) { Text(stringResource(R.string.save)) }
                        OutlinedButton(
                            onClick = {
                                viewModel.isDialogOpened = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                        ) { Text(stringResource(R.string.cancel)) }

                    }
                }
            }
        }
    )
}