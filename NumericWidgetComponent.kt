package com.pratik.loginmodule.cleanArch.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumericUpDownComponent(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    minValue: Int = Int.MIN_VALUE,
    maxValue: Int = Int.MAX_VALUE,
    step: Int = 1
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .wrapContentWidth()
            .padding(8.dp)
    ) {
        IconButton(
            onClick = {
                if (value + step <= maxValue) {
                    onValueChange(value + step)
                }
            },
            enabled = value < maxValue,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "Increase")
        }
        BasicTextField(
            value = value.toString(),
            onValueChange = { newValue ->
                newValue.toIntOrNull()?.let {
                    if (it in minValue..maxValue) {
                        onValueChange(it)
                    }
                }
            },
            modifier = Modifier
                .width(60.dp)
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally),
            singleLine = true,
            textStyle = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Center)
        )
        IconButton(
            onClick = {
                if (value - step >= minValue) {
                    onValueChange(value - step)
                }
            },
            enabled = value > minValue,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Decrease")
        }
    }
}
