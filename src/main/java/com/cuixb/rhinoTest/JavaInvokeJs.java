package com.cuixb.rhinoTest;

import org.mozilla.javascript.*;

/**
 * Java invoke js
 */
public class JavaInvokeJs {

    public static void launch(String args[])
    {
        Context cx = Context.enter();
        try {
            Scriptable scope = cx.initStandardObjects();

            // Collect the arguments into a single string.
            String s = "";
            for (int i=0; i < args.length; i++) {
                s += args[i];
            }

            // Now evaluate the string we've collected. We'll ignore the result.
            cx.evaluateString(scope, s, "<cmd>", 1, null);

            // Print the value of variable "x"
            Object x = scope.get("x", scope);
            if (x == Scriptable.NOT_FOUND) {
                System.out.println("x is not defined.");
            } else {
                System.out.println("x = " + Context.toString(x));
            }

            // Call function "f('my arg')" and print its result.
            Object fObj = scope.get("f", scope);
            if (!(fObj instanceof Function)) {
                System.out.println("f is undefined or not a function.");
            } else {
                Object functionArgs[] = { "my arg" };
                Function f = (Function)fObj;
                Object result = f.call(cx, scope, scope, functionArgs);
                String report = "f('my args') = " + Context.toString(result);
                System.out.println(report);
            }
        } finally {
            Context.exit();
        }
    }

    public static void main(String args[]) {
        //Analyzing JS start
        String[] param = {"x = 7"};
        launch(param);
        //Analyzing JS end

        //Analyzing JS start
        String[] param1 = {"function f(a) { return a; }"};
        launch(param1);
        //Analyzing JS end
    }
}
