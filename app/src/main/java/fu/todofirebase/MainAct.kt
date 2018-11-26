package fu.todofirebase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*

class MainAct : AppCompatActivity() {

    private lateinit var mFirebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)


        val properties = getSharedPreferences("properties", Context.MODE_PRIVATE)
        val likeGameProperties = properties.getString("likeGame", "null")
        if (likeGameProperties == "null"){
            val intent = Intent(this, AskLikeActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            mFirebaseAnalytics.logEvent("button", null)
            val intent = Intent(this, AskLikeActivity::class.java)
            startActivity(intent)
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val bundle = Bundle()
                bundle.putString("value", seekBar!!.progress.toString())
                mFirebaseAnalytics.logEvent("seekBar", bundle)
            }

        })

        switch1.setOnCheckedChangeListener {
                buttonView, isChecked ->
            mFirebaseAnalytics.logEvent("switch", null)
        }
    }

}
