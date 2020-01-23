package com.example.genericadapter

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity(), GenericAdapter.AppAdapterListener<Movie> {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var adapter: GenericAdapter<Movie>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupAdapter(getMoviesList())
    }

    private fun setupRecyclerView() {
        val dividerItemDecoration = DividerItemDecoration(
            this,
            RecyclerView.VERTICAL
        )
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView = recycler_view
        recyclerView.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }

    private fun setupAdapter(movies: List<Movie>) {
        adapter = GenericAdapter<Movie>(movies)
        adapter?.listener = this
        recyclerView.adapter = adapter
    }

    override fun onItemClick(model: Movie, position: Int) {
        //Do want you want with the object
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        setupSearch(menu)

        return true
    }

    private fun setupSearch(menu: Menu?) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.app_bar_search)
            ?.actionView as SearchView
        searchView.setSearchableInfo(searchManager
            .getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter?.filter?.filter(query)
                return false
            }
            override fun onQueryTextChange(query: String): Boolean {
                adapter?.filter?.filter(query)
                return false
            }
        })
    }

    private fun getMoviesList(): MutableList<Movie> {
        val list = mutableListOf<Movie>()
        list.add(Movie("Thor: Ragnarok ", 2017, "Taika Waititi", "Marvel Studios"))
        list.add(Movie("Mulan", 1998, "Tony Bancroft, Barry Cook", "Disney"))
        list.add(Movie("Aquaman", 2018, "James Wan", "DC Entertainment"))
        list.add(Movie("Jurassic World: Fallen Kingdom", 2018, " J.A. Bayona", "Universal Studios"))
        list.add(Movie("Superman", 1978, "Richard Donner", "Dovemead Films"))
        list.add(Movie("The Mummy ", 1999, "Stephen Sommers", "Universal Pictures"))
        list.add(Movie("Blade II", 2002, "Guillermo del Toro", "New Line Cinema"))
        list.add(Movie("Rosemary's Baby", 1968, "Roman Polanski", "William Castle Productions"))

        return list
    }
}