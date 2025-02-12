package com.hansel.rickandmorty.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hansel.rickandmorty.R
import com.hansel.rickandmorty.domain.model.Character
import com.hansel.rickandmorty.ui.theme.AppTheme
import com.hansel.rickandmorty.util.CustomPreview
import com.hansel.rickandmorty.util.PREVIEW_CHARACTER

@Composable
fun CharacterCard(
    modifier: Modifier = Modifier,
    character: Character
) {
    ElevatedCard(
        modifier = modifier
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .build(),
                contentDescription = "Image of ${character.name}",
                modifier = Modifier
                    .size(180.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(
                modifier = Modifier
                    .width(dimensionResource(R.dimen.padding_small))
            )
            Column(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_extra_small))
            ) {
                Text(
                    text = character.name,
                    fontSize = 24.sp
                )
                Text(
                    text = character.species
                )
                StatusTag(status = character.status)
            }
        }
    }
}

@CustomPreview
@Composable
private fun CharacterCardPreview() {
    AppTheme {
        CharacterCard(
            character = PREVIEW_CHARACTER
        )
    }
}