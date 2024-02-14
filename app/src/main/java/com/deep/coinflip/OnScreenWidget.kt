package com.deep.coinflip

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import java.util.Random

object OnScreenWidget : GlanceAppWidget() {

    val resultKey = stringPreferencesKey("result")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val count = currentState(key = resultKey) ?: "Heads"
            Column(
                modifier = GlanceModifier.background(Color.DarkGray)
                    .fillMaxSize(),
                verticalAlignment = Alignment.Vertical.CenterVertically,
                horizontalAlignment = Alignment.Horizontal.CenterHorizontally
            ) {
                Text(
                    text = count, style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        color = ColorProvider(Color.White),
                        fontSize = 26.sp
                    )
                )
                Box (modifier = GlanceModifier.height(12.dp)) {
                    
                }
                Button(
                    text = "Flip A Coin",
                    onClick = actionRunCallback(IncrementActionCallback::class.java)
                )
            }
        }
    }
}

class OnScreenCounterWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = OnScreenWidget
}

object IncrementActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) { prefs ->
            val currentCount = prefs[OnScreenWidget.resultKey]
            if (currentCount != null) {
                if (Random().nextBoolean()) {
                    prefs[OnScreenWidget.resultKey] = "Heads"
                } else {
                    prefs[OnScreenWidget.resultKey] = "Tails"
                }
            } else {
                prefs[OnScreenWidget.resultKey] = "Heads"
            }
        }
        OnScreenWidget.update(context, glanceId)
    }

}