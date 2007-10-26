package org.jsecurity.web.servlet;

import org.jsecurity.context.SecurityContext;
import org.jsecurity.session.InvalidSessionException;
import org.jsecurity.session.Session;
import org.jsecurity.util.ThreadContext;
import org.jsecurity.web.support.DefaultWebSessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionContext;
import java.util.*;

/**
 * Wrapper class that uses a JSecurity session under the hood for all session operations instead of the
 * Servlet Container's session mechanism.  This is preferred in heterogeneous client environments where the Session
 * is used on both the business tier as well as in multiple client technologies (web, swing, flash, etc).
 *
 * @since 0.2
 * @author Les Hazlewood
 */
public class JSecurityHttpSession implements HttpSession {

    public static final String DEFAULT_SESSION_ID_NAME = "JSECSESSIONID";

    private static final Enumeration EMPTY_ENUMERATION = new Enumeration() {
        public boolean hasMoreElements() {
            return false;
        }

        public Object nextElement() {
            return null;
        }
    };

    private static final HttpSessionContext HTTP_SESSION_CONTEXT = new HttpSessionContext() {
        public HttpSession getSession( String s ) {
            return null;
        }

        public Enumeration getIds() {
            return EMPTY_ENUMERATION;
        }
    };

    protected ServletContext servletContext = null;

    public JSecurityHttpSession( ServletContext servletContext ) {
        this.servletContext = servletContext;
    }

    protected Session getSession() {
        return getSession( true );
    }

    protected Session getSession( boolean create ) {
        SecurityContext sc = ThreadContext.getSecurityContext();
        return ( sc != null ? sc.getSession( create ) : null );
    }

    public long getCreationTime() {
        try {
            return getSession().getStartTimestamp().getTime();
        } catch ( Exception e ) {
            throw new IllegalStateException( e );
        }
    }

    public String getId() {
        return getSession().getSessionId().toString();
    }

    public long getLastAccessedTime() {
        return getSession().getLastAccessTime().getTime();
    }

    public ServletContext getServletContext() {
        return this.servletContext;
    }

    public void setMaxInactiveInterval( int i ) {
        try {
            getSession().setTimeout( i * 1000 );
        } catch ( InvalidSessionException e ) {
            throw new IllegalStateException( e );
        }
    }

    public int getMaxInactiveInterval() {
        try {
            return ( new Long( getSession().getTimeout() / 1000 ) ).intValue();
        } catch ( InvalidSessionException e ) {
            throw new IllegalStateException( e );
        }
    }

    public HttpSessionContext getSessionContext() {
        return HTTP_SESSION_CONTEXT;
    }

    public Object getAttribute( String s ) {
        try {
            return getSession().getAttribute( s );
        } catch ( InvalidSessionException e ) {
            throw new IllegalStateException( e );
        }
    }

    public Object getValue( String s ) {
        return getAttribute( s );
    }

    protected Set<String> getKeyNames() {
        Collection<Object> keySet = null;
        try {
            keySet = getSession().getAttributeKeys();
        } catch ( InvalidSessionException e ) {
            throw new IllegalStateException( e );
        }
        Set<String> keyNames = null;
        if ( keySet != null && !keySet.isEmpty() ) {
            keyNames = new HashSet<String>( keySet.size() );
            for ( Object o : keySet ) {
                keyNames.add( o.toString() );
            }
        } else {
            keyNames = Collections.EMPTY_SET;
        }
        return keyNames;
    }

    public Enumeration getAttributeNames() {
        Set<String> keyNames = getKeyNames();
        final Iterator iterator = keyNames.iterator();
        return new Enumeration() {
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            public Object nextElement() {
                return iterator.next();
            }
        };
    }

    public String[] getValueNames() {
        Set<String> keyNames = getKeyNames();
        String[] array = new String[keyNames.size()];
        if ( keyNames.size() > 0 ) {
            array = keyNames.toArray( array );
        }
        return array;
    }

    protected void beforeBound( String s, Object o ) {
        if ( o instanceof HttpSessionBindingListener ) {
            HttpSessionBindingListener listener = (HttpSessionBindingListener)o;
            HttpSessionBindingEvent event = new HttpSessionBindingEvent( this, s, o );
            listener.valueBound( event );
        }
    }

    protected void afterUnbound( String s, Object o ) {
        if ( o != null && o instanceof HttpSessionBindingListener ) {
            HttpSessionBindingListener listener = (HttpSessionBindingListener)o;
            HttpSessionBindingEvent event = new HttpSessionBindingEvent( this, s, o );
            listener.valueUnbound( event );
        }
    }

    public void setAttribute( String s, Object o ) {
        beforeBound( s, o );
        try {
            getSession().setAttribute( s, o );
        } catch ( InvalidSessionException e ) {
            afterUnbound( s, o );
            throw new IllegalStateException( e );
        }
    }

    public void putValue( String s, Object o ) {
        setAttribute( s, o );
    }

    public void removeAttribute( String s ) {
        try {
            Object attribute = getSession().removeAttribute( s );
            afterUnbound( s, attribute );
        } catch ( InvalidSessionException e ) {
            throw new IllegalStateException( e );
        }
    }

    public void removeValue( String s ) {
        removeAttribute( s );
    }

    public void invalidate() {
        try {
            getSession().stop();
        } catch ( InvalidSessionException e ) {
            throw new IllegalStateException( e );
        }
    }

    public boolean isNew() {
        Boolean value = (Boolean)ThreadContext.get( DefaultWebSessionFactory.REQUEST_REFERENCED_SESSION_IS_NEW );
        return value != null && value.equals( Boolean.TRUE );
    }
}