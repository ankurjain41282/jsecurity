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
package org.jsecurity.realm;

import java.util.Collection;

/**
 * Enables JSecurity end-users to configure and initialize an application's {@link Realm Realm} instances
 * in any manner desired.
 *
 * <p>The <code>Realm</code> instances returned will used to construct the application's
 * {@link org.jsecurity.mgt.SecurityManager SecurityManager} instance.
 *
 * @since 0.9
 */
public interface RealmFactory {

    /**
     * Returns a collection of {@link Realm Realm} instances that will be used to construct
     * the application's SecurityManager instance.
     *
     * <p>The order of the collection is important.  The {@link org.jsecurity.mgt.SecurityManager SecurityManager}
     * implementation will consult the Realms during authentication (log-in) and authorization (access control)
     * operations in the collection's <b>iteration order</b>.  That is, the resulting collection's
     * {@link java.util.Iterator Iterator} determines the order in which Realms are used.
     *
     * @return the <code>Collection</code> of Realms that the application's <code>SecurityManager</code> will use
     *         for security data access.
     */
    Collection<Realm> getRealms();

}