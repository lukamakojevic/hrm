package com.hyperoptic.homework.hrm.exceptions;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionSupplierTest {

    @Test
    void testConstructorIsPrivate() throws NoSuchMethodException {
        Constructor<ExceptionSupplier> constructor = ExceptionSupplier.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }
}