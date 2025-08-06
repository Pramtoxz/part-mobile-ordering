package ahm.parts.ordering

import ahm.parts.ordering.R
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import android.os.Bundle
import android.view.View

class MainActivity : BaseActivity<HomeViewModel>(), View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Langkah 2: Menggunakan binding untuk menampilkan layout
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initListener()
    }

    private fun initUI(){
        // Langkah 3 (Contoh): Mengatur UI awal
        binding.tvTitle.text = "Halaman Utama"
    }

    private fun initListener(){
        // Langkah 3 (Contoh): Mendaftarkan listener
        binding.btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!){

        }
    }

}
