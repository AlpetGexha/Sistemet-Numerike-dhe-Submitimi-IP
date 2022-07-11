public class TEst {
    public static void main(String[] args) {
        String result = "63";
        for (int i = 0; i < (8 - result.length()); i++) {
            result = "0" + result;
        }
        System.out.println(result);

    }


}
