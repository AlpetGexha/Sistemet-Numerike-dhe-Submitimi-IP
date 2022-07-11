import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Converts {
    static Random rand = new Random();

    //Binars
    public static String binToDec(String binar) {

        int bin = Integer.parseInt(binar, 2);

        return Integer.toString(bin);
    }

    public static String binToOct(String bin) {

        int oct = Integer.parseInt(bin, 2);

        return Integer.toOctalString(oct);
    }

    public static String binToHex(String bin) {
        int decimal = Integer.parseInt(bin, 2);

        return Integer.toHexString(decimal);
    }

    //    Octals
    public static int octToDec(String octal) {

        return Integer.parseInt(octal, 8);
    }

    public static String octToBin(String octal) {
        int decimal = Integer.parseInt(octal, 8);

        return Integer.toBinaryString(decimal);
    }

    public static String octToHex(String octal) {
        int decimal = Integer.parseInt(octal, 8);

        return Integer.toHexString(decimal);
    }

    //    Decimal
    public static String decToBin(String decimal) {
        int decimal1 = Integer.parseInt(decimal);

        return Integer.toBinaryString(decimal1);
    }

    public static int decToOct(String decimal) {
        int decimal1 = Integer.parseInt(decimal);
        String oct = Integer.toOctalString(decimal1);

        return Integer.parseInt(oct);
    }

    public static String decToHex(String decimal) {
        int decimal1 = Integer.parseInt(decimal);

        return Integer.toHexString(decimal1);
    }

    //    Hexadecimal
    public static int hexToDec(String hex) {

        return Integer.parseInt(hex, 16);
    }

    public static int hexToOct(String hex) {
        int decimal = Integer.parseInt(hex, 16);
        String oct = Integer.toOctalString(decimal);

        return Integer.parseInt(oct);
    }

    //        Get  random binary number with length of n
    public static String getRandomBinary(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(rand.nextInt(2));
        }
        return sb.toString();
    }

    //        Get  random octal number with length of n
    public static String getRandomOctal(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(rand.nextInt(8));
        }
        return sb.toString();
    }

    //        Get  random decimal number with length of n
    public static String getRandomDecimal(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    //        Get  random hexadecimal number with length of n
    public static String getRandomHexadecimal(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(rand.nextInt(16));
        }
        return sb.toString();
    }

    //    get Random mac address
    public static String getRandomMacAddress() {
        byte[] macAddr = new byte[6];
        rand.nextBytes(macAddr);

        macAddr[0] = (byte) (macAddr[0] & (byte) 254);  //zeroing last 2 bytes to make it unicast and locally adminstrated

        StringBuilder sb = new StringBuilder(18);
        for (byte b : macAddr) {

            if (sb.length() > 0) sb.append(":");

            sb.append(String.format("%02x", b));
        }


        return sb.toString();
    }

    public static String[] splitMacAddress(String mac) throws Exception {
        if (!isMacAddressValid(mac)) {
            throw new Exception("Mac " + mac + " its not valid");
        }

        return mac.split(":");
    }

    public static boolean isMacAddressValid(String mac) {
        Pattern p = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})|([0-9a-fA-F]{4}\\\\.[0-9a-fA-F]{4}\\\\.[0-9a-fA-F]{4})$");
        Matcher m = p.matcher(mac);
        return m.find();
    }

    public static String binary8Bit(String x) {
        StringBuilder result = new StringBuilder();

        for (int i = 7; i >= 0; i--) {
            int mask = 1 << i;
            result.append((Integer.parseInt(x) & mask) != 0 ? 1 : 0);
        }

        return result.toString();
    }

    public void getMacAddressConvertToAll(String MAC) {
        try {
            String[] macAddress = splitMacAddress(MAC);
            System.out.println();

            System.out.println("Mac Address:   " + MAC);

            StringBuilder bin = new StringBuilder();
            StringBuilder dec = new StringBuilder();
            StringBuilder oct = new StringBuilder();
            for (String mac : macAddress) {
                bin.append(this.hexToBin(mac)).append(":");
                oct.append(hexToOct(mac)).append(":");
                dec.append(hexToDec(mac)).append(":");
            }
            System.out.println("Binar:        " + bin.deleteCharAt(bin.length() - 1));
            System.out.println("Oktal:        " + oct.deleteCharAt(oct.length() - 1));
            System.out.println("Decimal:      " + dec.deleteCharAt(dec.length() - 1));
            System.out.println("Hexadecimal:  " + MAC);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String hexToBin(String hex) {
        long longs = Long.parseLong(hex, 16);

        return Long.toBinaryString(longs);
    }

    public String getRandomIP() {
        return rand.nextInt(255) + "." + rand.nextInt(256) + "." + rand.nextInt(256) + "." + (rand.nextInt(256));
    }

    public void getBinary(String bin) {
//        ceck if the string is binarys
        if (!bin.matches("[0-1]+")) {
            throw new IllegalArgumentException("Numri \"" + bin + "\" nuk është numër binar (0 ose 1) \n");
        }

        System.out.println("Octal: " + binToOct(bin) + " (8)");
        System.out.println("Decimal: " + binToDec(bin) + " (10)");
        System.out.println("Hexadecimal: " + binToHex(bin) + " (16)");
    }

    public void getOctal(String octal) {
//        ceck if the string is octal
        if (!octal.matches("[0-7]+")) {
            throw new IllegalArgumentException("Numri \"" + octal + "\" nuk është numër octal (0-7) \n");
        }

        System.out.println("Binary: " + octToBin(octal) + " (2)");
        System.out.println("Decimal: " + octToDec(octal) + " (10)");
        System.out.println("Hexadecimal: " + octToHex(octal) + " (16)");
    }

    public void getDecimal(String decimal) {
//        ceck if the string is decimal
        if (!decimal.matches("\\d+")) {
            throw new IllegalArgumentException("Numri \"" + decimal + "\" nuk është numër decimal (0-9) \n");
        }

        System.out.println("Binary: " + decToBin(decimal) + " (2)");
        System.out.println("Octal: " + decToOct(decimal) + " (8)");
        System.out.println("Hexadecimal: " + decToHex(decimal) + " (16)");
    }

//    public String getBinary8Bits(String bin) {
//        for (int i = 0; i < (8 - bin.length()); i++) {
//            bin += "0";
//        }
//
//        return bin;
//    }
//
//    public void getBinary8Bits(StringBuilder bin) {
//        for (int i = 0; i < (8 - bin.length()); i++) {
//            bin.append("0");
//        }
//    }

    public void getHexadecimal(String hex) {
//        ceck if the string is hexadecimal

        if (!hex.matches("[0-9a-fA-F]+")) {
            throw new IllegalArgumentException("Numri \"" + hex + "\" nuk është numër hexadecimal (0-9 A-F) \n");
        }

        System.out.println("Binary: " + hexToBin(hex) + " (2)");
        System.out.println("Octal: " + hexToOct(hex) + " (8)");
        System.out.println("Decimal: " + hexToDec(hex) + "(10)");
    }

    public void getIPConvertToAll(String IP) {
        String IPRules = "(\\d{1,2}|([01])\\d{2}|2[0-4]\\d|25[0-5])"; // rregulli i IP Addreses
        String IPRulesResult = IPRules + "\\." + IPRules + "\\." + IPRules + "\\." + IPRules;
        Pattern p = Pattern.compile(IPRulesResult);
        Matcher m = p.matcher(IP);
        if (!m.find()) {
            throw new IllegalArgumentException("IP Adresën \"" + IP + "\" nuk është IP Adresë valide \n");
        }

        String[] IPSplit = IP.split("\\.");

        System.out.println();

        System.out.println("IP address:   " + IP);

        StringBuilder bin = new StringBuilder();
        StringBuilder oct = new StringBuilder();
        StringBuilder hex = new StringBuilder();
        for (String IPAddress : IPSplit) {
            bin.append(binary8Bit(IPAddress)).append(".");
            oct.append(decToOct(IPAddress)).append(".");
            hex.append(decToHex(IPAddress)).append(".");
        }

        System.out.println("Binar:        " + bin.deleteCharAt(bin.length() - 1));
        System.out.println("Oktal:        " + oct.deleteCharAt(oct.length() - 1));
        System.out.println("Decimal:      " + IP);
        System.out.println("Hexadecimal:  " + hex.deleteCharAt(hex.length() - 1));
    }


}
