package net.basicmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.dmoral.prefs.Prefs
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    var defaultIndex = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        defaultIndex = Prefs.with(this).readInt("map", -1)
        if (defaultIndex != -1) {
            when (defaultIndex) {
                0 -> {
                    sbNormal.isChecked = true
                    sbHybird.isChecked = false
                    sbSat.isChecked = false
                    sbTerrain.isChecked = false
                }
                1 -> {
                    sbNormal.isChecked = false
                    sbHybird.isChecked = true
                    sbSat.isChecked = false
                    sbTerrain.isChecked = false
                }
                2 -> {
                    sbNormal.isChecked = false
                    sbHybird.isChecked = false
                    sbSat.isChecked = true
                    sbTerrain.isChecked = false
                }
                3 -> {
                    sbNormal.isChecked = false
                    sbHybird.isChecked = false
                    sbSat.isChecked = false
                    sbTerrain.isChecked = true
                }
            }
        }

        sbNormal.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
//                sbNormal.isChecked = true
                sbHybird.isChecked = false
                sbSat.isChecked = false
                sbTerrain.isChecked = false
                Prefs.with(this).writeInt("map", 0)
            }
        }
        sbHybird.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sbNormal.isChecked = false
//                sbHybird.isChecked = true
                sbSat.isChecked = false
                sbTerrain.isChecked = false
                Prefs.with(this).writeInt("map", 1)
            }
        }
        sbSat.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sbNormal.isChecked = false
                sbHybird.isChecked = false
//                sbSat.isChecked = true
                sbTerrain.isChecked = false
                Prefs.with(this).writeInt("map", 2)
            }
        }
        sbTerrain.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sbNormal.isChecked = false
                sbHybird.isChecked = false
                sbSat.isChecked = false
//                sbTerrain.isChecked = true
                Prefs.with(this).writeInt("map", 3)
            }
        }
    }
}