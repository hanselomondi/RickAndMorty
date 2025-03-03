package com.hansel.rickandmorty.util

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "small font",
    group = "fonts",
    fontScale = 0.5f
)
@Preview(
    name = "large font",
    group = "fonts",
    fontScale = 1.5f
)
annotation class FontScalePreviews

@Preview(
    name = "phone",
    group = "devices",
    device = "spec:width=360dp,height=640dp,dpi=480"
)
@Preview(
    name = "phone",
    group = "devices",
    device = "spec:width=673dp,height=841dp,dpi=480"
)
@Preview(
    name = "phone",
    group = "devices",
    device = "spec:width=1280dp,height=800dp,dpi=480"
)
annotation class DevicePreviews

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
@FontScalePreviews
@DevicePreviews
annotation class CustomPreview