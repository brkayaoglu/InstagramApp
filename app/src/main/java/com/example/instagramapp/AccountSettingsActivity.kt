package com.example.instagramapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class AccountSettingsActivity : AppCompatActivity() {






    override fun onCreate(savedInstanceState: Bundle?) {

        val sharedPref = this.getSharedPreferences("com.example.darktheme", Context.MODE_PRIVATE)

        if(!sharedPref.getBoolean("NightMode",false)){
            setTheme(R.style.Theme_InstagramApp)
        }else{
            setTheme(R.style.Theme_DarkMode)
        }



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        val mySwitch = findViewById<Switch>(R.id.switch1)
        if(!sharedPref.getBoolean("NightMode",false)){
            mySwitch.isChecked = false
            mySwitch.text = "Light Mode"
        }else{
            mySwitch.isChecked = true
            mySwitch.text = "Dark Mode"
        }

        mySwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                findViewById<Switch>(R.id.switch1).text = "Dark Mode"
                sharedPref.edit().putBoolean("NightMode",true).apply()
                restartApp()
            }else{
                findViewById<Switch>(R.id.switch1).text = "Light Mode"
                sharedPref.edit().putBoolean("NightMode",false).apply()
                restartApp()
            }
        }

        val logoutBtn = findViewById<Button>(R.id.logout_btn)
        logoutBtn.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        val builder = AlertDialog.Builder(this@AccountSettingsActivity)
        builder.setMessage("Are you sure you want to logout?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this,SignInActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun restartApp() {
        val intent = Intent(this, AccountSettingsActivity::class.java)
        startActivity(intent)
        finish()
    }
}