package ru.alfabank.android.detekt.rules

import com.example.detekt.rules.ContextOrder
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.rules.KotlinCoreEnvironmentTest
import io.gitlab.arturbosch.detekt.test.compileAndLintWithContext
import io.kotest.matchers.collections.shouldHaveSize
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.junit.jupiter.api.Test

@KotlinCoreEnvironmentTest
class ContextOrderTest(private val env: KotlinCoreEnvironment) {

    @Test
    fun `reports context order`() {
        val code = """
import android.content.Context

class ContextOrderSample {
    fun foo(bar: String, context: Context) {
        if (context.isRestricted) {
            println(bar)
        }
    }
}
        """
        val findings = ContextOrder(Config.empty).compileAndLintWithContext(env, code)
        findings shouldHaveSize 1
    }

    @Test
    fun `doesn't report context order`() {
        val code = """
import android.content.Context

class ContextOrderSample {
    fun foo(context: Context, bar: String) {
        if (context.isRestricted) {
            println(bar)
        }
    }
}
        """
        val findings = ContextOrder(Config.empty).compileAndLintWithContext(env, code)
        findings shouldHaveSize 0
    }
}