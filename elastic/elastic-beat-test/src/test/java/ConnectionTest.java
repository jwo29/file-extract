import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;

import java.util.Map;

public class ConnectionTest {

    private RestHighLevelClient client = null;

    public void createClient() throws Exception {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
    }

    public void closeConnection() throws Exception {
        client.close();
        System.out.println("Connection closed");
    }

    public void getDocument(String index, String id) throws Exception {
        // =========================== GET DOCUMENT TEST =====================

        GetRequest getRequest = new GetRequest(index, id);

        // Synchronous execution
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);


        // Get Response

        // Response does not hold any source document and its isExists method returns false.
        if(getResponse.isExists()) {
            long version = getResponse.getVersion();
            String sourceAsString = getResponse.getSourceAsString();
            Map<String, Object> sourceASMap = getResponse.getSourceAsMap();
            byte[] sourceAsBytes = getResponse.getSourceAsBytes();


            System.out.println("Index: " + index);
            System.out.println("ID: " + id);
            System.out.println("Version: " + version);

            for (Map.Entry<String, Object> entry : sourceASMap.entrySet()) {
                if (entry != null) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
            }

        } else {
            // The document was not found.
            // *Note: although the returned response has 404 status code,
            // a valid GetResponse is returned rather than an exception thrown.
        }
    }

    public void checkIndex(String index) throws Exception {
        // 인덱스가 존재하는지 확인하는 메서드

        GetIndexRequest request = new GetIndexRequest(index);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);

        if (exists) {
            System.out.println("Index " + index + " exists");
        } else {
            System.out.println("Index " + index + " does not exists");
        }
    }


}
