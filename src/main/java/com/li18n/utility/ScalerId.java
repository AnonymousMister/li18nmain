package com.li18n.utility;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;


@ThreadSafe
public class ScalerId {

    @GuardedBy("this")
    private int value;

    public ScalerId() {

    }

    public ScalerId(int value) {
        this.value = value;
    }

    public synchronized int getNext() {
        return value++;
    }


    public int getValue() {
        return value;
    }

}
