package fr.isen.boldeskul.androiderestaurant

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.isen.boldeskul.androiderestaurant.ui.theme.AndroidERestaurantTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.util.Locale
import androidx.compose.material3.Divider
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import fr.isen.boldeskul.androiderestaurant.basket.Basket
import fr.isen.boldeskul.androiderestaurant.basket.BasketActivity



enum class DishType {
    STARTER, MAIN, DESSERT;

    @Composable
    fun title(): String {
        return when(this) {
            STARTER -> stringResource(id = R.string.menu_starters)
            MAIN -> stringResource(id = R.string.menu_main)
            DESSERT -> stringResource(id = R.string.menu_dessert)
        }
    }

    fun key(): String {
        return when(this) {
            STARTER -> "Entrées"
            MAIN -> "Plats"
            DESSERT -> "Desserts"
        }
    }

}

interface MenuInterface{
    fun dishPressed(dishType: DishType)
}



class HomeActivity : ComponentActivity(), MenuInterface {
        @OptIn(ExperimentalMaterial3Api::class)
        @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                AndroidERestaurantTheme {
                    val basketItemCount =
                        remember { mutableStateOf(Basket.itemCount(this@HomeActivity)) }

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Scaffold(
                            topBar = {
                                MexicanRestaurantTopApp(
                                    basketItemCount = basketItemCount.value,
                                    onBasketClick = {
                                        val intent =
                                            Intent(this@HomeActivity, BasketActivity::class.java)
                                        startActivity(intent)
                                    })
                            },
                        )
                        { paddingValues ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(PaddingValues())
                            ) {
                                SetupView(this@HomeActivity)
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(
                                            top = paddingValues.calculateTopPadding() + 16.dp,
                                            end = 16.dp,
                                            start = 16.dp
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.mexicanguy),
                                        contentDescription = "Decorative Image",
                                        modifier = Modifier.size(150.dp)
                                    )
                                    Text(
                                        text = "¡Bienvenido a nuestro restaurante!",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            color = Color(0xFFC62828),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 25.sp
                                        ),
                                        modifier = Modifier
                                            .padding(start = 20.dp)


                                    )
                                }
                            }
                        }

                    }
                }
            }

        }

        override fun dishPressed(dishType: DishType) {
            val intent = Intent(this, CategoryActivity::class.java).apply {
                putExtra(CategoryActivity.CATEGORYNAME, dishType)
            }
            startActivity(intent)
        }


    }


@Composable
fun CustomBadgeIcon(basketItemCount: Int, onBasketClick: () -> Unit) {
    IconButton(onClick = onBasketClick) {
        if (basketItemCount > 0) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.shoppingcart),
                    contentDescription = "Basket",
                    tint = Color.White
                )
                CircleBadge(count = basketItemCount)
            }
        } else {
            Icon(
                painter = painterResource(id = R.drawable.shoppingcart),
                contentDescription = "Basket",
                tint = Color.White
            )
        }
    }
}

@Composable
fun CircleBadge(count: Int) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(18.dp)
            .clip(CircleShape)
            .background(Color.Red)) {
        Text(
            text = count.toString(),
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MexicanRestaurantTopApp(basketItemCount: Int, onBasketClick: () -> Unit) {
    SmallTopAppBar(
        title = {
            Text(
                text = "Mexican Restaurant",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFFC62828),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
            )
        },
        actions = {
            CustomBadgeIcon(basketItemCount = basketItemCount, onBasketClick = onBasketClick)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFF039BE5)
        )
    )
}


//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MexicanRestaurantTopApp(){
//    SmallTopAppBar(
//        title = {
//
//            Text(
//                text = "Mexican Restaurant",
//                color = Color.White,
//                style = TextStyle(
//                    fontSize = 24.sp,
//                    //fontFamily = ralewayFontFamily,
//                    fontWeight = FontWeight.Bold,
//                    letterSpacing = 0.5.sp
//                )
//            )
//        },
//        colors = TopAppBarDefaults.smallTopAppBarColors(
//            containerColor = Color(0xFF039BE5)
//        )
//    )
//}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidERestaurantTheme {
        SetupView(HomeActivity())
    }
}


@Composable
fun TextStyledButton(text: String, onClick: () -> Unit){
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text.toUpperCase(Locale.ROOT),
            color = Color(0xFFFB8C00),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFFC62828),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        )
    }
    Divider(
        color = Color(0xFF039BE5),
        thickness = 3.dp,
        modifier = Modifier.padding(horizontal = 50.dp)

   )
}

@Composable
fun SetupView(menu: MenuInterface) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        )
        {
//        Image(painterResource(R.drawable.ic_launcher_foreground), null)
            TextStyledButton(text = stringResource(R.string.menu_starters)) {
                menu.dishPressed(DishType.STARTER)
            }

            TextStyledButton(text = stringResource(R.string.menu_main)) {
                menu.dishPressed(DishType.MAIN)
            }
            TextStyledButton(text = stringResource(R.string.menu_dessert)) {
                menu.dishPressed(DishType.DESSERT)
            }
        }
    }
}