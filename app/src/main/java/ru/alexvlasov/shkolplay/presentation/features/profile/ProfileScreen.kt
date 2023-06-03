package ru.alexvlasov.shkolplay.presentation.features.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.alexvlasov.shkolplay.R
import ru.alexvlasov.shkolplay.data.datastore.Themes
import ru.alexvlasov.shkolplay.presentation.base.LoadingButton
import ru.alexvlasov.shkolplay.presentation.base.PasswordTextField
import ru.alexvlasov.shkolplay.presentation.features.auth.AccountViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(), //Hilt реализует viewmodel
    accountViewModel: AccountViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                snackbar = { Snackbar(snackbarData = it) })
        },
        topBar = { ProfileTopBar(accountViewModel) },
    ) { p ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues = p)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(Modifier.fillMaxWidth(0.8f)) {
                OutlinedTextField(
                    value = viewModel.nameText,
                    onValueChange = {viewModel.nameText = it},
                    enabled = viewModel.changing,
                    label = { Text(stringResource(R.string.username)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                )
                OutlinedTextField(
                    value = viewModel.surnameText,
                    onValueChange = {viewModel.surnameText = it},
                    enabled = viewModel.changing,
                    label = { Text(stringResource(R.string.surname)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                )
                OutlinedTextField(
                    value = viewModel.emailText,
                    onValueChange = {viewModel.emailText = it},
                    enabled = false,
                    label = { Text(stringResource(R.string.email)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                )
                OutlinedTextField(
                    value = viewModel.teacherText,
                    onValueChange = {},
                    enabled = false,
                    label = { Text(stringResource(R.string.role)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                )
                AnimatedVisibility(visible = viewModel.changing) {
                    Column {
                        LoadingButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            onClick = {
                                viewModel.update {
                                    focusManager.clearFocus()
                                    snackbarHostState.showSnackbar(viewModel.snackBarText())
                                }
                            },
                            loading = !viewModel.isUIEnabled(),
                            text = stringResource(R.string.save),
                        )
                    }
                }
                Column {
                    LoadingButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        onClick = {
                            if (viewModel.changing)
                                viewModel.reset()
                            viewModel.changing = !viewModel.changing
                        },
                        loading = !viewModel.isUIEnabled(),
                        text = if (viewModel.changing)
                            stringResource(R.string.cancel)
                        else
                            stringResource(R.string.change),
                    )
                }
                AnimatedVisibility(visible = viewModel.isPasswordChanging) {
                    Column(Modifier.fillMaxWidth()) {
                        PasswordTextField(
                            password = viewModel.passwordText,
                            onPasswordChange = { viewModel.setPassword(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            label = { Text(stringResource(R.string.old_password)) },
                            passwordVisible = viewModel.passwordVisible,
                            onPasswordVisibleChange = { viewModel.passwordVisible = it },
                            isInvalid = viewModel.isError()
                        )
                        PasswordTextField(
                            password = viewModel.newPasswordText,
                            onPasswordChange = { viewModel.setNewPassword(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            label = { Text(stringResource(R.string.new_password)) },
                            passwordVisible = viewModel.newPasswordVisible,
                            onPasswordVisibleChange = { viewModel.newPasswordVisible = it },
                            isInvalid = viewModel.isError()
                        )
                        LoadingButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            onClick = {
                                viewModel.updatePassword {
                                    focusManager.clearFocus()
                                    snackbarHostState.showSnackbar(viewModel.snackBarText())
                                }
                            },
                            loading = !viewModel.isUIEnabled(),
                            text = stringResource(R.string.save),
                        )
                    }
                }
                LoadingButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.isPasswordChanging = !viewModel.isPasswordChanging },
                    loading = !viewModel.isUIEnabled(),
                    text = stringResource(
                        if (viewModel.isPasswordChanging) R.string.cancel
                        else R.string.update_password
                    )
                )
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().height(70.dp).padding(top = 16.dp)) {
                    Themes.values().forEach {
                        SuggestionChip(
                            onClick = { accountViewModel.setTheme(it) },
                            label = { Text(it.normalName, Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                            enabled = it.name != accountViewModel.theme.collectAsState().value,
                            modifier = Modifier.weight(0.3f).fillMaxHeight().padding(2.dp)
                        )
                    }
                }

            }
        }
    }
    LaunchedEffect(true) {
        //Корутина вне потока отрисовки проверяет тему и подтягивает данные пользователя
        viewModel.fetch()
        accountViewModel.fetchTheme()
    }
}


@Composable
fun ProfileTopBar(accountViewModel: AccountViewModel) {
    SmallTopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp, 8.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.nav_profile),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            IconButton(modifier = Modifier
                .size(28.dp)
                .padding(start = 8.dp),
                onClick = {
                    accountViewModel.logOut()
                }) {
                Icon(
                    Icons.Rounded.Logout,
                    stringResource(R.string.logout),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    })
}