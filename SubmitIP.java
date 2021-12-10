package SistemetNumerike;

import java.util.*;

public class SubmitIP {

	public int mainIPAddress; // ipja kryesore
	public int subnetMask; // ip maska

	public String typeIP; // Tipi i IP-s (A,B,C)
	public String klasaIP; // Klasa e IP-s (Publike ose Private)
	public int bitForMask; // Bita per secilen Maskë (A-24, B-16, C-8)

	public String subnetMaskNumber;

	/**
	 * Formati i IP-s në Classless inter-domain routing (CIDR) p.sh
	 * SubmitIP("192.168.0.1/28");
	 *
	 *
	 * @param IPinCIDR {@code xxx.xxx.xxx.xxx/xx}<br>
	 *                 IP-Address/SubnetMask
	 */
	public SubmitIP(String IPinCIDR) throws NumberFormatException {

		// Ndajra e ips dhe submnet maskes
		String[] str = IPinCIDR.split("\\/");
		if (str.length != 2)
			throw new NumberFormatException(
					"Formati i CIDR eshte i pavlefshëm  '" + IPinCIDR + "', duhet te jete: xxx.xxx.xxx.xxx/xx");

		String symbolicIP = str[0]; // 192.168.0.1
		String symbolicCIDR = str[1]; // 29
		this.subnetMaskNumber = symbolicCIDR;

		Integer numericCIDR = new Integer(symbolicCIDR);
		if (numericCIDR >= 31)
			throw new NumberFormatException("CIDR nuk mund te jete më e madhe se 30");// 30 është
																						// 255.255.255.252(11111111.11111111.11111111.11111100)
		/* IP */
		str = symbolicIP.split("\\.");

		if (str.length != 4)
			throw new NumberFormatException("IP-ja e pavlefshme: " + symbolicIP);

		int i = 24;
		this.subnetMask = 0;

		for (int n = 0; n < str.length; n++) {
			// Deklarimi i tipit te IP-s

			if (Integer.parseInt(str[0]) == 10) {
				this.klasaIP = "A";
				this.typeIP = "Private";
				this.bitForMask = 24;
			} else if (Integer.parseInt(str[0]) <= 126) {
				this.klasaIP = "A";
				this.typeIP = "Publike";
				this.bitForMask = 24;
			} else if (Integer.parseInt(str[0]) == 127 && Integer.parseInt(str[3]) == 1) {
				this.klasaIP = "B";
				this.typeIP = "Private";
				this.bitForMask = 16;

			} else if (Integer.parseInt(str[0]) <= 191) {
				this.klasaIP = "B";
				this.typeIP = "Publike";
				this.bitForMask = 16;

			} else if (Integer.parseInt(str[0]) == 192 && Integer.parseInt(str[1]) == 168) {
				this.klasaIP = "C";
				this.typeIP = "Private";
				this.bitForMask = 8;

			} else if (Integer.parseInt(str[0]) < 254) {
				this.klasaIP = "C";
				this.typeIP = "Publike";
				this.bitForMask = 8;
			}

			int value = Integer.parseInt(str[n]);

			if (value != (value & 0xff)) {
				throw new NumberFormatException("IP-ja e pavlefshme: " + symbolicIP);
			}
			this.mainIPAddress += value << i;
			i -= 8;
//			System.out.println(value);// 192 168 10 0 

		}

		for (int n = 0; n < str.length; n++) {

		}

		/* netmask from CIDR */
		if (numericCIDR < 8)
			throw new NumberFormatException("CIDR nuk mund te jete më e vogël se 8");
		this.subnetMask = 0xffffffff;
		this.subnetMask = this.subnetMask << (32 - numericCIDR);
//		System.out.println(numericCIDR);// 29

//		SubmitIP submit = new SubmitIP("192.168.10.0/29"); //192 168 10 0 29

	}

	/**
	 * 
	 * @return Tipin e IP-s Private Ose Publike
	 */
	public String getIPType() {
		return this.typeIP;
	}

	/**
	 * 
	 * @return Klasen e IP-s (A B C)
	 */

	public String getIPClass() {
		return this.klasaIP;
	}

	/**
	 * Merr IP-n nga from xxx.xxx.xxx.xxx
	 *
	 * @return
	 */

	public String getIP() {
		return converIPAddressToSymblic(this.mainIPAddress);
	}

	private String converIPAddressToSymblic(Integer ip) {
		/*
		 * Klasa StringBuffer përdoret për të krijuar objekte të vargut të ndryshueshëm
		 * (të modifikueshëm). Klasa StringBuffer në Java është e njëjtë me klasën
		 * String përveçse është e ndryshueshme, pra mund të ndryshohet
		 * 
		 * & operator: Ai vlerëson të gjitha kushtet edhe nëse ato janë false. Kështu,
		 * çdo ndryshim në vlerat e të dhënave për shkak të kushteve do të pasqyrohet
		 * vetëm në këtë rast. Përdoret vetem te vleart binare p.sh
		 * 
		 * 12 = 00001100 (In Binary) 25 = 00011001 (In Binary) 00001100 & 00011001 =
		 * 00011101 = 8 (In Decimal)
		 */
		StringBuffer sb = new StringBuffer(15);

		for (int i = 24; i > 0; i -= 8) {

			// process 3 bytes, from high order byte down.
			sb.append(Integer.toString((ip >>> i) & 0xff));

			sb.append('.');
		}
		sb.append(Integer.toString(ip & 0xff));

		return sb.toString();
	}

