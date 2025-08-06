package ahm.parts.ordering.helper

import ahm.parts.ordering.R
import ahm.parts.ordering.data.model.spinner.SpinnerData
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import android.animation.LayoutTransition
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.github.chrisbanes.photoview.PhotoView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.wang.avi.AVLoadingIndicatorView
import io.rmiri.skeleton.SkeletonGroup
import me.shaohui.advancedluban.Luban
import me.shaohui.advancedluban.OnCompressListener
import org.jetbrains.anko.backgroundColorResource
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException
import java.io.File
import java.util.*
import javax.net.ssl.HttpsURLConnection
import kotlin.collections.ArrayList

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.hideView() {
    this.visibility = View.GONE
}

fun View.invisibleView() {
    this.visibility = View.INVISIBLE
}

fun Activity.onSearch(timer : Timer,onSearch: () -> Unit): Timer {

    try {
        timer.cancel()
    } catch (e: Exception) {
    }

    val timer = Timer()

    timer.schedule(
        object : TimerTask() {
            override fun run() {
                // TODO: do what you need here (refresh list)
                try {
                    this@onSearch.runOnUiThread {
                        onSearch()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        },
        1000
    )


    return timer
}

fun View.background(resDrawableId: Int, resColorId: Int) {
    val newIcon = ContextCompat.getDrawable(context, resDrawableId)
    newIcon!!.mutate()
        .setColorFilter(ContextCompat.getColor(context, resColorId), PorterDuff.Mode.SRC_IN)

    this.background = newIcon
}

fun View.background(resDrawableId: Drawable, resColorId: Int) {
    val newIcon = resDrawableId
    newIcon.mutate()
        .setColorFilter(ContextCompat.getColor(context, resColorId), PorterDuff.Mode.SRC_IN)

    this.background = newIcon
}


fun EditText.itText(): String {
    return this.text.toString()
}


fun EditText.isEmpty(): Boolean {
    return this.text.toString().isEmpty()
}


fun EditText.onChangeText(onChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onChanged.invoke(p0.toString())
        }

        override fun afterTextChanged(editable: Editable?) {

        }
    })
}

fun EditText.afterChange(onChanged: (String) -> Unit) {


    this.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->

        if (hasFocus) {
            if (this.itText() == "0.00" ||
                this.itText() == "0" ||
                this.itText() == "0,00"
            ) {
                this.setText("")
            }
        }
    }

    this.addTextChangedListener(object : TextWatcher {

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(editable: Editable?) {
            editable?.toString()?.let { onChanged.invoke(it) }
        }
    })
}

fun EditText.onChangeAfter(afterChange: (String) -> Unit, onChanged: (String) -> Unit) {

    this.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->

        if (hasFocus) {
            if (this.itText() == "0.00" ||
                this.itText() == "0" ||
                this.itText() == "0,00"
            ) {
                this.setText("")
            }
        }
    }


    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onChanged.invoke(p0.toString())
        }

        override fun afterTextChanged(editable: Editable?) {
            editable?.toString()?.let { afterChange.invoke(it) }
        }
    })
}


infix fun WebView.htmlTextLoad(html: String) {
    this.setBackgroundColor(resources.getColor(R.color.transparent))

    //webView.loadData(html, "text/html; charset=UTF-8", null)
    //webView.loadData(html, "text/html", "UTF-8")
    this.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)

}


infix fun TextView.textHtml(htmlText: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
    } else {
        this.text = Html.fromHtml(htmlText)
    }
}



//fun TextView.text(text: String) {
//    if (text.isEmpty() || text.equals("null", true))
//        this.text = "-"
//    else
//        this.text = text
//}


infix fun ViewGroup.inflate(layoutResId: Int): View =
    LayoutInflater.from(context).inflate(layoutResId, this, false)


fun View.onClick(function: () -> Unit) {
    setOnClickListener {
        function()
    }
}


fun <T> RecyclerView.setAdapter(
    context: Context,
    listData: ArrayList<T>,
    layout: Int,
    bindHolder: View.(Int) -> Unit,
    itemClick: T.(Int) -> Unit = {}
): RecyclerAdapter<T> {

    var recyclerAdapter = RecyclerAdapter(listData, layout, {
        val item = listData[it]

        this.bindHolder(it)

    }, {
        this.itemClick(it)
    })

    this.apply {
        layoutManager = LinearLayoutManager(context)
        setHasFixedSize(true)
        isNestedScrollingEnabled = false
        adapter = recyclerAdapter
    }


    return recyclerAdapter
}

