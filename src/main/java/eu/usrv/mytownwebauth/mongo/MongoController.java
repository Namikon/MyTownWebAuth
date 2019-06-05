package eu.usrv.mytownwebauth.mongo;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import eu.usrv.mytownwebauth.MyTownWebAuth;
import org.bson.Document;

/**
 * <p>Controller class for MongoDB operations.</p>
 *
 * <p>Contains a static instance so that only one connection
 * can be made between the MC server and the database.</p>
 */
public class MongoController {
  private static MongoController instance;
  private MongoClient client;

  /**
   * Gets the MongoController instance.
   * If instance has not been initialized and assigned yet, do so.
   *
   * @return MongoController instance
   */
  public static MongoController getInstance(){
    if(instance == null){
      instance = new MongoController();
    }

    return instance;
  }

  private MongoClient getClient(){
    if (client == null)
    {
      client = new MongoClient(
          new MongoClientURI( "mongodb://" + MyTownWebAuth.MTWACfg.Mongo_Username +
              ":" + MyTownWebAuth.MTWACfg.Mongo_Password +
              "@" + MyTownWebAuth.MTWACfg.Mongo_Server + "/?authSource=" +
              MyTownWebAuth.MTWACfg.Mongo_AuthDB ) );
    }

    return client;
  }

  public boolean unlockAccount( String pAccountToken, String pPlayerUUID )
  {
    MongoDatabase database = getClient().getDatabase( MyTownWebAuth.MTWACfg.Mongo_Database );
    MongoCollection<Document> tAccounts = database.getCollection( "accounts" );

    BasicDBObject query = new BasicDBObject("accountToken", pAccountToken);
    BasicDBObject set = new BasicDBObject("$set", new BasicDBObject("playerUUID", pPlayerUUID));
    Document ret = tAccounts.findOneAndUpdate(query, set);

    return (ret != null);
  }
}