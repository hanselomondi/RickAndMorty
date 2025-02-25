package com.hansel.rickandmorty.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.hansel.rickandmorty.R

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    message: String,
    textColor: Color = MaterialTheme.colorScheme.error
) {
    Text(
        text = message,
        color = textColor,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small))
    )
}