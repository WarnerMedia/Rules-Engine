package com.warnermedia.rulesengine

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

class Engine(val id: String, rules: ArrayList<Rule>, val options: EngineOptions) {
    val rules = if (options.sortRulesByPriority) rules.sortedByDescending { it.options.priority } else rules

    fun evaluate(facts: HashMap<String, Any>): EvaluationResult {
        val evaluationResult = rules.evaluateEngineRulesLatestInclusive(facts, options)
        return EvaluationResult(
            evaluationResult.first,
            if (evaluationResult.second) ExitCriteria.EarlyExit(evaluationResult.first.last()) else ExitCriteria.NormalExit()
        )
    }

    fun saveToFile(path: String) {
        val mapper = jacksonObjectMapper()
        val jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this)
        File(path).writeText(jsonString, Charsets.UTF_8)
    }

    private fun Iterable<Rule>.evaluateEngineRulesLatestInclusive(
        facts: HashMap<String, Any>, engineOptions: EngineOptions
    ): Pair<ArrayList<RuleResult>, Boolean> {
        val list = ArrayList<RuleResult>()
        for (item in this) {
            val result = item.evaluate(facts, RuleEvaluationOptions(engineOptions.upcastFactValues))
            list.add(result)
            when (engineOptions.evaluationType) {
                EngineEvaluationType.FIRST_ERROR -> if (result is RuleResult.Error) return Pair(list, true)
                EngineEvaluationType.FIRST_FAILURE -> if (result is RuleResult.Failure) return Pair(list, true)
                EngineEvaluationType.FIRST_SUCCESS -> if (result is RuleResult.Success) return Pair(list, true)
                else -> continue
            }
        }
        return Pair(list, false)
    }

    companion object {
        fun readFromFile(path: String): Engine {
            val mapper = jacksonObjectMapper()
            val readInString = File(path).readText(Charsets.UTF_8)
            return mapper.readValue(readInString, Engine::class.java)
        }
    }
}
