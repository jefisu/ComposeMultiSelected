package com.jefisu.composemultiselected

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jefisu.composemultiselected.data.dto.ListItem
import com.jefisu.composemultiselected.ui.theme.ComposeMultiSelectedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMultiSelectedTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MultiSelectedList()
                }
            }
        }
    }
}

@Composable
fun MultiSelectedList() {
    var items by remember {
        mutableStateOf(
            (1..20).map {
                ListItem(
                    text = "Item $it",
                    isClicked = false
                )
            }
        )
    }
    LazyColumn {
        itemsIndexed(items) { index, item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 8.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
            ) {
                Canvas(
                    modifier = Modifier
                        .matchParentSize()
                ) {
                    val clipPath = Path().apply {
                        lineTo(x = size.width - 15.dp.toPx(), y = 0f)
                        lineTo(x = size.width, y = 15.dp.toPx())
                        lineTo(x = size.width, y = size.height)
                        lineTo(x = 0f, y = size.height)
                        close()
                    }
                    clipPath(clipPath) {
                        drawRoundRect(
                            color = if (item.isClicked) Color.Green else Color.LightGray,
                            size = size,
                            cornerRadius = CornerRadius(15.dp.toPx())
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 8.dp,
                            horizontal = 16.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.text,
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.h5
                    )
                    Checkbox(
                        checked = item.isClicked,
                        onCheckedChange = {
                            items = items.mapIndexed { i, item ->
                                if (i == index) {
                                    item.copy(isClicked = it)
                                } else item
                            }
                        }
                    )
                }
            }
        }
    }
}