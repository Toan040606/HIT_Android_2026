package com.calculator

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calculator.databinding.ActivityChangeThemeBinding
import androidx.core.content.edit

class ChangeTheme : AppCompatActivity() {
    private lateinit var binding: ActivityChangeThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = getSharedPreferences("theme", MODE_PRIVATE)

        when (prefs.getString("theme", "amber")) {
            "amber" -> setTheme(R.style.Theme_Calculator_Amber)
            "baw" -> setTheme(R.style.Theme_Calculator_BlackAndWhite)
            "bag" -> setTheme(R.style.Theme_Calculator_BlueAndGray)
            "green" -> setTheme(R.style.Theme_Calculator_Green)
            "cyan" -> setTheme(R.style.Theme_Calculator_Cyan)
            "gray" -> setTheme(R.style.Theme_Calculator_Gray)
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChangeThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Toast.makeText(this, "On Create ChangeTheme", Toast.LENGTH_SHORT).show()

        binding.baw.setOnClickListener {
            saveTheme("baw")
            finish()
        }

        binding.amber.setOnClickListener {
            saveTheme("amber")
            finish()
        }

        binding.bag.setOnClickListener {
            saveTheme("bag")
            finish()
        }

        binding.cyan.setOnClickListener {
            saveTheme("cyan")
            finish()
        }

        binding.green.setOnClickListener {
            saveTheme("green")
            finish()
        }

        binding.gray.setOnClickListener {
            saveTheme("gray")
            finish()
        }

        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun saveTheme(theme: String) {
        val prefs = getSharedPreferences("theme", MODE_PRIVATE)
        prefs.edit { putString("theme", theme).apply() }

        //Log.i("CurrentTheme", "Saved theme: $theme")
    }
}