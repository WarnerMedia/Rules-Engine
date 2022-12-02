package com.warnermedia.rulesengine.core

/**
 * Operator type
 *
 * @constructor Create empty Operator type
 */
enum class OperatorType {
    /**
     * Contained In operator
     *
     * @constructor Create empty Contained In
     */
    CONTAINED_IN {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any,
            evaluationOptions: OperatorEvaluationOptions
        ): OperatorResult {
            return when (operatorValue) {
                is Array<*> -> operatorValue.contains(valueFromFacts)
                is ArrayList<*> -> operatorValue.contains(valueFromFacts)
                is HashMap<*, *> -> operatorValue.containsKey(valueFromFacts)
                is HashSet<*> -> operatorValue.contains(valueFromFacts)
                is String -> valueFromFacts.castToString()?.let { operatorValue.contains(it) }
                else -> null
            }.getOperatorResult()
        }
    },

    /**
     * Ends With operator
     *
     * @constructor Create empty Ends With
     */
    ENDS_WITH {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any,
            evaluationOptions: OperatorEvaluationOptions
        ): OperatorResult {
            return when (operatorValue) {
                is String -> valueFromFacts.castToString()?.endsWith(operatorValue)
                else -> null
            }.getOperatorResult()
        }
    },

    /**
     * Equals operator
     *
     * @constructor Create empty Equals
     */
    EQUALS {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any,
            evaluationOptions: OperatorEvaluationOptions
        ): OperatorResult {
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
            }.getOperatorResult()
        }
    },

    /**
     * Greater Than operator
     *
     * @constructor Create empty Greater Than
     */
    GREATER_THAN {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any,
            evaluationOptions: OperatorEvaluationOptions
        ): OperatorResult {
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
            }.getOperatorResult()
        }
    },

    /**
     * Greater Than Equals operator
     *
     * @constructor Create empty Greater Than Equals
     */
    GREATER_THAN_EQUALS {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any,
            evaluationOptions: OperatorEvaluationOptions
        ): OperatorResult {
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
            }.getOperatorResult()
        }
    },

    /**
     * Less Than operator
     *
     * @constructor Create empty Less Than
     */
    LESS_THAN {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any,
            evaluationOptions: OperatorEvaluationOptions
        ): OperatorResult {
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
            }.getOperatorResult()
        }
    },

    /**
     * Less Than Equals operator
     *
     * @constructor Create empty Less Than Equals
     */
    LESS_THAN_EQUALS {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any,
            evaluationOptions: OperatorEvaluationOptions
        ): OperatorResult {
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
            }.getOperatorResult()
        }
    },

    /**
     * Not Equals operator
     *
     * @constructor Create empty Not Equals
     */
    NOT_EQUALS {
        override fun evaluate(
            operatorValue: Any,
            valueFromFacts: Any,
            evaluationOptions: OperatorEvaluationOptions
        ): OperatorResult {
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
            }.getOperatorResult()
        }
    };

    /**
     * Evaluate
     *
     * @param operatorValue
     * @param valueFromFacts
     * @param evaluationOptions
     * @return operator result
     */
    abstract fun evaluate(
        operatorValue: Any,
        valueFromFacts: Any,
        evaluationOptions: OperatorEvaluationOptions
    ): OperatorResult

    /**
     * Cast to array
     *
     * @return this as array
     */
    fun Any.castToArray(): Array<*>? {
        return this as? Array<*>
    }

    /**
     * Cast to array list
     *
     * @return this as arraylist
     */
    fun Any.castToArrayList(): ArrayList<*>? {
        return this as? ArrayList<*>
    }

    /**
     * Cast to boolean
     *
     * @return this as boolean
     */
    fun Any.castToBoolean(): Boolean? {
        return this as? Boolean
    }

    /**
     * Cast to byte
     *
     * @return this as byte
     */
    fun Any.castToByte(): Byte? {
        return this as? Byte
    }

    /**
     * Cast to char
     *
     * @return this as char
     */
    fun Any.castToChar(): Char? {
        return this as? Char
    }

    /**
     * Cast to double
     *
     * @param upcast
     * @return
     */
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

    /**
     * Cast to float
     *
     * @return this as float
     */
    fun Any.castToFloat(): Float? {
        return this as? Float
    }

    /**
     * Cast to hash map
     *
     * @return this as hashmap
     */
    fun Any.castToHashMap(): HashMap<*, *>? {
        return this as? HashMap<*, *>
    }

    /**
     * Cast to hash set
     *
     * @return this as hashset
     */
    fun Any.castToHashSet(): HashSet<*>? {
        return this as? HashSet<*>
    }

    /**
     * Cast to int
     *
     * @param upcast
     * @return this as int
     */
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

    /**
     * Cast to long
     *
     * @param upcast
     * @return this as long
     */
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

    /**
     * Cast to short
     *
     * @param upcast
     * @return this as short
     */
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

    /**
     * Cast to string
     *
     * @return this as string
     */
    fun Any.castToString(): String? {
        return this as? String
    }

    /**
     * Get operator result
     *
     * @return boolean as operator result
     */
    fun Boolean?.getOperatorResult(): OperatorResult {
        val badCastErrorMessage = "runtime cast error"
        return when (this) {
            null -> OperatorResult.Error(badCastErrorMessage)
            else -> OperatorResult.Ok(this)
        }
    }
}
