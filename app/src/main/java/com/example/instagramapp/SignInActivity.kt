package com.example.instagramapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val btn = findViewById<Button>(R.id.signup_btn)
        btn.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        val loginBtn = findViewById<Button>(R.id.login_btn)

        loginBtn.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val loading = Loading(this)
        loading.startLoadingActivity()
        val password = findViewById<EditText>(R.id.password_login).text.toString()
        val email = findViewById<EditText>(R.id.email_login).text.toString()

        when{
            TextUtils.isEmpty(email) -> Toast.makeText(this, "Full name is required!", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this, "Username is required!", Toast.LENGTH_LONG).show()
            else -> {
                val mAuth : FirebaseAuth  = FirebaseAuth.getInstance()
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
                    if (task.isSuccessful){
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
    }

    override fun onStart() {
        super.onStart()

        if(FirebaseAuth.getInstance().currentUser != null){
            val intent = Intent(this,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}