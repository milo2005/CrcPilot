package vult.crc.database.services

import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.json.JsonObject
import io.vertx.rx.java.RxHelper
import io.vertx.rxjava.ext.mongo.MongoClient
import rx.Observable
import vult.crc.database.models.Report


class ReportServiceImpl(val client: MongoClient) : ReportService {

    val collection: String = "reports"

    override fun all(resultHandler: Handler<AsyncResult<List<Report>>>) {
        client.rxFind(collection, JsonObject())
                .flatMapObservable { Observable.from(it) }
                .map { Report(it) }
                .toList()
                .subscribe(RxHelper.toSubscriber(resultHandler))
    }

    override fun insert(report: Report, resultHandler: Handler<AsyncResult<String>>) {
        client.rxInsert(collection, report.toJson())
                .subscribe(RxHelper.toSubscriber(resultHandler))
    }
}