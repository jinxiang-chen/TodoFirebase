package fu.todofirebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class PasswordSignInAct: AppCompatActivity(), View.OnClickListener{


    companion object {
        const val TAG = "PasswordSignInAct"
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnSignIn: Button
    private lateinit var btnSignOut: Button
    private lateinit var btnDelete: Button
    private lateinit var btnCreateAccount: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_password_signin)

        auth = FirebaseAuth.getInstance()

        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        btnSignIn = findViewById(R.id.btnSignIn)
        btnSignOut = findViewById(R.id.btnSignOut)
        btnDelete = findViewById(R.id.btnDelete)
        btnCreateAccount = findViewById(R.id.btnCreateAccount)

        btnSignIn.setOnClickListener(this)
        btnSignOut.setOnClickListener(this)
        btnDelete.setOnClickListener(this)
        btnCreateAccount.setOnClickListener(this)

        auth.addAuthStateListener {
            updateUI(it.currentUser)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        Log.w(TAG, "user = $user")
    }


    override fun onClick(v: View?) {

        when(v?.id){
            R.id.btnSignIn ->{
                auth.signInWithEmailAndPassword(editEmail.text.toString(), editPassword.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }

                        // ...
                    }
            }

            R.id.btnSignOut ->{
                auth.signOut()
            }

            R.id.btnDelete ->{
                auth.currentUser?.delete()?.addOnCompleteListener {
                    if(it.isSuccessful){
                        updateUI(null)
                    }else{
                        Toast.makeText(baseContext, "Delete failed.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            R.id.btnCreateAccount ->{
                startActivity(Intent(this, PasswordCreateAct::class.java))
            }
        }
    }
}