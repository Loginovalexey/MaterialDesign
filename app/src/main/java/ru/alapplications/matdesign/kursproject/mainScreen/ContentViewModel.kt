package ru.alapplications.matdesign.kursproject.mainScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.alapplications.matdesign.kursproject.R
import ru.alapplications.matdesign.kursproject.mainScreen.CollapsingState.*


class ContentViewModel(application: Application) : AndroidViewModel(application)
{
    val navigationType = MutableLiveData<Int>()
    val contentViewType = MutableLiveData<Int>()
    val categoryItems = MutableLiveData<List<Item>>()
    val categoryHeader = MutableLiveData<Header>()
    val isCollapsing = MutableLiveData<Boolean>()
    var collapsingState = MutableLiveData<CollapsingState>()
    var themeId = MutableLiveData<Int>()
    var category: Category? = null

    init
    {
        setCategory(R.id.fruits_item)
        setContentViewType(R.id.recyclerItem)
        setNavigationType(R.id.drawerItem)
        setCollapsingState(EXPANDED)
        setTheme(R.id.item_violet)
    }

    fun setCollapsingState(collapsingState: CollapsingState)
    {
        this.collapsingState.value = collapsingState
    }

    fun setContentViewType(selectedContentViewTypeId: Int)
    {
        if (this.contentViewType.value == null || this.contentViewType.value != selectedContentViewTypeId)
        {
            this.contentViewType.value = selectedContentViewTypeId
            when (selectedContentViewTypeId)
            {
                R.id.tabsItem -> setCollapsing(false)
                R.id.recyclerItem -> setCollapsing(true)
            }
        }
    }

    fun setNavigationType(selectedNavigationViewTypeId: Int)
    {
        if (this.navigationType.value == null || this.navigationType.value != selectedNavigationViewTypeId)
            this.navigationType.value = selectedNavigationViewTypeId
    }

    private fun setCollapsing(isCollapsing: Boolean)
    {
        this.isCollapsing.value = isCollapsing
        if (!isCollapsing) categoryHeader.value = category!!.header
    }

    fun setCategory(selectedCategoryTypeId: Int)
    {
        if (this.category == null || this.category!!.categoryType != selectedCategoryTypeId)
        {
            when(selectedCategoryTypeId)
            {
                R.id.fruits_item -> category = categoryFruits
                R.id.vegetables_item -> category = categoryVegetables
                R.id.nature_item -> category = categoryNature
            }
            categoryItems.value = category!!.items
            categoryHeader.value = category!!.header
        }
    }

    fun setTheme(themeId: Int)
    {
        this.themeId.value = themeId
    }
}