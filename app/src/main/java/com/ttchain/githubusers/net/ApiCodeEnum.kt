package com.ttchain.githubusers.net

import com.google.gson.TypeAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException

@JsonAdapter(ApiCodeEnum.Adapter::class)
enum class ApiCodeEnum(val value: Int) {

    NUMBER_1(1), //成功
    NUMBER_4008(4008), //錯誤的Hash
    NUMBER_9001(9001), //帳號不存在
    NUMBER_MINUS_1(-1); //未定義的錯誤

    class Adapter : TypeAdapter<ApiCodeEnum>() {
        @Throws(IOException::class)
        override fun write(
            jsonWriter: JsonWriter,
            enumeration: ApiCodeEnum
        ) {
            jsonWriter.value(enumeration.value)
        }

        @Throws(IOException::class)
        override fun read(jsonReader: JsonReader): ApiCodeEnum {
            val value = jsonReader.nextInt()
            return fromValue(value)
        }
    }

    companion object {
        fun fromValue(value: Int): ApiCodeEnum {
            return values().find {
                it.value == value
            } ?: NUMBER_MINUS_1
        }

        fun fromCode(apiCodeEnum: ApiCodeEnum): ApiCodeEnum? {
            return values().find {
                it == apiCodeEnum
            }
        }
    }
}