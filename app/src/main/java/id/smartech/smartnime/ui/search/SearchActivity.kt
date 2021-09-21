package id.smartech.smartnime.ui.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.smartech.smartnime.R
import id.smartech.smartnime.adapter.SearchAdapter
import id.smartech.smartnime.base.BaseActivity
import id.smartech.smartnime.databinding.ActivitySearchBinding
import id.smartech.smartnime.dialog.FilterSearchFragment
import id.smartech.smartnime.ui.detail.anime.DetailAnimeActivity
import id.smartech.smartnime.ui.detail.character.DetailCharacterActivity
import id.smartech.smartnime.ui.detail.manga.DetailMangaActivity
import id.smartech.smartnime.ui.search.model.SearchModel

class SearchActivity : BaseActivity<ActivitySearchBinding>(), FilterSearchFragment.OnInputListener {
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter
    private lateinit var layoutManager: GridLayoutManager
    private var list = ArrayList<SearchModel>()
    private var searchkey: String? = ""
    private var filter: String = "anime"

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_search
        super.onCreate(savedInstanceState)

        searchkey = intent.getStringExtra("key")

        setViewModel(searchkey!!)
        setData(searchkey!!)
        subscribeLiveData()
        setRecyclerView()
        setSearchView(filter, searchkey!!)
        setOnClick()
    }

    private fun setSearchView(filter: String, key: String) {
        bind.searchButton.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchkey = query
                viewModel.getSearch(filter, searchkey!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun subscribeLiveData() {
        this.let {
            viewModel.isLoadingLiveData.observe(it) { isLoading ->
                if(isLoading) {
                    bind.progressBar.visibility = View.VISIBLE
                    bind.rvSearch.visibility = View.GONE
                    bind.lottieNotFound.visibility = View.GONE
                } else {
                    bind.progressBar.visibility = View.GONE
                    bind.rvSearch.visibility = View.VISIBLE
                }
            }
        }

        this.let {
            viewModel.onSuccessLiveData.observe(it) { list ->
                adapter.addList(list)
            }
        }

        this.let {
            viewModel.onFailLiveData.observe(it) { isFail ->
                if(isFail) {
                    bind.lottieNotFound.visibility = View.VISIBLE
                    bind.rvSearch.visibility = View.GONE
                } else {
                    bind.lottieNotFound.visibility = View.GONE
                    bind.rvSearch.visibility = View.VISIBLE
                }

            }
        }
    }

    private fun setViewModel(key: String) {
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.setService(createApi(this))
        viewModel.getSearch(filter, key)

    }


    private fun setData(key: String) {
        bind.query.text = "Search $key in $filter filter"
    }

    private fun setRecyclerView() {
        bind.rvSearch.isNestedScrollingEnabled = false
        layoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
        bind.rvSearch.layoutManager = layoutManager
        adapter = SearchAdapter(list)
        bind.rvSearch.adapter = adapter
        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback{
            override fun onClickItem(data: SearchModel) {
                when(filter) {
                    "anime" -> {
                        val intent = Intent(this@SearchActivity, DetailAnimeActivity::class.java)
                        intent.putExtra("id", data.malId)
                        startActivity(intent)
                    }
                    "manga" -> {
                        val intent = Intent(this@SearchActivity, DetailMangaActivity::class.java)
                        intent.putExtra("id", data.malId)
                        startActivity(intent)
                    }
                    "person" -> {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
                        startActivity(browserIntent)
                    }
                    "character" -> {
                        val intent = Intent(this@SearchActivity, DetailCharacterActivity::class.java)
                        intent.putExtra("id", data.malId)
                        startActivity(intent)
                    }
                    else -> {
                        noticeToast("anime")
                    }
                }
            }
        })
    }

    private fun setOnClick() {
        bind.filter.setOnClickListener {
            val dialogFilter = FilterSearchFragment()
            dialogFilter.show(supportFragmentManager, "filter")
        }
    }

    override fun sendInput(input: String?) {
        bind.query.text = "Search $searchkey in $input filter"
        filter = input!!
        viewModel.getSearch(filter = filter, query = searchkey!!)
    }

}