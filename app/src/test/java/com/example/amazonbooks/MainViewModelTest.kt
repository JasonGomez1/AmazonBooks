package com.example.amazonbooks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.amazonbooks.data.local.repo.BookRepo
import com.example.amazonbooks.data.remote.BookDataImpl
import com.example.amazonbooks.ui.MainViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var mockBookRepo: BookRepo

    @MockK
    lateinit var mockObserver: Observer<List<BookDataImpl>>
    lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        Dispatchers.setMain(Dispatchers.Unconfined)
        mainViewModel = MainViewModel(mockBookRepo)
    }

    @Test
    fun `emits books when books livedata is observed`() {
        every { mockBookRepo.getBooks() } returns flowOf(listOf(mockk()))
        mainViewModel.books.observeForever(mockObserver)
        verify { mockBookRepo.getBooks() }
        verify { mockObserver.onChanged(ofType()) }
        mainViewModel.books.removeObserver(mockObserver)
    }

    @Test
    fun `emits every book from getBooks()`() {
        val slot = slot<List<BookDataImpl>>()
        val bookOne = BookDataImpl(
            title = "Enlightenment Now",
            author = "Steven Pinker",
            imageURL = "",
            id = 0
        )
        val bookTwo = BookDataImpl(
            title = "The better angels of our nature",
            author = "Steven Pinker",
            imageURL = "",
            id = 1
        )
        every { mockBookRepo.getBooks() } returns flowOf(listOf(bookOne, bookTwo))
        mainViewModel.books.observeForever(mockObserver)
        verifyAll {
            mockObserver.onChanged(capture(slot))
            mockBookRepo.getBooks()
        }
        assertThat(slot.captured).containsExactly(bookOne, bookTwo)
        mainViewModel.books.removeObserver(mockObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
