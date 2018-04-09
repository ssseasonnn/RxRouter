package zlc.season.sample

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_clazz_menu.*
import zlc.season.rxrouter.RxRouter
import zlc.season.rxrouterannotation.Uri
import zlc.season.samplelibrary.LibraryClassForResultActivity

@Uri(clazzMenu)
class ClassMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clazz_menu)

        clazz.setOnClickListener {
            RxRouter.of(this)
                    .routeClass(ClassActivity::class.java)
                    .subscribe({
                        "no result".toast()
                    }, {
                        it.message?.toast()
                    })
        }

        clazz_for_result.setOnClickListener {
            RxRouter.of(this)
                    .with(10)
                    .with(true)
                    .with(20.12)
                    .with("this is a string value")
                    .routeClass(ClassForResultActivity::class.java)
                    .subscribe({
                        if (it.resultCode == Activity.RESULT_OK) {
                            val intent = it.data
                            val stringResult = intent.getStringExtra("result")
                            result_text.text = stringResult
                            stringResult.toast()
                        }
                    }, {
                        it.message?.toast()
                    })
        }

        library_clazz_for_result.setOnClickListener {
            RxRouter.of(this)
                    .with(2000)
                    .with(9999999999999999)
                    .with(false)
                    .with(0.00001)
                    .with("this is a string value")
                    .routeClass(LibraryClassForResultActivity::class.java)
                    .subscribe({
                        if (it.resultCode == Activity.RESULT_OK) {
                            val intent = it.data
                            val stringResult = intent.getStringExtra("result")
                            result_text.text = stringResult
                            stringResult.toast()
                        }
                    }, {
                        it.message?.toast()
                    })
        }
    }
}
