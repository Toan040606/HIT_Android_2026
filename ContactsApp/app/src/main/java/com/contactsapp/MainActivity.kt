package com.contactsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.contactsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val contacts = mutableListOf<Contact>()

        adapter = ContactAdapter(contacts) {
            Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show()
        }

        val rvContacts = binding.rvContacts
        rvContacts.layoutManager = LinearLayoutManager(this)
        rvContacts.adapter = adapter

        adapter.updateAll(contacts)


        binding.addBtn.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val phone = binding.phoneInput.text.toString()

            if (!(name.isEmpty() || phone.isEmpty())) {
                contacts.add(Contact(R.mipmap.ic_launcher_round, name, phone))
            }
            adapter.updateAll(contacts.toMutableList())


        }


    }
}