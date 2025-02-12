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
import com.hansel.rickandmorty.R
import com.hansel.rickandmorty.domain.model.CharacterStatus
import com.hansel.rickandmorty.ui.theme.AppTheme
import com.hansel.rickandmorty.ui.theme.greenStatusBackground
import com.hansel.rickandmorty.util.CustomPreview

@Composable
fun StatusTag(
    modifier: Modifier = Modifier,
    status: CharacterStatus
) {
    val backgroundColor = when (status) {
        CharacterStatus.Alive -> greenStatusBackground
        CharacterStatus.Dead -> MaterialTheme.colorScheme.errorContainer
        CharacterStatus.Unknown -> MaterialTheme.colorScheme.inverseSurface
    }
    val textColor = when (status) {
        CharacterStatus.Alive -> Color.Green
        CharacterStatus.Dead -> MaterialTheme.colorScheme.onErrorContainer
        CharacterStatus.Unknown -> MaterialTheme.colorScheme.inverseOnSurface
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100f))
            .background(backgroundColor)
            .padding(horizontal = dimensionResource(R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = status.status,
            color = textColor
        )
    }
}

@CustomPreview
@Composable
private fun StatusTagPreview() {
    AppTheme {
        StatusTag(status = CharacterStatus.Alive)
    }
}