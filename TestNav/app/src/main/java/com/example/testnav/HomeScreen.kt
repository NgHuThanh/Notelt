package com.example.testnav
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController:NavHostController,
               modifier: Modifier = Modifier,
               names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 1.dp).padding(2.dp)) {
                items(items = names) { name ->
                    DetailTopic(
                        name = name,
                        navController
                    )
                }
            }
        }
@Composable
private fun DetailTopic(name: String, navController: NavHostController,) {
    var shouldShowPractive by remember { mutableStateOf(false) }
    var id=""
    Surface(
    ) {
        Row(
            modifier = Modifier
                .padding(18.dp)
                //.background(Color.White, shape = RoundedCornerShape(10.dp))
                .border(
                    width = 2.dp,               // Độ dày của đường viền
                    color = Color.Cyan,// Màu của đường viền
                    shape = RoundedCornerShape(10.dp)      // Hình dạng của đường viền (ở đây là hình chữ nhật)
                )
                .padding(10.dp)                  // Tăng khoảng cách giữa đường viền và nội dung
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "TOPIC ")
                Text(text = name)
                Row {
                    ElevatedButton(
                        onClick = {},
                        modifier = Modifier
                            .border(2.dp, Color.Cyan, shape = MaterialTheme.shapes.small),
                    ) {
                        Text(
                            "Practice",
                            color = Color.Cyan // Set text color
                        )
                    }
                    ElevatedButton(
                        onClick = { navController.navigate("Detail")},
                        modifier = Modifier
                            .border(2.dp, Color.Cyan, shape = MaterialTheme.shapes.small)
                    ) {
                        Text(
                            "Review",
                            color = Color.Cyan // Set text color
                        )
                    }
                }
            }
        }
    }
}
