/*
    Copyright 2016 Stefan 'Namikon' Thomanek <sthomanek at gmail dot com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.usrv.mytownwebauth.config;


import java.io.File;

import eu.usrv.yamcore.config.ConfigManager;


public class MTWAConfig extends ConfigManager
{
  public MTWAConfig( File pConfigBaseDirectory, String pModCollectionDirectory, String pModID )
  {
    super( pConfigBaseDirectory, pModCollectionDirectory, pModID );
  }

  public String Mongo_Server;
  public int Mongo_Port;
  public String Mongo_Username;
  public String Mongo_Password;
  public String Mongo_Database;
  public String Mongo_AuthDB;

  @Override
  protected void PreInit()
  {
    Mongo_Server = "localhost";
    Mongo_Port = 27010;
    Mongo_Username = "";
    Mongo_Password = "";
    Mongo_Database = "mytownweb-login";
    Mongo_AuthDB = "authentication_database_name";
  }

  @Override
  protected void Init()
  {
    Mongo_Port = _mainConfig.getInt( "Mongo_Port", "DataBase", Mongo_Port, 0, 65532, "Port of the MongoDB Instance" );

    Mongo_Server = _mainConfig.getString( "Mongo_Server", "DataBase", Mongo_Server, "Your MongoDB Server you use for User-Authentication on MyTownWeb" );
    Mongo_Username = _mainConfig.getString( "Mongo_Username", "DataBase", Mongo_Username, "The DB Username" );
    Mongo_Password = _mainConfig.getString( "Mongo_Password", "DataBase", Mongo_Password, "The DB Password" );
    Mongo_Database = _mainConfig.getString( "Mongo_Database", "DataBase", Mongo_Database, "The DB Name" );
    Mongo_AuthDB = _mainConfig.getString( "Mongo_AuthDB", "DataBase", Mongo_AuthDB, "The Mongo AuthDB Name" );
  }

  @Override
  protected void PostInit()
  {

  }
}