fun <T> RecyclerView.setAdapterHorizontal(
    context: Context,
    listData: ArrayList<T>,
    layout: Int,
    bindHolder: View.(Int) -> Unit,
    itemClick: T.(Int) -> Unit = {}
): RecyclerAdapter<T> {

    var recyclerAdapter = RecyclerAdapter(listData, layout, {
        val item = listData[it]

        this.bindHolder(it)

    }, {
        this.itemClick(it)
    })

    this.apply {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        setHasFixedSize(true)
        isNestedScrollingEnabled = false
        adapter = recyclerAdapter
    }


    return recyclerAdapter
}

fun <T> RecyclerView.setAdapterGrid(
    context: Context,
    listData: ArrayList<T>,
    layout: Int,
    gridCount: Int,
    bindHolder: View.(Int) -> Unit,
    itemClick: T.(Int) -> Unit = {}
): RecyclerAdapter<T> {

    var recyclerAdapter = RecyclerAdapter(listData, layout, {
        val item = listData[it]

        this.bindHolder(it)

    }, {
        this.itemClick(it)
    })

    this.apply {
        layoutManager = GridLayoutManager(context, gridCount)
        setHasFixedSize(true)
        isNestedScrollingEnabled = false
        adapter = recyclerAdapter
    }


    return recyclerAdapter
}

fun RecyclerView.initItem(activity: Context, adapter: RecyclerView.Adapter<*>) {
    val mLayoutManager = LinearLayoutManager(activity)
    this.layoutManager = mLayoutManager
    this.setHasFixedSize(false)
    this.setItemViewCacheSize(20)
    this.itemAnimator = DefaultItemAnimator()
    this.adapter = adapter
    this.isNestedScrollingEnabled = false
}

fun RecyclerView.initItemHorizontal(activity: Context, adapter: RecyclerView.Adapter<*>) {
    val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    this.layoutManager = mLayoutManager
    this.setHasFixedSize(false)
    this.setItemViewCacheSize(20)
    this.itemAnimator = DefaultItemAnimator()
    this.adapter = adapter
    this.isNestedScrollingEnabled = false
}

fun RecyclerView.initItemGrid(activity: Context, adapter: RecyclerView.Adapter<*>, gridCount: Int) {
    val mLayoutManager = GridLayoutManager(activity, gridCount)
    this.layoutManager = mLayoutManager
    this.setHasFixedSize(false)
    this.setItemViewCacheSize(20)
    this.itemAnimator = DefaultItemAnimator()
    this.adapter = adapter
    this.isNestedScrollingEnabled = false
}

fun RecyclerView.onEndScroll(isGrid: Boolean, listener: () -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            var mLayoutManager: LinearLayoutManager

            if (isGrid)
                mLayoutManager = recyclerView.layoutManager as GridLayoutManager
            else
                mLayoutManager = recyclerView.layoutManager as LinearLayoutManager


            val visibleItemCount = recyclerView.childCount
            val totalItemCount = mLayoutManager.itemCount
            val firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition()


            if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                listener()
            }
        }
    })
}

fun NestedScrollView.scrollBottom(reachBottom: () -> Unit){
    this.getViewTreeObserver().addOnScrollChangedListener {

        val isBottomReached = this.canScrollVertically(1)

        if (!isBottomReached) {
            reachBottom()
        }

    }
}

infix fun SwipeRefreshLayout.swipe(swipe : Boolean){
    this.isRefreshing = swipe
}

fun String.getToDouble(): Double {
    if (this.isEmpty())
        return 0.0
    else {
        try {
            return this.toDouble()
        } catch (e: NumberFormatException) {
            return 0.0
        }
    }
}

fun String.getToInt(): Int {
    if (this.isEmpty())
        return 0
    else {
        try {
            return Integer.parseInt(this)
        } catch (e: NumberFormatException) {
            return 0
        }
    }
}

fun Boolean.getToInt(): Int {
    return if (this)
        1
    else {
        0
    }
}

infix fun ImageView.loadImage(strUrl: String) {
    Glide.with(this).load(strUrl).placeholder(R.drawable.es_no_data).into(this)
}

infix fun ImageView.loadImageCircle(strUrl: String) {
    Glide.with(this).load(strUrl)
        .apply(RequestOptions.circleCropTransform())
        .placeholder(R.drawable.es_no_data)
        .into(this)
}


