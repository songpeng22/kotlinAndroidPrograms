package com.example.xml_json

import com.fasterxml.jackson.annotation.JsonCreator
import kotlinx.serialization.Serializable

@Serializable
sealed class ResultOf(var ret:Boolean)
@Serializable
data class ResultSuccess(var msg: String = "success") : ResultOf(true)
@Serializable
data class ResultFailure(var msg: String = "failure") : ResultOf(false)