/*
package ahm.parts.ordering.ui.home.dealer.competitor.adapter

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.helper.goTo
import ahm.parts.ordering.helper.loadImage
import ahm.parts.ordering.helper.onClick
import ahm.parts.ordering.ui.previewimage.PreviewImageActivity
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.loopeer.shadow.ShadowView
import io.rmiri.skeleton.SkeletonGroup

import java.util.ArrayList


class SliderCarouselAdapter(private val context: Activity) : PagerAdapter(), CardAdapter {
    private val cardViews: MutableList<ShadowView>
    private val cslItems: MutableList<String>
    private var mBaseElevation: Float = 0.toFloat()

    init {
        cslItems = ArrayList()
        cardViews = ArrayList()
    }

    fun addCardItem(items: List<String>) {
        cslItems.addAll(items)
        for (i in cslItems.indices) {
            cardViews.add(ShadowView(context))
        }
    }

    override fun getBaseElevation(): Float {
        return mBaseElevation
    }

    override fun getCardViewAt(position: Int): ShadowView? {
        return cardViews[position]
    }

    override fun getCount(): Int {
        return cslItems.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.item_slider_competitor, container, false)

        val item = cslItems[position]
        container.addView(view)

        val cardView = view.findViewById<View>(R.id.carousel_cardView) as ShadowView
        cardView.onClick {
            context.goTo<PreviewImageActivity> {
                putExtra(Constants.BUNDLE.PHOTO, item)
            }
        }

        val ivItemImage = view.findViewById<ImageView>(R.id.iv_card_view)
        val skGroup = view.findViewById<SkeletonGroup>(R.id.skGroup)

        ivItemImage.loadImage(item, skGroup)

//        if (mBaseElevation == 0f) {
//            mBaseElevation = cardView.cardElevation
//        }

        //cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
      */
/*  cardView.maxCardElevation = (mBaseElevation * 1.8).toFloat()*//*

        cardViews[position] = cardView
        return view
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
        try {
            cardViews.set(position, null!!)
        } catch (e: Exception) {
        }

    }

}*/
