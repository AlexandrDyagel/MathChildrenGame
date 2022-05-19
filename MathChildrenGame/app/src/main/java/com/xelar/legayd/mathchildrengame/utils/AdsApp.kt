package com.xelar.legayd.mathchildrengame.utils

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class AdsApp(private val context: Context,private val adView: AdView) {

    private var adRequest: AdRequest? = null

    init {
        MobileAds.initialize(context)
        adRequest = AdRequest.Builder().build()
    }

    fun loadAd(){
        adRequest?.let { adView.loadAd(it) }
    }

    fun resume(){
        adView.resume()
    }

    fun destroy(){
        adView.destroy()
    }

    fun pause(){
        adView.pause()
    }

}