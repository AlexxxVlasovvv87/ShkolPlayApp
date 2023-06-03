package ru.alexvlasov.shkolplay.presentation.features.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import ru.alexvlasov.shkolplay.presentation.navigation.NavigationItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun SignInScreen(navController: NavController, viewModel: SignInViewModel = hiltViewModel()) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(32.dp, 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(Modifier.fillMaxWidth().fillMaxHeight(0.3f)) {
                Image(
                    painter = painterResource(R.mipmap.back_text),
                    contentDescription = stringResource(R.string.logo),
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Card {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    OutlinedTextField(
                        value = viewModel.email,
                        onValueChange = { viewModel.setNewEmail(it) },
                        label = { Text(stringResource(R.string.email)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        isError = viewModel.isError(),
                    )
                    PasswordTextField(
                        password = viewModel.password,
                        onPasswordChange = { viewModel.setNewPassword(it) },
                        modifier = Modifier.fillMaxWidth(),
                        passwordVisible = viewModel.passwordVisible,
                        onPasswordVisibleChange = { viewModel.passwordVisible = it },
                        isInvalid = viewModel.isError()
                    )
                    when (val state = viewModel.state.value) {
                        is SignInState.Error -> Text(
                            text = state.error,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        else -> {}
                    }
                    LoadingButton(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        onClick = { viewModel.signIn() },
                        loading = !viewModel.isUIEnabled(),
                        text = stringResource(R.string.signin),
                    )
                }
            }


            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 16.dp, 0.dp, 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.has_no_account))
                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.signup),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable { navController.navigate(NavigationItem.Register.route) }
                )
            }
        }
    }
}
