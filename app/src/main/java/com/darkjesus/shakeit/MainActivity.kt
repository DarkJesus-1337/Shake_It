package com.darkjesus.shakeit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.darkjesus.shakeit.ui.navigation.ShakeItApp
import com.darkjesus.shakeit.ui.theme.ShakeItTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShakeItTheme {
                ShakeItApp()
            }
        }
    }
}

