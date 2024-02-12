package com.example.casinoslots.repository

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.example.casinoslots.api.RetrofitInstance
import com.example.casinoslots.constant.MAIN
import com.example.casinoslots.model.ResponseWebView
import com.squareup.picasso.Picasso
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Repository {

    //функция отправки настроек на сервер + получение ответа от сервера
    suspend fun setParametersPhone(phoneName:String,locale:String,unique:String): Response<ResponseWebView> {
        return RetrofitInstance.api.setPostParametersPhone(phoneName = phoneName, locale = locale, unique = unique)
    }

    //функция загрузки изображения
    fun loadImage(url:String,id: ImageView?){
        Picasso.get()
            .load(url)
            .into(id)
    }

    //функция получения денежного счета
    fun getMoneyInCashAccount(): Int {
        return MAIN.getMoneyInCashAccount()
    }

    //функция добавления денег к счету
    fun addMoneyInCashAccount(money:Int){
        MAIN.addMoneyInCashAccount(money = money)
    }

    //функция уменьшения денег на счете
    fun minusMoneyInCashAccount(money:Int){
        MAIN.minusMoneyInCashAccount(money = money)
    }

    //функция получения последнего дня, когда был пополнен счет
    fun getLastDay(): String {
        return MAIN.getLastDay()
    }

    //функция получения текущей даты
    @SuppressLint("NewApi")
    fun getCurrentDate(): String? {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
        return currentDate.format(formatter)
    }

    //функция установки последнего дня, когда был пополнен счет
    fun setLastDay(day:String){
        MAIN.setLastDay(day = day)
    }

    //функция показа короткого всплывающего сообщения
    fun shortToast(context: Context,message:String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    //функция показа длительного всплывающего сообщения
    fun longToast(context: Context,message:String){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }

    //функция проверки прошел ли день после последнего пополнения счета
    @SuppressLint("NewApi")
    fun checkTodayAndLastDay(): Boolean {
        return getCurrentDate() != getLastDay()
    }


}