fun ImageView.loadImage(strUrl : String,skeletonGroup : SkeletonGroup){

    skeletonGroup.startAnimation()
    skeletonGroup.setShowSkeleton(true)

    Glide.with(this)
        .load(strUrl)
        .placeholder(R.drawable.es_no_data)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                // log exception
                Log.e("TAG", "Error loading image", e)
                skeletonGroup.finishAnimation()
                skeletonGroup.setShowSkeleton(false)
                return false // important to return false so the error placeholder can be placed
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                skeletonGroup.finishAnimation()
                skeletonGroup.setShowSkeleton(false)
                return false
            }
        })
        .into(this)
}


fun ImageView.loadImage(strUrl : String,vLoading : AVLoadingIndicatorView){

    vLoading.showView()

    Glide.with(this)
        .load(strUrl)
        .placeholder(R.drawable.es_no_data)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                vLoading.hideView()
                return false // important to return false so the error placeholder can be placed
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                vLoading.hideView()
                return false
            }
        })
        .into(this)
}


fun Int.getToBool(): Boolean {
    return this == 1
}

/**
 * convert json
 */

inline fun <reified ITEM> String.toList(): List<ITEM> {
    val vList = ArrayList<ITEM>()
    val arry = JsonParser().parse(this).asJsonArray
    for (jsonElement in arry) {
        vList.add(Gson().fromJson(jsonElement, ITEM::class.java))
    }
    return vList
}

inline fun <reified ITEM> JSONArray.toList(): List<ITEM> {
    val vList = ArrayList<ITEM>()
    val arry = JsonParser().parse(this.toString()).asJsonArray
    for (jsonElement in arry) {
        vList.add(Gson().fromJson(jsonElement, ITEM::class.java))
    }
    return vList
}

inline fun <reified ITEM> String.getObject(): ITEM {
    return Gson().fromJson(this, ITEM::class.java)
}

inline fun <reified ITEM> JSONObject.getObject(): ITEM {
    return Gson().fromJson(this.toString(), ITEM::class.java)
}

inline fun <reified ITEM> ITEM.getString(): String {
    return Gson().toJson(this, ITEM::class.java)
}

fun JSONObject.string(value: String): String {
    try {
        return this.getString(value)
    } catch (e: Exception) {
        return ""
    }
}

fun JSONObject.int(value: String): Int {
    try {
        return this.getInt(value)
    } catch (e: Exception) {
        return 0
    }
}

fun JSONObject.array(value: String): JSONArray {
    try {
        return this.getJSONArray(value)!!
    } catch (e: Exception) {
        return JSONArray()
    }
}

fun JSONObject.obj(value: String): JSONObject {
    try {
        return this.getJSONObject(value)!!
    } catch (e: Exception) {
        return JSONObject()
    }
}

/**
 * extra intent
 */

infix fun Activity.extra(value: String): String {
    try {
        return intent.getStringExtra(value)!!
    } catch (e: Exception) {
        return ""
    }
}

fun Activity.extra(value: String, default: Int): Int {
    return intent.getIntExtra(value, default)
}

fun Activity.extra(value: String, default: Boolean): Boolean {
    return intent.getBooleanExtra(value, default)
}

infix fun Intent.extra(value: String): String {
    return this.getStringExtra(value)!!
}

fun Intent.extra(value: String, default: Int): Int {
    return this.getIntExtra(value, default)
}

fun Intent.extra(value: String, default: Boolean): Boolean {
    return this.getBooleanExtra(value, default)
}

inline fun Activity.setResult(resultCode : Int,noinline init: Intent.() -> Unit = {}){
    val intent = Intent()
    intent.init()
    setResult(resultCode,intent)
    finish()
}

inline fun <reified ITEM> AppCompatSpinner.initWithHint(context: Context, items: ArrayList<ITEM>) {
    val myAdapter = object : ArrayAdapter<ITEM>(context, R.layout.item_spinner, items) {
        override fun isEnabled(position: Int): Boolean {
            return position != 0
        }

        override fun getDropDownView(
            position: Int, convertView: View?,
            parent: ViewGroup
        ): View {
            val view = super.getDropDownView(position, convertView, parent)
            val tv = view as TextView
            if (position == 0) {
                // Set the disable item text color
                tv.setTextColor(Color.parseColor("#B1B1B0"))
            } else {
                tv.setTextColor(Color.parseColor("#4B4A4F"))
            }
            return view
        }
    }

    myAdapter.setDropDownViewResource(R.layout.item_spinner)

    this.adapter = myAdapter
}

