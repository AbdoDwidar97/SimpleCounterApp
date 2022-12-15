package me.dwidar.counterapp.app.result_screen.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import me.dwidar.counterapp.app.main_screen.viewModel.MainViewModel
import me.dwidar.counterapp.core.observerDP.AppView
import me.dwidar.counterapp.core.observerDP.AppViewModelProviders
import me.dwidar.counterapp.databinding.ActivityFinalResultBinding

class FinalResultActivity : AppCompatActivity(), AppView
{
    lateinit var binding: ActivityFinalResultBinding
    lateinit var mainViewModel : MainViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = AppViewModelProviders.viewModels(MainViewModel(), this) as MainViewModel

        observe()
    }

    override fun observe()
    {
        binding.TxtResult.text = mainViewModel.getCount().toString()
    }
}