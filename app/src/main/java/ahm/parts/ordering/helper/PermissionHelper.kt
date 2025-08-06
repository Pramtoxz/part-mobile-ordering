package ahm.parts.ordering.helper

import android.content.Context
import android.widget.Toast
import com.tedpark.tedpermission.rx2.TedRx2Permission

open class PermissionHelper constructor(val context : Context){

    fun request(vararg permission : String, listener : () -> Unit){
        permission.all {
            TedRx2Permission.with(context)
                .setRationaleTitle("")
                .setRationaleMessage("") // "we need permission for read contact and find your location"
                .setPermissions(it)
                .request()
                .subscribe({ tedPermissionResult ->
                    if (tedPermissionResult.isGranted) {
                        listener()
                    } else {
                        Toast.makeText(
                            context,
                            "Permission Denied\n" + tedPermissionResult.deniedPermissions.toString(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }, { throwable -> throwable.printStackTrace() })

            true
        }
    }
}