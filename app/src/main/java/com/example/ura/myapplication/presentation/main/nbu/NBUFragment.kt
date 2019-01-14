package com.example.ura.myapplication.presentation.main.nbu

import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.example.ura.myapplication.R
import com.example.ura.myapplication.data.exchange.db.models.ExchangeRateSave
import com.example.ura.myapplication.presentation.base.bank.BankFragment
import com.example.ura.myapplication.presentation.main.BankItemClickObserverReceiver
import com.example.ura.myapplication.utils.CurrencyCodeUtils
import io.reactivex.Observable
import javax.inject.Inject

class NBUFragment : BankFragment<NBUPresenter>(), NBUView, BankItemClickObserverReceiver {
    override val fragmentLayout: Int
        get() = R.layout.fragment_nbu

    override fun newAdapter(): NBUAdapter {
        return NBUAdapter()
    }

    override lateinit var adapter: BankAdapter<*>

    override val recyclerId: Int
        get() = R.id.fragment_nbu_recycler

    override val titleId: Int
        get() = R.string.nbu

    override lateinit var presenter: NBUPresenter
        @Inject
        set


    override fun scrollTo(exchangeRateSave: ExchangeRateSave) {
        (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
            adapter.getItemIndex(
                exchangeRateSave.currency!!
            ), recyclerView.measuredHeight / 2
        )
        adapter.selectAndHighlight(exchangeRateSave.currency!!)
    }

    override fun onObservableReceive(observable: Observable<ExchangeRateSave>) {
        presenter.onPrivateClickObservableReceived(observable)
    }


    override fun setExchangeRateSaves(exchangeRateSaves: MutableList<ExchangeRateSave>) {
        adapter.exchangeRateSaves=exchangeRateSaves;
    }

    inner class NBUAdapter : BankAdapter<NBUAdapter.NBUViewHolder>() {

        override val itemLayout: Int
            get() = R.layout.layout_nbu_item

        override fun newHolder(itemView: View): NBUViewHolder {
            return NBUViewHolder(itemView)
        }

        inner class NBUViewHolder(itemView: View) : BankFragment<NBUPresenter>.BankAdapter<NBUAdapter.NBUViewHolder>.BankViewHolder(itemView) {
            @BindView(R.id.layout_nbu_item_currency_textView)
            lateinit var currencyTextView: TextView
            @BindView(R.id.layout_nbu_item_price_base_textView)
            lateinit var saleRateTextView: TextView
            @BindView(R.id.layout_nbu_item_price_textView)
            lateinit var saleRatePefTextView: TextView

            override fun bindPrivate(exchangeRate: ExchangeRateSave, index: Int) {
                val resources = resources
                if (index % 2 == 0)
                    root.background = ColorDrawable(resources.getColor(R.color.colorPrimary))
                else
                    root.background = ColorDrawable(resources.getColor(R.color.default_background))

                var baseCurrencyVal = exchangeRate.purchaseRateNB
                var otherCurrencyVal = 1.0

                if (baseCurrencyVal == 0.0)
                    baseCurrencyVal = 1.0
                while (baseCurrencyVal < 1) {
                    baseCurrencyVal *= 10.0
                    otherCurrencyVal *= 10.0
                }

                saleRatePefTextView.text = String.format(FLOAT_FORMAT, otherCurrencyVal, exchangeRate.currency)
                saleRateTextView.text = String.format(FLOAT_FORMAT, baseCurrencyVal, exchangeRate.baseCurrency)

                val currencyName = CurrencyCodeUtils.getNameByCode(getResources(), exchangeRate.currency!!)
                currencyTextView.text = currencyName ?: exchangeRate.currency
            }



        }

    }
    companion object {
        private const val FLOAT_FORMAT = "%.2f%s"
    }
}
