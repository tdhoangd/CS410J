package edu.pdx.cs410J.thhoang;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    /**
     *
     * @param count
     * @return
     */
    public static String getMappingCount( int count )
    {
        return String.format( "Server contains %d phone bills", count );
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public static String formatKeyValuePair( String key, String value )
    {
        return String.format("  %s -> %s", key, value);
    }

    /**
     *
     * @param parameterName
     * @return
     */
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public static String mappedKeyValue( String key, String value )
    {
        return String.format( "Mapped %s to %s", key, value );
    }
}