inline fun <reified ITEM> AppCompatSpinner.init(context: Context, items: ArrayList<ITEM>) {
    val myAdapter = object : ArrayAdapter<ITEM>(context, R.layout.item_spinner, items) {
        override fun isEnabled(position: Int): Boolean {
            return true
        }

        override fun getDropDownView(
            position: Int, convertView: View?,
            parent: ViewGroup
        ): View {
            val view = super.getDropDownView(position, convertView, parent)
            val tv = view as TextView
            tv.setTextColor(Color.parseColor("#4B4A4F"))
            return view
        }
    }

    myAdapter.setDropDownViewResource(R.layout.item_spinner)

    this.adapter = myAdapter
}

fun SearchView.init(hint: String, onChanged: (String) -> Unit, onSubmit: (String) -> Unit) {
    this.queryHint = hint
    this.maxWidth = android.R.attr.width

    (this.findViewById(R.id.search_src_text) as EditText)
        .setHintTextColor(resources.getColor(R.color.txt_hint_login))
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            onSubmit(query)
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            onChanged(newText)

            return true
        }
    })
}




fun ViewGroup.lyTrans() {
    try {
        val transition = LayoutTransition()
        transition.enableTransitionType(LayoutTransition.CHANGING)
        transition.setStartDelay(LayoutTransition.APPEARING, 2000)
        transition.setStartDelay(LayoutTransition.CHANGE_APPEARING, 2000)
        this.layoutTransition = transition
    } catch (e: Exception) {
    }
}


inline fun delay(milliseconds: Long, crossinline action: () -> Unit) {
    Handler().postDelayed({
        try {
            action()
        }catch (e : Exception){
            e.printStackTrace()
        }
    }, milliseconds)
}

infix fun TextView.text(value: String?) {
    text = StringMasker().validateEmpty(value)
}


fun Activity.statusBarChange(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }
}


/**
 * animated parent
 */
fun ViewGroup.anims() {
    try {
        TransitionManager.beginDelayedTransition(this)
    } catch (e: Exception) {
    }
}

/**
 * intent activity
 */

inline fun <reified T : Any> Activity.goTo(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
    fade()
}


inline fun <reified T : Any> Activity.goTo(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
    fade()
}


inline fun <reified T : Any> androidx.fragment.app.Fragment.goTo(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(activity!!)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
    activity!!.fade()
}

inline fun <reified T : Any> androidx.fragment.app.Fragment.goTo(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(activity!!)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
    activity!!.fade()
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)



fun Activity.animateRightIn() {
    this.overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out)
}


fun Activity.flagFullScreen() {
    if (Build.VERSION.SDK_INT >= 21) {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
}


fun Activity.fade() {
    try {
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}



fun EditText.div(vDivider: View, color: Int) {
    this.setOnFocusChangeListener { view, hasFocus ->
        if (hasFocus) {
            vDivider.backgroundColorResource = color
        } else {
            vDivider.backgroundColorResource = R.color.grey_divider_1
        }
    }
}

fun EditText.div(vDivider: View, color: Int,onFocus: () -> Unit) {
    this.setOnFocusChangeListener { view, hasFocus ->
        if (hasFocus) {
            vDivider.backgroundColorResource = color
            onFocus()
        } else {
            vDivider.backgroundColorResource = R.color.grey_divider_1
        }
    }
}


fun EditText.isFocus(onFocus: () -> Unit) {
    this.setOnFocusChangeListener { view, hasFocus ->
        Log.e("hasFocus",""+hasFocus)
        if (hasFocus) {
            onFocus()
        }
    }
}

fun Context.datePicker(onSelect: (String) -> Unit) {

    var strDateSelect = ""

    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

        strDateSelect = "" + dayOfMonth + " " + ""+(monthOfYear + 1) + " " + year

        onSelect(strDateSelect)

    }, year, month, day)

    dpd.datePicker.minDate = System.currentTimeMillis() - 1000
    dpd.show()

}

fun Context.datePickerWithOutMin(onSelect: (String) -> Unit) {

    var strDateSelect = ""

    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

        strDateSelect = "" + dayOfMonth + " " + ""+(monthOfYear + 1) + " " + year

        onSelect(strDateSelect)

    }, year, month, day)

    dpd.show()

}

