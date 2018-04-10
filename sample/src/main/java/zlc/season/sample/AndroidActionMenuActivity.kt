package zlc.season.sample

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_android_action_menu.*
import zlc.season.rxrouter.RxRouter
import zlc.season.rxrouterannotation.Url

@Url(androidActionMenu)
class AndroidActionMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_action_menu)

        call.setOnClickListener {
            RxRouter.of(this)
                    .addUri(Uri.parse("tel:123456"))
                    .routeSystemAction(Intent.ACTION_DIAL)
                    .subscribe()
        }

        send_message.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("sms_body", "这是信息内容")

            RxRouter.of(this)
                    .addUri(Uri.parse("smsto:10086"))
                    .with(bundle)
                    .routeSystemAction(Intent.ACTION_SENDTO)
                    .subscribe()
        }


    }
}
