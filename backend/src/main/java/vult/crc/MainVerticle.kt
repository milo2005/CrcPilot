package vult.crc

import io.vertx.core.DeploymentOptions
import io.vertx.core.Future
import io.vertx.rxjava.core.AbstractVerticle

@Suppress("unused")
class MainVerticle : AbstractVerticle() {
    override fun start(startFuture: Future<Void>) {
        vertx.rxDeployVerticle("vult.crc.database.DatabaseVerticle")
                .flatMap {
                    vertx.rxDeployVerticle("vult.crc.http.HttpServerVerticle"
                            , DeploymentOptions().setInstances(2))
                }.subscribe({startFuture.complete()}, startFuture::fail)
    }
}