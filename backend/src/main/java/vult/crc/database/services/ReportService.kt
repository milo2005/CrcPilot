package vult.crc.database.services

import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.rxjava.core.Vertx
import io.vertx.rxjava.ext.mongo.MongoClient
//import io.vertx.rxjava.ext.mongo.MongoClient

import vult.crc.database.models.Report
import vult.crc.database.rxjava.services.ReportService as RxReportService


@ProxyGen
@VertxGen
interface ReportService {
    fun all(resultHandler: Handler<AsyncResult<List<Report>>>)
    fun insert(report: Report, resultHandler: Handler<AsyncResult<String>>)
}

object ReportServiceBuilder {
    @JvmStatic
    fun create(client: MongoClient): ReportService = ReportServiceImpl(client)


    @JvmStatic
    fun createProxy(vertx: Vertx, address: String): RxReportService = RxReportService(ReportServiceVertxEBProxy(vertx.delegate, address))
}