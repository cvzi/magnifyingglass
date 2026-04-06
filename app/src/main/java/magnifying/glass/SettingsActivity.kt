/*
 * Modified fork of https://github.com/kloener/visor-android
 * Copyright (c) 2015 Christian Illies
 * File modified by cuzi <https://github.com/cvzi/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package magnifying.glass

import android.hardware.Camera
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.DropDownPreference
import androidx.preference.PreferenceFragmentCompat
import kotlin.math.max


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            initPreviewResolutionWidth()
            initCameraChooser()
        }

        private fun initCameraChooser() {
            val visorSurface = VisorSurface.getInstance()
            val numberOfCameras = Camera.getNumberOfCameras()
            val entryValues = MutableList(numberOfCameras) { i -> i.toString() }
            val entries = MutableList(numberOfCameras) { _ -> resources.getString(R.string.wide_or_other_camera) }

            for (cameraId in 0 until numberOfCameras) {
                val cameraInfo = Camera.CameraInfo()
                Camera.getCameraInfo(cameraId, cameraInfo)
                entries[cameraId] = when (cameraInfo.facing) {
                    Camera.CameraInfo.CAMERA_FACING_BACK -> resources.getString(R.string.main_camera)
                    Camera.CameraInfo.CAMERA_FACING_FRONT -> resources.getString(R.string.selfie_camera)
                    else -> resources.getString(R.string.wide_or_other_camera)
                }
            }
            val cameraIdPreference = findPreference<DropDownPreference>(resources.getString(R.string.key_preference_camera_id))
            if (cameraIdPreference != null) {
                cameraIdPreference.entries = entries.toTypedArray()
                cameraIdPreference.entryValues = entryValues.toTypedArray()
                cameraIdPreference.setValueIndex(visorSurface.preferredCameraId)
            }
        }

        private fun initPreviewResolutionWidth() {
            val visorSurface = VisorSurface.getInstance()
            val availablePreviewWidths: Array<CharSequence>? = visorSurface.availablePreviewWidths
            val previewResolutionPreference = findPreference<DropDownPreference>(resources.getString(R.string.key_preference_preview_resolution))
            if (previewResolutionPreference != null && availablePreviewWidths != null) {
                previewResolutionPreference.entries = availablePreviewWidths
                previewResolutionPreference.entryValues = availablePreviewWidths
                val currentPreviewWidth = visorSurface.cameraPreviewWidth
                val currentIndex = max(0, availablePreviewWidths.indexOf(currentPreviewWidth.toString()))
                previewResolutionPreference.setValueIndex(currentIndex)
            }
        }

    }
}
