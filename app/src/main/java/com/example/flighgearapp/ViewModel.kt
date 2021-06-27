package com.example.flighgearapp

import androidx.databinding.BaseObservable

class ViewModel: BaseObservable() {

    var port: String = ""
        get() = field
        set(value) {
            if(value != "") {
                field = value
                model.setPort(port.toInt()) // update the model
            }

        }
    var ip: String = ""
        get() = field
        set(value) {
            if (value != "") {
                field = value
                model.setIp(value) // update the model
            }
        }
    var aileron: Float = 0f
        get() = field
        set(value) {
            field = value
            model.sendAileron(value) // update the model
        }
    var elevator: Float = 0f
        get() = field
        set(value) {
            field = value
            model.sendElevator(value) // update the model
        }
    var rudder: Int = 0
        get() = field
        set(value) {
            field = value
            model.sendRudder(value.toFloat()/1000) // update the model
        }
    var throttel: Int = 0
        get() = field
        set(value) {
            field = value
            model.sendThrottle(value.toFloat()/1000) // update the model
        }

    // the model
    var model:FgModel = FgModel()

    // connect to server
    fun connect(){
        model.Connect();

    }

    // disconnect from the server at the end of the running
    fun disconnect() {
        model.disconnect()
    }


}