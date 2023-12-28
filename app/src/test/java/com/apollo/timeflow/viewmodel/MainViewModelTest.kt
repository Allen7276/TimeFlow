package com.apollo.timeflow.viewmodel

import app.cash.turbine.test
import com.apollo.timeflow.model.DataStoreModel
import com.apollo.timeflow.model.Model
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.CoroutineContext

class MainViewModelTest {
    private val scope = CoroutineScope(Dispatchers.Unconfined)

    private val dataStoreModel: DataStoreModel = mockk(relaxed = true)
    private val timeModel: Model = mockk(relaxed = true)
    private lateinit var mainViewModel: MainViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun init() {
        val testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        mockkStatic(Dispatchers::class)
        every { Dispatchers.IO } returns testDispatcher
        every { Dispatchers.IO.fold<Any>(any(), any()) } answers {
            testDispatcher.fold(
                firstArg(),
                secondArg()
            )
        }
        every { Dispatchers.Default } returns testDispatcher
        every {
            Dispatchers.Default.fold<Any>(
                any(),
                any()
            )
        } answers { testDispatcher.fold(firstArg(), secondArg()) }
        every { Dispatchers.Default.plus(any<CoroutineContext>()) } answers {
            testDispatcher.plus(
                firstArg<CoroutineContext>()
            )
        }

        mainViewModel = MainViewModel(
            dataStoreModel,
            timeModel,
        )
    }

    @After
    fun clear() {
        unmockkAll()
    }

    @Test
    fun editTimeFormat() = runTest {
        mainViewModel.editTimeFormat(MainViewModel.TimeFormat.Base12)
        mainViewModel.timeFormat
            .shareIn(
                CoroutineScope(Dispatchers.Unconfined),
                started = SharingStarted.WhileSubscribed()
            )
            .test {
                assert(MainViewModel.TimeFormat.Base12 == this.awaitItem())
            }
    }

    @Test
    fun updateTime() = runTest {
        every { timeModel.getCurrentTime(any()) } returns listOf(8, 45)
        mainViewModel.updateTime()
        mainViewModel.topLeftModelFlowOf.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.WhileSubscribed()
        ).test {
            assert(0 == this.awaitItem())
        }
        mainViewModel.topRightModelFlowOf.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.WhileSubscribed()
        ).test {
            assert(8 == this.awaitItem())
        }
        mainViewModel.bottomLeftModelFlowOf.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.WhileSubscribed()
        ).test {
            assert(4 == this.awaitItem())
        }
        mainViewModel.bottomRightModelFlowOf.shareIn(
            CoroutineScope(Dispatchers.Unconfined),
            started = SharingStarted.WhileSubscribed()
        ).test {
            assert(5 == this.awaitItem())
        }
    }

    @Test
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