fun Context.showToast(message: String) {
    if (message != "") {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


infix fun EditText.onSubmitSearch(onSearch: (String) -> Unit) {
    this.setOnEditorActionListener { textView, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            onSearch(this.itText())
            true
        }
        false
    }
}

fun Context.compressImage(fileImage: File, fileCompress: (File) -> Unit) {
    Luban.compress(this, fileImage)
        .setMaxSize(200).launch(object : OnCompressListener {
            override fun onStart() {
            }

            override fun onSuccess(file: File) {
                fileCompress.invoke(file)
            }

            override fun onError(e: Throwable) {

            }
        })
}

fun Context.dialogPreview(imageFile: String) {
    val dialog = initDialog(R.layout.dialog_preview_image, true, Gravity.CENTER)

    val ivImage = dialog.findViewById<PhotoView>(R.id.ivImage)
    val rootView = dialog.findViewById<RelativeLayout>(R.id.rootView)

    rootView.onClick { dialog.dismiss() }
    ivImage.onClick { dialog.dismiss() }

    ivImage loadImage imageFile
}

fun Context.dialogSheetPreview(imageFile: String) {
    val dialog = bottomSheet(R.layout.dialog_preview_image) {}
    val ivImage = dialog.findViewById<PhotoView>(R.id.ivImage)!!
    val rootView = dialog.findViewById<RelativeLayout>(R.id.rootView)!!

    rootView.onClick { dialog.dismiss() }
    ivImage.onClick { dialog.dismiss() }

    ivImage loadImage imageFile
}


fun Context.bottomSheet(resLayout : Int, getView : BottomSheetDialog.(View) -> Unit) : BottomSheetDialog {

    val view = LayoutInflater.from(this).inflate(
        resLayout,
        null
    )
    val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)

    getView(dialog,view)

    dialog.setContentView(view)

    dialog.setOnShowListener {
        val bottomSheet = dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED)
    }

    val vp = view.parent as View
    vp.setBackgroundColor(Color.TRANSPARENT)

    dialog.show()

    return dialog
}

fun Context.bottomSheetWithOutShow(resLayout : Int, getView : BottomSheetDialog.(View) -> Unit) : BottomSheetDialog {

    val view = LayoutInflater.from(this).inflate(
        resLayout,
        null
    )
    val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)

    getView(dialog,view)

    dialog.setContentView(view)

    dialog.setOnShowListener {
        val bottomSheet = dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED)
    }

    val vp = view.parent as View
    vp.setBackgroundColor(Color.TRANSPARENT)

    return dialog
}

fun Context.initDialog(layoutResId: Int, cancelable: Boolean, gravity: Int): Dialog {
    var dialog: Dialog? = null
    try {
        dialog = Dialog(this, R.style.DialogLight)
        dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layoutResId)
        dialog.setCancelable(cancelable)

        val size = Point()
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        display.getSize(size)
        val mWidth = size.x

        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = gravity
        wlp.x = 0
        wlp.y = 0
        wlp.width = mWidth
        window.attributes = wlp
        dialog.show()
        return dialog
    } catch (e: Exception) {
        return dialog!!
        e.printStackTrace()
    }
}

fun Context.initDialogWithOutShow(layoutResId: Int, cancelable: Boolean, gravity: Int): Dialog {
    var dialog: Dialog? = null
    try {
        dialog = Dialog(this, R.style.DialogLight)
        dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layoutResId)
        dialog.setCancelable(cancelable)

        val size = Point()
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        display.getSize(size)
        val mWidth = size.x

        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = gravity
        wlp.x = 0
        wlp.y = 0
        wlp.width = mWidth
        window.attributes = wlp


        return dialog
    } catch (e: Exception) {
        return dialog!!
        e.printStackTrace()
    }
}

fun Context.initDialogFade(layoutResId: Int, cancelable: Boolean, gravity: Int): Dialog {
    var dialog: Dialog? = null
    try {
        dialog = Dialog(this, R.style.DialogLight)
        dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimationFade
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layoutResId)
        dialog.setCancelable(cancelable)

        val size = Point()
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        display.getSize(size)
        val mWidth = size.x

        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = gravity
        wlp.x = 0
        wlp.y = 0
        wlp.width = mWidth
        window.attributes = wlp
        dialog.show()
        return dialog
    } catch (e: Exception) {
        return dialog!!
        e.printStackTrace()
    }
}


