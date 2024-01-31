package com.apollo.timeflow.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainViewModelTest {
    private val scope = CoroutineScope(Dispatchers.Unconfined)

    @Before
    fun init() {
    }

    @After
    fun clear() {
//        unmockkAll()
    }

    @Test
    fun editTimeFormat() = runTest {

    }

    @Test
    fun updateTime() = runTest {

    }

    fun getCurrentDate() {
    }

    @Test
    fun updateDate() {
    }

    @Test
    fun isDateShowDataStoreFlow() {
    }

    @Test
    fun isDateShow() {
    }

    @Test
    fun getTimeFormatRecordDataStoreFlow() {
    }

    @Test
    fun timeFormatRecordUpdate() {
    }
}