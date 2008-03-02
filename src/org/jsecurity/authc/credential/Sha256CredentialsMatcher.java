/*
 * Copyright (C) 2005-2008 Les Hazlewood
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
package org.jsecurity.authc.credential;

import org.jsecurity.crypto.hash.AbstractHash;
import org.jsecurity.crypto.hash.Hash;
import org.jsecurity.crypto.hash.Sha256Hash;

/**
 * <tt>HashedCredentialsMatcher</tt> implementation that expects the stored <tt>Account</tt> credentials to be
 * SHA-256 hashed.
 *
 * @author Les Hazlewood
 * @since 0.9
 */
public class Sha256CredentialsMatcher extends HashedCredentialsMatcher {

    protected AbstractHash newHashInstance() {
        return new Sha256Hash();
    }

    protected Hash getProvidedCredentialsHash(Object credentials, Object salt, int hashIterations ) {
        return new Sha256Hash( toBytes( credentials ), salt, hashIterations );
    }
}