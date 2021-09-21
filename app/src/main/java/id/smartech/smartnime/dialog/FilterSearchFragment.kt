package id.smartech.smartnime.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import id.smartech.smartnime.R
import id.smartech.smartnime.base.BaseDialogFragment
import id.smartech.smartnime.databinding.FragmentFilterSearchBinding


class FilterSearchFragment : BaseDialogFragment<FragmentFilterSearchBinding>() {

    interface OnInputListener {
        fun sendInput(input: String?)
    }

    var mOnInputListener: OnInputListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_filter_search
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mOnInputListener = activity as OnInputListener?
        } catch (e: ClassCastException) {
            Log.e("error", "onAttach: ClassCastException: " + e.message)
        }
    }

    private fun onClick() {
        bind.btnSubmit.setOnClickListener {
            when {
                bind.anime.isChecked -> {
                    sharedPref.saveFilter("anime")
                }
                bind.manga.isChecked -> {
                    sharedPref.saveFilter("manga")
                }
                bind.person.isChecked -> {
                    sharedPref.saveFilter("person")
                }
                bind.character.isChecked -> {
                    sharedPref.saveFilter("character")
                }
                else -> {
                    sharedPref.saveFilter("anime")
                }
            }

            mOnInputListener!!.sendInput(sharedPref.getFilter())
            dismiss()
        }

        bind.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}