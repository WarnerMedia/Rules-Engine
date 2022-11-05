package com.warnermedia.rulesengine.jsonadapter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.warnermedia.rulesengine.core.Engine
import java.io.File

class Adapter {
    companion object {
        private val mapper = jacksonObjectMapper()

        @JvmStatic
        fun readFromFile(path: String): Engine {
            val engineStringRepresentation = File(path).readText(Charsets.UTF_8)
            return readFromString(engineStringRepresentation)
        }

        @JvmStatic
        fun saveToFile(engine: Engine, path: String) {
            File(path).writeText(engineToString(engine), Charsets.UTF_8)
        }

        private fun engineToString(engine: Engine): String {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(engine)
        }

        private fun readFromString(engineStringRepresentation: String): Engine {
            return mapper.readValue(engineStringRepresentation, Engine::class.java)
        }
    }
}
