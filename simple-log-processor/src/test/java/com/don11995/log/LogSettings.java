package com.don11995.log;

public interface LogSettings {

    @MapClassInner(methods = "innerTest", prefixes = "TEST")
    void mapClassToTest(ClassToTest classToTest);
}
