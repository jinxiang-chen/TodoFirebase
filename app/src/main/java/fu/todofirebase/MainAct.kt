package fu.todofirebase

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

        button.setOnClickListener {
            mFirebaseAnalytics.logEvent("button", null)
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val bundle = Bundle()
                bundle.putString("value", seekBar!!.progress.toString())
                mFirebaseAnalytics.logEvent("switch", bundle)
            }

        })

        switch1.setOnCheckedChangeListener {
                buttonView, isChecked ->
            mFirebaseAnalytics.logEvent("switch", null)
        }
    }

}
