package JardinCollectif.tables;

import JardinCollectif.Connexion;

/*
 * Forcer certaines actions dans la BD
 */

public abstract class SQLTable {
	public abstract Boolean insert();

	public abstract Boolean update();

	public abstract Boolean destroy();

	public abstract Boolean fetch();
}
