package JardinCollectif.tables;

public abstract class SQLTable {
	public abstract Boolean insert();

	public abstract Boolean update();

	public abstract Boolean destroy();

	public abstract Boolean fetch();
	
	public abstract String toString();
}
