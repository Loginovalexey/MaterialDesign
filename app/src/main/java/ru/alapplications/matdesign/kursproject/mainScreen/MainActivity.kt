package ru.alapplications.matdesign.kursproject.mainScreen

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_main.*
import ru.alapplications.matdesign.kursproject.R
import ru.alapplications.matdesign.kursproject.R.id.*
import ru.alapplications.matdesign.kursproject.mainScreen.CollapsingState.EXPANDED
import ru.alapplications.matdesign.kursproject.mainScreen.CollapsingState.NOT_EXPANDED


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var contentViewModel: ContentViewModel
    private lateinit var toggle: ActionBarDrawerToggle
    private var selectedNavigationType = 0
    private var selectedContentViewType = 0
    private var selectedTheme = 0


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        initViewModel()
        changeTheme()
    }

    private fun changeTheme()
    {
        contentViewModel.themeId.observe(this, Observer {
            selectedTheme = it
            if (it == item_violet) setTheme(R.style.VioletTheme)
            if (it == item_blue) setTheme(R.style.BlueTheme)
            if (it == item_green) setTheme(R.style.GreenTheme)
            contentViewModel.themeId.removeObservers(this)
            continueInit()
        })
    }

    private fun continueInit()
    {
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initAppBarLayout()
        initDrawerNavigation()
        initBottomNavigation()
        initViewModelObservers()
    }


    private fun initViewModel()
    {
        contentViewModel = ViewModelProvider(this).get(ContentViewModel::class.java)
    }

    private fun initViewModelObservers()
    {
        observeContentType()
        observeNavigationType()
        observeCollapsingState()
        observeCollapsing()
        observeCategoryHeader()
    }


    private fun observeCollapsingState()
    {
        contentViewModel.collapsingState.observe(this, Observer {
            if (it == EXPANDED)
                appBarLayout.setExpanded(true)
            else
                appBarLayout.setExpanded(false)
        })
    }

    private fun observeCategoryHeader()
    {
        contentViewModel.categoryHeader.observe(this, Observer
        {
            appBarImageView.setImageResource(it.imageId)
            toolbar_layout.title = it.title
            supportActionBar?.title = it.title
        })
    }

    private fun observeCollapsing()
    {
        contentViewModel.isCollapsing.observe(this, Observer {
            if (it)
                appBarImageView.visibility = VISIBLE
            else
                appBarImageView.visibility = GONE
            toolbar_layout.isTitleEnabled = it
        })
    }

    private fun observeNavigationType()
    {
        contentViewModel.navigationType.observe(this, Observer
        {
            if (it == drawerItem)
            {
                toggle.isDrawerIndicatorEnabled = true
                bottomNavigation.visibility = INVISIBLE
                selectedNavigationType = drawerItem

            } else
            {
                toggle.isDrawerIndicatorEnabled = false
                bottomNavigation.visibility = VISIBLE
                selectedNavigationType = bottomItem

            }
        })
    }


    private fun observeContentType()
    {
        contentViewModel.contentViewType.observe(this, Observer {
            fun changeFragment(fragment: Fragment)
            {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.contentFramelayout, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commitNow()
            }
            if (it == recyclerItem)
            {
                selectedContentViewType = recyclerItem
                changeFragment(RecyclerViewFragment.newInstance())
            } else if (it == tabsItem)
            {
                selectedContentViewType = tabsItem
                changeFragment(TabsFragment.newInstance())
            }
        }
        )
    }

    private fun initAppBarLayout()
    {
        appBarLayout.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0)
                contentViewModel.setCollapsingState(EXPANDED)
            else
                contentViewModel.setCollapsingState(NOT_EXPANDED)
        }
        )

    }

    private fun initBottomNavigation()
    {
        bottomNavigation.itemIconTintList = null
        bottomNavigation.setOnNavigationItemSelectedListener(this::onNavigationItemSelected)
    }


    private fun initDrawerNavigation()
    {
        val navView = findViewById<NavigationView>(drawerNavigation)
        navView.itemIconTintList = null
        navView.setNavigationItemSelectedListener(this)
        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean
    {
        menu?.findItem(selectedContentViewType)?.isChecked = true
        menu?.findItem(selectedNavigationType)?.isChecked = true
        menu?.findItem(selectedTheme)?.isChecked = true
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else super.onBackPressed()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean
    {
        contentViewModel.setCategory(item.itemId)
        drawerLayout.close()
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if(item.itemId == recyclerItem ||item.itemId == tabsItem)
            contentViewModel.setContentViewType(item.itemId)
        if(item.itemId == bottomItem ||item.itemId == drawerItem)
            contentViewModel.setNavigationType(item.itemId)
        if (item.itemId == item_violet ||item.itemId == item_blue ||item.itemId == item_green)
            setNewTheme(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setNewTheme(themeId: Int)
    {
        contentViewModel.setTheme(themeId)
        recreate()
    }
}