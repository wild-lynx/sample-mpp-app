package util

/**
 * Get just device model.
 *
 * @return a string with the device model.
 * */
expect fun getDeviceModel(): String

/**
 * Get device model and OS.
 *
 * @return a string with the device information
 * */
expect fun getFullDeviceInfo(): String
