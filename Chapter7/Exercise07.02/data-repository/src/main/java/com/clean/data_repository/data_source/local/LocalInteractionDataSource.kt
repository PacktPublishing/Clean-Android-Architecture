package com.clean.data_repository.data_source.local

import com.clean.domain.entity.Interaction
import kotlinx.coroutines.flow.Flow

interface LocalInteractionDataSource {

    fun getInteraction(): Flow<Interaction>

    suspend fun saveInteraction(interaction: Interaction)
}