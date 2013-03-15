/**
 * ====================
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2008-2009 Sun Microsystems, Inc. All rights reserved.
 * Copyright 2011-2013 Tirasa. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License("CDDL") (the "License"). You may not use this file
 * except in compliance with the License.
 *
 * You can obtain a copy of the License at https://oss.oracle.com/licenses/CDDL
 * See the License for the specific language governing permissions and limitations
 * under the License.
 *
 * When distributing the Covered Code, include this CDDL Header Notice in each file
 * and include the License file at https://oss.oracle.com/licenses/CDDL.
 * If applicable, add the following below this CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * ====================
 */
package org.identityconnectors.contract.data.groovy;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * Support for lazy evaluation. "Lazy evaluation" means that property value is
 * evaluated just when get(String) query is called in GroovyDataProvider.
 * </p>
 * <p>
 * Methods {@link Lazy#get(Object)}, {@link Lazy#random(Object)} and
 * {@link Lazy#random(Object, Class)} are ones used in contract tests'
 * configuration.
 * </p>
 * <p>
 * More detailed information is on web
 * {@link https://identityconnectors.dev.java.net/contract-tests-groovy/index.html}
 * </p>
 * 
 * @author Zdenek Louzensky
 * 
 */
public abstract class Lazy {

    protected List<Object> successors = new LinkedList<Object>();
    protected Object value;

    /**
     * <p>
     * overriding plus operation to be able to concatenate Lazy objects to
     * others.
     * </p>
     * <p>
     * <strong>(not intented for programmer use)</strong>
     * </p>
     * 
     * @param s
     * @return
     */
    public Lazy plus(String s) {
        successors.add(s);
        return this;
    }

    /**
     * @see Lazy#plus(String)
     */
    public Lazy plus(Lazy lazy) {
        successors.add(lazy);
        return this;
    }

    /**
     * retrieve value of another property dynamically
     * @param prop property name
     * @return property value
     */
    public static Lazy get(Object prop) {
        return new Get(prop);
    }

    /**
     * @see Lazy#random(Object, Class)
     */
    public static Lazy random(Object pattern) {
        return new Random(pattern);
    }

    /**
     * generate a random object based on given pattern. Object's constructor
     * will be initialized with the string generated by pattern.
     * 
     * @param pattern
     * @see {@link org.identityconnectors.contract.data.RandomGenerator#generate(String)}
     * @param clazz
     * @return
     */
    public static Lazy random(Object pattern, Class<?> clazz) {
        return new Random(pattern, clazz);
    }

    public List<Object> getSuccessors() {
        return successors;
    }

    public void setSuccessors(List<Object> successors) {
        this.successors = successors;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
