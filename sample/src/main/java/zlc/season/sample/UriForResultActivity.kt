package zlc.season.sample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import zlc.season.rxrouterannotation.Uri

@Uri("this is another uri")
class UriForResultActivity : AppCompatActivity() {

    companion object {
        val OUT_STRING = "outString"
        val RESULT_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uri_for_result)
    }

    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra(OUT_STRING, "This is result!")
        setResult(RESULT_CODE, resultIntent)

        super.onBackPressed()
    }
}
