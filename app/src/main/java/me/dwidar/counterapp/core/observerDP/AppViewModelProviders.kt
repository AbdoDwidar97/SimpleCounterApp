package me.dwidar.counterapp.core.observerDP
import android.os.Build
import androidx.annotation.RequiresApi

object AppViewModelProviders
{
    private var viewModels = ArrayList<AppViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun viewModels(viewModelInstance : AppViewModel, appView: AppView) : AppViewModel
    {
        for (vm in viewModels)
        {
            if (vm::class.java.typeName.equals(viewModelInstance::class.java.typeName))
            {
                vm.addView(appView)
                return vm
            }
        }

        viewModelInstance.addView(appView)
        viewModels.add(viewModelInstance)
        return viewModelInstance
    }
}