/*
 * The MIT License
 *
 * Copyright 2017 Karus Labs.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.karuslabs.commons.command.parser;

import java.util.Map;
import javax.annotation.Nullable;


public abstract class Element<T> {
    
    private Map<String, T> declarations;
    
    
    public Element(Map<String, T> declarations) {
        this.declarations = declarations;
    }
    
    
    public void declare(String key, Object value) {
        declarations.put(key, Element.this.parse(key, value));
    }
    
    
    public T parse(String key, Object value) {
        T parsed;
        if (value instanceof String) {
            return getDeclaration((String) value);
            
        } else if ((parsed = parse(value)) != null) {
            return parsed;
            
        } else {
            throw new ParserException("Failed to parse key:" + key);
        }
    }
    
    protected T getDeclaration(String key) {
        T value = declarations.get(key);
        if (value != null) {
            return value;
            
        } else {
            throw new ParserException("Failed to find declaration for: " + key);
        }
    }
    
    protected abstract @Nullable T parse(Object value);
    
    
    public Map<String, T> getDeclarations() {
        return declarations;
    }
    
}
