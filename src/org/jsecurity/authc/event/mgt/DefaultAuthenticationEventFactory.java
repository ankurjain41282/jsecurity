/*
 * Copyright (C) 2005-2007 Les Hazlewood
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
package org.jsecurity.authc.event.mgt;

import org.jsecurity.authc.Account;
import org.jsecurity.authc.AuthenticationException;
import org.jsecurity.authc.AuthenticationToken;
import org.jsecurity.authc.event.AuthenticationEvent;
import org.jsecurity.authc.event.FailedAuthenticationEvent;
import org.jsecurity.authc.event.LogoutEvent;
import org.jsecurity.authc.event.SuccessfulAuthenticationEvent;

/**
 * Simple principal-based implementation of the AuthenticationEventFactory interface.
 *
 * @since 0.1
 * @author Les Hazlewood
 */
public class DefaultAuthenticationEventFactory implements AuthenticationEventFactory {

    /**
     * Uses the principal found in the token to construct a {@link org.jsecurity.authc.event.FailedAuthenticationEvent}
     * @param token the authentication token submitted during the authentication attempt
     * @param cause the cause of the failed authentication attempt
     * @return a {@link org.jsecurity.authc.event.FailedAuthenticationEvent} to send due to the failed attempt.
     */
    public AuthenticationEvent createFailureEvent( AuthenticationToken token, AuthenticationException cause ) {
        return new FailedAuthenticationEvent( token, cause );
    }

    /**
     * Uses the principal found in the <em>Account</em> parameter (not the authentication token) to
     * construct a {@link org.jsecurity.authc.event.SuccessfulAuthenticationEvent}
     * @param token the authentication token submitted during the authentication attempt.
     * @param account the account data constructed due to the successful attempt.
     * @return a {@link org.jsecurity.authc.event.SuccessfulAuthenticationEvent} to send due to the successful attempt.
     */
    public AuthenticationEvent createSuccessEvent( AuthenticationToken token, Account account ) {
        return new SuccessfulAuthenticationEvent( token, account );
    }

    public AuthenticationEvent createLogoutEvent(Object subjectPrincipal) {
        return new LogoutEvent( subjectPrincipal );
    }
}