<img src="/app/playstore_images/launcher_icon.png" width="192px" />

# Logcat Reader

A simple app for viewing logs on an android device.

This fork is based on [Logcat Reader by darshanparajuli](https://github.com/darshanparajuli/LogcatReader)

# Downloads
[Github releases](https://github.com/icewithcola/LogcatReader/releases)

## Screenshots
<img src="/app/playstore_images/screenshots/screenshot-1.png" width="300px" /> <img src="/app/playstore_images/screenshots/screenshot-2.png" width="300px" />

## Usage

Use ADB to grant `android.permission.READ_LOGS` to LogCatReader.

```sh
adb shell "pm grant com.dp.logcatapp android.permission.READ_LOGS && am force-stop com.dp.logcatapp"
``` 
or use root permission to grant this permission.\
On newer android versions (Android 13+) you still need to allow access all device logs manually.

## Contributing

Pull requests are welcome!