package com.cuixb.rhinoTest;

import org.mozilla.javascript.*;

/**
 * Execute scripts in an environment that includes the
 *             example Counter class.
 */
public class UsingCounter {

    public static void main(String args[]) throws Exception {
        //Analyzing Java APIs using logogram start
        String[] param = {"c = new cuixbContext(3); c.count;" + "c.count;"};
        launch(param);
        //Analyzing Java APIs using logogram end
    }

    public static void launch(String args[])
            throws Exception
    {
        Context cx = Context.enter();
        try {
            Scriptable scope = cx.initStandardObjects();

            // Use the Counter class to define a Counter constructor
            // and prototype in JavaScript.
            ScriptableObject.defineClass(scope, Counter.class);

            // Create an instance of Counter and assign it to
            // the top-level variable "myCounter". This is
            // equivalent to the JavaScript code
            //    myCounter = new Counter(7);
            Object[] arg = { new Integer(7) };
            Scriptable myCounter = cx.newObject(scope, "cuixbContext", arg);
            scope.put("myCounter", scope, myCounter);

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
