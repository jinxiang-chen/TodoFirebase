package fu.todofirebase

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.metrics.AddTrace
import kotlinx.android.synthetic.main.activity_main.*
import java.io.DataOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MainAct : AppCompatActivity() {

    private lateinit var mFirebaseAnalytics: FirebaseAnalytics

    @AddTrace(name = "onCreateTrace", enabled = true /* optional */)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)


//        val properties = getSharedPreferences("properties", Context.MODE_PRIVATE)
//        val likeGameProperties = properties.getString("likeGame", "null")
//        if (likeGameProperties == "null"){
//            val intent = Intent(this, AskLikeActivity::class.java)
//            startActivity(intent)
//        }

        button.setOnClickListener {
//            AA().start()
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

    class AA : Thread(){
        override fun run() {
            val data = "badgerbadgerbadgerbadgerMUSHROOM!".toByteArray()

            val metric = FirebasePerformance.getInstance().newHttpMetric("https://www.google.com",
                FirebasePerformance.HttpMethod.GET)
            val url = URL("https://www.google.com")
            metric.start()
            val conn = url.openConnection() as HttpURLConnection
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/json")
            try {
                val outputStream = DataOutputStream(conn.outputStream)
                outputStream.write(data)
            } catch (ignored: IOException) {
            }

            metric.setRequestPayloadSize(data.size.toLong())
            metric.setHttpResponseCode(conn.responseCode)

            conn.disconnect()
            metric.stop()
        }
    }
}
