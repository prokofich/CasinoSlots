package com.example.casinoslots.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.casinoslots.R
import com.example.casinoslots.constant.APP_PREFERENCES
import com.example.casinoslots.constant.LAST_DAY
import com.example.casinoslots.constant.MAIN
import com.example.casinoslots.constant.MY_CASH_ACCOUNT
import com.example.casinoslots.databinding.ActivityMainBinding
import com.example.casinoslots.view.fragments.CashAccountFragment
import com.example.casinoslots.view.fragments.GameFragment

class MainActivity : AppCompatActivity() {

    private val cashAccountFragment = CashAccountFragment()
    private val gameFragment = GameFragment()

    private var binding:ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        //полноэкранный режим
        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        MAIN = this

        //смена фрагментов на экране путем нажатия на кнопки меню
        replaceFragment(gameFragment)
        @Suppress("DEPRECATION")
        binding?.idBottomNavigation?.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.id_menu_game ->replaceFragment(gameFragment)
                R.id.id_menu_cash_account ->replaceFragment(cashAccountFragment)
            }
            true
        }

    }

    //функция замены фрагмента на экране
    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()
    }

    //функция показа диалогового сообщения о выходе
    fun showExitDialog(context: Context) {
        val options = arrayOf("exit", "cancel")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("do you want to get out?")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> {
                    finishAffinity()
                }
                1 -> {
                    dialog.cancel()
                }
            }
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    //функция добавления денег к счету
    fun addMoneyInCashAccount(money:Int){
        val preferences = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE)
        preferences.edit()
            .putInt(MY_CASH_ACCOUNT,getMoneyInCashAccount()+money)
            .apply()
    }

    //функция получения денежного счета
    fun getMoneyInCashAccount(): Int {
        return getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE).getInt(MY_CASH_ACCOUNT,0)
    }

    //функция уменьшения денег на счете
    fun minusMoneyInCashAccount(money:Int){
        val preferences = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE)
        preferences.edit()
            .putInt(MY_CASH_ACCOUNT,getMoneyInCashAccount()-money)
            .apply()
    }

    //функция получения последнего дня,когда был пополнен счет
    fun getLastDay(): String {
        val preferences = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE).getString(LAST_DAY,"")
        return preferences ?: ""
    }

    //функция установки последнего дня,когда был пополнен счет
    fun setLastDay(day:String){
        val preferences = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE)
        preferences.edit()
            .putString(LAST_DAY,day)
            .apply()
    }

}