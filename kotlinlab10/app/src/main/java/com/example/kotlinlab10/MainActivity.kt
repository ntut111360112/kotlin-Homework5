package com.example.kotlinlab10

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )
        } else {
            initMap()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                initMap()
            } else {
                finish()
            }
        }
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // 檢查是否授權定位權限
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        // 顯示目前位置與目前位置的按鈕
        googleMap.isMyLocationEnabled = true

        // 加入標記
        val taipei101 = LatLng(25.033611, 121.565000)
        val taipeiStation = LatLng(25.047924, 121.517081)

        googleMap.addMarker(
            MarkerOptions().position(taipei101).title("台北 101").draggable(true)
        )

        googleMap.addMarker(
            MarkerOptions().position(taipeiStation).title("台北車站").draggable(true)
        )
        val polylineOptions = PolylineOptions()
            .add(taipei101)
            .add(LatLng(25.032728, 121.564137))
            .add(taipeiStation)
            .color(Color.BLUE)
            .width(10f)

        googleMap.addPolyline(polylineOptions)

        // 初始化地圖中心點及size
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(25.034, 121.545), 13f)
        )
    }
}

// 繪製線段