package de.larshauke.swapi_android.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun AlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    @StringRes dismissRes: Int,
    @StringRes titleRes: Int,
    @StringRes textRes: Int? = null,
    textPlain: String? = null,
) {
    AlertDialog(
        modifier = modifier.fillMaxWidth(),
        onDismissRequest = onDismissRequest,
        confirmButton = {
            DialogTextButton(
                onClick = onDismissRequest,
                res = dismissRes
            )
        },
        title = { Text(text = stringResource(titleRes)) },
        text = {
            Text(
                text = when {
                    textRes != null -> stringResource(id = textRes)
                    textPlain != null -> textPlain
                    else -> ""
                }
            )
        },
        shape = RoundedCornerShape(10.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 1.dp
    )
}


@Composable
private fun DialogTextButton(
    onClick: () -> Unit,
    @StringRes res: Int
)  {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(
            modifier = Modifier.widthIn(100.dp),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface
            ),
        ) {
            Text(
                text = stringResource(id = res)
            )
        }
    }
}