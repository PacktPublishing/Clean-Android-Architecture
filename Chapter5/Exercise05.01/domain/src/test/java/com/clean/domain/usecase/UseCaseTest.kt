package com.clean.domain.usecase

import com.clean.domain.entity.Result
import com.clean.domain.entity.UseCaseException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class UseCaseTest {

    @ExperimentalCoroutinesApi
    private val configuration = UseCase.Configuration(TestCoroutineDispatcher())
    private val request = mock<UseCase.Request>()
    private val response = mock<UseCase.Response>()

    @ExperimentalCoroutinesApi
    private lateinit var useCase: UseCase<UseCase.Request, UseCase.Response>

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        useCase = object : UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun process(request: Request): Flow<Response> {
                assertEquals(this@UseCaseTest.request, request)
                return flowOf(response)
            }

        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testExecuteSuccess() = runBlockingTest {
        val result = useCase.execute(request).first()
        assertEquals(Result.Success(response), result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testExecuteUserException() {
        useCase = object : UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun process(request: Request): Flow<Response> {
                assertEquals(this@UseCaseTest.request, request)
                return flow {
                    throw UseCaseException.UserException(Throwable())
                }
            }

        }
        runBlockingTest {
            val result = useCase.execute(request).first()
            assertTrue((result as Result.Error).exception is UseCaseException.UserException)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testExecutePostException() {
        useCase = object : UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun process(request: Request): Flow<Response> {
                assertEquals(this@UseCaseTest.request, request)
                return flow {
                    throw UseCaseException.PostException(Throwable())
                }
            }

        }
        runBlockingTest {
            val result = useCase.execute(request).first()
            assertTrue((result as Result.Error).exception is UseCaseException.PostException)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testExecuteUnknownException() {
        useCase = object : UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun process(request: Request): Flow<Response> {
                assertEquals(this@UseCaseTest.request, request)
                return flow {
                    throw Throwable()
                }
            }

        }
        runBlockingTest {
            val result = useCase.execute(request).first()
            assertTrue((result as Result.Error).exception is UseCaseException.UnknownException)
        }
    }
}