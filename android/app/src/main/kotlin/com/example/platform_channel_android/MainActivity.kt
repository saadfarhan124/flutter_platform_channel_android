package com.example.platform_channel_android

import android.content.Intent
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private val CHANNEL = "saad.farhan/maps"
    private val REQUEST_CODE = 100
    private var methodChannel: MethodChannel? = null
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        methodChannel =  MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
        methodChannel!!.setMethodCallHandler{
            call, result ->
            when (call.method) {
                "getMapActivity" -> {
                    val intent = Intent(applicationContext, SelectLocationActivity::class.java)
                    if(call.arguments != null){
                        val args = call.arguments as HashMap<String, Double>
                        if (args.containsKey("latitude")){
                            intent.putExtra("latitude", args["latitude"])
                            intent.putExtra("longitude", args["longitude"])
                        }
                    }
                    startActivityForResult(intent, REQUEST_CODE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE){
            if(resultCode == 1){
                val hashMap: HashMap<String, Any> = HashMap<String, Any>()
                hashMap["latitude"] = data!!.getDoubleExtra("latitude", 12.2)
                hashMap["longitude"] = data!!.getDoubleExtra("longitude", 12.2)
                methodChannel!!.invokeMethod("locationUpdated", hashMap)
            }
        }
    }
}
