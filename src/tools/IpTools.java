package tools;

import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class IpTools {

    private static Pattern VALID_IPV4_PATTERN = null;
    private static Pattern VALID_IPV6_PATTERN = null;
    private static final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    private static final String ipv6Pattern = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";

    static {
        try {
            VALID_IPV4_PATTERN = Pattern.compile(ipv4Pattern, Pattern.CASE_INSENSITIVE);
            VALID_IPV6_PATTERN = Pattern.compile(ipv6Pattern, Pattern.CASE_INSENSITIVE);
        } catch (PatternSyntaxException e) {
            //logger.severe("Unable to compile pattern", e);
        }
    }

    /**
     * Determine if the given string is a valid IPv4 or IPv6 address. This
     * method uses pattern matching to see if the given string could be a valid
     * IP address.
     *
     * @param ipAddress A string that is to be examined to verify whether or not
     * it could be a valid IP address.
     * @return true if the string is a value that is a valid IP
     * address, false otherwise.
     */
    public static boolean isIpAddress(String ipAddress) {

        Matcher m1 = IpTools.VALID_IPV4_PATTERN.matcher(ipAddress);
        if (m1.matches()) {
            return true;
        }
        Matcher m2 = IpTools.VALID_IPV6_PATTERN.matcher(ipAddress);
        return m2.matches();
    }

    /**
     * Given a socket, gets client's IP address.
     *
     * @param socket
     * @return 
     */
    public static String clientAddress(final Socket socket) {
        return socket.getInetAddress().toString().split("/")[1];
    }
}
