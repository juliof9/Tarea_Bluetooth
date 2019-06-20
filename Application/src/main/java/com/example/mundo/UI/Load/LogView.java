
package com.example.mundo.UI.Load;

import android.app.Activity;
import android.content.Context;
import android.util.*;
import android.widget.TextView;


public class LogView extends android.support.v7.widget.AppCompatTextView implements Nodo {

    public LogView(Context context) {
        super(context);
    }

    public LogView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LogView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void println(int priority, String tag, String msg, Throwable tr) {

        
        String priorityStr = null;

        // For the purposes of this View, we want to print the priority as readable text.
        switch(priority) {
            case android.util.Log.VERBOSE:
                priorityStr = "VERBOSE";
                break;
            case android.util.Log.DEBUG:
                priorityStr = "DEBUG";
                break;
            case android.util.Log.INFO:
                priorityStr = "INFO";
                break;
            case android.util.Log.WARN:
                priorityStr = "WARN";
                break;
            case android.util.Log.ERROR:
                priorityStr = "ERROR";
                break;
            case android.util.Log.ASSERT:
                priorityStr = "ASSERT";
                break;
            default:
                break;
        }

        // Handily, the Load class has a facility for converting a stack trace into a usable string.
        String exceptionStr = null;
        if (tr != null) {
            exceptionStr = android.util.Log.getStackTraceString(tr);
        }

        // Take the priority, tag, message, and exception, and concatenate as necessary
        // into one usable line of text.
        final StringBuilder outputBuilder = new StringBuilder();

        String delimiter = "\t";
        appendIfNotNull(outputBuilder, priorityStr, delimiter);
        appendIfNotNull(outputBuilder, tag, delimiter);
        appendIfNotNull(outputBuilder, msg, delimiter);
        appendIfNotNull(outputBuilder, exceptionStr, delimiter);

        // In case this was originally called from an AsyncTask or some other off-UI thread,
        // make sure the update occurs within the UI thread.
        ((Activity) getContext()).runOnUiThread( (new Thread(new Runnable() {
            @Override
            public void run() {
                // Display the text we just generated within the LogView.
                appendToLog(outputBuilder.toString());
            }
        })));

        if (mNext != null) {
            mNext.println(priority, tag, msg, tr);
        }
    }

    public Nodo getNext() {
        return mNext;
    }

    public void setNext(Nodo node) {
        mNext = node;
    }


    private StringBuilder appendIfNotNull(StringBuilder source, String addStr, String delimiter) {
        if (addStr != null) {
            if (addStr.length() == 0) {
                delimiter = "";
            }

            return source.append(addStr).append(delimiter);
        }
        return source;
    }

    // The next Nodo in the chain.
    Nodo mNext;


    public void appendToLog(String s) {
        append("\n" + s);
    }


}
