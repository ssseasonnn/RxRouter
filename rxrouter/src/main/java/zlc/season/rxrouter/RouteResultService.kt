package zlc.season.rxrouter

import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor

class RouteResultService {
    private val processor: FlowableProcessor<Result> = PublishProcessor.create()

    fun dispatch(result: Result) {
        processor.onNext(result)
    }

    fun get(): FlowableProcessor<Result> {
        return processor
    }
}