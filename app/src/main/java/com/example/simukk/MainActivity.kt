package com.example.simukk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simukk.Request.LoginRequest
import com.example.simukk.Response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var session: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var login: AppCompatButton = findViewById(R.id.button_login)
        var email: EditText = findViewById(R.id.email_input)
        var password: EditText = findViewById(R.id.password_input)
        session = getSharedPreferences("session", Context.MODE_PRIVATE)
        var editor = session.edit()

        login.setOnClickListener {
            if (email.text.isNullOrEmpty())
            {
                email.error = "This field is required"
            }else if (password.text.isNullOrEmpty())
            {
                password.error = "This field is required"
            }else{
                var loginRequest = LoginRequest(email.text.toString(), password.text.toString())
                RetrofitClient.instance.loginRoute(loginRequest).enqueue(object :
                    Callback<LoginResponse>{
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful){
                            Log.d("Login Response", "onResponse: " + response.body())
                            var body = response.body()
                            Toast.makeText(applicationContext, "Success to login!", Toast.LENGTH_SHORT).show()
                            editor.putString("token", body?.token.toString())
                            editor.apply()

                            var intent = Intent(applicationContext ,BottomNavActivity::class.java)
                            intent.putExtra("index", "0")
                            startActivity(intent)
                        }else{
                            Toast.makeText(applicationContext, "Username and password not match", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        t.printStackTrace()
                    }
                    })

            }
        }

    }
}