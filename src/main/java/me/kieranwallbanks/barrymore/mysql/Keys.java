/**
 * This class is generated by jOOQ
 */
package me.kieranwallbanks.barrymore.mysql;

/**
 * This class is generated by jOOQ.
 *
 * A class modelling foreign key relationships between tables of the <code>barrymore</code> 
 * schema
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.1.0" },
                            comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.Identity<me.kieranwallbanks.barrymore.mysql.tables.records.UsersRecord, Long> IDENTITY_USERS = Identities0.IDENTITY_USERS;

	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.UniqueKey<me.kieranwallbanks.barrymore.mysql.tables.records.UsersRecord> KEY_USERS_PRIMARY = UniqueKeys0.KEY_USERS_PRIMARY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------


	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class Identities0 extends org.jooq.impl.AbstractKeys {
		public static org.jooq.Identity<me.kieranwallbanks.barrymore.mysql.tables.records.UsersRecord, Long> IDENTITY_USERS = createIdentity(me.kieranwallbanks.barrymore.mysql.tables.Users.USERS, me.kieranwallbanks.barrymore.mysql.tables.Users.USERS.ID);
	}

	private static class UniqueKeys0 extends org.jooq.impl.AbstractKeys {
		public static final org.jooq.UniqueKey<me.kieranwallbanks.barrymore.mysql.tables.records.UsersRecord> KEY_USERS_PRIMARY = createUniqueKey(me.kieranwallbanks.barrymore.mysql.tables.Users.USERS, me.kieranwallbanks.barrymore.mysql.tables.Users.USERS.ID);
	}
}
