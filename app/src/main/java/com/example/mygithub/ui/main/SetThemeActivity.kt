package com.example.mygithub.ui.main

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.mygithub.databinding.ActivitySetThemeBinding

class SetThemeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetThemeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val switchTheme = binding.switchTheme
        val pref =SettingPreferences.getInstance(application.dataStore)
        val mainViewModel= ViewModelProvider(this,SetThemeViewModelFactory(pref)).get(MainViewModel::class.java)


        mainViewModel.getThemeSettings().observe(this){isDarkModeActive->
            if(isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked=true
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked=false
            }
        }

        switchTheme.setOnCheckedChangeListener{_:CompoundButton?,isChecked:Boolean->
            mainViewModel.saveThemeSetting(isChecked)
        }

    }
}