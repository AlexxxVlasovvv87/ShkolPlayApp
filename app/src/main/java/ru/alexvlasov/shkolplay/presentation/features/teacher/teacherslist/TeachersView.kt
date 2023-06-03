package ru.alexvlasov.shkolplay.presentation.features.teacher.teacherslist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.alexvlasov.shkolplay.domain.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeachersView(
    navController: NavController,
    u: User,
) {
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
                    u.fullName(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Почта: ${u.email}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Light,
                )
            }
        }
    }
}
