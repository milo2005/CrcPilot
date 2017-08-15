package vult.crc.database

import io.vertx.core.Future
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.rxjava.core.AbstractVerticle
import io.vertx.rxjava.ext.mongo.MongoClient
import io.vertx.serviceproxy.ProxyHelper
import vult.crc.database.services.ReportService

import vult.crc.database.services.ReportServiceBuilder

@Suppress("unused")
class DatabaseVerticle:AbstractVerticle(){

    lateinit var client: MongoClient

    override fun start(startFuture: Future<Void>) {
        val config = json {
            obj (
                    "connection_string" to config().getString(CONFIG_CON, "mongodb://localhost:27017"),
                    "db_name" to config().getString(CONFIG_DB_NAME, "crc")
            )
        }
        client = MongoClient.createShared(vertx, config)

        val service = ReportServiceBuilder.create(client)
        ProxyHelper.registerService(ReportService::class.java, vertx.delegate, service, CONFIG_QUEUE)

        startFuture.complete()
    }

    companion object {
        val CONFIG_CON = "mongo.host"
        val CONFIG_DB_NAME = "mongo.db.name"
        val CONFIG_QUEUE = "mongo.db.queue"
    }

}