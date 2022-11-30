package com.warnermedia.rulesengine.core

/**
 * Engine evaluation type
 *
 * @constructor Create empty Engine evaluation type
 */
enum class EngineEvaluationType {
    /**
     * All
     *
     * @constructor Create empty All
     */
    ALL,

    /**
     * First Error
     *
     * @constructor Create empty First Error
     */
    FIRST_ERROR,

    /**
     * First Failure
     *
     * @constructor Create empty First Failure
     */
    FIRST_FAILURE,

    /**
     * First Success
     *
     * @constructor Create empty First Success
     */
    FIRST_SUCCESS
}
