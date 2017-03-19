
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author zhanjing
 */

//CRUD operations are from https://www.tutorialspoint.com/mongodb/mongodb_java.htm
public class MongoDB {

    //db.MyApp.insert( { searchAmount: 10, searchFrom: "USD", searchTo: "CNY",  fromRate:"1.2", toRate: "0.6", searchResult:50, date: "2016.11.2"})
    public void insert(String searchAmount, String searchFrom, String searchTo, String fromRate,
            String toRate, String searchResult, String phoneModel, String date) {
        BasicDBObject appInfo = new BasicDBObject();
        appInfo.put("searchAmount", Double.parseDouble(searchAmount));
        appInfo.put("searchFrom", searchFrom);
        appInfo.put("searchTo", searchTo);
        appInfo.put("fromRate", fromRate);
        appInfo.put("toRate", toRate);
        appInfo.put("searchResult", Double.parseDouble(searchResult));
        appInfo.put("phoneModel", phoneModel);
        appInfo.put("time", date);

        MongoClientURI uri = new MongoClientURI("mongodb://jzhan1:1a2b1c4d@ds139937.mlab.com:39937/ds_pro4"); //mLab url
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB(uri.getDatabase());
        DBCollection myApp = db.getCollection("MyApp");
        myApp.insert(appInfo);
        client.close();
    }

    //get all dashboard result 
    public String[] getDashboard() {
        MongoClientURI uri = new MongoClientURI("mongodb://jzhan1:1a2b1c4d@ds139937.mlab.com:39937/ds_pro4");
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB(uri.getDatabase());
        DBCollection myApp = db.getCollection("MyApp");
        String[] result = new String[6];
        result[0] = (findAll(myApp));
        result[1] = (analysis1(myApp));
        result[2] = (analysis2(myApp));
        result[3] = (analysis3(myApp));
        result[4] = (analysis4(myApp));
        result[5] = (analysis5(myApp));
        return result;
    }

    //all counts
    private String findAll(DBCollection myApp) {
        DBCursor cursor = myApp.find();
        StringBuilder result = new StringBuilder();
        try {
            while (cursor.hasNext()) {
                result.append(cursor.next() + "<br>");
            }
        } finally {
            cursor.close();
        }
        return result.toString();
    }

    //visit count
    private String analysis1(DBCollection myApp) {
        return myApp.count() + "";
        
    }
    
    //last time visit
    private String analysis2(DBCollection myApp) {
        DBCursor cursor = myApp.find().sort(new BasicDBObject("_id", -1)).limit(1);
        StringBuilder sb = new StringBuilder();
        for (DBObject dbObject : cursor) {
            sb.append(dbObject.get("time") + "<br>");
        }
        return sb.toString();
    }
    
    //count to usd 
    private String analysis3(DBCollection myApp) {
        BasicDBObject query = new BasicDBObject("searchFrom", "USD");
        DBCursor cursor = myApp.find(query);
        int i = 0;
        try {
            while (cursor.hasNext()) {
                cursor.next();
                i++;
            }
        } finally {
            cursor.close();
        }
        return i + "";
    }
    
    //count amount > 10
    private String analysis4(DBCollection myApp) {
        BasicDBObject query = new BasicDBObject("searchAmount", new BasicDBObject("$gt", 10.0));
        DBCursor cursor = myApp.find(query);
        int i = 0;
        try {
            while (cursor.hasNext()) {
                cursor.next();
                i ++;
            }
        } finally {
            cursor.close();
        }
        return i + "";
    }
    
    //count android vs browser
    private String analysis5(DBCollection myApp) {
        DBCursor cursor = myApp.find();
        int mobileCount = 0;
        int laptopCount = 0;
        try {
            while (cursor.hasNext()) {
                if(cursor.next().get("phoneModel").toString().contains("Android")) {
                    mobileCount++;
                } else {
                    laptopCount++;
                }
                
            }
        } finally {
            cursor.close();
        }
        String result = "phone: " + mobileCount + "<br>" + "browser:" + laptopCount;
        return result;
    }

}
