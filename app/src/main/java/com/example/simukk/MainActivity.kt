package com.example.simukk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Visibility
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

        val progressBar: ProgressBar = findViewById(R.id.progress_bar)
        val content: LinearLayout = findViewById(R.id.content)

        login.setOnClickListener {
            if (email.text.isNullOrEmpty())
            {
                email.error = "This field is required"
            }else if (password.text.isNullOrEmpty())
            {
                password.error = "This field is required"
            }else{
                progressBar.visibility = View.VISIBLE
                content.visibility = View.GONE
                var loginRequest = LoginRequest(email.text.toString(), password.text.toString())
                RetrofitClient.instance.loginRoute(loginRequest).enqueue(object :
                    Callback<LoginResponse>{
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        progressBar.visibility = View.GONE
                        content.visibility = View.VISIBLE
                        if (response.isSuccessful){
                            Log.d("Login Response", "onResponse: " + response.body())
                            var body = response.body()
                            editor.putString("token", body?.token.toString())
                            editor.apply()

                            if (body?.role == "assessor"){
                                Toast.makeText(applicationContext, "Success to login!", Toast.LENGTH_SHORT).show()

                                var intent = Intent(applicationContext ,BottomNavActivity::class.java)
                                intent.putExtra("index", "0")
                                startActivity(intent)
                            }else if(body?.role == "student"){
                                Toast.makeText(applicationContext, "Success to login!", Toast.LENGTH_SHORT).show()

                                var intent = Intent(applicationContext , BottomStudentActivity::class.java)
                                intent.putExtra("index", "0")
                                startActivity(intent)
                            }else{
                                Toast.makeText(applicationContext, "Sorry you don't have access to this app", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(applicationContext, "Username and password not match", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        progressBar.visibility = View.GONE
                        content.visibility = View.VISIBLE
                        t.printStackTrace()
                    }
                    })


            }
        }

    }
}