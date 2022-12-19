
# Simple Counter App
Android Mobile App for Counting.

## The General Idea
Let's make an a counter android app with MVVM archtecture, 
without using Jetpack lifecycle.
just we implement our MVVM with observer design pattern.


## Apply Observer Design Pattern.

### Definition
The observer design pattern is one of Behavior design patterns. it makes a subscriber to register with 
and receive update notifications from a provider. The pattern defines 
a provider (also known as a subject or an observable) and one 
or more observers. Observers register with the provider (Subject), 
and whenever update or state change occurs, the provider 
automatically notifies all observers by calling one of their methods. 
In this method call, the provider can also provide current state 
information to observers.

See more information about observer design pattern here: 
(https://www.javatpoint.com/observer-pattern)

According to the Definition, in our senario, the subscriber (Observable) 
is the View Model that it contains the business logic and states 
changes, also the view model registers the observers, and the observers 
in our case is the View. thus, every view model has one or more subscribed 
views (Activities, Fragments, etc ...).

#### Observer (App View) Interface
Making an app view interface, in order to make android view implements
the observe function.

```
interface AppView
{
    fun observe()
}
```

#### Subject / Observable (App View Model) abstract class
Making an app view model abstract class, in order to register 
observers (android views) to notify any state changes.

```
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
```

As shown in the above code snippet, there is an array list which 
holds registered observers (android views) with addView method. also, there is a function 
notifyLestiners in order to notify all subscribers (android views)
inside the appViews array list.

#### App View Model providers
Finally, we make app view model providers class that holds an active 
view models in case of some view model defined in the first time.
The objective of this class is to manage and control the view models
used in the app, and make sure there is no more one instance of 
a view model used in the app runtime.

```
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
```

As shown in the above code snippet, this class is singlton, to 
achieve it's objective. that's where the view models array list is only
used during the app runtime.
Every time we define the view model inside the app view,
a viewModels method invoked, and take two parameters, one for the app
view model instance (observable), and the other one for app view instance (observer).
In case of view model instance class type exists inside viewModels arraylist,
register the app view in the existed instance of this view model and return it.
Otherwise, add the view model instance to viewModels arraylist, and register the appView instance
inside this view model, and return this view model instance.


#### Android View (Main Activity)

```
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
```

As shown in the above code snippet, the main activity implements
the interface class (appView), an overrides ```observe()``` method, in order 
to notify and update the view for any changes occures inside view model.



#### Main View Model
```
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
```

As shown in the above code snippet, MainViewModel class inherits
AppViewModel abstract class which holds App views arraylist and
```notifyLestiners()``` methods.
After making any changes or change any value, in our senario: ```count``` variable.
There are two methods update this variable: ```increase()``` for increment ```count``` variable, 
 & ```decrease()``` for decrement ```count``` variable.


 #### Final Result Activity
 We use ```MainViewModel``` inside another activity: ```FinalResultActivity```,
 to take the ```count``` variable from main view model, and print the value
 in this activity.

 ```
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
 ```
 
## Tech Stack (Android Kotlin)

**Tools:** Kotlin, View-Binding

**Archtecture:** MVVM with Observer design pattern.


## Screenshots
![1](https://user-images.githubusercontent.com/34503868/208419876-30614e34-f139-477e-b373-d472b8c034c0.png)
![2](https://user-images.githubusercontent.com/34503868/208419895-4b545b24-b8fd-4c15-9df4-7291ae13c33c.png)
![3](https://user-images.githubusercontent.com/34503868/208420011-e71e2607-aa44-4c1b-8ffb-c1c16e275647.png)
![4](https://user-images.githubusercontent.com/34503868/208420029-294780b3-3d1f-4464-a950-ac3145d31c81.png)
![6](https://user-images.githubusercontent.com/34503868/208420046-0f205359-a583-40d4-b494-9454f3ccff5f.png)
![7](https://user-images.githubusercontent.com/34503868/208420057-4e07be65-17c5-4a3a-aaba-8d3c8242c205.png)
![8](https://user-images.githubusercontent.com/34503868/208420069-5f2b926d-0732-4af8-85bd-56cbad4838b7.png)


