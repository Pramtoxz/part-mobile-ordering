package ahm.parts.ordering.ui.widget.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import java.util.*

class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
    private val mFragmentList: MutableList<Fragment>
    private val mFragmentTitleList: MutableList<String>
    private var currentPosition = -1

    init {
        mFragmentList = ArrayList()
        mFragmentTitleList = ArrayList()
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun clear(manager: FragmentManager) {
        val transaction = manager.beginTransaction()
        for (fragment in mFragmentList) {
            transaction.remove(fragment)
        }
        mFragmentList.clear()
    }


    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getItemPosition(`object`: Any): Int {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return PagerAdapter.POSITION_NONE
    }

    override fun getPageTitle(position: Int): String? {
        return mFragmentTitleList[position]
    }


}
