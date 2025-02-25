package uz.gita.otabek.notejon.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uz.gita.otabek.notejon.R

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    hint: String = "",
    singleLine: Boolean,
    textAlign: TextAlign = TextAlign.Start,
    errorText: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors()
) {
    BasicTextField(value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        textStyle = textStyle.copy(textAlign = textAlign),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(Color.DarkGray),
        decorationBox = { innerTextField ->
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colors.backgroundColor(enabled = true).value, shape = RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp)
                        .defaultMinSize(minHeight = 50.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    leadingIcon?.invoke()
                    Box(modifier = Modifier.fillMaxWidth()) {
                        if (value.isEmpty()) {
                            Text(
                                text = hint, modifier = Modifier.fillMaxWidth(), style = textStyle.copy(color = Color.Gray)
                            )
                        }
                        innerTextField()
                    }
                    trailingIcon?.invoke()
                }

                errorText?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorText, style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.montserrat_regular)), fontWeight = FontWeight.Medium, color = Color.Red
                        ), modifier = Modifier.padding(start = 16.dp), maxLines = 1
                    )
                }
            }
        })
}