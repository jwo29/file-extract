public class MainTest {
    public static void main(String argv[]) throws Exception{
        ConnectionTest connectionTest = new ConnectionTest();

        // Create connection
        connectionTest.createClient();



        connectionTest.checkIndex("notExistIndex");

        connectionTest.getDocument("kibana_sample_data_ecommerce", "etVok4EBGGEXRg-hKB1U");


        // Close connection
        connectionTest.closeConnection();
    }
}
