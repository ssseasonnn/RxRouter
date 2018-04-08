package zlc.season.sample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class ActionForResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_for_result)
    }

    companion object {
        val OUT_STRING = "outString"
        val RESULT_CODE = 1000
    }

    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra(OUT_STRING, "This is result!")
        setResult(RESULT_CODE, resultIntent)

        super.onBackPressed()
    }
}
