package ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.snowadv.kinopoiskfeaturedmovies.R

@Composable
fun ErrorMessageBox(
    modifier: Modifier = Modifier,
    errorMessage: String?,
    onRefresh: (() -> Unit)?,
    defaultStringResId: Int = R.string.not_found
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (errorMessage != null) {
            Icon(
                modifier = Modifier.size(110.dp),
                painter = painterResource(id = R.drawable.no_connection),
                contentDescription = stringResource(R.string.no_connection),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                modifier = Modifier.padding(top = 5.dp, bottom = 20.dp),
                text = errorMessage,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = Color.White,
                contentColor = Color.White
            ),
            onClick = onRefresh ?: {},
            enabled = onRefresh != null
        ) {
            Text(stringResource(if (errorMessage?.isNotEmpty() == true) R.string.retry else defaultStringResId))
        }
    }
}