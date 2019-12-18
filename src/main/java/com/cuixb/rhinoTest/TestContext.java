package com.cuixb.rhinoTest;


import org.mozilla.javascript.*;
import org.mozilla.javascript.debug.Debugger;

import java.io.*;

/**
 * Here's an example use of rhino
 */
public class TestContext {

    protected static org.mozilla.javascript.Context jsContext;

    public static void main(String args[]) throws IOException {

        //Analyzing Java APIs start
        String[] param3 = {"userVariables ='cuixb'; userVariables;"};
        launch(param3);
        //Analyzing Java APIs end
    }

    protected static Script compile(String scriptName, int startLine, Reader reader) throws IOException {
        Script s;
        s = jsContext.compileReader(reader, scriptName, startLine, null);
        return s;
    }

    public static void launch(String args[]) throws IOException {
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
            NativeObject userVariables = new NativeObject();
            InputStreamReader reader = null;
            try {
                File file=new File("src/main/java/com/cuixb/rhinoTest/system.js");
                System.out.println(file.length());
                FileInputStream fis=new FileInputStream(file);
                reader = new InputStreamReader(fis,"utf8");
                compile("cuixbscript", 1, reader).exec(cx, scope);
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
            ScriptableObject.putProperty(scope, "cuixbUserVariables", userVariables);

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

