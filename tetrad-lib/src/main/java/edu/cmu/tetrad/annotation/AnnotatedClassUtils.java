/*
 * Copyright (C) 2017 University of Pittsburgh.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package edu.cmu.tetrad.annotation;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;

/**
 *
 * Sep 6, 2017 11:11:38 AM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
public class AnnotatedClassUtils {

    private AnnotatedClassUtils() {
    }

    public static List<AnnotatedClass> getAnnotatedClasses(String packageName, Class<? extends Annotation> type) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(type);

        return classes.stream()
                .map(e -> new AnnotatedClass(e, e.getAnnotation(type)))
                .collect(Collectors.toList());
    }

}
