package util

import platform.Foundation.NSFoundationVersionNumber
import platform.UIKit.UIDevice

/**
 * Get just device model.
 *
 * @return a string with the device model.
 * */
actual fun getDeviceModel(): String {
    return UIDevice.currentDevice.model()
}


/**
 * Get device model and OS.
 *
 * @return a string with the device information
 * */
actual fun getFullDeviceInfo(): String {
    val osName = UIDevice.currentDevice.systemName
    val osVersion = UIDevice.currentDevice.systemVersion
    val foundation = "iOS foundation version: ${NSFoundationVersionNumber}"

    return """
        plain device name: ${getDeviceModel()};
        OS name & version: $osName $osVersion;

        foundation version: $foundation.
        """
}