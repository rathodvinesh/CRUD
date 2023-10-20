package com.example.crud.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.RadioButton
import android.widget.Toast
import com.example.crud.R
import com.example.crud.databinding.ActivityReadFormBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ReadForm : AppCompatActivity() {

    //binding
    private lateinit var binding:ActivityReadFormBinding

    //db
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rgValMale = binding.rgValMale
        val rgValFemale = binding.rgValFemale
        val rgValother = binding.rgValOther

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        binding.readBtn.setOnClickListener {
//            val etName = binding.etName
//            val rgGender = binding.rgGender
//            val deptSpinner = binding.spinnerDept

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
        }
    }
}