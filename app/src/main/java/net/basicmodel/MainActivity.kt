package net.basicmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import me.shaohui.bottomdialog.BottomDialog

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    var map: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapview) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        fb.setOnClickListener {
            createDlg()
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context): Location {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) as Location
    }

    fun getCameraPosition(lat:Double,lgt:Double): CameraPosition {
        return CameraPosition.builder().target(LatLng(lat, lgt))
            .zoom(15.5f)
            .bearing(0f)
            .tilt(25f)
            .build()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap?) {
        map = p0
        map!!.mapType = GoogleMap.MAP_TYPE_NORMAL
        map!!.uiSettings.isZoomControlsEnabled = true
        map!!.isMyLocationEnabled = true
        map!!.uiSettings.isMyLocationButtonEnabled = true
        val location = getLocation(this)
        val lat = location.latitude
        val lgt = location.longitude
        if (lat != 0.0 && lgt != 0.0) {
            map?.let {
                it.animateCamera(
                    CameraUpdateFactory.newCameraPosition(
                        getCameraPosition(
                            lat,
                            lgt
                        )
                    ), 1000, null
                )
                it.addMarker(
                    MarkerOptions()
                        .position(LatLng(lat, lgt)).title("On Your Location")
                )
            }
        }
    }
     fun createDlg(){
         BottomDialog.create(supportFragmentManager)
             .setLayoutRes(R.layout.dialog_selelct)
             .setTag("Select your option")
             .setDimAmount(0.8f)
             .show()
     }
}