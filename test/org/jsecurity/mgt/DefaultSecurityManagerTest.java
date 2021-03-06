/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jsecurity.mgt;

import org.jsecurity.authc.UsernamePasswordToken;
import org.jsecurity.session.Session;
import org.jsecurity.subject.Subject;
import org.jsecurity.util.ThreadContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * TODO class JavaDoc
 *
 * @author Les Hazlewood
 * @since 0.2
 */
public class DefaultSecurityManagerTest {

    DefaultSecurityManager sm = null;

    @Before
    public void setup() {
        sm = new DefaultSecurityManager();
        ThreadContext.clear();
    }

    @After
    public void tearDown() {
        sm.destroy();
        ThreadContext.clear();
    }

    @Test
    public void testDefaultConfig() {
        InetAddress localhost = null;
        try {
            localhost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Subject subject = sm.getSubject();
        subject.login(new UsernamePasswordToken("guest", "guest", localhost));
        assert subject.isAuthenticated();
        assert "guest".equals(subject.getPrincipal());
        assert subject.hasRole("guest");
        Session session = subject.getSession();
        session.setAttribute("key", "value");
        subject.logout();
    }
}
