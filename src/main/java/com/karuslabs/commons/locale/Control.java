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
package com.karuslabs.commons.locale;

import com.karuslabs.commons.annotation.Immutable;
import com.karuslabs.commons.locale.resources.Resource;

import java.io.*;
import java.util.*;
import javax.annotation.Nullable;

import static com.karuslabs.commons.configuration.Configurations.from;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;


public class Control extends ResourceBundle.Control {
    
    public static final @Immutable List<String> FORMATS = unmodifiableList(asList("java.properties", "yml", "yaml"));
    
    
    private List<Resource> resources;
    
    
    public Control(Resource... resources) {
        this(asList(resources));
    }
    
    public Control(List<Resource> resources) {
        this.resources = resources;
    }
    
    
    @Override
    public @Nullable ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) {
        if (getFormats(baseName).contains(format)) {
            String bundleName = toBundleName(baseName, locale);
            String bundle = toResourceName(bundleName, format);
            for (Resource resource : resources) {
                if (resource.exists(bundle)) {
                    return load(format, resource.load(bundle));
                }
            }
        }
            
        return null;
    }
    
    protected ResourceBundle load(String format, InputStream stream) {
        switch (format) {
            case "java.properties":
                try (InputStream aStream = stream) {
                    return new PropertyResourceBundle(aStream);

                } catch (IOException e) {
                    return null;
                }
                
            case "yml":
            case "yaml":
                return new YamlResourceBundle(from(stream));
                
            default:
                return null;
        }
    }

    
    @Override
    public List<String> getFormats(String bundleName) {
        return FORMATS;
    }
    
    public List<Resource> getResources() {
        return resources;
    }

}