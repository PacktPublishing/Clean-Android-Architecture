package com.clean.data_repository.repository

import com.clean.data_repository.data_source.local.LocalInteractionDataSource
import com.clean.domain.entity.Interaction
import com.clean.domain.repository.InteractionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InteractionRepositoryImpl @Inject constructor(
    private val interactionDataSource: LocalInteractionDataSource
) : InteractionRepository {

    override fun getInteraction(): Flow<Interaction> = interactionDataSource.getInteraction()

    override fun saveInteraction(interaction: Interaction): Flow<Interaction> = flow {
        interactionDataSource.saveInteraction(interaction)
        this.emit(Unit)
    }.flatMapLatest {
        getInteraction()
    }
}