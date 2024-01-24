package com.example.bookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OtpVerification : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)



        val BtnSubmit: Button= findViewById(R.id.OtpBtn)
        BtnSubmit.setOnClickListener {
            val intent = Intent(this,HomePage::class.java)
            startActivity(intent)
        }






        auth= FirebaseAuth.getInstance()
        val sortedVerificationId= intent.getStringExtra("sortedVerificationId")
//        refenece
        val verify= findViewById<Button>(R.id.OtpBtn)
        val otpGiven= findViewById<EditText>(R.id.OTPNumber)

        verify.setOnClickListener {
            var otp= otpGiven.text.toString().trim()
            if(otp.isEmpty()){
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    sortedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                startActivity(Intent(this@OtpVerification,HomePage::class.java))
                Toast.makeText(this,"Enter OTP", Toast.LENGTH_SHORT).show()

            }
        }
    }



    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential){
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    startActivity(Intent(this@OtpVerification,HomePage::class.java))
                    finish()
                }else{
//                    sign in faild display a msg and update the UI
                    if(task.exception is FirebaseAuthInvalidCredentialsException){
//                        The verification code inter is invalid
                        Toast.makeText(this,"Invalid Otp",Toast.LENGTH_SHORT).show()

                    }
                }
            }
    }
}