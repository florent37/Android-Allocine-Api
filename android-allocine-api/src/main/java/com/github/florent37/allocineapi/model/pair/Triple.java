package com.github.florent37.allocineapi.model.pair;

import android.util.Pair;


public class Triple<A,B,C> extends Pair<A,B> {

    public final C third;

    public Triple(A first, B second, C third) {
        super(first, second);
        this.third = third;
    }

    public static <A, B, C> Triple<A, B, C> create(A a, B b, C c) {
        return new Triple<>(a, b, c);
    }
}
