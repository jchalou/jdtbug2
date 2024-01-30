package org.example;

import org.example.GenerateInnerClassTriggerGen.Inner;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SuppressWarnings("unused")
public class GenerateInnerClassTrigger {
    @GenerateInnerClassAnnotation
    private int x;

    {
        Inner inner = new Inner();
    }

    /**
     * Without this unused annotation definition, the bug is not reproducible.
     */
    @Retention(RetentionPolicy.SOURCE)
    @interface OtherAnnotation {

    }

}
