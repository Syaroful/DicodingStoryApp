package com.syaroful.dicodingstoryapp.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.syaroful.dicodingstoryapp.data.StoryRepository
import com.syaroful.dicodingstoryapp.di.Injection
import com.syaroful.dicodingstoryapp.view.create_story.CreateStoryViewModel
import com.syaroful.dicodingstoryapp.view.detail.DetailViewModel
import com.syaroful.dicodingstoryapp.view.main.MainViewModel
import com.syaroful.dicodingstoryapp.view.maps.MapsViewModel
import com.syaroful.dicodingstoryapp.view.onboarding.OnboardingViewModel

class ViewModelFactory(private val repository: StoryRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }

            modelClass.isAssignableFrom(OnboardingViewModel::class.java) -> {
                OnboardingViewModel(repository) as T
            }

            modelClass.isAssignableFrom(CreateStoryViewModel::class.java) -> {
                CreateStoryViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("unknown ViewModel Class: " + modelClass.name)
        }
    }

//    companion object {
//        @Volatile
//        private var instance: ViewModelFactory? = null
//        @JvmStatic
//        fun getInstance(context: Context): ViewModelFactory {
//            if (instance == null) {
//                synchronized(ViewModelFactory::class.java) {
//                    instance = ViewModelFactory(Injection.provideStoryRepository(context))
//                }
//            }
//            return instance as ViewModelFactory
//        }
//    }

    companion object {
        fun getInstance(context: Context) =
            ViewModelFactory(Injection.provideStoryRepository(context))
    }
}