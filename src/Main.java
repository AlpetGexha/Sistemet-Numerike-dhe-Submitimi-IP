import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Random rand = new Random();
        Converts c = new Converts();

        System.out.println("> Aplikacioni për Sistemin e Numrave Dhe Submitimin e IP-jave");
        shfaqOpsionet();
        int choise;

//		Studenti std = new Studenti();

        while ((choise = s.nextInt()) > 0) {
            if (choise == 1) {
                boolean choise1 = false;
                while (!choise1) {
                    try {
                        System.out.print("Shkruani Numrin Binar: ");
                        String binar = stringFormatWithNoCharset(s);
                        c.getBinary(binar);
                        choise1 = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }

            } else if (choise == 2) {
                boolean choise2 = false;
                while (!choise2) {
                    try {
                        System.out.print("Shruani Numrin Octal: ");
                        String octal = stringFormatWithNoCharset(s);
                        c.getOctal(octal);
                        choise2 = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }

            } else if (choise == 3) {
                boolean choise3 = false;
                while (!choise3) {
                    try {
                        System.out.print("Shruani Numrin Decimal: ");
                        String decimal = stringFormatWithNoCharset(s);
                        c.getDecimal(decimal);
                        choise3 = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else if (choise == 4) {
                boolean choise4 = false;
                while (!choise4) {
                    try {
                        System.out.print("Shruani Numrin Hexadecimal: ");
                        String hexadecimal = stringFormatWithNoSpace(s);
                        c.getHexadecimal(hexadecimal);
                        choise4 = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }

            } else if (choise == 5) {

                int n = rand.nextInt(4) + 3;
                String binary = Converts.getRandomBinary(n + 4);
                System.out.println("Binary: " + binary + " (2)");

                String octal = Converts.getRandomOctal(n);
                System.out.println("Octal: " + octal + " (8)");

                String decimal = Converts.getRandomDecimal(n);
                System.out.println("Decimal: " + decimal + " (10)");

                String hexadecimal = Converts.getRandomHexadecimal(rand.nextInt(3) + 2);
                System.out.println("Hexadecimal: " + hexadecimal + " (16)");


                System.out.print("\nShtyp 0 për rezultatet: ");
                int choise_for_result = s.nextInt();

                if (choise_for_result == 0) {
                    System.out.println("\n" + "Binar: " + binary);
                    c.getBinary(binary);

                    System.out.println("\n" + "Octal: " + octal);
                    c.getOctal(octal);

                    System.out.println("\n" + "Decimal: " + decimal);
                    c.getDecimal(decimal);

                    System.out.println("\n" + "Hexadecimal: " + hexadecimal);
                    c.getHexadecimal(hexadecimal);
                }

            } else if (choise == 6) {

                boolean choise6 = false;
                while (!choise6) {
                    try {
                        System.out.println("Shruani numrin IP xxx.xxx.xxx.xxx: ");

                        if (s.hasNext()) {
                            String IP = s.nextLine().replaceAll("\\s+", "");
                            c.getIPConvertToAll(IP);
                        }

                        choise6 = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }

                }

            } else if (choise == 7) {
                String IP = c.getRandomIP();

                c.getIPConvertToAll(IP);

            } else if (choise == 8) {
                String mac = Converts.getRandomMacAddress();
                c.getMacAddressConvertToAll(mac);

            } else if (choise == 99) {


            }

            System.out.println(">");
            System.out.println(">");
            System.out.println(">");
            System.out.println(">");
            System.out.println("> Për të vazhduar shtypeni numrin nëntë (9)");
            System.out.println("> Për të mbyllur shtypeni numrin nëntë (0)");
            System.out.print(": ");
            if (s.nextInt() == 9) shfaqOpsionet();
            else break;
        }

        s.close();

        {
            System.out.println("> Aplikacioni përfundoi");
            System.exit(1);
        }

    }


    public static void shfaqOpsionet() {

        System.out.print("""

                >
                >
                >
                >

                Shtyp 1 për numër Binar\s
                Shtyp 2 për numër Oktal\s
                Shtyp 3 për numër Decimal\s
                Shtyp 4 për numër Hexadecimal\s
                Shtyp 5 për të gjeneruar numra Binar, Oktal, Deciaml dhe Hexadecimal \s
                Shtyp 6 për kthyer IP Address-n në numër Binar, Oktal, Deciaml dhe Hexadecimal\s
                Shtyp 7 për të gjeneruar IP Address\s
                Shtyp 8 për të gjeneruar MAC Address\s
                Shtyp 10 për të submituar IP Address\s
                Shtyp 0 për të përfunduar Aplikacionin\s

                >
                >
                >
                >
                Jep numrin:\s""");

    }

    public static String stringFormatWithNoSpace(Scanner s) {
        return s.nextLine().trim().toLowerCase().replaceAll("\s+", "");
    }

    public static String stringFormatWithNoCharset(Scanner s) {
        return s.nextLine().trim().toLowerCase().replaceAll("\\W+", "");
    }

}