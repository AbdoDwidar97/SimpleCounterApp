package me.dwidar.counterapp.core.observerDP

abstract class AppViewModel
{
    private val appViews = ArrayList<AppView>()

    fun addView(appView: AppView)
    {
        appViews.add(appView)
    }

    fun notifyListeners()
    {
        for (view in appViews) view.observe()
    }
}