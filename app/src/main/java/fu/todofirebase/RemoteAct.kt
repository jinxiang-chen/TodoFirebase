package fu.todofirebase

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class RemoteAct: AppCompatActivity(){

    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    private lateinit var layRoot: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_remote)
        layRoot = findViewById(R.id.act_remote_layRoot)

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        firebaseRemoteConfig.setConfigSettings(
            FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build())
        val default = mutableMapOf<String, Any>()
        default["appTheme"] = "white"
        firebaseRemoteConfig.setDefaults(default)
        val fetch = firebaseRemoteConfig.fetch(0)
        fetch.addOnCompleteListener {
            if(it.isSuccessful){
                firebaseRemoteConfig.activateFetched()
                val appTheme = firebaseRemoteConfig.getString("appTheme")
                Toast.makeText(this, appTheme, Toast.LENGTH_SHORT).show()
                if(appTheme == "red"){
                    layRoot.setBackgroundColor(Color.RED)
                }else{
                    layRoot.setBackgroundColor(Color.WHITE)
                }
            }else{

            }
        }

    }


}