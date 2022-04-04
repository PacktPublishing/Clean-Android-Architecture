package com.clean.app.repository

import com.clean.app.idling.ComposeCountingIdlingResource
import com.clean.app.idling.attachIdling
import com.clean.domain.entity.Interaction
import com.clean.domain.repository.InteractionRepository
import kotlinx.coroutines.flow.Flow

class IdlingInteractionRepository(
    private val interactionRepository: InteractionRepository,
    private val countingIdlingResource: ComposeCountingIdlingResource
) : InteractionRepository {

    override fun getInteraction(): Flow<Interaction> {
        return interactionRepository.getInteraction()
            .attachIdling(countingIdlingResource)
    }

    override fun saveInteraction(interaction: Interaction): Flow<Interaction> {
        return interactionRepository.saveInteraction(interaction)
            .attachIdling(countingIdlingResource)
    }
}