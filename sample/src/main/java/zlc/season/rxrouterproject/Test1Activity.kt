package zlc.season.rxrouterproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import zlc.season.rxrouterannotation.Router
import zlc.season.rxrouterannotation.Uri

@Uri("test1_activity")
class Test1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test1)
    }
}
