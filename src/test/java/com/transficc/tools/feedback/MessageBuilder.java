/*
 * Copyright 2016 TransFICC Ltd.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.  The ASF licenses this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the specific language governing permissions and limitations under the License.
 */
package com.transficc.tools.feedback;

import java.lang.reflect.Field;

public final class MessageBuilder
{
    private final Object instance;

    public MessageBuilder(final Class clazz)
    {
        try
        {
            this.instance = clazz.newInstance();
        }
        catch (final IllegalAccessException | InstantiationException e)
        {
            throw new RuntimeException(e);
        }
    }

    public MessageBuilder setField(final String field, final Object value)
    {
        try
        {
            final Field actualField = instance.getClass().getDeclaredField(field);
            final boolean accessible = actualField.isAccessible();
            actualField.setAccessible(true);
            actualField.set(instance, value);
            actualField.setAccessible(accessible);
            return this;
        }
        catch (final NoSuchFieldException | IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"unchecked", "TypeParameterUnusedInFormals"})
    public <T> T build()
    {
        return (T)instance;
    }
}
