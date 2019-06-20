
package com.example.mundo.UI.Load;


public class LoadOneMessage implements Nodo {

    Nodo mNext;

    public LoadOneMessage(Nodo next) {
        mNext = next;
    }

    public LoadOneMessage() {
    }

    @Override
    public void println(int priority, String tag, String msg, Throwable tr) {
        if (mNext != null) {
            getNext().println(Load.NONE, null, msg, null);
        }
    }

    public Nodo getNext() {
        return mNext;
    }


    public void setNext(Nodo node) {
        mNext = node;
    }

}
