package id.smartech.smartnime.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.smartech.smartnime.R
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivitySearchBinding

class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    private var searchkey: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_search
        super.onCreate(savedInstanceState)

        setData()
    }

    private fun setData() {
        searchkey = intent!!.getStringExtra("key")
        noticeToast(searchkey.toString())
    }
}