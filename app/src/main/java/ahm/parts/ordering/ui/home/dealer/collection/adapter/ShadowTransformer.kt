package ahm.parts.ordering.ui.home.dealer.collection.adapter

import android.view.View
import androidx.viewpager.widget.ViewPager


class ShadowTransformer(private val viewPager: ViewPager, private val cardAdapter: CardAdapter) :
    ViewPager.OnPageChangeListener, ViewPager.PageTransformer {
    private var lastOffset: Float = 0.toFloat()
    var isEnabledScaling: Boolean = false
        private set

    init {
        viewPager.currentItem = 0
        viewPager.addOnPageChangeListener(this)
    }

    fun enableScaling(enable: Boolean) {
        if (isEnabledScaling && !enable) {
            // Compress main card
            val currentCard = cardAdapter.getCardViewAt(viewPager.currentItem)
            if (currentCard != null) {
                currentCard.animate().scaleY(1f)
                currentCard.animate().scaleX(1f)
            }
        } else if (!isEnabledScaling && enable) {
            // Uncompress main card
            val currentCard = cardAdapter.getCardViewAt(viewPager.currentItem)
            if (currentCard != null) {
                currentCard.animate().scaleY(1.1f)
                currentCard.animate().scaleX(1.1f)
            }
        }
        isEnabledScaling = enable
    }

    override fun transformPage(page: View, position: Float) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val realCurrentPosition: Int
        val nextPosition: Int
        val baseElevation = cardAdapter.baseElevation
        val realOffset: Float
        val goingLeft = lastOffset > positionOffset

        // If we're going backwards, onPageScrolled receives the last position
        // instead of the current one
        if (goingLeft) {
            realCurrentPosition = position + 1
            nextPosition = position
            realOffset = 1 - positionOffset
        } else {
            nextPosition = position + 1
            realCurrentPosition = position
            realOffset = positionOffset
        }

        // Avoid crash on overscroll
        if (nextPosition > cardAdapter.count - 1 || realCurrentPosition > cardAdapter.count - 1) {
            return
        }

        val currentCard = cardAdapter.getCardViewAt(realCurrentPosition)

        // This might be null if a fragment is being used
        // and the views weren't created yet
        if (currentCard != null) {
            if (isEnabledScaling) {
                currentCard.scaleX = (1 + 0.1 * (1 - realOffset)).toFloat()
                currentCard.scaleY = (1 + 0.1 * (1 - realOffset)).toFloat()
            }
            currentCard.cardElevation = baseElevation + (baseElevation
                    * (CardAdapter.MAX_ELEVATION_FACTOR - 1).toFloat() * (1 - realOffset))
        }

        val nextCard = cardAdapter.getCardViewAt(nextPosition)

        // We might be scrolling fast enough so that the next (or previous) card
        // was already destroyed or a fragment might not have been created yet
        if (nextCard != null) {
            if (isEnabledScaling) {
                nextCard.scaleX = (1 + 0.1 * realOffset).toFloat()
                nextCard.scaleY = (1 + 0.1 * realOffset).toFloat()
            }
            nextCard.cardElevation = baseElevation + (baseElevation
                    * (CardAdapter.MAX_ELEVATION_FACTOR - 1).toFloat() * realOffset)
        }
        lastOffset = positionOffset
    }

    override fun onPageSelected(position: Int) {}

    override fun onPageScrollStateChanged(state: Int) {}
}
