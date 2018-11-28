package fu.todofirebase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RealDBAct: AppCompatActivity(), View.OnClickListener{

    companion object {
        const val TAG = "RealDBAct"
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    private lateinit var btnSignIn: Button
    private lateinit var btnGet: Button
    private lateinit var btnPost: Button
    private lateinit var btnDelete: Button
    private lateinit var btnPut: Button
    private lateinit var txtContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_realdb)

        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference

        btnSignIn = findViewById(R.id.btnSignIn)
        btnGet = findViewById(R.id.btnGet)
        btnPost = findViewById(R.id.btnPost)
        btnDelete = findViewById(R.id.btnDelete)
        btnPut = findViewById(R.id.btnPut)
        txtContent = findViewById(R.id.txtContent)

        btnSignIn.setOnClickListener(this)
//        btnGet.setOnClickListener(this)
//        btnPost.setOnClickListener(this)
//        btnDelete.setOnClickListener(this)
//        btnPut.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()

        databaseRef.child("content").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                txtContent.text = dataSnapshot.value.toString()
            }
        })
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSignIn->{
                auth.signInAnonymously().addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this@RealDBAct, "auth success", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@RealDBAct, "auth fail", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            R.id.btnGet->{
                databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        txtContent.text = dataSnapshot.value.toString()
                    }

                })
            }

            R.id.btnPost->{
                databaseRef.child("appSetting")
                    .child("title")
                    .setValue("Welcome")
                databaseRef.child("appSetting")
                    .child("timeStamp")
                    .setValue(ServerValue.TIMESTAMP)
            }

            R.id.btnDelete->{
                databaseRef.child("appSetting").removeValue()
            }

            R.id.btnPut->{
                val member = mutableMapOf<String,String>()
                member["name"] = "Steven"
                member["gender"] = "male"
                member["height"] = "160"
                databaseRef.child("member").push().setValue(member)
            }
        }
    }
}