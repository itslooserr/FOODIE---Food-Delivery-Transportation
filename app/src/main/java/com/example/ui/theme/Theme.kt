package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Clean, bright, high-contrast light color scheme according to user brand specifications
private val FoodieColorScheme = lightColorScheme(
  primary = FoodiePrimary,
  onPrimary = Color.White,
  primaryContainer = FoodiePrimaryLight,
  onPrimaryContainer = FoodiePrimary,
  secondary = FoodieTextPrimary,
  onSecondary = Color.White,
  secondaryContainer = FoodieSecondary,
  onSecondaryContainer = FoodiePrimary,
  tertiary = FoodieSuccess,
  onTertiary = Color.White,
  background = Color.White,
  onBackground = FoodieTextPrimary,
  surface = Color.White,
  onSurface = FoodieTextPrimary,
  surfaceVariant = FoodieSecondary,
  onSurfaceVariant = FoodieTextSecondary,
  outline = FoodieBorder,
  outlineVariant = FoodieDivider
)

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit
) {
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as? Activity)?.window
      if (window != null) {
        window.statusBarColor = Color.Transparent.toArgb()
        // Dark status bar icons on white background
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
      }
    }
  }

  MaterialTheme(
    colorScheme = FoodieColorScheme,
    typography = Typography,
    content = content
  )
}
