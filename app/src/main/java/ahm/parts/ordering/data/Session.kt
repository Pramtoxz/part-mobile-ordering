package ahm.parts.ordering.data

import ahm.parts.ordering.data.constant.Constants
import android.content.Context
import android.content.SharedPreferences


/**
 * Created by nuryazid on 4/21/18.
 */

class Session(context: Context) {

    var pref: SharedPreferences =
        context.getSharedPreferences(Constants.SHAREDPREF.NAME, Context.MODE_PRIVATE)

    private var editor: SharedPreferences.Editor? = pref.edit()


    val fcmId: String
        get() = pref.getString(
            Constants.SHAREDPREF.KEY_FCM_ID,
            Constants.SHAREDPREF.DEFAULT_VALUE_FCM_ID
        ) ?: ""

    val userId: String
        get() = pref.getString(
            Constants.SHAREDPREF.KEY_USER_ID,
            Constants.SHAREDPREF.DEFAULT_VALUE_USER_ID
        ) ?: ""

    var passwordUser: String
        get() = pref.getString(Constants.SHAREDPREF.KEY_PASSWORD_USERNAME, "")!!
        set(s) {
            editor!!.putString(Constants.SHAREDPREF.KEY_PASSWORD_USERNAME, s!!)
            editor!!.apply()
        }

    var idRole: String?
        get() = pref.getString(Constants.SHAREDPREF.KEY_ROLE_ID, "")!!
        set(s) {
            editor!!.putString(Constants.SHAREDPREF.KEY_ROLE_ID, s!!)
            editor!!.apply()
        }


    var dashboardDealerIdSelected: String
        get() = pref.getString(Constants.SHAREDPREF.KEY_DASHBOARD_DEALER_ID_SELECTED, "")!!
        set(s) {
            editor!!.putString(Constants.SHAREDPREF.KEY_DASHBOARD_DEALER_ID_SELECTED, s!!)
            editor!!.apply()
        }

    var dashboardDealerNameSelected: String
        get() = pref.getString(Constants.SHAREDPREF.KEY_DASHBOARD_DEALER_NAME_SELECTED, "")!!
        set(s) {
            editor!!.putString(Constants.SHAREDPREF.KEY_DASHBOARD_DEALER_NAME_SELECTED, s!!)
            editor!!.apply()
        }

    val accessToken: String? get() = pref.getString(Constants.SHAREDPREF.KEY_TOKEN, null)
    val sessionId: String? get() = pref.getString(Constants.SHAREDPREF.KEY_SID, null)



    fun saveFcmId(value: String?) {
        val editor = pref.edit()
        editor?.putString(Constants.SHAREDPREF.KEY_FCM_ID, value)
        editor?.apply()
    }

    fun saveUserId(value: String?) {
        val editor = pref.edit()
        editor?.putString(Constants.SHAREDPREF.KEY_USER_ID, value)
        editor?.apply()
    }

    fun saveAccessToken(value: String?) {
        val editor = pref.edit()
        editor?.putString(Constants.SHAREDPREF.KEY_TOKEN, value)
        editor?.apply()
    }

    fun saveSid(value: String?) {
        val editor = pref.edit()
        editor?.putString(Constants.SHAREDPREF.KEY_SID, value)
        editor?.apply()
    }



    fun clearAll() {
        val bkFcmId = fcmId
        val bkUserId = userId

        val editor = pref.edit()
        editor.clear()

        editor?.putString(Constants.SHAREDPREF.KEY_FCM_ID, bkFcmId)
        editor?.putString(Constants.SHAREDPREF.KEY_USER_ID, bkUserId)

        editor.apply()
    }
}