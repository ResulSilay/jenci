package com.x.presentation.ui.composable

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.x.presentation.ui.theme.Theme

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Checkbox(
        checked = checked,
        colors = CheckboxDefaults.colors(
            checkedColor = Theme.colors.secondary,
            uncheckedColor = Theme.colors.onSurface,
            checkmarkColor = Theme.colors.surface,
            disabledColor = Theme.colors.onSurface
        ),
        onCheckedChange = onCheckedChange
    )
}

@Composable
fun CustomRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
) {
    RadioButton(
        selected = selected,
        colors = RadioButtonDefaults.colors(
            selectedColor = Theme.colors.secondary,
            unselectedColor = Theme.colors.onSurface,
            disabledColor = Theme.colors.onSurface
        ),
        onClick = onClick
    )
}