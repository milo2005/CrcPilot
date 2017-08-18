package vult.crc.util

import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.rxjava.ext.web.Route
import io.vertx.rxjava.ext.web.Router
import io.vertx.rxjava.ext.web.RoutingContext


fun Router.get(path: String, handler: (RoutingContext) -> Unit): Route {
    return get(path).handler(handler)
}

fun Router.post(path: String, handler: (RoutingContext) -> Unit): Route {
    return post(path).handler(handler)
}

fun RoutingContext.apiFailure(error: Throwable) {
    response().statusCode = 500
    response().putHeader("Content-Type", "application/json")
    response().end(json { obj("success" to false, "error" to error.message) }.encode())
}

fun RoutingContext.apiFailure(message: String) {
    response().statusCode = 500
    response().putHeader("Content-Type", "application/json")
    response().end(json { obj("success" to false, "error" to message) }.encode())
}

fun RoutingContext.endWithJsonId(id: String) {
    response().putHeader("Content-Type", "application/json")
    response().end(json { obj("success" to true, "id" to id) }.encode())
}

fun RoutingContext.endWithJsonData(data: Any) {
    response().putHeader("Content-Type", "application/json")
    response().end(json { obj("success" to true, "data" to data) }.encode())
}

fun RoutingContext.endWithFile(filePath: String) {
    response().rxSendFile(filePath)
            .subscribe({}, {
                response().setStatusCode(404)
                        .end("Resources not found")
            })
}

fun RoutingContext.formAttribute(attributeName:String):String? = request().getFormAttribute(attributeName)

fun RoutingContext.publish(address:String, message:JsonObject){
    vertx().eventBus().publish(address, message)
}
