package com.fiqsky.simpleretrofit

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeApiRequest()
        backgroundAnimation()

        btn_fab.setOnClickListener {
            btn_fab.animate().apply {
                rotationBy(360f)
                duration = 1000
            }.start()
            makeApiRequest()
            iv_image.visibility = View.GONE
        }
    }

    private fun backgroundAnimation() {
        val animationDrawable: AnimationDrawable = rl_view.background as AnimationDrawable
        animationDrawable.apply {
            setEnterFadeDuration(1000)
            setExitFadeDuration(3000)
            start()
        }
    }

    private fun makeApiRequest() {
        val api = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getRandomCat()
                Log.d("Main", "Size: ${response.img}")

                withContext(Dispatchers.Main) {
                    Glide.with(applicationContext)
                            .load(response.img)
                            .into(iv_image)
                    iv_image.visibility = View.VISIBLE
                }

            } catch (e: Exception) {
                Log.e("Main", "Error: ${e.message}")
            }
        }
    }
}