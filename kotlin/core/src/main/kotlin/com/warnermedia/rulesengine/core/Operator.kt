package com.warnermedia.rulesengine.core

/**
 * Operator with a type and a value to evaluate against
 *
 * @property operatorType operator type
 * @property operatorValue operator value
 * @constructor Create empty Operator
 */
class Operator(val operatorType: OperatorType, val operatorValue: Any)
