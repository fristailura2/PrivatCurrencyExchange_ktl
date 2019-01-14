package com.example.ura.myapplication.presentation.main.privat

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.ura.myapplication.R
import com.example.ura.myapplication.data.exchange.db.models.ExchangeRateSave
import com.example.ura.myapplication.presentation.base.bank.BankFragment
import com.example.ura.myapplication.presentation.main.BankItemClickObserverReceiver
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PrivatFragment() : BankFragment<PrivatePresenter>(), PrivateView {

    override fun setExchangeRateSaves(exchangeRateSaves: MutableList<ExchangeRateSave>) {
        adapter.exchangeRateSaves=exchangeRateSaves;
    }

    private val onClickSubject = PublishSubject.create<ExchangeRateSave>()

    override val clickObservable: Observable<ExchangeRateSave>
        get() = onClickSubject


    override lateinit var presenter: PrivatePresenter
        @Inject
        set

    override val fragmentLayout: Int
        get() = R.layout.fragment_privat

    override val recyclerId: Int
        get() = R.id.fragment_privat_recycler

    override val titleId: Int
        get() = R.string.privat

    override fun sendClickObservableUp() {
        val upActivity = activity
        if (upActivity is BankItemClickObserverReceiver)
            (upActivity as BankItemClickObserverReceiver).onObservableReceive(clickObservable)
    }

    override fun newAdapter(): BankAdapter<*> {
        return PrivateAdapter()
    }

    inner class PrivateAdapter : BankAdapter<PrivateAdapter.PrivateViewHolder>() {

        override val itemLayout: Int
            get() = R.layout.layout_private_item

        override fun newHolder(itemView: View): PrivateViewHolder {
            return PrivateViewHolder(itemView)
        }

        inner class PrivateViewHolder(itemView: View) : BankFragment<PrivatePresenter>.BankAdapter<PrivateAdapter.PrivateViewHolder>.BankViewHolder(itemView) {
            @BindView(R.id.textView9)
            @JvmField
            var currencyTextView: TextView? = null
            @BindView(R.id.textView10)
            @JvmField
            var saleRateTextView: TextView? = null
            @BindView(R.id.textView11)
            @JvmField
            var saleRatePefTextView: TextView? = null

            init {
                ButterKnife.bind(this, root)
            }

            override fun bindPrivate(exchangeRate: ExchangeRateSave, index: Int) {
                saleRatePefTextView!!.text = String.format(FLOAT_FORMAT, exchangeRate.purchaseRate)
                saleRateTextView!!.text = String.format(FLOAT_FORMAT, exchangeRate.saleRate)
                currencyTextView!!.text = exchangeRate.currency
                root.background = ColorDrawable(resources.getColor(R.color.default_background))

            }

            @OnClick(R.id.layout_private_item_root)
            fun onItemClick() {
                selectAndHighlight(model.currency!!)
                onClickSubject.onNext(model)
            }


        }
    }
    companion object {

        private const val FLOAT_FORMAT = "%.3f"
    }
}
