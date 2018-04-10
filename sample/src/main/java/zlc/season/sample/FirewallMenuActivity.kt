package zlc.season.sample

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_firewall_menu.*
import zlc.season.rxrouter.Datagram
import zlc.season.rxrouter.Firewall
import zlc.season.rxrouter.RxRouter
import zlc.season.rxrouterannotation.Uri

@Uri(firewallMenu)
class FirewallMenuActivity : AppCompatActivity() {

    var loginStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firewall_menu)

        tv_login_status.text = "未登录"

        btn_login.setOnClickListener {
            loginStatus = true
            tv_login_status.text = "已登录"
        }

        btn_logout.setOnClickListener {
            loginStatus = false
            tv_login_status.text = "未登录"
        }

        btn_uri.setOnClickListener {
            RxRouter.of(this)
                    .with(123)
                    .with(false)
                    .addFirewall(LoginFirewall(loginStatus))
                    .route("this is another uri")
                    .subscribe({
                        if (it.resultCode == Activity.RESULT_OK) {
                            val intent = it.data
                            val stringResult = intent.getStringExtra("result")
                            stringResult.toast()
                        }
                    }, {
                        it.message?.toast()
                    })
        }

        btn_action.setOnClickListener {
            RxRouter.of(this)
                    .with(123)
                    .with(false)
                    .addFirewall(LoginFirewall(loginStatus))
                    .routeAction("zlc.season.sample.action_for_result")
                    .subscribe({
                        if (it.resultCode == Activity.RESULT_OK) {
                            val intent = it.data
                            val stringResult = intent.getStringExtra("result")
                            stringResult.toast()
                        }
                    }, {
                        it.message?.toast()
                    })
        }

        btn_class.setOnClickListener {
            RxRouter.of(this)
                    .with(123)
                    .with(false)
                    .addFirewall(LoginFirewall(loginStatus))
                    .routeClass(ClassForResultActivity::class.java)
                    .subscribe({
                        if (it.resultCode == Activity.RESULT_OK) {
                            val intent = it.data
                            val stringResult = intent.getStringExtra("result")
                            stringResult.toast()
                        }
                    }, {
                        it.message?.toast()
                    })
        }
    }

    class LoginFirewall(var loginStatus: Boolean) : Firewall {
        override fun allow(datagram: Datagram): Boolean {
            if (!loginStatus) {
                "您还没有登录，请先登录".toast()
            }
            return loginStatus
        }

        override fun thrown(): Throwable? {
            return null
        }
    }
}
