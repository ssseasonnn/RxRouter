package zlc.season.rxrouter

import android.content.Intent

data class Result(val resultCode: Int,
                  val data: Intent) {
    companion object {
        fun empty(): Result {
            return Result(-1000, Intent())
        }
    }
}