package com.deep.coinflip

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import java.util.Random

class MainViewModel: ViewModel() {

    suspend fun getCoinValue(): CoinState {
        delay(1500)
        return if(Random().nextBoolean()) {
            CoinState.Heads
        } else {
            CoinState.Tails
        }
    }

    fun playSound(context: Context) {
        val mp: MediaPlayer = MediaPlayer.create(context, R.raw.coin_flip)
        mp.start()
    }

}