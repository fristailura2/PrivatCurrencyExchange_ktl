package com.example.ura.myapplication.presentation.main


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import butterknife.BindView
import com.example.ura.myapplication.R
import com.example.ura.myapplication.data.exchange.db.models.ExchangeRateSave
import com.example.ura.myapplication.presentation.base.BaseActivity
import com.example.ura.myapplication.presentation.graph.GraphActivity
import io.reactivex.Observable
import javax.inject.Inject

class MainActivity : BaseActivity<MainPresenter>(), MainView, BankItemClickObserverReceiver {
    override val getLayout: Int
        get() = R.layout.activity_main

    @BindView(R.id.activity_main_root_linearLayout)
    lateinit var linearLayout: LinearLayout

    override lateinit var presenter: MainPresenter
        @Inject
        set

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        linearLayout.orientation = 2 - resources.configuration.orientation
    }

    override fun openGraphActivity() {
        val intent = Intent()
        intent.setClass(applicationContext, GraphActivity::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_action_graph)
            presenter.onMenuGraphButtonClick()

        return super.onOptionsItemSelected(item)
    }


    override fun onObservableReceive(observable: Observable<ExchangeRateSave>) {
        (supportFragmentManager
            .findFragmentById(R.id.activity_main_fragment_nbu) as BankItemClickObserverReceiver)
            .onObservableReceive(observable)
    }
}
