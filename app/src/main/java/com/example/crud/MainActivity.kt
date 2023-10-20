package com.example.crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.crud.databinding.ActivityMainBinding
import com.example.crud.form.ReadForm
import com.example.crud.form.UpdateForm
import com.example.crud.form.create_form
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createButton.setOnClickListener{
            startActivity(Intent(this@MainActivity, create_form::class.java))
        }

        binding.readButton.setOnClickListener{
            startActivity(Intent(this@MainActivity, ReadForm::class.java))
        }

        binding.updateButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, UpdateForm::class.java))
        }
    }
}