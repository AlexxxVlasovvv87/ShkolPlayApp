package ru.alexvlasov.shkolplay.presentation.features.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.alexvlasov.shkolplay.R
import ru.alexvlasov.shkolplay.presentation.base.LoadingButton
import ru.alexvlasov.shkolplay.presentation.base.PasswordTextField

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel = hiltViewModel()) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(32.dp, 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f)
                    .padding(bottom = 8.dp)) {
                Image(
                    painter = painterResource(R.mipmap.avatar2),
                    contentDescription = stringResource(R.string.logo),
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Card {
                Column(
                    Modifier
                        .fillMaxWidth().padding(16.dp, 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    OutlinedTextField(
                        value = viewModel.name,
                        onValueChange = { viewModel.setNewName(it) },
                        label = { Text(stringResource(R.string.username)) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        isError = viewModel.isError()
                    )
                    OutlinedTextField(
                        value = viewModel.surname,
                        onValueChange = { viewModel.setNewSurname(it) },
                        label = { Text(stringResource(R.string.surname)) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        isError = viewModel.isError()
                    )
                    OutlinedTextField(
                        value = viewModel.email,
                        onValueChange = { viewModel.setNewEmail(it) },
                        label = { Text(stringResource(R.string.email)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        isError = viewModel.isError()
                    )
                    PasswordTextField(
                        password = viewModel.password,
                        onPasswordChange = { viewModel.setNewPassword(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        passwordVisible = viewModel.passwordVisible,
                        onPasswordVisibleChange = { viewModel.passwordVisible = it },
                        isInvalid = viewModel.isError()
                    )
                    PasswordTextField(
                        password = viewModel.passwordAgain,
                        onPasswordChange = { viewModel.setAgainPassword(it) },
                        label = { Text(stringResource(R.string.passwordAgain)) },
                        modifier = Modifier.fillMaxWidth(),
                        passwordVisible = viewModel.passwordAgainVisible,
                        onPasswordVisibleChange = { viewModel.passwordAgainVisible = it },
                        isInvalid = viewModel.isError() || !viewModel.passwordsAreSimilar()
                    )

                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().height(70.dp).padding(top = 16.dp)) {
                        SuggestionChip(
                            onClick = { viewModel.setIsTeacher(false) },
                            label = { Text("Я ученик", Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                            modifier = Modifier.weight(0.4f).fillMaxHeight().padding(2.dp),
                            icon = { Icon(if (!viewModel.isTeacher) Icons.Rounded.Done else Icons.Rounded.Close, contentDescription = "Done")}
                        )
                        SuggestionChip(
                            onClick = { viewModel.setIsTeacher(true) },
                            label = { Text("Я учитель", Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                            modifier = Modifier.weight(0.4f).fillMaxHeight().padding(2.dp),
                            icon = { Icon(if (viewModel.isTeacher) Icons.Rounded.Done else Icons.Rounded.Close, contentDescription = "Done")}
                        )
                    }
                    when (val state = viewModel.state.value) {
                        is SignUpState.Error -> Text(
                            text = state.error,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        else -> {}
                    }
                    LoadingButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        onClick = { viewModel.signUp() },
                        enabled = viewModel.isUIEnabled() && viewModel.passwordsAreSimilar(),
                        loading = !viewModel.isUIEnabled(),
                        text = stringResource(R.string.signup),
                    )

                }
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 16.dp, 0.dp, 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.has_account))
                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.signin),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
                )
            }
        }
    }
}
