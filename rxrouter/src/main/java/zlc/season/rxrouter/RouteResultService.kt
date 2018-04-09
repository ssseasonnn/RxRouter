package zlc.season.rxrouter

import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor

class RouteResultService {
    private val processor: FlowableProcessor<Result> = PublishProcessor.create()

    fun success(result: Result) {
        processor.onNext(result)
        processor.onComplete()
    }

    fun error(throwable: Throwable) {
        processor.onError(throwable)
    }

    fun get(): FlowableProcessor<Result> {
        return processor
    }
}