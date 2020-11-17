package ru.alapplications.matdesign.kursproject.mainScreen

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import ru.alapplications.matdesign.kursproject.R


class RecyclerViewFragment : Fragment()
{


    companion object
    {
        @JvmStatic
        fun newInstance() =
            RecyclerViewFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        observeCategoryItems()
    }

    private fun observeCategoryItems()
    {
        val contentViewModel =
            ViewModelProvider(requireActivity()).get(ContentViewModel::class.java)
        contentViewModel.categoryItems.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = RecyclerViewAdapter(it)
        })
    }

    private fun initRecyclerView()
    {
        recyclerView.setHasFixedSize(true)
        val spanCount =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 3
            else 4
        recyclerView.layoutManager = GridLayoutManager(activity, spanCount)
    }

}