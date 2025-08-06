package ahm.parts.ordering.ui.widget.activity.prospect.detail

import ahm.parts.ordering.R
import ahm.parts.ordering.data.model.home.FollowUpDetailProspect
import ahm.parts.ordering.data.model.home.StepDetailProspect
import android.graphics.Color
import ahm.parts.ordering.ui.base.BaseActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail_prospect.*
import java.util.ArrayList

class DetailProspectActivity : BaseActivity<DetailProspectViewModel>() {


    private lateinit var stepAdapter: StepAdapter

    private var stepDetailProspect = ArrayList<StepDetailProspect>()
    private var followUpDetailProspect = ArrayList<FollowUpDetailProspect>()

    val listStep = arrayOf(
        "PROSPEK",
        "SPK",
        "SALES"
    )
    val listFollowUp = arrayOf(
        "Follow Up 3 . Bertemu"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_prospect)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        setSupportActionBar(toolbar_detail_prospect)

        viewModel = ViewModelProviders.of(this).get(DetailProspectViewModel::class.java)
        // TODO: Use the ViewModel

        initRecycelrViewStep()
        initRecycelrViewFollowUp()

    }

    private fun initRecycelrViewStep(){
//        recycler_step.setHasFixedSize(true)
        recycler_step.layoutManager = LinearLayoutManager(this)

        for (i in 0 until listStep.size){

            stepDetailProspect.add(StepDetailProspect(listStep.get(i)))

            if(listStep.size - 1 == i){
                val adapter = StepAdapter(stepDetailProspect)
                adapter.notifyDataSetChanged()

                //tampilkan data dalam recycler view
                recycler_step.adapter = adapter
            }

        }
    }

    private fun initRecycelrViewFollowUp(){
        recycler_follw_up.setHasFixedSize(true)
        recycler_follw_up.layoutManager = LinearLayoutManager(this)

        for (i in 0 until listFollowUp.size){

            followUpDetailProspect.add(FollowUpDetailProspect(listFollowUp.get(i)))

            if(listFollowUp.size - 1 == i){
                val adapter1 = FollowUpAdapter(followUpDetailProspect)
                adapter1.notifyDataSetChanged()

                //tampilkan data dalam recycler view
                recycler_follw_up.adapter = adapter1
            }

        }
    }

}
