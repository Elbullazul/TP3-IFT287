package JardinCollectif.tables;

/*
 * Forcer certaines actions dans la BD
 */

public abstract class SQLTable {
	public abstract Boolean create();
	public abstract Boolean update();
	public abstract Boolean destroy();
}
