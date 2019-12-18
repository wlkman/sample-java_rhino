package com.cuixb.rhinoTest;

import org.mozilla.javascript.*;

/**
 * reflects the System.out into JavaScript.
 *
 */
public class logogram {
    public static void main(String args[])
    {
        //Analyzing Java APIs using logogram start
        String[] param = {"out.println(666)"};
        launch(param);
        //Analyzing Java APIs using logogram end
    }

    public static void launch(String args[])
    {
        Context cx = Context.enter();
        try {
            Scriptable scope = cx.initStandardObjects();

            // Add a global variable "out" that is a JavaScript reflection
            // of System.out
            Object jsOut = Context.javaToJS(System.out, scope);
            ScriptableObject.putProperty(scope, "out", jsOut);

            String s = "";
            for (int i=0; i < args.length; i++) {
                s += args[i];
            }
            Object result = cx.evaluateString(scope, s, "<cmd>", 1, null);
            System.err.println(Context.toString(result));
        } finally {
            Context.exit();
        }
    }

}