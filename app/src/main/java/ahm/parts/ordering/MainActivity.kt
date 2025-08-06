package ahm.parts.ordering

import ahm.parts.ordering.databinding.ActivityHomeBinding // Pastikan import ini ada
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import android.os.Bundle
import android.view.View

class MainActivity : BaseActivity<HomeViewModel>() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    private fun initUI(){
    }

    private fun initListener(){
    }
}