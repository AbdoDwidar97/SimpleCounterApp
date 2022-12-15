package me.dwidar.counterapp.app.main_screen.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import me.dwidar.counterapp.app.main_screen.viewModel.MainViewModel
import me.dwidar.counterapp.app.result_screen.view.FinalResultActivity
import me.dwidar.counterapp.core.observerDP.AppView
import me.dwidar.counterapp.core.observerDP.AppViewModelProviders
import me.dwidar.counterapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AppView
{
    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var mainViewModel : MainViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        mainViewModel = AppViewModelProviders.viewModels(MainViewModel(), this) as MainViewModel

        observe()

        activityMainBinding.btnIncrease.setOnClickListener {
            increase()
        }

        activityMainBinding.btnDecrease.setOnClickListener {
            decrease()
        }

        activityMainBinding.btnGoResult.setOnClickListener {
            goToResultScreen()
        }
    }

    fun increase()
    {
        mainViewModel.increase()
    }

    fun decrease()
    {
        mainViewModel.decrease()
    }

    fun goToResultScreen()
    {
        val intent = Intent(this, FinalResultActivity::class.java)
        startActivity(intent)
    }

    override fun observe() {
        activityMainBinding.txtCounter.text = mainViewModel.getCount().toString()
    }
}