package fr.isen.boldeskul.androiderestaurant

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onDestroy() {
        Log.d("lifecycle", "menu ondestroy")
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "menu onresume")
    }

    override fun onPause() {
        Log.d("lifecycle", "menu onpause")
        super.onPause()
    }

    companion object {
         val CATEGORY_EXTRA_KEY = "CATEGORY_EXTRA_KEY"
    }
}