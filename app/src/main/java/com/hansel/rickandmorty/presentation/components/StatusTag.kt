package com.hansel.rickandmorty.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.hansel.rickandmorty.R
import com.hansel.rickandmorty.domain.model.Status
import com.hansel.rickandmorty.ui.theme.AppTheme
import com.hansel.rickandmorty.ui.theme.greenStatusBackground
import com.hansel.rickandmorty.util.CustomPreview

@Composable
fun StatusTag(
    modifier: Modifier = Modifier,
    status: Status
) {
    val backgroundColor = when (status) {
        Status.ALIVE -> greenStatusBackground
        Status.DEAD -> MaterialTheme.colorScheme.errorContainer
        Status.UNKNOWN -> MaterialTheme.colorScheme.surfaceDim
    }
    val textColor = when (status) {
        Status.ALIVE -> Color.Green
        Status.DEAD -> MaterialTheme.colorScheme.onErrorContainer
        Status.UNKNOWN -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100f))
            .background(backgroundColor)
            .padding(horizontal = dimensionResource(R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(
                when (status) {
                    Status.DEAD -> R.string.dead_status
                    Status.ALIVE -> R.string.alive_status
                    Status.UNKNOWN -> R.string.unknown_status
                }
            ),
            color = textColor
        )
    }
}

@CustomPreview
@Composable
private fun StatusTagPreview() {
    AppTheme {
        StatusTag(status = Status.ALIVE)
    }
}