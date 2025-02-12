package com.hansel.rickandmorty.util

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "LightThemePreview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "DarkThemePreview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class CustomPreview