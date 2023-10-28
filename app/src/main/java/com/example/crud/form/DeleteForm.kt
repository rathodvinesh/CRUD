package com.example.crud.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.RadioButton
import android.widget.Toast
import com.example.crud.MainActivity
import com.example.crud.R
import com.example.crud.databinding.ActivityDeleteFormBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DeleteForm : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityDeleteFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_delete_form)
        binding = ActivityDeleteFormBinding.inflate(layoutInflater)
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

                //disable
                binding.etName.isEnabled = false
                binding.rgGender.isEnabled = false
                for (i in 0 until binding.rgGender.childCount) {
                    val radioButton = binding.rgGender.getChildAt(i) as RadioButton
                    radioButton.isEnabled = false
                }
                binding.spinnerDept.isEnabled = false

            }
            else{
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Data cannot be fetch", Toast.LENGTH_SHORT).show()
        }

        binding.deleteBtn.setOnClickListener{
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // User exists in the database, proceed with deletion
                        databaseReference.removeValue()
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this@DeleteForm,
                                    "User deleted successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    this@DeleteForm,
                                    "Failed to delete user",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    } else {
                        // User does not exist in the database
                        Toast.makeText(this@DeleteForm, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        binding.backBtn.setOnClickListener {
            startActivity(Intent(this@DeleteForm, MainActivity::class.java))
            finish()
        }
    }
}