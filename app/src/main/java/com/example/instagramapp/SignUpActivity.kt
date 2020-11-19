package com.example.instagramapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)



        val btn = findViewById<Button>(R.id.signin_btn_in_signup)
        btn.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        val signUpBtn = findViewById<Button>(R.id.signup_btn)
        signUpBtn.setOnClickListener {
            createAccount()
        }
    }

    private fun createAccount() {

        val loading = Loading(this)
        loading.startLoadingActivity()
        val fullName = findViewById<EditText>(R.id.fullname_signup).text.toString()
        val userName = findViewById<EditText>(R.id.username_signup).text.toString()
        val password = findViewById<EditText>(R.id.password_signup).text.toString()
        val email = findViewById<EditText>(R.id.email_signup).text.toString()

        when{
            TextUtils.isEmpty(fullName) -> Toast.makeText(this, "Full name is required!", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(userName) -> Toast.makeText(this, "Username is required!", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this, "Password is required!", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(email) -> Toast.makeText(this, "Email is required!", Toast.LENGTH_LONG).show()

            else -> {
                val mAuth : FirebaseAuth  = FirebaseAuth.getInstance()
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        saveUserInfo(fullName,userName,email,loading)
                        loading.dismissDialog()
                    }else{
                        val message = task.exception!!.toString()
                        Toast.makeText(this,"Error: $message",Toast.LENGTH_LONG).show()
                        loading.dismissDialog()
                        mAuth.signOut()
                    }
                }
            }
        }
    }

    private fun saveUserInfo(fullName: String, userName: String, email: String, loading: Loading) {
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef : DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserId
        userMap["fullName"] = fullName.toLowerCase()
        userMap["userName"] = userName.toLowerCase()
        userMap["email"] = email
        userMap["bio"] = "hey it is my bio on instagram app"
        userMap["profileImage"] = "https://firebasestorage.googleapis.com/v0/b/instagramapp-d63ac.appspot.com/o/Default%20Images%2Fprofile.png?alt=media&token=862fea05-c02c-40ec-b377-b036d92a2cfd"

        usersRef.child(currentUserId).setValue(userMap).addOnCompleteListener{task ->
            if(task.isSuccessful){
                Toast.makeText(this,"Account has been created successfully!",Toast.LENGTH_LONG).show()
                loading.dismissDialog()
                val intent = Intent(this,MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }else{
                val message = task.exception!!.toString()
                Toast.makeText(this,"Error: $message",Toast.LENGTH_LONG).show()
                loading.dismissDialog()
                FirebaseAuth.getInstance().signOut()
            }
        }
    }


}