package me.dwidar.counterapp.app.main_screen.viewModel

import me.dwidar.counterapp.core.observerDP.AppViewModel

class MainViewModel : AppViewModel()
{
    private var count = 0

    fun getCount() : Int = count

    fun increase()
    {
        count++
        notifyListeners()
    }

    fun decrease()
    {
        count--
        notifyListeners()
    }
}