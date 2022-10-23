package com.warnermedia.rulesengine

enum class OperatorType {
    EQUALS {
        override fun evaluate(
            operatorValue: Any, valueFromFacts: Any, evaluationOptions: ConditionEvaluationOptions
        ): ConditionResult {
            return when (operatorValue) {
                is Array<*> -> valueFromFacts.castToArray()?.let { it.contentEquals(operatorValue) }
                is ArrayList<*> -> valueFromFacts.castToList()?.let { it == operatorValue }
                is Boolean -> valueFromFacts.castToBoolean()?.let { it == operatorValue }
                is Byte -> valueFromFacts.castToByte()?.let { it == operatorValue }
                is Char -> valueFromFacts.castToChar()?.let { it == operatorValue }
                is Double -> valueFromFacts.castToDouble(evaluationOptions.upcastFactValues)
                    ?.let { it == operatorValue }

                is Float -> valueFromFacts.castToFloat()?.let { it == operatorValue }
                is HashMap<*, *> -> valueFromFacts.castToMap()?.let { it == operatorValue }
                is Int -> valueFromFacts.castToInt(evaluationOptions.upcastFactValues)?.let { it == operatorValue }
                is Long -> valueFromFacts.castToLong(evaluationOptions.upcastFactValues)?.let { it == operatorValue }
                is Short -> valueFromFacts.castToShort(evaluationOptions.upcastFactValues)?.let { it == operatorValue }
                is String -> valueFromFacts.castToString()?.let { it == operatorValue }
                else -> null
            }.getConditionResult()
        }
    },
    GREATER_THAN {
        override fun evaluate(
            operatorValue: Any, valueFromFacts: Any, evaluationOptions: ConditionEvaluationOptions
        ): ConditionResult {
            return when (operatorValue) {
                is Byte -> valueFromFacts.castToByte()?.let { it > operatorValue }
                is Char -> valueFromFacts.castToChar()?.let { it > operatorValue }
                is Double -> valueFromFacts.castToDouble(evaluationOptions.upcastFactValues)?.let { it > operatorValue }
                is Float -> valueFromFacts.castToFloat()?.let { it > operatorValue }
                is Int -> valueFromFacts.castToInt(evaluationOptions.upcastFactValues)?.let { it > operatorValue }
                is Long -> valueFromFacts.castToLong(evaluationOptions.upcastFactValues)?.let { it > operatorValue }
                is Short -> valueFromFacts.castToShort(evaluationOptions.upcastFactValues)?.let { it > operatorValue }
                is String -> valueFromFacts.castToString()?.let { it > operatorValue }
                else -> null
            }.getConditionResult()
        }
    },
    NOT_EQUALS {
        override fun evaluate(
            operatorValue: Any, valueFromFacts: Any, evaluationOptions: ConditionEvaluationOptions
        ): ConditionResult {
            return when (operatorValue) {
                is Array<*> -> valueFromFacts.castToArray()?.let { !it.contentEquals(operatorValue) }
                is ArrayList<*> -> valueFromFacts.castToList()?.let { it != operatorValue }
                is Boolean -> valueFromFacts.castToBoolean()?.let { it != operatorValue }
                is Byte -> valueFromFacts.castToByte()?.let { it != operatorValue }
                is Char -> valueFromFacts.castToChar()?.let { it != operatorValue }
                is Double -> valueFromFacts.castToDouble(evaluationOptions.upcastFactValues)
                    ?.let { it != operatorValue }

                is Float -> valueFromFacts.castToFloat()?.let { it != operatorValue }
                is HashMap<*, *> -> valueFromFacts.castToMap()?.let { it != operatorValue }
                is Int -> valueFromFacts.castToInt(evaluationOptions.upcastFactValues)?.let { it != operatorValue }
                is Long -> valueFromFacts.castToLong(evaluationOptions.upcastFactValues)?.let { it != operatorValue }
                is Short -> valueFromFacts.castToShort(evaluationOptions.upcastFactValues)?.let { it != operatorValue }
                is String -> valueFromFacts.castToString()?.let { it != operatorValue }
                else -> null
            }.getConditionResult()
        }
    },
    LESS_THAN {
        override fun evaluate(
            operatorValue: Any, valueFromFacts: Any, evaluationOptions: ConditionEvaluationOptions
        ): ConditionResult {
            return when (operatorValue) {
                is Byte -> valueFromFacts.castToByte()?.let { it < operatorValue }
                is Char -> valueFromFacts.castToChar()?.let { it < operatorValue }
                is Double -> valueFromFacts.castToDouble(evaluationOptions.upcastFactValues)?.let { it < operatorValue }
                is Float -> valueFromFacts.castToFloat()?.let { it < operatorValue }
                is Int -> valueFromFacts.castToInt(evaluationOptions.upcastFactValues)?.let { it < operatorValue }
                is Long -> valueFromFacts.castToLong(evaluationOptions.upcastFactValues)?.let { it < operatorValue }
                is Short -> valueFromFacts.castToShort(evaluationOptions.upcastFactValues)?.let { it < operatorValue }
                is String -> valueFromFacts.castToString()?.let { it < operatorValue }
                else -> null
            }.getConditionResult()
        }
    };

    abstract fun evaluate(
        operatorValue: Any, valueFromFacts: Any, evaluationOptions: ConditionEvaluationOptions
    ): ConditionResult

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

    fun Any.castToDouble(upcast: Boolean): Double? {
        return (this as? Double) ?: run {
            when (upcast) {
                true -> {
                    when (this) {
                        is Float -> this.toDouble()
                        else -> null
                    }
                }

                false -> null
            }
        }
    }

    fun Any.castToFloat(): Float? {
        return this as? Float
    }

    fun Any.castToInt(upcast: Boolean): Int? {
        return (this as? Int) ?: run {
            when (upcast) {
                true -> {
                    when (this) {
                        is Byte -> this.toInt()
                        is Short -> this.toInt()
                        else -> null
                    }
                }

                false -> null
            }
        }
    }

    fun Any.castToList(): List<*>? {
        return this as? List<*>
    }

    fun Any.castToLong(upcast: Boolean): Long? {
        return (this as? Long) ?: run {
            when (upcast) {
                true -> {
                    when (this) {
                        is Byte -> this.toLong()
                        is Int -> this.toLong()
                        is Short -> this.toLong()
                        else -> null
                    }
                }

                false -> null
            }
        }
    }

    fun Any.castToMap(): Map<*, *>? {
        return this as? Map<*, *>
    }

    fun Any.castToShort(upcast: Boolean): Short? {
        return (this as? Short) ?: run {
            when (upcast) {
                true -> {
                    when (this) {
                        is Byte -> this.toShort()
                        else -> null
                    }
                }

                false -> null
            }
        }
    }

    fun Any.castToString(): String? {
        return this as? String
    }

    fun Boolean?.getConditionResult(): ConditionResult {
        val badCastErrorMessage = "runtime cast error"
        return when (this) {
            null -> ConditionResult.Error(badCastErrorMessage)
            else -> ConditionResult.Ok(this)
        }
    }
}
