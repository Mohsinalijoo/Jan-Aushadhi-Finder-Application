package com.example.janaushadhifinder.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.janaushadhifinder.ui.theme.AppColors

fun Modifier.medicalLogoBackground(): Modifier {
    return this.clip(RoundedCornerShape(12.dp)).background(AppColors.PrimaryBlue)
}
