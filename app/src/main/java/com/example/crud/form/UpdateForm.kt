package com.example.crud.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.RadioButton
import android.widget.Toast
import com.example.crud.MainActivity
import com.example.crud.R
import com.example.crud.databinding.ActivityUploadFormBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateForm : AppCompatActivity() {

    private lateinit var binding: ActivityUploadFormBinding

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.get().addOnSuccessListener {
            if(it.exists()){
                val editableName = it.child("name").value.toString()

                val name = Editable.Factory.getInstance().newEditable(editableName)
                val gender = it.child("gender").value.toString()
                val dept = it.child("dept").value.toString()

                binding.etName.text = name

                when(gender){
                    "Male" -> binding.rgGender.check(R.id.rgValMale)
                    "Female" -> binding.rgGender.check(R.id.rgValFemale)
                    else -> binding.rgGender.check(R.id.rgValOther)
                }

                val deptArray = resources.getStringArray(R.array.departments)
                val deptPosi = deptArray.indexOf(dept)

                binding.spinnerDept.setSelection(deptPosi)

            }
            else{
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Data cannot be fetch", Toast.LENGTH_SHORT).show()
        }

        binding.updateBtn.setOnClickListener {

            val newName = binding.etName.text.toString()

            // For RadioGroup, get the selected radio button's text
            val selectedRadioButtonId = binding.rgGender.checkedRadioButtonId
            val radioGroup = findViewById<RadioButton>(selectedRadioButtonId)
            val newGender = radioGroup.text.toString()

            // For Spinner, get the selected item
            val newDept = binding.spinnerDept.selectedItem.toString()


            // Assuming you have a unique key for the data you want to update (e.g., user key)
            // val userKey = "your_unique_user_key"

            val updatedData = mapOf(
                "name" to newName,
                "gender" to newGender,
                "dept" to newDept
            )

            databaseReference.updateChildren(updatedData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@UpdateForm, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Data update failed", Toast.LENGTH_SHORT).show()
                }

        }
    }
}