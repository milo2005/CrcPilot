package vult.crc.http

import io.vertx.rxjava.core.Vertx
import io.vertx.rxjava.ext.web.Router
import io.vertx.rxjava.ext.web.RoutingContext
import io.vertx.rxjava.ext.web.handler.BodyHandler
import vult.crc.database.models.Report
import vult.crc.database.rxjava.services.ReportService
import vult.crc.util.*
import java.util.*

fun reportRouter(vertx: Vertx, service: ReportService) = Router.router(vertx).apply {
    post("/").handler(BodyHandler.create().setUploadsDirectory("uploads"))
    get("/") { fetchReports(it, service) }
    get("/uploads/:name") { fetchImage(it) }
    post("/") { saveReport(it, service) }
}!!

private fun fetchReports(context: RoutingContext, service: ReportService) {
    service.rxAll()
            .subscribe(context::endWithJsonData, context::apiFailure)
}

private fun fetchImage(context: RoutingContext) {
    val name = context.pathParam("name")
    context.endWithFile("uploads/$name")
}

private fun saveReport(context: RoutingContext, service: ReportService) {
    if (context.fileUploads().size > 0) {
        val description: String? = context.formAttribute("description")
        val fileName = context.fileUploads().elementAt(0).uploadedFileName()
        service.rxInsert(Report(fileName, description, Date().time))
                .subscribe(context::endWithJsonId, context::apiFailure)
    } else context.apiFailure("Error al subir imagen")
}





