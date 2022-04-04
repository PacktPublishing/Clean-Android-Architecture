package com.clean.presentation_user.single

import android.content.Context
import com.clean.domain.entity.User
import com.clean.domain.usecase.GetUserUseCase
import com.clean.presentation_user.R
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class UserConverterTest {

    private val context = mock<Context>()
    private val converter = UserConverter(context)

    @Test
    fun testConvertSuccess() {
        val response = GetUserUseCase.Response(
            user = User(
                id = 1L,
                name = "name",
                username = "username",
                email = "email"
            )
        )
        val formattedName = "formattedName"
        val formattedUsername = "formattedUsername"
        val formattedEmail = "formattedEmail"
        whenever(context.getString(R.string.name, "name")).thenReturn(formattedName)
        whenever(context.getString(R.string.username, "username")).thenReturn(formattedUsername)
        whenever(context.getString(R.string.email, "email")).thenReturn(formattedEmail)
        val result = converter.convertSuccess(response)
        Assert.assertEquals(UserModel(formattedName, formattedUsername, formattedEmail), result)
    }
}