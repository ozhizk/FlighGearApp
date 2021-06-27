package com.example.flighgearapp

import java.io.PrintWriter
import java.net.Socket

class FgModel {

    private var ip:String = ""
    private var port:Int = 0
    private var socket:Socket = Socket()
    private lateinit var stream:PrintWriter
    var isConnect:Boolean = false


    // set ip value
    fun setIp(serverIp: String) {
        ip = serverIp
    }

    // set port value
    fun setPort(serverPort: Int) {
        port = serverPort
    }


    // connect to the server by port and ip
    fun Connect() {
        Thread(Runnable {
            // try to connect
            try {
                socket = Socket(ip, port)
                stream = PrintWriter(socket.getOutputStream(), true)
                isConnect = true
            } catch (e: Exception) {
                isConnect = false
            }
        }).start()
    }


    // sent to the server aileron value
    fun sendAileron(value: Float){
        if(this::stream.isInitialized){
            Thread(Runnable {
                stream.print("set /controls/flight/aileron $value\r\n")
                stream.flush()
            }).start()

        }

    }

    // sent to the server elevator value
    fun sendElevator(value: Float){
        if(this::stream.isInitialized){
            Thread(Runnable {
                stream.write("set /controls/flight/elevator $value\r\n")
                stream.flush()
            }).start()

        }

    }

    // sent to the server rudder value
    fun sendRudder (value: Float) {
        if(this::stream.isInitialized){
            Thread(Runnable {
                stream.print("set /controls/flight/rudder $value\r\n")
                stream.flush()
            }).start()

        }
    }

    // sent to the server throttle value
    fun sendThrottle (value: Float) {
        if(this::stream.isInitialized){
            Thread(Runnable {
                stream.print("set /controls/engines/current-engine/throttle $value\r\n")
                stream.flush()
            }).start()

        }
    }


    // disconnect from the server
    fun disconnect(){
        Thread( Runnable {
            stream.close()
            socket.close()
        }).start()
    }


}