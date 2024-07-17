import android.content.Context

class ContextOrderSample {

    //FAIL
    fun foo(bar: String, context: Context) {
        if (context.isRestricted) {
            println(bar)
        }
    }

    //SUCCESS
    fun foo(context: Context, bar: String) {
        if (context.isRestricted) {
            println(bar)
        }
    }
}