	/**
	 * Merr Masken nga xxx.xxx.xxx.xxx
	 *
	 * @return Submit Masket nga /xx
	 */

	public String getNetmask() {
		StringBuffer sb = new StringBuffer(15);

		for (int i = 24; i > 0; i -= 8) {

			// process 3 bytes, from high order byte down.
			sb.append(Integer.toString((this.subnetMask >>> i) & 0xff));

			sb.append('.');
		}
		sb.append(Integer.toString(this.subnetMask & 0xff));
		return sb.toString();
	}

	/**
	 * Kthe Subnet Maken në numer Binar
	 * 
	 * @return Binary String (Mask on Binary)
	 */
	public String getBinaryMask() {

		return Integer.toBinaryString(this.subnetMask);
	}
//	public String getBinaryMask() {
//		StringBuffer sb = new StringBuffer(15);
//
//		
//		for (int i = 24; i > 0; i -= 8) {
//
//			// process 3 bytes, from high order byte down.
//			sb.append(Integer.toBinaryString((this.subnetMask >>> i) & 0xff));
//
//			sb.append('.');
//		}
//		sb.append(Integer.toBinaryString(this.subnetMask & 0xff));
//		return sb.toString();
//		
//	}

	/**
	 * Kthe IP-n në numer Binar
	 * 
	 * @return Binary String (IP on Binary)
	 */
	public String getBinaryIP() {
		return Integer.toBinaryString(this.mainIPAddress);
	}

	/**
	 *
	 * Merr IP-n and Submitmasket në CIDR form, xxx.xxx.xxx.xxx/xx
	 * 
	 * Mblidhe IPAddressen me Submet Masket
	 *
	 * @return Default Gateway(Subnet)
	 */

	public String getCIDR() {
		for (int i = 0; i < 32; i++) {
			if ((this.subnetMask << i) == 0)
				break;

		}
		return converIPAddressToSymblic(this.mainIPAddress & this.subnetMask); // + "/" + i;
	}

	/**
	 * Numrin e ipjave ne subnet
	 *
	 * @return IP-n e parë, IP-n e fundit dhe Broadcast IP
	 */
	public String getHostAddressRange() {

		int numberOfBits;
		for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {

			if ((this.subnetMask << numberOfBits) == 0)
				break;
		}
		Integer numberOfIPs = 0;
		for (int n = 0; n < (32 - numberOfBits); n++) {

			numberOfIPs = numberOfIPs << 1;
			numberOfIPs = numberOfIPs | 0x01;

		}

		Integer baseIP = this.mainIPAddress & this.subnetMask;
		String firstIP = converIPAddressToSymblic(baseIP + 1);
		String lastIP = converIPAddressToSymblic(baseIP + numberOfIPs - 1);
		String broadcast = converIPAddressToSymblic(baseIP + (numberOfIPs - 1) + 1);

		return "IP-ja e Parë\t  :" + firstIP + "\n" + "IP-ja e Fundit    :" + lastIP + "\n" + "Broadcast:\t  :"
				+ broadcast;
	}

	/**
	 * Kthen numrin e hostave të IP-s
	 *
	 * @return Nurmin e hostave
	 */
	public Long getHostNumber() {
		int numberOfBits;

		for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {
			if ((this.subnetMask << numberOfBits) == 0)
				break;
		}

		Double x = Math.pow(2, (32 - numberOfBits)); // 2^n

		if (x == -1)
			x = 1D;

		return x.longValue();
	}

	/**
	 * Numri i IP-ja valide
	 * 
	 * @return Nurmin e Ipva Valide
	 */
	public Long getIPSub() {
		return (this.getHostNumber() - 2);
	}

	/**
	 * E kthen mprapsh Strigun
	 * 
	 * @return Textin Mbrapsht
	 */
	public static String reverseString(String value) {
		StringBuilder sb = new StringBuilder(value);
		sb.reverse();
		return sb.toString();
	}

	/**
	 * 
	 * @return Numrin e subneteve në IP
	 */
	public int getSubnetNumber() {
		String y = reverseString(this.getBinaryMask()); // mund të funksionoj edhe pa reverseString
		int zeros = y.length() - y.replaceAll("0", "").length();
//		int bit = content.length() - content.replaceAll("1", "").length();

		Double x = Math.pow(2, (this.bitForMask - zeros));

		if (x == -1)
			x = 1D;

		return x.intValue();
	}

	public static void main(String[] args) {
		String IP = "127.0.0.1/22";
		SubmitIP submit = new SubmitIP(IP);
//		System.out.println(submit.getIP() + " " + submit.getNetmask());
		System.out.println("Ipja \t\t  :" + IP);
		System.out.println("Maska e re \t  :" 		+ submit.getNetmask());
		System.out.println("Numri i Subneteve :" 	+ submit.getSubnetNumber());
		System.out.println("Numri i Hostave   :" 	+ submit.getHostNumber());
		System.out.println("IP/Subnet \t  :" 		+ submit.getIPSub());
		System.out.println("IP+Mask \t  :" 			+ submit.getCIDR());
		System.out.println(							  submit.getHostAddressRange());
		System.out.println("Klasa\t\t  :" 			+ submit.getIPClass());
		System.out.println("Tipi \t\t  :" 			+ submit.getIPType());
		System.out.println("Maska Binare \t  :"		+ submit.getBinaryMask());
		System.out.println("IP-ja Binare \t  :" 	+ submit.getBinaryIP());
	}

}
