package dev.resent.client;

public class ClientInfo {

	public static final String name = "Resent";
	public static final String version = "3.6";
	public static final String author = "Nitwit";
	public static final String release = Release.BETA.name;
	
	public enum Release {
		BETA("Beta"),
		STABLE("Stable");
		
		String name;
		Release(String name) {
			this.name = name;
		}
	}
	
}
