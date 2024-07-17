package com.example.detekt.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtNamedFunction

class ContextOrder(config: Config) : Rule(config) {

    override val issue = Issue(javaClass.simpleName, Severity.Maintainability, ISSUE_DESCRIPTION, Debt.FIVE_MINS)

    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)
        val parameters = function.valueParameters
        if (parameters.size < 2) {
            return
        }
        val parameterTypeList = parameters.map { x -> x.children[0].text }
        if (isContext(parameterTypeList[0])) {
            return
        }
        else if (isContext(parameterTypeList)) {
            report(CodeSmell(issue, Entity.from(function), REPORT_MESSAGE))
        }
    }

    private fun isContext(s: String?): Boolean {
        return (s == CONTEXT || s == CONTEXT_WITH_QUESTION_MARK)
    }

    private fun isContext(s: List<String>): Boolean {
        return (s.contains(CONTEXT) || s.contains(CONTEXT_WITH_QUESTION_MARK))
    }

    companion object {

        const val CONTEXT = "Context"
        const val CONTEXT_WITH_QUESTION_MARK = "$CONTEXT?"
        const val REPORT_MESSAGE = "Context must be the first parameter"
        const val ISSUE_DESCRIPTION = "This rule reports the method which doesn't use Context as the first parameter"
    }
}
