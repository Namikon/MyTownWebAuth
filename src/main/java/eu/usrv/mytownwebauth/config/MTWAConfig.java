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

  public String MongoConnectionString;
  public String Mongo_Collection;

  @Override
  protected void PreInit()
  {
    MongoConnectionString = "mongodb://";
    Mongo_Collection = "mytownweb-login";
  }

  @Override
  protected void Init()
  {
    MongoConnectionString = _mainConfig.getString( "MongoConnectionString", "MongoDB", MongoConnectionString, "Your MongoDB Server Connection String" );
    Mongo_Collection = _mainConfig.getString( "Mongo_Collection", "MongoDB", Mongo_Collection, "The Collection Name" );
  }

  @Override
  protected void PostInit()
  {

  }
}