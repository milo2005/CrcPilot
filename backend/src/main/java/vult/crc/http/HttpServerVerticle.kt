package vult.crc.http


import io.vertx.core.Future
import io.vertx.ext.web.handler.sockjs.BridgeOptions
import io.vertx.ext.web.handler.sockjs.PermittedOptions
import io.vertx.rxjava.core.AbstractVerticle
import io.vertx.rxjava.ext.web.Router
import io.vertx.rxjava.ext.web.handler.StaticHandler
import io.vertx.rxjava.ext.web.handler.sockjs.SockJSHandler
import vult.crc.database.DatabaseVerticle
import vult.crc.database.rxjava.services.ReportService
import vult.crc.database.services.ReportServiceBuilder


@Suppress("unused")
class HttpServerVerticle : AbstractVerticle() {

    override fun start(startFuture: Future<Void>) {
        val service: ReportService = ReportServiceBuilder.createProxy(vertx, DatabaseVerticle.CONFIG_QUEUE)


        val router: Router = Router.router(vertx)

        val sockJSHandler: SockJSHandler = SockJSHandler.create(vertx)
        val bridgeOptions = BridgeOptions()
                .addOutboundPermitted(PermittedOptions().setAddress("report.added"))
        sockJSHandler.bridge(bridgeOptions)
        router.route("/eventbus/*").handler(sockJSHandler)

        router.mountSubRouter("/api/report", reportRouter(vertx, service))
        router.get("/*").handler(StaticHandler.create().setCachingEnabled(false))

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .rxListen(8080)
                .subscribe({ startFuture.complete() }, startFuture::fail)

    }

}