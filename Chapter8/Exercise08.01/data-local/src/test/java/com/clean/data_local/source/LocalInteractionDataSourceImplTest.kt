package com.clean.data_local.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.clean.domain.entity.Interaction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LocalInteractionDataSourceImplTest {

    private val dataStore = mock<DataStore<Preferences>>()
    private val interactionDataSource = LocalInteractionDataSourceImpl(dataStore)

    @ExperimentalCoroutinesApi
    @Test
    fun testGetInteraction() = runBlockingTest {
        val clicks = 10
        val interaction = Interaction(clicks)
        val preferences = mock<Preferences>()
        whenever(preferences[KEY_TOTAL_TAPS]).thenReturn(clicks)
        whenever(dataStore.data).thenReturn(flowOf(preferences))
        val result = interactionDataSource.getInteraction().first()
        assertEquals(interaction, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSaveInteraction() = runBlockingTest {
        val clicks = 10
        val interaction = Interaction(clicks)
        val preferences = mock<MutablePreferences>()
        whenever(preferences.toMutablePreferences()).thenReturn(preferences)
        whenever(dataStore.updateData(any())).thenAnswer {
            runBlocking {
                it.getArgument<suspend (Preferences) -> Preferences>(0).invoke(preferences)
            }
            preferences
        }
        interactionDataSource.saveInteraction(interaction)
        verify(preferences)[KEY_TOTAL_TAPS] = clicks
    }
}