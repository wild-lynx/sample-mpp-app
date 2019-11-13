package util

import com.jaredrummler.android.device.DeviceName

/**
 * Get just the device model in a nice way.
 *
 * The code uses an external library, https://github.com/jaredrummler/AndroidDeviceNames.
 *
 * @return a string with the correct device name for the top 600 Android devices. If the device is unrecognized, then Build.MODEL is returned
 * */
actual fun getDeviceModel(): String = DeviceName.getDeviceName()
