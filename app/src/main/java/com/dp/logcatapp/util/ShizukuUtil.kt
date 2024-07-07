package com.dp.logcatapp.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import com.dp.logger.Logger
import rikka.shizuku.Shizuku
import rikka.shizuku.Shizuku.OnRequestPermissionResultListener
import rikka.shizuku.ShizukuBinderWrapper
import rikka.shizuku.SystemServiceHelper

class ShizukuUtil : Fragment() {
  companion object {
    val REQUEST_CODE = 1000;
  }

  private class RequestPermissionResultListener : OnRequestPermissionResultListener {
    override fun onRequestPermissionResult(
        requestCode: Int,
        grantResult: Int,
    ) {
      val granted = grantResult == PackageManager.PERMISSION_GRANTED
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    Shizuku.addRequestPermissionResultListener(RequestPermissionResultListener());
    if (checkPermission(REQUEST_CODE)) {

    } else {
      Shizuku.requestPermission(REQUEST_CODE)
    }
    super.onCreate(savedInstanceState)
  }

  override fun onDestroy() {
    Shizuku.removeRequestPermissionResultListener(RequestPermissionResultListener())
    super.onDestroy()
  }

  private fun checkPermission(code: Int): Boolean {
    if (Shizuku.isPreV11()) {
      Logger.debug(this::class, "PreV11 Shizuku not supported.")
      return false
    }

    if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
      // Granted
      return true
    } else if (Shizuku.shouldShowRequestPermissionRationale()) {
      // Users choose "Deny and don't ask again"
      return false
    } else {
      // Request the permission
      Shizuku.requestPermission(code)
      return false
    }
  }

  @SuppressLint("PrivateApi") fun grantPermission(): Boolean {
    if (checkPermission(REQUEST_CODE)) {
      val iPmClass = Class.forName("android.content.pm.IPackageManager")
      val iPmStub = Class.forName("android.content.pm.IPackageManager\$Stub")
      val asInterfaceMethod = iPmStub.getMethod("asInterface", IBinder::class.java)
      val grantRuntimePermissionMethod = iPmClass.getMethod(
        "grantRuntimePermission", String::class.java /* package name */,
        String::class.java /* permission name */, Int::class.java /* user ID */
      )
      val iPmInstance = asInterfaceMethod.invoke(
        null, ShizukuBinderWrapper(
          SystemServiceHelper.getSystemService("package")
        )
      )

      grantRuntimePermissionMethod.invoke(
        iPmInstance, "com.dp.logcatapp", Manifest.permission.READ_LOGS, 0
      )
      return true
    } else {
      return false
    }
  }
}