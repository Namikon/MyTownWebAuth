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
      client = new MongoClient( new MongoClientURI( MyTownWebAuth.MTWACfg.MongoConnectionString ));
    }

    return client;
  }

  private boolean updateRecordByField(String pSearchField, String pSearchValue, String pFieldToUpdate, String pNewValue  )
  {
    MongoDatabase database = getClient().getDatabase( MyTownWebAuth.MTWACfg.Mongo_Collection);
    MongoCollection<Document> tAccounts = database.getCollection( "accounts" );

    BasicDBObject query = new BasicDBObject(pSearchField, pSearchValue);
    BasicDBObject set = new BasicDBObject("$set", new BasicDBObject(pFieldToUpdate, pNewValue));
    Document ret = tAccounts.findOneAndUpdate(query, set);

    return (ret != null);
  }

  public boolean setStaff(String pPlayerUUID, String pStaffFlag)
  {
    return updateRecordByField("playerUUID", pPlayerUUID, "staff", pStaffFlag);
  }

  public boolean unlockAccount( String pAccountToken, String pPlayerUUID )
  {
    return updateRecordByField("accountToken", pAccountToken, "playerUUID", pPlayerUUID);
  }
}