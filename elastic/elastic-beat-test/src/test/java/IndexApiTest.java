import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.xcontent.XContentType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IndexApiTest {

    public void indexTest() {

        // Index Request

//        request.id("1"); // Document id for the request


//        String jsonString = "{" +
//                "\"user\":\"kimchy\"," +
//                "\"postDate\":\"2013-01-30\"," +
//                "\"message\":\"trying out Elasticsearch\"" +
//                "}";
//        request.source(jsonString, XContentType.JSON); // Document source provided as a String


//        Map<String, Object> jsonMap = new HashMap<>();
//        jsonMap.put("user", "kimchy");
//        jsonMap.put("postDate", new Date());
//        jsonMap.put("message", "trying out Elasticsearch");
//
//        // Document source provided as a Map which gets automatically converted to JSON format
//        request.source(jsonMap);

        System.out.println("Create index success");

    }
}
