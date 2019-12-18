package com.cuixb.rhinoTest;


import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * Here's an example use of rhino
 */
public class FirstExample {

    public static void main(String args[])
    {
        //Analyzing JS start
        String[] param1 = {"function fu(){return Math.cos(Math.PI)} fu()"};
        launch(param1);

        String[] param2 = {"function f(x){return x+1} f(7)"};
        launch(param2);
        //Analyzing JS end

        //Analyzing Java APIs start
        String[] param3 = {"java.lang.System.out.println(3)"};
        launch(param3);
        //Analyzing Java APIs end
    }

    public static void launch(String args[]){
        // Creates and enters a Context. The Context stores information
        // about the execution environment of a script.
        Context cx = Context.enter();
        try {
            // Initialize the standard objects (Object, Function, etc.)
            // This must be done before scripts can be executed. Returns
            // a scope object that we use in later calls.
            Scriptable scope = cx.initStandardObjects();

            // Collect the arguments into a single string.
            String s = "";
            for (int i=0; i < args.length; i++) {
                s += args[i];
            }

            // uses the Context cx to evaluate a string. Evaluation of the script looks up variables in scope,
            // and errors will be reported with the filename <cmd> and line number 1.
            Object result = cx.evaluateString(scope, s, "<cmd>", 1, null);

            // prints the result of evaluating the script (contained in the variable result).
            // result could be a string, JavaScript object, or other values.
            // The toString method converts any JavaScript value to a string.
            System.err.println(Context.toString(result));

        } finally {
            // exits the Context. This removes the association between the Context and the current thread and is an essential cleanup action.
            // There should be a call to exit for every call to enter. To make sure that it is called even if an exception is thrown,
            // it is put into the finally block corresponding to the try block starting after Context.enter().
            Context.exit();
        }
    }
}

