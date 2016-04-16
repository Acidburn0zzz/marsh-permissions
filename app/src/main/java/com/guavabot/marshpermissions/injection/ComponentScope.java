package com.guavabot.marshpermissions.injection;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A scoping annotation to permit objects whose lifetime should to the life of
 * an Android component to be memorized in the correct component.
 */
@Scope
@Retention(RUNTIME)
public @interface ComponentScope {}