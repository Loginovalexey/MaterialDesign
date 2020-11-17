package ru.alapplications.matdesign.kursproject.mainScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import kotlinx.android.synthetic.main.fragment_tabs.*
import ru.alapplications.matdesign.kursproject.R


class TabsFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() =
            TabsFragment()
    }

    private lateinit var contentViewModel: ContentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tabs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        observeCategoryItems()
    }

    private fun observeCategoryItems() {
        contentViewModel =
            ViewModelProvider(requireActivity()).get(ContentViewModel::class.java)
        contentViewModel.categoryItems.observe(viewLifecycleOwner, Observer {
            viewPager2.adapter = ViewPager2Adapter(it)
            TabLayoutMediator(
                tabs, viewPager2
            ) { tab: TabLayout.Tab, position: Int ->
                tab.text = it[position].title
            }.attach()
        })
    }
}


