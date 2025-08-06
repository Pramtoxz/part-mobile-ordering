package ahm.parts.ordering.data.constant

import ahm.parts.ordering.data.constant.Constants.PACKAGENAME.PACKAGE_NAME

class Constants {

    object ROLE {
        //const val SALESMAN = "Salesman"
        const val SALESMAN = "MD_H3_SM"
        const val NONCHANNEL = "D_OT"
        const val DEALER = "D_H3"
        const val KOORDINATOR = "MD_H3_KORSM"
        const val MANAGER = "MD_H3_MGMT"
    }

    object DEFAULT {
        const val BASIC_AUTH_USER = "webservice"
        const val BASIC_AUTH_PASS = "Honda2020~"
        const val TAG = "tag"
    }

    object SHAREDPREF {
        val DEFAULT_VALUE_FCM_ID = null
        const val DEFAULT_VALUE_USER_ID = ""
        const val NAME = "NAME"
        const val KEY_FCM_ID = "FCM_ID"
        const val KEY_USER_ID = "USER_ID"
        const val KEY_TOKEN = "TOKEN"
        const val KEY_SID = "SID"

        const val KEY_PASSWORD_USERNAME = "PASSWORD SID"

        const val KEY_ROLE_ID = "ROLE ID"

        const val KEY_DASHBOARD_DEALER_ID_SELECTED = "DASHBOARD_DEALER_ID_SELECTED"
        const val KEY_DASHBOARD_DEALER_NAME_SELECTED = "DASHBOARD_DEALER_NAME_SELECTED"

        const val KEY_PASSWORD_USER = "PASSWORD_USER_ID"
    }

    object STORAGE {
        const val DIR_IMAGE_PICKER = "AHM Sales Tools"
        const val DIR = "ahm_sales_tools/"
    }

    object DIRECTORY {
        const val FILES = "file"

    }

    object REQUEST {

        const val PART_NUMBER_BARANG_SEARCH = 100
        const val PART_NUMBER_MOTOR_SEARCH = 101
        const val PART_NUMBER_FILTER = 102
        const val PART_NUMBER_HISTORY_ORDER = 103
        const val PART_NUMBER_SUGGEST_ORDER = 104

        const val COMPETITOR_ADD = 111

        const val COMPETITOR_KODE_DEALER = 110
        const val COLLECTION_KODE_DEALER = 110

        const val SKEMA_PEMBELIAN = 112

        const val SUGGEST_ORDER_RO = 113

        const val PART_NUMBER_SUMMARY = 114

        const val PICK_IMAGE_ADD_COMPETITOR = 115

        const val KODE_DEALER = 116

        const val RENCANA_VISIT = 117

        const val VISIT_ADD = 118

        const val KOORDINATOR_SALESMAN = 119

        const val SALESMAN_SELECT = 120

        const val PHOTO_ADD_DEALER = 121

    }

    object RESULT {

        const val PART_NUMBER_BARANG_SEARCH = 200
        const val PART_NUMBER_MOTOR_SEARCH = 201
        const val PART_NUMBER_FILTER = 202
        const val PART_NUMBER_HISTORY_ORDER = 203
        const val PART_NUMBER_SUGGEST_ORDER = 204

        const val COMPETITOR_ADD = 211
        const val COLLECTION_ADD = 211
        const val COMPETITOR_KODE_DEALER = 210

        const val COLLECTION_KODE_DEALER = 210

        const val SKEMA_PEMBELIAN = 212
        const val SKEMA_PEMBELIAN_UPDATE = 213

        const val SUGGEST_ORDER_RO = 213

        const val PART_NUMBER_SUMMARY = 214

        const val KODE_DEALER = 216

        const val RENCANA_VISIT = 217

        const val VISIT_ADD = 218

        const val KOORDINATOR_SALESMAN = 219

        const val SALESMAN_SELECT = 220
    }

