package zlc.season.samplelibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import zlc.season.rxrouterannotation.Uri

@Uri(libraryFoo)
class FooLibraryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foo_library)
    }
}
