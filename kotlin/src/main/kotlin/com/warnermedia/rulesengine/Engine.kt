package com.warnermedia.rulesengine

import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import java.io.File

class Engine(val id: String, rules: ArrayList<Rule>, private val options: EngineOptions) {
    private val rules = if (options.sortRulesByPriority) rules.sortedByDescending { it.options.priority } else rules

    fun evaluate(facts: HashMap<String, Any>): EvaluationResult {
        val evaluationResult = rules.evaluateEngineRulesLatestInclusive(facts, options)
        return EvaluationResult(
            evaluationResult.first,
            if (evaluationResult.second) ExitCriteria.EarlyExit(evaluationResult.first.last()) else ExitCriteria.NormalExit()
        )
    }

    fun saveToFile(path: String) {
        val gson = GsonBuilder().setPrettyPrinting().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).create()
        val str = gson.toJson(this)

        File(path).writeText(str, Charsets.UTF_8)
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
            val gson =
                GsonBuilder().setPrettyPrinting().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).create()
            val readInString = File(path).readText(Charsets.UTF_8)
            return gson.fromJson(readInString, Engine::class.java)
        }
    }
}