fun Spinner.initSp(
    context: Context,
    listSp: ArrayList<SpinnerData>,
    onSelected: Int.(SpinnerData) -> Unit
): SpinnerAdapter {

    this.apply {
        val arrayAdapter = SpinnerAdapter(context, listSp)
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        adapter = arrayAdapter

        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                onSelected(i,listSp[i])

            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

        return arrayAdapter
    }
}


class SpinnerAdapter(internal var context: Context, items: java.util.ArrayList<SpinnerData>) :
    ArrayAdapter<SpinnerData>(context, R.layout.spinner_dropdown_item, items) {

    private var inflater: LayoutInflater? = null
    private var listSpinner: MutableList<SpinnerData> = items

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var vi = convertView
        val holder: ViewHolder

        if (convertView == null)
            vi = inflater!!.inflate(R.layout.spinner_dropdown_item, null)
        holder = ViewHolder(vi!!)
        vi.tag = holder

        val item = listSpinner[position]

        if (position == 0) {
            holder.textspinner.setTextColor(context.resources.getColor(R.color.grey_hint))
        } else {
            holder.textspinner.setTextColor(context.resources.getColor(R.color.txt_black_category))
        }

        holder.textspinner.text = item.name
        return vi
    }

    internal inner class ViewHolder(view: View) {
        var textspinner: TextView

        init {
            textspinner = view.findViewById(R.id.text1)
        }
    }
}



fun Throwable.isErrorServer(): Boolean {
    var message = ""
    val isErrorServer: Boolean
    when ((this as HttpException).code()) {
        HttpsURLConnection.HTTP_UNAUTHORIZED -> {
            isErrorServer = true
            message = "Unauthorised User "
        }
        HttpsURLConnection.HTTP_FORBIDDEN -> {
            isErrorServer = true
            message = "Forbidden"
        }
        HttpsURLConnection.HTTP_INTERNAL_ERROR -> {
            isErrorServer = true
            message = "Internal Server Error"
        }
        HttpsURLConnection.HTTP_NOT_FOUND -> {
            isErrorServer = true
            message = "Internal Server Error"
        }
        HttpsURLConnection.HTTP_BAD_REQUEST -> {
            isErrorServer = true
            message = "Bad Request"
        }
        HttpsURLConnection.HTTP_BAD_GATEWAY -> {
            isErrorServer = true
            message = "Bad Request"
        }
        HttpsURLConnection.HTTP_SERVER_ERROR -> {
            isErrorServer = true
            message = "Server Error"
        }
        0 -> {
            isErrorServer = false
            message = "No Internet Connection"
        }
        else -> {
            isErrorServer = true
            message = this.localizedMessage!!
        }
    }
    return isErrorServer
}


/**
 * Fungsi untuk mengembalikan throwable respon dari api response
 */
fun isErrorServer(throwable: Throwable): Int {
    return if (throwable is HttpException) {
        throwable.code()
    }else{
        0
    }
}

//    public static void main(String args[])  {
//        try{
//            String input = "Text to encrypt";
//            System.out.println("input:" + input);
//            String cipher = CryptoHelper.encrypt(input);
//            System.out.println("cipher:" + cipher);
//            //cipher = "cSeTlY/nakstEWZ9EOzi2A==";
//            //System.out.println("cipher:" + cipher);
//            String output = CryptoHelper.decrypt(cipher);
//            System.out.println("output:" + output);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//    public static void main(String args[])  {
//        try{
//            String input = "Text to encrypt";
//            System.out.println("input:" + input);
//            String cipher = CryptoHelper.encrypt(input);
//            System.out.println("cipher:" + cipher);
//            //cipher = "cSeTlY/nakstEWZ9EOzi2A==";
//            //System.out.println("cipher:" + cipher);
//            String output = CryptoHelper.decrypt(cipher);
//            System.out.println("output:" + output);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

fun AESEncrypt(plainText: String?): String {
    val key: String = "cr0c0d1c"
    val iv: String = "crocodic"
    val aes = AES(key, 128, iv)
    return aes.encrypt(plainText)
}

fun AESEDecrypt(encryptText: String?): String {
    val key: String = "cr0c0d1c"
    val iv: String = "crocodic"
    val aes = AES(key, 128, iv)
    return aes.decrypt(encryptText)
}

/**
 * Fungsi untuk membuat page transparant
 *
 */
fun Activity.flagTransparant(){
    window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    window.statusBarColor = Color.TRANSPARENT
}

