package com.warnermedia.rulesengine.core

/**
 * Type of join for the conditions in a rule
 *
 * @constructor Create empty Condition joiner
 */
enum class ConditionJoiner {
    /**
     * Logical AND (&&) operator
     *
     * @constructor Create empty And
     */
    AND,

    /**
     * Logical OR (||) operator
     *
     * @constructor Create empty Or
     */
    OR
}
