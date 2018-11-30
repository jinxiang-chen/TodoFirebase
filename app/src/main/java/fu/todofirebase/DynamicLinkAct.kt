package fu.todofirebase

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DynamicLinkAct: AppCompatActivity(){

    lateinit var txtContent: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_dynamic_link)

        txtContent = findViewById(R.id.act_dynamic_link_txtContent)
    }
}