    object BUNDLE {
        const val START_TIME = "start_time"
        const val END_TIME = "end_time"
        const val JSON = "json"
        const val NAME = "NAME"
        const val ROLE = "ROLE"
        const val CALENDAR = "CALENDAR"
        const val PHOTO = "PHOTO"
        const val KODEDEALER = "KODEDEALER"
        const val NOTRANSAKSI = "NOTRANSAKSI"
        const val TOTALBAYAR = "TOTALBAYAR"
        const val KOORDINATOR = "KOORDINATOR"
        const val COLLECTION = "COLLECTION"
        const val SALESMAN = "SALESMAN"
        const val KOORDINATOR_SALESMAN = "KOORDINATOR_SALESMAN"
        const val ALLDEALER = "ALLDEALER"
        const val ALLSALESMAN = "ALLSALESMAN"
        const val ALL_KOORDINATOR_SALESMAN = "ALL_KOORDINATOR_SALESMAN"
        const val PARAM = "param"
        const val POSITION = "position"
        const val LIST = "list"
        const val TOOLBAR = "toolbar"

        object EFEKTIVITAS_PLAN {
            const val DEALER = "dealer_efektivitas_selected"
            const val START_TIME = "start_time"
            const val END_TIME = "end_time"
            const val ALL_DEALER = "is_all_dealer"
            const val SALESMAN = "salesman"
        }

        object PARTSEARCH {

            const val SIMILARITY = "similarity"
            const val PART_NUMBER = "partnumber"
            const val PART_DESKRIPSI = "partdeskripsi"
            const val JSON_BARANG = "json_barang"
            const val JSON_MOTOR = "json_motor"
            const val JSON_DEALER = "json_dealer"
            const val JSON_CART = "json_cart"
            const val JSON_SUMMARY = "json_summary"
            const val JSON_ORDER = "json_order"

        }

        object ORDERSUGESTION {

            const val SIMILARITY = "similarity"
            const val PART_NUMBER = "partnumber"
            const val PART_DESKRIPSI = "partdeskripsi"
            const val JSON_BARANG = "json_barang"
            const val JSON_MOTOR = "json_motor"
            const val JSON_DEALER = "json_dealer"
            const val JSON_CART = "json_cart"
            const val JSON_SUMMARY = "json_summary"

        }

        object DEALER {
            const val KODE_DEALER = "kode_dealer"
            const val RENCANA_VISIT = "rencana_visit"
            const val VISIT_ADD = "visit_add"
        }


    }

    object REMOTE {
        const val API_STATUS = "status"
        const val API_MESSAGE = "message"
        const val API_STATUS_SUCCESS = 1
        const val LIMIT = 10
        const val OBJ_DATA = "data"
        const val ARR_DATA = "data"
        const val ARR_LIST = "list"
        const val DATE = "yyyy-MM-dd"
    }

    object INTENT {
        const val RES_INTENT = 99
        const val RES_SELECT_CUSTOMER = 98

        const val REQ_INTENT = 99


        const val KEY_ITEM = "item"
        const val KEY_FLAG = "flag"

        const val FLAG_STOCK_UNIT = 1
        const val FLAG_STOCK_ACCESSORIES = 2
        const val FLAG_STOCK_APPAREL = 3

    }

    object BROADCAST {

        const val DASHBOARD = "action_dashboard"
        const val REFRESH_DASHBOARD_CALENDAR = "action_refresh_dashboard_calendar"
        const val DASHBOARD_CHECKOUT = "dashboard_checkout"

    }

    object LOCATIONPICKER {

        const val KEY_LAT = "lat"
        const val KEY_LNG = "lng"
        const val KEY_ADDRESS = "addrss"

        const val REQ_LOCATION = 607
        const val RES_LOCATION_PICKER = 708
        const val REQ_LOCATION_PICKER = 608


        const val PACKAGE = "ahm.sales.tools"
        const val LOCATION_DATA_EXTRA = "$PACKAGE.LOCATION_DATA_EXTRA"
        const val RECEIVER = "$PACKAGE.RECEIVER"
        const val RESULT_DATA_KEY = "$PACKAGE.RESULT_DATA_KEY"

        const val SUCCESS_RESULT = 0
        const val FAILURE_RESULT = 1

        const val INT_STATUS = 1
    }

    object DASHBOARD {

        const val DEALER = "Dealer"
        const val SALESMAN = "Salesman"

        object TYPE {

            const val BERTAHAN = "Bertahan"
            const val NAIK = "Naik"
            const val TURUN = "Turun"

        }
    }

    object PACKAGENAME{
        const val PACKAGE_NAME = "ahm.parts.ordering"
    }

}