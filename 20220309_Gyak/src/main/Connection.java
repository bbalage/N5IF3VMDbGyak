package main;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;

public class Connection {

    private LDAPConnection ldapConnection = new LDAPConnection();

    public LDAPConnection getLdapConnection() {
        return ldapConnection;
    }

    public void setLdapConnection(LDAPConnection ldapConnection) {
        this.ldapConnection = ldapConnection;
    }

    public static class ConnectionBuilder {
        private final String host;
        private int ldapPort = com.novell.ldap.LDAPConnection.DEFAULT_PORT;
        private int ldapVersion = com.novell.ldap.LDAPConnection.LDAP_V3;
        private final String loginDN;
        private final byte[] password;

        public ConnectionBuilder(String host, String loginDN, byte[] password) {
            this.host = host;
            this.loginDN = loginDN;
            this.password = password;
        }

        public ConnectionBuilder(String host, String loginDN, String password) {
            this(host, loginDN, password.getBytes());
        }

        public ConnectionBuilder ldapPort(int ldapPort) {
            this.ldapPort = ldapPort;
            return this;
        }

        public ConnectionBuilder ldapVersion(int ldapVersion) {
            this.ldapVersion = ldapVersion;
            return this;
        }

        public Connection build() throws LDAPException {
            Connection conn = new Connection();
            conn.getLdapConnection().connect(host, ldapPort);
            conn.getLdapConnection().bind(ldapVersion, loginDN, password);
            validateConnection(conn);
            return conn;
        }

        private void validateConnection(Connection conn) {
            assert conn.getLdapConnection().isConnected() : "Connection not connected!";
            assert conn.getLdapConnection().isBound() : "Connection unbound!";
        }
    }
}
