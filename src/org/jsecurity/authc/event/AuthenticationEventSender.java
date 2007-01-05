/*
 * Copyright (C) 2005 Les Hazlewood
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the
 *
 * Free Software Foundation, Inc.
 * 59 Temple Place, Suite 330
 * Boston, MA 02111-1307
 * USA
 *
 * Or, you may view it online at
 * http://www.opensource.org/licenses/lgpl-license.php
 */
package org.jsecurity.authc.event;

/**
 * Sends a {@link org.jsecurity.authc.event.AuthenticationEvent} to interested parties.  How
 * interested parties receive the events (e.g. via sychronous calls to listeners, 
 * publishing JMS messages, etc) is determined by the implementation.
 *
 * @since 0.1
 * @author Les Hazlewood
 */
public interface AuthenticationEventSender {

    /**
     * Sends the specified AuthenticationEvent to interested parties.  The underlying transport
     * mechanism is implementation-specific (e.g. synchronous Java Listeners, JMS, SOAP, etc).
     *
     * @param event the event to send.
     */
    void send( AuthenticationEvent event );

}