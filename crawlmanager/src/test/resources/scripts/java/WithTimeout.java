public class WithTimeout{

    public static void main(String[] args) {
        try {
            Thread.sleep(2000);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        System.out.println("Completed even after timeout");
    }

}