package com.warnermedia.rulesengine.core

/**
 * Engine rule evaluation type
 *
 * @constructor Create empty Engine evaluation type
 */
enum class EngineEvaluationType {
    /**
     * Evaluate all rules
     *
     * @constructor Create empty All
     */
    ALL,

    /**
     * Evaluate rules until first error
     *
     * @constructor Create empty First Error
     */
    FIRST_ERROR,

    /**
     * Evaluate rules until first failure
     *
     * @constructor Create empty First Failure
     */
    FIRST_FAILURE,

    /**
     * Evaluate rules until first success
     *
     * @constructor Create empty First Success
     */
    FIRST_SUCCESS
}
