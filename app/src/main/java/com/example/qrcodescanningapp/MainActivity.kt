package com.example.qrcodescanningapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.qrcodescanningapp.databinding.ActivityMainBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity() {

    private val requestPermissionLancher=registerForActivityResult(ActivityResultContracts.RequestPermission()){}
    private val scanLauncher=
        registerForActivityResult(ScanContract()){ result:ScanIntentResult->
            run {
                

                    if (result.contents == null) {
                        Toast.makeText(this, "cancelled", Toast.LENGTH_LONG).show()
                    } else {
                        setResult(result.contents)
                    }
                
            }

        }

    private fun setResult(string: String) {
            binding.textResult.text=string
    }


    private lateinit var binding:ActivityMainBinding

    private fun showCamera()
    {
        val options=ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan QR code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)

        scanLauncher.launch(options)

    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       initBinding()
       initViews()


    }

    private fun initViews() {
       binding.fab.setOnClickListener{
           checkPermissionCamera(this)
       }
    }

    private fun checkPermissionCamera(context: Context) {
        if(ContextCompat.checkSelfPermission(context,android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                showCamera()
        }else if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA))
        {
            Toast.makeText(context,"camera permission required",Toast.LENGTH_LONG).show()
        }else{
            requestPermissionLancher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun initBinding() {

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}