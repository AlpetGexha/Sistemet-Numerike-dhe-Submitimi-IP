package SistemetNumerike;

public class SubmitIP {

	public int mainIPAddress; // ipja kryesore
	public int subnetMask; // ip maska

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
			int value = Integer.parseInt(str[n]);

			if (value != (value & 0xff)) {
				throw new NumberFormatException("IP-ja e pavlefshme: " + symbolicIP);
			}
			this.mainIPAddress += value << i;
			i -= 8;
//			System.out.println(value);// 192 168 10 0 

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
		 * 12 = 00001100 (In Binary) 25 = 00011001 (In Binary) 
		 * 00001100 & 00011001 = 00011101 = 8 (In Decimal)
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
	 *
	 * Merr IP-n and Submitmasket në CIDR form, xxx.xxx.xxx.xxx/xx
	 * 
	 * Mblidhe IPAddressen me Submet Masket
	 *
	 * @return  Default Gateway(Subnet)
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
		
		return "IP-ja e parë: \t " + firstIP + "\n" + "IP-ja e Fundit:  " + lastIP + "\n" + "Broadcast: \t " + broadcast;
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

	public static void main(String[] args) {
		String IP = "198.143.50.3/24";
		SubmitIP submit = new SubmitIP(IP);
//		System.out.println(submit.getIP() + " "+ submit.getNetmask());
		System.out.println("Ipja: \t\t " + IP);
		System.out.println("Maska e re: \t " + submit.getNetmask());
		System.out.println("Numri i Hostave: " + submit.getHostNumber());
		System.out.println("IP/Subnet: \t " + submit.getIPSub());
		System.out.println("IP+Mask: \t " + submit.getCIDR());
		System.out.println(submit.getHostAddressRange());

	}

}
