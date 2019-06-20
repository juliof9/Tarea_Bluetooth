
package com.example.mundo.UI.Load;

import android.util.Log;

public class Wrapper implements Nodo {

    // For piping:  The next node to receive Load data after this one has done its work.
    private Nodo mNext;


    public Nodo getNext() {
        return mNext;
    }


    public void setNext(Nodo node) {
        mNext = node;
    }


    @Override
    public void println(int priority, String tag, String msg, Throwable tr) {
        // There actually are log methods that don't take a msg parameter.  For now,
        // if that's the case, just convert null to the empty string and move on.
        String useMsg = msg;
        if (useMsg == null) {
            useMsg = "";
        }

        // If an exeption was provided, convert that exception to a usable string and attach
        // it to the end of the msg method.
        if (tr != null) {
            msg += "\n" + Log.getStackTraceString(tr);
        }

        // This is functionally identical to Load.x(tag, useMsg);
        // For instance, if priority were Load.VERBOSE, this would be the same as Load.v(tag, useMsg)
        Log.println(priority, tag, useMsg);

        // If this isn't the last node in the chain, move things along.
        if (mNext != null) {
            mNext.println(priority, tag, msg, tr);
        }
    }
}
