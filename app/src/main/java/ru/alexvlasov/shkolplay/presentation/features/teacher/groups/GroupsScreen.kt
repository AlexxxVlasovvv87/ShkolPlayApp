package ru.alexvlasov.shkolplay.presentation.features.teacher.groups

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.alexvlasov.shkolplay.R


@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun GroupsScreen(
    navController: NavController,
    viewModel: GroupsViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    if (viewModel.isDialogOpened) {
        CreateDialog(viewModel, snackbarHostState)
    }
    Scaffold(
        topBar = { GroupsAppBar(viewModel) },
        snackbarHost = { SnackbarHost(snackbarHostState, snackbar = { Snackbar(snackbarData = it) }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.isDialogOpened = true
            }) { Text("+") }
        }
    ) { p ->
        val groups = viewModel.modelList.collectAsState(listOf()).value
        Column(
            modifier = Modifier
                .padding(p)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            if (viewModel.filteredIsEmpty()) {
                Text(
                    text = stringResource(id = R.string.nothing_found),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center

                )
            }
            LazyColumn(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(8.dp)
            ) {
                items(groups.sortedBy { it.groupName }) { g ->
                    AnimatedVisibility(viewModel.isFiltered(g)) { //Анимация для скрытия
                        GroupView(navController, g, viewModel)
                    }
                }
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.fetchGroups()
    }
}


@Composable
fun GroupsAppBar(viewModel: GroupsViewModel) {
    SmallTopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp, 8.dp)
        ) {
            if (viewModel.isSearchOpened)
                OutlinedTextField(
                    value = viewModel.searchText,
                    onValueChange = { viewModel.searchText = it },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    textStyle = MaterialTheme.typography.bodyMedium,
                )
            else {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.nav_groups),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(modifier = Modifier.size(28.dp),
                onClick = {
                    viewModel.isSearchOpened = !viewModel.isSearchOpened
                    viewModel.searchText = ""
                }) {
                if (viewModel.isSearchOpened)
                    Icon(
                        Icons.Rounded.Close,
                        "Закрыть",
                        tint = MaterialTheme.colorScheme.primary
                    )
                else
                    Icon(
                        Icons.Rounded.Search,
                        "Найти",
                        tint = MaterialTheme.colorScheme.primary
                    )
            }
        }
    })
}