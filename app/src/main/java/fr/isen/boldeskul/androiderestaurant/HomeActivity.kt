package fr.isen.boldeskul.androiderestaurant

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fr.isen.boldeskul.androiderestaurant.ui.theme.ralewayFontFamily
import java.util.Locale



enum class DishType{
    STARTER, MAIN, DESSERT
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    Scaffold(
                        topBar = { MexicanRestaurantTopApp() },
                    ) {
                        paddingValues ->
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(PaddingValues())) {
                            SetupView(this@HomeActivity)


                            Row(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = paddingValues.calculateTopPadding() + 16.dp,
                                             end = 16.dp,
                                             start = 16.dp),
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
                                        fontFamily = ralewayFontFamily,
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

    override fun onDestroy() {
        Log.d("lifecycle", "home ondestroy")
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "home onresume")
    }

    override fun onPause() {
        Log.d("lifecycle", "home onpause")
        super.onPause()
    }


//
//    How to Implement: In the dishPressed method, create an Intent to start the new activity.
//    Add the category name as an extra to the intent using intent.putExtra(name, value), and
//    then call startActivity(intent) to launch the new activity.


    override fun dishPressed(dishType: DishType){
        val intent = Intent(this, CategoryActivity::class.java).apply{
            putExtra(CategoryActivity.CATEGORYNAME, dishType.name)
        }
        startActivity(intent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MexicanRestaurantTopApp(){
    SmallTopAppBar(
        title = {

            Text(
                text = "Mexican Restaurant",
                color = Color.White,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = ralewayFontFamily,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFF039BE5)
        )
    )
}

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
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = ralewayFontFamily
            )
        )
    }
    Divider(
        color = Color(0xFF039BE5),
        thickness = 1.dp,
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



