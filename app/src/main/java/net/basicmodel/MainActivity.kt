package net.basicmodel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jessewu.library.SuperAdapter
import com.jessewu.library.view.ViewHolder
import es.dmoral.prefs.Prefs
import kotlinx.android.synthetic.main.activity_main.*
import me.shaohui.bottomdialog.BottomDialog
import org.w3c.dom.Text
import utils.NearbyData


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    var map: GoogleMap? = null
    val nearData = NearbyData.initNearbyData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapview) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        val adapter: SuperAdapter<String> =
            object : SuperAdapter<String>(R.layout.item_search) {
                override fun bindView(itemView: ViewHolder, data: String, position: Int) {
                    val tv = itemView.getView<TextView>(R.id.itemTv)
                    tv.text = data
                }
            }
        adapter.addData(nearData)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter


        fb.setOnClickListener {
            createDlg()
        }
        editText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()){
                    if (recycler.visibility == View.GONE){
                        recycler.visibility = View.VISIBLE
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context): Location {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) as Location
    }

    fun getCameraPosition(lat: Double, lgt: Double): CameraPosition {
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

    override fun onResume() {
        super.onResume()
        val type = Prefs.with(this).readInt("map", -1)
        if (type != -1) {
            when (type) {
                0 -> {
                    map?.let {
                        it.mapType = GoogleMap.MAP_TYPE_NORMAL
                    }
                }
                1 -> {
                    map?.let {
                        it.mapType = GoogleMap.MAP_TYPE_HYBRID
                    }
                }
                2 -> {
                    map?.let {
                        it.mapType = GoogleMap.MAP_TYPE_SATELLITE
                    }
                }
                3 -> {
                    map?.let {
                        it.mapType = GoogleMap.MAP_TYPE_TERRAIN
                    }
                }
            }
        }
    }

    fun createDlg() {
        val dlg = BottomDialog.create(supportFragmentManager)
        dlg
            .setLayoutRes(R.layout.dialog_selelct)
            .setTag("Select your option")
            .setViewListener {
                it.findViewById<TextView>(R.id.setting).setOnClickListener {
                    startActivity(Intent(this, SettingActivity::class.java))
                    dlg.dismiss()
                }
                it.findViewById<TextView>(R.id.inter).setOnClickListener {
                    startActivity(Intent(this, InteractiveActivity::class.java))
                    dlg.dismiss()
                }
            }
            .setDimAmount(0.8f)
            .show()
    }
}