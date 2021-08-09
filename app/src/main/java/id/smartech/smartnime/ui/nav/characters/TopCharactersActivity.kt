package id.smartech.smartnime.ui.nav.characters

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.smartech.smartnime.R
import id.smartech.smartnime.adapter.TopPeopleAdapter
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivityTopCharactersBinding
import id.smartech.smartnime.ui.SidebarActivity
import id.smartech.smartnime.ui.nav.people.model.TopPeopleModel

class TopCharactersActivity : BaseActivity<ActivityTopCharactersBinding>() {
    private lateinit var viewModel: TopCharactersViewModel
    private lateinit var adapter: TopPeopleAdapter
    private lateinit var layoutManager: GridLayoutManager
    private var list = ArrayList<TopPeopleModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_top_characters
        super.onCreate(savedInstanceState)

        setViewModel()
        subscribeLiveData()
        setRecyclerView()
        setOnClick()
    }

    private fun setOnClick() {
        bind.back.setOnClickListener {
            intents<SidebarActivity>(this)
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(TopCharactersViewModel::class.java)
        viewModel.setService(createApi(this))
        viewModel.getTopCharacters()
    }

    private fun subscribeLiveData() {
        this.let {
            viewModel.isLoadingLiveData.observe(it) { isLoading ->
                if(isLoading) {
                    bind.progressBar.visibility = View.VISIBLE
                    bind.rvTopCharacters.visibility = View.GONE
                } else {
                    bind.progressBar.visibility = View.GONE
                    bind.rvTopCharacters.visibility = View.VISIBLE
                }
            }
        }

        this.let {
            viewModel.onSuccessLiveData.observe(it) { list ->
                adapter.addList(list)
            }
        }

        this.let {
            viewModel.onFailLiveData.observe(it) { message ->
                noticeToast(message)
            }
        }
    }

    private fun setRecyclerView() {
        bind.rvTopCharacters.isNestedScrollingEnabled = false
        layoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
        bind.rvTopCharacters.layoutManager = layoutManager
        adapter = TopPeopleAdapter(list)
        bind.rvTopCharacters.adapter = adapter
    }
}