package de.accso.masspingpong;

/**
 * Vert.x example 'mass-pingpong'
 *    Helper class with some constants
 * 
 * @author lehmann, schaal, grammes
 */
public interface Constants {
	// bus addresses
	public static final String BUS_ADDRESS_PING = "ping-address";
	public static final String BUS_ADDRESS_PONG = "pong-address";
	// name of the shared-data map
	public static final String COUNTING_MAP        = "sharedData-countingMap";
	// attributes in Json object
	public static final String JSON_ATTR_ID        = "id";
	public static final String JSON_ATTR_TIMESTAMP = "timestamp";
}