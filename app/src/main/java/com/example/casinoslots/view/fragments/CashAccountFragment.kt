package com.example.casinoslots.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.casinoslots.constant.listMoneyForCashAccount
import com.example.casinoslots.constant.url_image_cash_account
import com.example.casinoslots.constant.url_image_symbol_money
import com.example.casinoslots.databinding.FragmentCashAccountBinding
import com.example.casinoslots.repository.Repository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CashAccountFragment : Fragment() {

    private var binding:FragmentCashAccountBinding? = null
    private var repository = Repository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCashAccountBinding.inflate(inflater,container,false)
        return binding?.root
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        @Suppress("DEPRECATION")
        super.onActivityCreated(savedInstanceState)
        //показ денежного счета
        binding!!.idCashAccountTvYourMoney.text = "your money:${repository.getMoneyInCashAccount()}"
    }

    @SuppressLint("SetTextI18n", "NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //загрузка фоновой картинки
        repository.loadImage(url_image_cash_account,binding!!.idCashAccountImg)

        //загрузка изображения денег
        repository.loadImage(url_image_symbol_money ,binding!!.idCashAccountImgMoney)

        if(repository.checkTodayAndLastDay()){
            binding!!.idCashAccountTvDesc2.text = "take the money!"
        }else{
            binding!!.idCashAccountTvDesc2.text = "try it tomorrow"
        }

        


        //обработка нажатия на кнопку пополнения счета
        binding!!.idCashAccountCsButtonGetMoney.setOnClickListener {
            if(repository.checkTodayAndLastDay()){
                //можно пополнить
                val currentDate = LocalDate.now()
                val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
                val formattedDate = currentDate.format(formatter)
                repository.setLastDay(day = formattedDate)
                val money = listMoneyForCashAccount.shuffled()[0]
                repository.addMoneyInCashAccount(money = money)
                repository.shortToast(context = requireContext(), message = "$money$")
                binding!!.idCashAccountTvDesc2.text = "+$money$"
            }else{
                //нельзя пополнить
                repository.longToast(context = requireContext(), message = "you will be able to top up your account only the next day")
                binding!!.idCashAccountTvDesc2.text = "try it tomorrow"
            }
        }

    }

    //очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}