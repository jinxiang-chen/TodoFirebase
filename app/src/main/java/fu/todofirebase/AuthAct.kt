package fu.todofirebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class AuthAct: AppCompatActivity(){

    lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var authStateListener: FirebaseAuth.AuthStateListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
//        firebaseAuth.signInAnonymously()
//            .addOnCompleteListener {
//                if(it.isSuccessful){
//                    Toast.makeText(this, "匿名登入成功 uid:\n ${firebaseAuth.currentUser?.uid}", Toast.LENGTH_SHORT).show()
//                }else{
//                    Toast.makeText(this, "匿名登入失敗", Toast.LENGTH_SHORT).show()
//
//                }
//            }
        firebaseAuth.addAuthStateListener(authStateListener)
        authStateListener = FirebaseAuth.AuthStateListener {
            val user = it.currentUser

            if(user != null){

            }else{

            }
        }


    }
}