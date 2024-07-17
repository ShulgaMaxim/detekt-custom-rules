package com.example.detekt.provider

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider
import com.example.detekt.rules.ContextOrder

class MyRuleSetProvider : RuleSetProvider {
    override val ruleSetId: String = "custom-rules"

    override fun instance(config: Config): RuleSet = RuleSet(
        ruleSetId, listOf(ContextOrder(config)),
    )
}
