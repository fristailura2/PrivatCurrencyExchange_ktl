package com.example.ura.myapplication.presentation.base.bank


import android.app.DatePickerDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.ura.myapplication.R
import com.example.ura.myapplication.data.exchange.db.models.ExchangeRateSave
import com.example.ura.myapplication.presentation.base.BaseFragment
import java.util.*

abstract class BankFragment<T : BankPresenter<*>> : BaseFragment<T>(), BankView {
    @BindView(R.id.layout_nbu_item_date_textView)
    lateinit var dateTextView: TextView
    @BindView(R.id.layout_nbu_item_bank_name_textView)
    lateinit var bankNameTextView: TextView

    protected open lateinit var adapter: BankAdapter<*>
    protected lateinit var recyclerView: RecyclerView
    @get:LayoutRes
    abstract val fragmentLayout: Int
    @get:IdRes
    abstract val recyclerId: Int
    @get:StringRes
    abstract val titleId: Int

    abstract fun  newAdapter(): BankAdapter<*>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(fragmentLayout, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bankNameTextView.setText(titleId)
        initRecycler(view)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val date = dateTextView.text.toString()
        outState.putString(DATE_KEY, date)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null && savedInstanceState.containsKey(DATE_KEY))
            presenter.onOrientationChanged(savedInstanceState.getString(DATE_KEY)!!)
    }

    private fun initRecycler(view: View) {
        recyclerView = view.findViewById(recyclerId)
        adapter = newAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }


    @OnClick(R.id.layout_nbu_item_date_picker_button_appCompatButton)
    fun onPickDateButtonClick() {
        presenter.onPickDateButtonClick()
    }

    override fun showDataPickerDialog() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            activity!!,
            { _, year, monthOfYear, dayOfMonth -> presenter.onDataPicked(year, monthOfYear, dayOfMonth) },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
            .show()
    }

    override fun showDate(date: String) {
        dateTextView.text = date
    }

    abstract inner class BankAdapter<V :BankFragment<T>.BankAdapter<V>.BankViewHolder> : RecyclerView.Adapter<V>() {
        var exchangeRateSaves: MutableList<ExchangeRateSave> = mutableListOf()
            set(exchangeRateSaves) {
                this.exchangeRateSaves.clear()
                this.exchangeRateSaves.addAll(exchangeRateSaves)
                notifyDataSetChanged()
            }

        protected var selectedExchangeRateSave: ExchangeRateSave? =null
        @get:LayoutRes
        protected abstract val itemLayout: Int

        fun selectAndHighlight(currency: String) {
            val exchangeRateSave= exchangeRateSaves
                .filter { it.currency==currency }
                .first()

            if (exchangeRateSave == selectedExchangeRateSave)
                return

            var itemIndex = exchangeRateSaves.indexOf(selectedExchangeRateSave)
            this.selectedExchangeRateSave = exchangeRateSave

            if (itemIndex != -1)
                notifyItemChanged(itemIndex)

            itemIndex = exchangeRateSaves.indexOf(selectedExchangeRateSave!!)
            notifyItemChanged(itemIndex)
        }

        fun getItemIndex(currency: String): Int {
            val exchangeRateSave = exchangeRateSaves.filter { it.currency==currency }.first()
            return Math.min(exchangeRateSaves.indexOf(exchangeRateSave) + 1, exchangeRateSaves.size - 1)
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): V {
            val itemView = LayoutInflater.from(context).inflate(itemLayout, viewGroup, false)
            return newHolder(itemView)
        }

        protected abstract fun newHolder(itemView: View): V


        override fun onBindViewHolder(bankViewHolder: V, i: Int) {
            bankViewHolder.bind(exchangeRateSaves[i], i)
        }

        override fun getItemCount(): Int {
            return exchangeRateSaves.size
        }

        abstract inner class BankViewHolder(protected var root: View) : RecyclerView.ViewHolder(root) {

            protected lateinit var model: ExchangeRateSave

            init {
                ButterKnife.bind(this, root)
            }

            fun bind(exchangeRate: ExchangeRateSave, index: Int) {
                this.model = exchangeRate

                bindPrivate(exchangeRate, index)

                if (model == selectedExchangeRateSave)
                    root.background = ColorDrawable(context!!.resources.getColor(R.color.colorPrimaryDark))
            }

            abstract fun bindPrivate(exchangeRate: ExchangeRateSave, index: Int)
        }
    }

    companion object {
        private const val DATE_KEY = "date_key"
    }
}
