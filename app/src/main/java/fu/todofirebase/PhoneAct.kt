package fu.todofirebase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneAct: AppCompatActivity(), View.OnClickListener{


    companion object {
        const val TAG = "PhoneAct"
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var editPhone: EditText
    private lateinit var editCode: EditText
    private lateinit var btnGetCode: Button
    private lateinit var btnSignIn: Button
    private lateinit var btnSignOut: Button
    private lateinit var txtContent: TextView
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private var verifyID:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_phone_auth)

        auth = FirebaseAuth.getInstance()

        editPhone = findViewById(R.id.editPhone)
        editCode = findViewById(R.id.editCode)
        btnGetCode = findViewById(R.id.btnGetCode)
        btnSignIn = findViewById(R.id.btnSignIn)
        btnSignOut = findViewById(R.id.btnSignOut)
        txtContent = findViewById(R.id.txtContent)

        btnGetCode.setOnClickListener(this)
        btnSignIn.setOnClickListener(this)
        btnSignOut.setOnClickListener(this)

        if(auth.currentUser != null){
            txtContent.text = "登入成功，門號：${auth.currentUser?.phoneNumber}"
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:$credential")

            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e)
            }

            override fun onCodeSent(
                verificationId: String?,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Toast.makeText(this@PhoneAct, "收到簡訊", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onCodeSent:" + verificationId!!)
                verifyID = verificationId
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnGetCode ->{
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    editPhone.text.toString(),      // Phone number to verify
                    60,               // Timeout duration
                    TimeUnit.SECONDS, // Unit of timeout
                    this,             // Activity (for callback binding)
                    callbacks) // OnVerificationStateChangedCallbacks
            }

            R.id.btnSignIn ->{
                val code = editCode.text.toString()
                val credential = PhoneAuthProvider.getCredential(verifyID, code)

                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            txtContent.text = """
                                登入成功：
                                ${auth.currentUser?.phoneNumber}
                                ${auth.currentUser?.uid}

                                """.trimIndent()
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.exception)
                            txtContent.text = "登入失敗"
                        }
                    }

            }

            R.id.btnSignOut ->{
                auth.signOut()
                txtContent.text = "請登入"
            }
        }
    }
}