package com.warnermedia.rulesengine

/**
 * Enum defining possible operators supported to use when creating a condition
 */
enum class OperatorType {
    CONTAINED_IN {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any,
            evaluationOptions: ConditionEvaluationOptions
        ): ConditionResult {
            return when (operatorValue) {
                is Array<*> -> operatorValue.contains(valueFromFacts)
                is ArrayList<*> -> operatorValue.contains(valueFromFacts)
                is HashMap<*, *> -> operatorValue.containsKey(valueFromFacts)
                is HashSet<*> -> operatorValue.contains(valueFromFacts)
                is String -> valueFromFacts.castToString()?.let { operatorValue.contains(it) }
                else -> null
            }.getConditionResult()
        }
    },
    ENDS_WITH {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any,
            evaluationOptions: ConditionEvaluationOptions
        ): ConditionResult {
            return when (operatorValue) {
                is String -> valueFromFacts.castToString()?.endsWith(operatorValue)
                else -> null
            }.getConditionResult()
        }
    },
    EQUALS {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any,
            evaluationOptions: ConditionEvaluationOptions
        ): ConditionResult {
            return when (operatorValue) {
                is Array<*> -> valueFromFacts.castToArray()?.let { it.contentEquals(operatorValue) }
                is ArrayList<*> -> valueFromFacts.castToArrayList()?.let { it == operatorValue }
                is Boolean -> valueFromFacts.castToBoolean()?.let { it == operatorValue }
                is Byte -> valueFromFacts.castToByte()?.let { it == operatorValue }
                is Char -> valueFromFacts.castToChar()?.let { it == operatorValue }
                is Double -> valueFromFacts.castToDouble(evaluationOptions.upcastFactValues)
                    ?.let { it == operatorValue }

                is Float -> valueFromFacts.castToFloat()?.let { it == operatorValue }
                is HashMap<*, *> -> valueFromFacts.castToHashMap()?.let { it == operatorValue }
                is HashSet<*> -> valueFromFacts.castToHashSet()?.let { it == operatorValue }
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
            operatorValue: Any,
            valueFromFacts: Any,
            evaluationOptions: ConditionEvaluationOptions
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
    GREATER_THAN_EQUALS {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any,
            evaluationOptions: ConditionEvaluationOptions
        ): ConditionResult {
            return when (operatorValue) {
                is Byte -> valueFromFacts.castToByte()?.let { it >= operatorValue }
                is Char -> valueFromFacts.castToChar()?.let { it >= operatorValue }
                is Double -> valueFromFacts.castToDouble(evaluationOptions.upcastFactValues)
                    ?.let { it >= operatorValue }

                is Float -> valueFromFacts.castToFloat()?.let { it >= operatorValue }
                is Int -> valueFromFacts.castToInt(evaluationOptions.upcastFactValues)?.let { it >= operatorValue }
                is Long -> valueFromFacts.castToLong(evaluationOptions.upcastFactValues)?.let { it >= operatorValue }
                is Short -> valueFromFacts.castToShort(evaluationOptions.upcastFactValues)?.let { it >= operatorValue }
                is String -> valueFromFacts.castToString()?.let { it >= operatorValue }
                else -> null
            }.getConditionResult()
        }
    },
    LESS_THAN {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any,
            evaluationOptions: ConditionEvaluationOptions
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
    },
    LESS_THAN_EQUALS {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any,
            evaluationOptions: ConditionEvaluationOptions
        ): ConditionResult {
            return when (operatorValue) {
                is Byte -> valueFromFacts.castToByte()?.let { it <= operatorValue }
                is Char -> valueFromFacts.castToChar()?.let { it <= operatorValue }
                is Double -> valueFromFacts.castToDouble(evaluationOptions.upcastFactValues)
                    ?.let { it <= operatorValue }

                is Float -> valueFromFacts.castToFloat()?.let { it <= operatorValue }
                is Int -> valueFromFacts.castToInt(evaluationOptions.upcastFactValues)?.let { it <= operatorValue }
                is Long -> valueFromFacts.castToLong(evaluationOptions.upcastFactValues)?.let { it <= operatorValue }
                is Short -> valueFromFacts.castToShort(evaluationOptions.upcastFactValues)?.let { it <= operatorValue }
                is String -> valueFromFacts.castToString()?.let { it <= operatorValue }
                else -> null
            }.getConditionResult()
        }
    },
    NOT_EQUALS {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any,
            evaluationOptions: ConditionEvaluationOptions
        ): ConditionResult {
            return when (operatorValue) {
                is Array<*> -> valueFromFacts.castToArray()?.let { !it.contentEquals(operatorValue) }
                is ArrayList<*> -> valueFromFacts.castToArrayList()?.let { it != operatorValue }
                is Boolean -> valueFromFacts.castToBoolean()?.let { it != operatorValue }
                is Byte -> valueFromFacts.castToByte()?.let { it != operatorValue }
                is Char -> valueFromFacts.castToChar()?.let { it != operatorValue }
                is Double -> valueFromFacts.castToDouble(evaluationOptions.upcastFactValues)
                    ?.let { it != operatorValue }

                is Float -> valueFromFacts.castToFloat()?.let { it != operatorValue }
                is HashMap<*, *> -> valueFromFacts.castToHashMap()?.let { it != operatorValue }
                is HashSet<*> -> valueFromFacts.castToHashSet()?.let { it != operatorValue }
                is Int -> valueFromFacts.castToInt(evaluationOptions.upcastFactValues)?.let { it != operatorValue }
                is Long -> valueFromFacts.castToLong(evaluationOptions.upcastFactValues)?.let { it != operatorValue }
                is Short -> valueFromFacts.castToShort(evaluationOptions.upcastFactValues)?.let { it != operatorValue }
                is String -> valueFromFacts.castToString()?.let { it != operatorValue }
                else -> null
            }.getConditionResult()
        }
    };

    abstract fun evaluate(
        operatorValue: Any,
        valueFromFacts: Any,
        evaluationOptions: ConditionEvaluationOptions
    ): ConditionResult

    fun Any.castToArray(): Array<*>? {
        return this as? Array<*>
    }

    fun Any.castToArrayList(): ArrayList<*>? {
        return this as? ArrayList<*>
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

    fun Any.castToHashMap(): HashMap<*, *>? {
        return this as? HashMap<*, *>
    }

    fun Any.castToHashSet(): HashSet<*>? {
        return this as? HashSet<*>
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
