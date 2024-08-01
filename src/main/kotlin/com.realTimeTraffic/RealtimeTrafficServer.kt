package com.realTimeTraffic

import io.grpc.Server
import io.grpc.ServerBuilder

class RealtimeTrafficServer(private val port: Int) {

    private val server: Server = ServerBuilder.forPort(port).addService(RealTimeService()).build()

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(Thread {
            println("*** shutting down gRPC")
            this@RealtimeTrafficServer.stop()
            println("*** server shut down")
        })
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    internal class RealTimeService : TrafficMonitoringGrpcKt.TrafficMonitoringCoroutineImplBase() {
        override suspend fun realTimeTraffic(request: SubscribeTrafficRequest): TrafficResponse =
            TrafficResponse.newBuilder().setMessage("Hello ${request.deviceId}").build()
    }
}

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 50051
    val server = RealtimeTrafficServer(port)
    server.start()
    server.blockUntilShutdown()
}
