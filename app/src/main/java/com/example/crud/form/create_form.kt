package com.example.crud.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.crud.MainActivity
import com.example.crud.databinding.ActivityCreateFormBinding
import com.example.crud.model.userDetails
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class create_form : AppCompatActivity() {

    //binding
    private lateinit var binding: ActivityCreateFormBinding

    //db reference
    private lateinit var databaseReference: DatabaseReference

    //initializing all the elements
    private lateinit var etName:TextView
    private lateinit var deptSpinner: Spinner
    private lateinit var radioGroup: RadioGroup
    private lateinit var uploadButton: Button
    private lateinit var spinnerValue : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_create_form)
        binding = ActivityCreateFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        etName = findViewById(R.id.etName)
////        deptSpinner = findViewById(R.id.spinnerDept)
////        radioGroup = findViewById(R.id.rgGender)
////        createButton = findViewById(R.id.uploadBtn)
        //binding
        etName = binding.etName
        deptSpinner = binding.spinnerDept
        radioGroup = binding.rgGender
//        uploadButton = binding.uploadBtn



//        deptSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//               spinnerValue = parent?.getItemAtPosition(position).toString()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//        }

            binding.uploadBtn.setOnClickListener {
                val name = etName.text.toString()
                val checkedButton = radioGroup.checkedRadioButtonId
                val rgValue = findViewById<RadioButton>(checkedButton)
                val rgGender = rgValue.text.toString()
                val spinnerDept = binding.spinnerDept.selectedItem.toString()

                databaseReference = FirebaseDatabase.getInstance().getReference("Users")

                val userData = userDetails(name,rgGender,spinnerDept)
                databaseReference.setValue(userData).addOnCompleteListener{
                    Toast.makeText(this, "Data sent Successfully $name $rgGender $spinnerDept", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@create_form, MainActivity::class.java))
                    finish()
                }.addOnCanceledListener {
                        Toast.makeText(this, "Cannot upload data", Toast.LENGTH_SHORT).show()
                    }
        }
    }
}