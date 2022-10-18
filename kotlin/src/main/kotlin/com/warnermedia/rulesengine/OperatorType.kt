package com.warnermedia.rulesengine

enum class OperatorType {
    EQUALS {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any
        ): ConditionResult {
            return when (operatorValue) {
                is Array<*> -> valueFromFacts.castToArray()?.let { it.contentEquals(operatorValue) }
                is Boolean -> valueFromFacts.castToBoolean()?.let { it == operatorValue }
                is Byte -> valueFromFacts.castToByte()?.let { it == operatorValue }
                is Char -> valueFromFacts.castToChar()?.let { it == operatorValue }
                is Int -> valueFromFacts.castToInt()?.let { it == operatorValue }
                is List<*> -> valueFromFacts.castToList()?.let { it == operatorValue }
                is Long -> valueFromFacts.castToLong()?.let { it == operatorValue }
                is Map<*, *> -> valueFromFacts.castToMap()?.let { it == operatorValue }
                is Short -> valueFromFacts.castToShort()?.let { it == operatorValue }
                is String -> valueFromFacts.castToString()?.let { it == operatorValue }
                else -> null
            }.getConditionResult()
        }
    },
    GREATER_THAN {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any
        ): ConditionResult {
            return when (operatorValue) {
                is Byte -> valueFromFacts.castToByte()?.let { it > operatorValue }
                is Char -> valueFromFacts.castToChar()?.let { it > operatorValue }
                is Int -> valueFromFacts.castToInt()?.let { it > operatorValue }
                is Long -> valueFromFacts.castToLong()?.let { it > operatorValue }
                is Short -> valueFromFacts.castToShort()?.let { it > operatorValue }
                is String -> valueFromFacts.castToString()?.let { it > operatorValue }
                else -> null
            }.getConditionResult()
        }
    },
    NOT_EQUALS {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any
        ): ConditionResult {
            return when (operatorValue) {
                is Array<*> -> valueFromFacts.castToArray()?.let { !it.contentEquals(operatorValue) }
                is Boolean -> valueFromFacts.castToBoolean()?.let { it != operatorValue }
                is Byte -> valueFromFacts.castToByte()?.let { it != operatorValue }
                is Char -> valueFromFacts.castToChar()?.let { it != operatorValue }
                is Int -> valueFromFacts.castToInt()?.let { it != operatorValue }
                is List<*> -> valueFromFacts.castToList()?.let { it != operatorValue }
                is Long -> valueFromFacts.castToLong()?.let { it != operatorValue }
                is Map<*, *> -> valueFromFacts.castToMap()?.let { it != operatorValue }
                is Short -> valueFromFacts.castToShort()?.let { it != operatorValue }
                is String -> valueFromFacts.castToString()?.let { it != operatorValue }
                else -> null
            }.getConditionResult()
        }
    },
    LESS_THAN {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any
        ): ConditionResult {
            return when (operatorValue) {
                is Byte -> valueFromFacts.castToByte()?.let { it < operatorValue }
                is Char -> valueFromFacts.castToChar()?.let { it < operatorValue }
                is Int -> valueFromFacts.castToInt()?.let { it < operatorValue }
                is Long -> valueFromFacts.castToLong()?.let { it < operatorValue }
                is Short -> valueFromFacts.castToShort()?.let { it < operatorValue }
                is String -> valueFromFacts.castToString()?.let { it < operatorValue }
                else -> null
            }.getConditionResult()
        }
    };

    private val badCastErrorMessage = "runtime cast error"

    abstract fun evaluate(operatorValue: Any, valueFromFacts: Any): ConditionResult

    fun Any.castToArray(): Array<*>? {
        return this as? Array<*>
    }

    fun Any.castToBoolean(): Boolean? {
        return this as? Boolean
    }

    fun Any.castToByte(): Byte? {
        return this as? Byte
    }

    fun Any.castToChar(): Char? {
        return this as? Char
    }

    fun Any.castToInt(): Int? {
        return this as? Int
    }

    fun Any.castToList(): List<*>? {
        return this as? List<*>
    }

    fun Any.castToLong(): Long? {
        return this as? Long
    }

    fun Any.castToMap(): Map<*, *>? {
        return this as? Map<*, *>
    }

    fun Any.castToShort(): Short? {
        return this as? Short
    }

    fun Any.castToString(): String? {
        return this as? String
    }

    fun Boolean?.getConditionResult(): ConditionResult {
        return when (this) {
            null -> ConditionResult.Error(badCastErrorMessage)
            else -> ConditionResult.Ok(this)
        }
    }
}
