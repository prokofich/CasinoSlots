package com.example.casinoslots.view.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.casinoslots.constant.APP_PREFERENCES
import com.example.casinoslots.constant.ID
import com.example.casinoslots.constant.url_image_splash
import com.example.casinoslots.databinding.ActivitySplashBinding
import com.example.casinoslots.repository.Repository
import com.example.casinoslots.viewmodel.SplashViewModel
import java.util.Locale
import java.util.UUID

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val repository = Repository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //загрузка фоновой картинки
        repository.loadImage(url_image_splash,binding.idSplashImg)

        val splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        val namePhone = Build.MODEL.toString()
        val locale = Locale.getDefault().displayLanguage.toString()
        val id: String

        if (getMyId()!=""){
            id = getMyId()
        }else{
            id = UUID.randomUUID().toString()
            setMyId(id)
        }

        splashViewModel.setPostParametersPhone(namePhone,locale,id)
        splashViewModel.webViewUrl.observe(this){ responce ->
            when(responce.body()!!.url){
                "no" -> { goToMainPush() }
                "nopush" -> { goToMainNoPush() }
                else -> { goToWeb(responce.body()!!.url) }
            }
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    //функция получения ID
    private fun getMyId():String{
        val preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(ID,"")
        return preferences ?: ""
    }

    //функция установки ID
    private fun setMyId(id:String){
        val preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        preferences.edit()
            .putString(ID,id)
            .apply()
    }

    private fun goToMainPush() {
        startActivity(Intent(this@SplashActivity,MainActivity::class.java))
    }

    private fun goToMainNoPush() {
        startActivity(Intent(this@SplashActivity,MainActivity::class.java))
    }

    private fun goToWeb(url:String) {
        val intent = Intent(this@SplashActivity,WebViewActivity::class.java)
        intent.putExtra("url",url)
        startActivity(intent)
    }

}