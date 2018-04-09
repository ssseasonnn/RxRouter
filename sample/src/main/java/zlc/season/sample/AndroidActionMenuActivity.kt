package zlc.season.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import zlc.season.rxrouterannotation.Uri

@Uri(androidActionMenu)
class AndroidActionMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_action_menu)
    }
}
