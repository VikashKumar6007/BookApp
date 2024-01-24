package com.example.bookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneAuth : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_auth)


        val buttonSubmit: Button= findViewById(R.id.SubmitBtn)
        buttonSubmit.setOnClickListener {
           val intent = Intent(this,OtpVerification::class.java)
            startActivity(intent)

        }





        auth= FirebaseAuth.getInstance()
        val Login= findViewById<Button>(R.id.SubmitBtn)
        var currentUser=auth.currentUser
        if(currentUser!=null){
            startActivity(Intent(applicationContext,HomePage::class.java))
            finish()
        }
        Login.setOnClickListener {
            login()
        }
        callbacks= object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext,HomePage::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext,"Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(verificationId: String,
                                    token:PhoneAuthProvider.ForceResendingToken)
            {
                Log.d("Tag","onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken=token
                var intent= Intent(applicationContext,OtpVerification::class.java)
                intent.putExtra("storedVerificationId",storedVerificationId)
                startActivity(intent)
            }
        }

    }
    private fun login() {
        val mobileNumber= findViewById<EditText>(R.id.PhoneNum)
        var number= mobileNumber.text.toString().trim()

        if(!number.isEmpty()){
            number="+91"+number
            sendVerificationcode(number)

        }else{
            Toast.makeText(this,"Enter your mobile number",Toast.LENGTH_SHORT).show()
        }
    }


    private fun sendVerificationcode(number: String) {
        val option= PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(option)
    }

}
