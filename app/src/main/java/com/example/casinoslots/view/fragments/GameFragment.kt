package com.example.casinoslots.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import com.example.casinoslots.constant.MAIN
import com.example.casinoslots.constant.listUrlSlots1
import com.example.casinoslots.constant.listUrlSlots2
import com.example.casinoslots.constant.url_image_game
import com.example.casinoslots.constant.url_image_symbol_question1
import com.example.casinoslots.constant.url_image_symbol_question2
import com.example.casinoslots.databinding.FragmentGameBinding
import com.example.casinoslots.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameFragment : Fragment() {

    private var binding:FragmentGameBinding? = null
    private val repository = Repository()
    private var job:Job = Job()

    private var listSlots1 = mutableListOf<String>()
    private var listSlots2 = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater,container,false)
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        @Suppress("DEPRECATION")
        super.onActivityCreated(savedInstanceState)
        //показ денежного счета
        binding!!.idGameTvYourMoney.text = "your money:${repository.getMoneyInCashAccount()}$"
    }

    @SuppressLint("SetTextI18n", "NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        job.cancel()

        //загрузка фоновой картинки
        repository.loadImage(url_image_game ,binding!!.idGameImg)

        //загрузка картинок для слотов
        repository.loadImage(url_image_symbol_question2,binding!!.idGameCs1Iv1)
        repository.loadImage(url_image_symbol_question2,binding!!.idGameCs1Iv2)
        repository.loadImage(url_image_symbol_question2,binding!!.idGameCs1Iv3)

        //загрузка картинок для слотов
        repository.loadImage(url_image_symbol_question1,binding!!.idGameCs2Iv1)
        repository.loadImage(url_image_symbol_question1,binding!!.idGameCs2Iv2)
        repository.loadImage(url_image_symbol_question1,binding!!.idGameCs2Iv3)
        repository.loadImage(url_image_symbol_question1,binding!!.idGameCs2Iv4)

        //обработка нажатия на кнопку начала игры
        binding!!.idGameCs1ButtonGo.setOnClickListener {
            if(!job.isActive){
                if(repository.getMoneyInCashAccount() >= 25){
                    //начало игры
                    repository.minusMoneyInCashAccount(25)
                    job = CoroutineScope(Dispatchers.Main).launch {
                        delay(1000)
                        listSlots1.add(listUrlSlots1.shuffled()[0])
                        repository.loadImage(listSlots1[0],binding!!.idGameCs1Iv1)
                        delay(1000)
                        listSlots1.add(listUrlSlots1.shuffled()[0])
                        repository.loadImage(listSlots1[1],binding!!.idGameCs1Iv2)
                        delay(1000)
                        listSlots1.add(listUrlSlots1.shuffled()[0])
                        repository.loadImage(listSlots1[2],binding!!.idGameCs1Iv3)
                        delay(500)
                        if(listSlots1[0]==listSlots1[1] && listSlots1[0]==listSlots1[2]){
                            //выигрыш
                            repository.longToast(requireContext(),"you've won!")
                            repository.shortToast(requireContext(),"+100$")
                            repository.addMoneyInCashAccount(100)
                        }else{
                            //проигрыш
                            repository.longToast(requireContext(),"no luck...")
                            repository.shortToast(requireContext(),"-25$")
                        }
                        binding!!.idGameTvYourMoney.text = "your money:${repository.getMoneyInCashAccount()}$"
                        delay(500)
                        repository.loadImage(url_image_symbol_question2,binding!!.idGameCs1Iv1)
                        repository.loadImage(url_image_symbol_question2,binding!!.idGameCs1Iv2)
                        repository.loadImage(url_image_symbol_question2,binding!!.idGameCs1Iv3)
                        listSlots1 = mutableListOf()
                        job.cancel()
                    }
                }else{
                    repository.shortToast(requireContext(),"not enough money")
                }
            }
        }

        //обработка нажатия на кнопку начала игры
        binding!!.idGameCs2ButtonGo.setOnClickListener {
            if(!job.isActive){
                if(repository.getMoneyInCashAccount() >= 75){
                    //начало игры
                    repository.minusMoneyInCashAccount(75)
                    job = CoroutineScope(Dispatchers.Main).launch {
                        delay(1000)
                        listSlots2.add(listUrlSlots2.shuffled()[0])
                        repository.loadImage(listSlots2[0],binding!!.idGameCs2Iv1)
                        delay(1000)
                        listSlots2.add(listUrlSlots2.shuffled()[0])
                        repository.loadImage(listSlots2[1],binding!!.idGameCs2Iv2)
                        delay(1000)
                        listSlots2.add(listUrlSlots2.shuffled()[0])
                        repository.loadImage(listSlots2[2],binding!!.idGameCs2Iv3)
                        delay(500)
                        listSlots2.add(listUrlSlots2.shuffled()[0])
                        repository.loadImage(listSlots2[3],binding!!.idGameCs2Iv4)
                        delay(500)
                        if(listSlots2[0]==listSlots2[1] && listSlots2[0]==listSlots2[2] && listSlots2[0]==listSlots2[3]){
                            //выигрыш
                            repository.longToast(requireContext(),"you've won!")
                            repository.shortToast(requireContext(),"+300$")
                            repository.addMoneyInCashAccount(300)
                        }else{
                            //проигрыш
                            repository.longToast(requireContext(),"no luck...")
                            repository.shortToast(requireContext(),"-75$")
                        }
                        binding!!.idGameTvYourMoney.text = "your money:${repository.getMoneyInCashAccount()}$"
                        delay(500)
                        repository.loadImage(url_image_symbol_question1,binding!!.idGameCs2Iv1)
                        repository.loadImage(url_image_symbol_question1,binding!!.idGameCs2Iv2)
                        repository.loadImage(url_image_symbol_question1,binding!!.idGameCs2Iv3)
                        repository.loadImage(url_image_symbol_question1,binding!!.idGameCs2Iv4)
                        listSlots2 = mutableListOf()
                        job.cancel()
                    }
                }else{
                    repository.shortToast(requireContext(),"not enough money")
                }
            }
        }

        //выход из игры по нажатии на кнопку НАЗАД
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            if(job.isActive){
                job.cancel()
            }
            MAIN.finishAffinity()
        }

    }

    //очистка биндинга при очистке вью
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}