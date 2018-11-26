package fu.todofirebase

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.act_ask_like.*

class AskLikeActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_ask_like)

        btnLike.setOnClickListener {
            val properties = getSharedPreferences("properties", Context.MODE_PRIVATE)
            properties.edit {
                putString("likeGame", "true")
            }
            FirebaseAnalytics.getInstance(this).setUserProperty("likeGame", "true")
            finish()
        }

        btnUnLike.setOnClickListener {
            val properties = getSharedPreferences("properties", Context.MODE_PRIVATE)
            properties.edit {
                putString("likeGame", "false")
            }
            FirebaseAnalytics.getInstance(this).setUserProperty("likeGame", "false")
            finish()
        }

    }
}