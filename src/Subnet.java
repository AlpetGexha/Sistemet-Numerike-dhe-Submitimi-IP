import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Subnet {

    public static List<String> SubnetIpList = new ArrayList<>();
    public static List<String> SubnetIpList2 = new ArrayList<>();
    static Random rand = new Random();
    public int IP;
    public int IPMask;
    public String IPType;
    public String IPClass;
    public int bitForMask;
    public String subnetMaskNumber;

    public Subnet(String IPinCIDR) throws NumberFormatException {

        String IPRules = "(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])"; // rregulli i IP Addreses
        String IPRulesResult = IPRules + "\\." + IPRules + "\\." + IPRules + "\\." + IPRules;

//        if (!IPinCIDR.matches(IPRulesResult)){
//            throw new NumberFormatException("IP Address is not valid");
//        }

        // Ndajra e ips dhe submnet maskes

        // Ndajra e ips dhe submnet maskes
        String[] str = IPinCIDR.split("/");
        if (str.length != 2)
            throw new NumberFormatException("Formati i CIDR eshte i pavlefshëm  '" + IPinCIDR + "', duhet te jete: xxx.xxx.xxx.xxx/xx");

        String symbolicIP = str[0]; // 192.168.0.1
        String symbolicCIDR = str[1]; // 29
        this.subnetMaskNumber = symbolicCIDR;

        int numericCIDR = new Integer(symbolicCIDR);
        if (numericCIDR >= 31) throw new NumberFormatException("CIDR nuk mund te jete më e madhe se 30");// 30 është
        // 255.255.255.252(11111111.11111111.11111111.11111100)
        /* IP */
        str = symbolicIP.split("\\.");

        if (str.length != 4) throw new NumberFormatException("IP-ja e pavlefshme: " + symbolicIP);

        int i = 24;
        this.IPMask = 0;

        for (String s : str) {
            // Deklarimi i tipit te IP-s

            if (Integer.parseInt(str[0]) == 10 && Integer.parseInt(str[1]) == 0 && Integer.parseInt(str[2]) == 0 && Integer.parseInt(str[3]) == 0) {
                this.IPClass = "A";
                this.IPType = "Private";
                this.bitForMask = 24;

            } else if (Integer.parseInt(str[0]) <= 126) {
                this.IPClass = "A";
                this.IPType = "Publike";
                this.bitForMask = 24;

            } else if (Integer.parseInt(str[0]) == 127 && Integer.parseInt(str[1]) == 0 && Integer.parseInt(str[2]) == 0 && Integer.parseInt(str[3]) == 1) {
                this.IPClass = "B";
                this.IPType = "Private";
                this.bitForMask = 16;

            } else if (Integer.parseInt(str[0]) <= 191) {
                this.IPClass = "B";
                this.IPType = "Publike";
                this.bitForMask = 16;

            } else if (Integer.parseInt(str[0]) == 192 && Integer.parseInt(str[1]) == 168 && Integer.parseInt(str[2]) == 0 && Integer.parseInt(str[3]) == 0) {
                this.IPClass = "C";
                this.IPType = "Private";
                this.bitForMask = 8;

            } else if (Integer.parseInt(str[0]) < 254) {
                this.IPClass = "C";
                this.IPType = "Publike";
                this.bitForMask = 8;
            }
            int value = Integer.parseInt(s);

            if (value != (value & 0xff)) {
                throw new NumberFormatException("IP-ja e pavlefshme: " + symbolicIP);
            }
            this.IP += value << i;
            i -= 8;
//			System.out.println(value);// 192 168 10 0

        }


        /* netmask from CIDR */
        if (numericCIDR < 8) throw new NumberFormatException("CIDR nuk mund te jete më e vogël se 8");
        this.IPMask = 0xffffffff;
        this.IPMask = this.IPMask << (32 - numericCIDR);
//		System.out.println(numericCIDR);// 29

//		SubmitIP submit = new SubmitIP("192.168.10.0/29"); //192 168 10 0 29
    }

    public static void main(String[] args) {

        String IP = "130.0.0.1/21";
        Subnet submit = new Subnet(IP);
        submit.getAllIPDetails(IP);
        submit.getAllSubetIP();
    }

    public static String reverseString(String value) {
        StringBuilder sb = new StringBuilder(value);
        sb.reverse();
        return sb.toString();
    }

    //    get Random IP with format x.x.x.x/x
    public static String getRandomIP() {
        return rand.nextInt(255) + "." + rand.nextInt(256) + "." + rand.nextInt(256) + "." + (rand.nextInt(256));
    }

    public void getAllIPDetails(String IP) {
        Subnet submit = new Subnet(IP);
//		System.out.println(submit.getIP() + " " + submit.getNetmask());
        System.out.println("Ipja \t\t\t  :" + IP);
        System.out.println("Maska e re \t\t  :" + submit.getNetmask());
        System.out.println("Numri i Subneteve :" + submit.getSubnetNumber());
        System.out.println("Numri i Hostave   :" + submit.getHostNumber());
        System.out.println("IP/Subnet \t\t  :" + submit.getIPSub());
        System.out.println("IP+Mask \t\t  :" + submit.getCIDR());
        System.out.println(submit.getHostAddressRange(0));
        System.out.println("Klasa\t\t\t  :" + submit.getIPClass());
        System.out.println("Tipi \t\t\t  :" + submit.getIPType());
        System.out.println("Maska Binare \t  :" + submit.getBinaryMask());
        System.out.println("IP-ja Binare \t  :" + submit.getBinaryIP());
    }

    public String getIPType() {
        return this.IPType;
    }

    public String getIPClass() {
        return this.IPClass;
    }

    public String getIP() {
        return converIPAddressToSymblic(this.IP);
    }

    public String getCIDR() {
        for (int i = 0; i < 32; i++) {
            if ((this.IPMask << i) == 0) break;

        }
        return converIPAddressToSymblic(this.IP & this.IPMask); // + "/" + i;
    }

    public String converIPAddressToSymblic(Integer ip) {

        StringBuilder sb = new StringBuilder(15);

        for (int i = 24; i > 0; i -= 8) {

            // process 3 bytes, from high order byte down.
            sb.append((ip >>> i) & 0xff);

            sb.append('.');
        }
        sb.append(ip & 0xff);

        return sb.toString();
    }

    public String getNetmask() {
        StringBuilder sb = new StringBuilder(15);

        for (int i = 24; i > 0; i -= 8) {

            // process 3 bytes, from high order byte down.
            sb.append((this.IPMask >>> i) & 0xff);

            sb.append('.');
        }
        sb.append(this.IPMask & 0xff);
        return sb.toString();
    }

    public String getBinaryMask() {
        return Integer.toBinaryString(this.IPMask);
    }

    public String getBinaryIP() {
        return Integer.toBinaryString(this.IP);
    }

    public String getHostAddressRange(int style) {
        int numberOfBits;
        for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {

            if ((this.IPMask << numberOfBits) == 0) break;
        }
        int numberOfIPs = 0;
        for (int n = 0; n < (32 - numberOfBits); n++) {

            numberOfIPs = numberOfIPs << 1;
            numberOfIPs = numberOfIPs | 0x01;

        }

        int baseIP = this.IP & this.IPMask;
        String firstIP = converIPAddressToSymblic(baseIP + 1);
        String lastIP = converIPAddressToSymblic(baseIP + numberOfIPs - 1);
        String broadcast = converIPAddressToSymblic(baseIP + (numberOfIPs - 1) + 1);

        if (style == 1) return firstIP + " - " + lastIP + "\t   " + broadcast;
        else
            return "IP-ja e Parë\t  :" + firstIP + "\n" + "IP-ja e Fundit    :" + lastIP + "\n" + "Broadcast\t\t  :" + broadcast;
    }

    public Long getHostNumber() {
        int numberOfBits;

        for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {
            if ((this.IPMask << numberOfBits) == 0) break;
        }

        Double x = Math.pow(2, (32 - numberOfBits)); // 2^n

        if (x == -1) x = 1D;

        return x.longValue();
    }

    public Long getIPSub() {
        return (this.getHostNumber() - 2);
    }

    public int getMaskZeorsNumber() {
        String y = reverseString(this.getBinaryMask()); // mund të funksionoj edhe pa reverseString
        //		int bit = content.length() - content.replaceAll("1", "").length();
        return y.length() - y.replaceAll("0", "").length();
    }

    public int getSubnetNumber() {

        double x = Math.pow(2, (this.bitForMask - this.getMaskZeorsNumber()));
        if (this.bitForMask == 8) {

        } else if (this.bitForMask == 16) {


        } else if (this.bitForMask == 24) {
        }

        if (x == -1) x = 1D;

        return (int) x;
    }

    public String getMyIpAddress() throws UnknownHostException {
        // Returns the instance of InetAddress containing
        // local host name and address
        InetAddress localhost = InetAddress.getLocalHost();
        System.out.println("System IP Address : " + (localhost.getHostAddress()).trim());

        // Find public IP address
        String systemipaddress = "";
        try {
            URL url_name = new URL("http://bot.whatismyipaddress.com");

            BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));

            // reads system IPAddress
            systemipaddress = sc.readLine().trim();
        } catch (Exception e) {
            systemipaddress = "Cannot Execute Properly";
        }
        return systemipaddress;
    }


    public void getAllSubetIP() {
        String[] str = this.getIP().split("\\.");

        if (this.bitForMask == 8) {
            int c = Integer.parseInt(str[3]) - Integer.parseInt(str[3]); // Barazo numrin e fundit me 0-ro
            SubnetIpList.add(str[0] + "." + str[1] + "." + str[2] + "." + c);
            for (int i = 2; c <= 255; i++) {
                c += this.getHostNumber();
                SubnetIpList.add(str[0] + "." + str[1] + "." + str[2] + "." + c);

            }

        } else if (this.bitForMask == 16) {

            int c = Integer.parseInt(str[3]) - Integer.parseInt(str[3]); // Barazo numrin e fundit me 0-ro
            SubnetIpList.add(str[0] + "." + str[1] + "." + str[2] + "." + c);
            for (int i = 2; c <= 255; i++) {
                c += this.getHostNumber();
                SubnetIpList.add(str[0] + "." + str[1] + "." + str[2] + "." + c);

            }


        } else if (this.bitForMask == 24) {
            int c = Integer.parseInt(str[3]) - Integer.parseInt(str[3]); // Barazo numrin e fundit me 0-ro
            SubnetIpList.add(str[0] + "." + str[1] + "." + str[2] + "." + c);
            for (int i = 2; c <= 255; i++) {
                c += this.getHostNumber();
                SubnetIpList.add(str[0] + "." + str[1] + "." + str[2] + "." + c);

            }


        }

        SubnetIpList.remove(SubnetIpList.size() - 1);
        int subnetNumber = 0;
        System.out.println("\nSub\t IP-ja\t\tIP-ja e parë\tIP-ja e fundit\t   Broadcast\n");
        for (String All : SubnetIpList) {
            subnetNumber++;
//			System.out.println(All + "/" + this.subnetMaskNumber);
            Subnet submit = new Subnet(All + "/" + this.subnetMaskNumber);

            System.out.println(subnetNumber + "\t" + All + "\t" + submit.getHostAddressRange(1));
        }
    }
}


