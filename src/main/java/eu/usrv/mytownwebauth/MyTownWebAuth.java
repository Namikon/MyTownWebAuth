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

package eu.usrv.mytownwebauth;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import eu.usrv.mytownwebauth.config.MTWAConfig;
import eu.usrv.mytownwebauth.server.VerifyAccountCommand;
import eu.usrv.yamcore.auxiliary.IngameErrorLog;
import eu.usrv.yamcore.auxiliary.LogHelper;
import net.minecraft.command.CommandHandler;

import java.util.Random;


@Mod( modid = MyTownWebAuth.MODID, name = MyTownWebAuth.MODNAME, version = MyTownWebAuth.VERSION, dependencies = "required-after:Forge@[10.13.4.1558,);required-after:YAMCore@[0.5.69,);", acceptableRemoteVersions = "*" )
public class MyTownWebAuth
{
  public static final String MODID = "mytownwebauth";
  public static final String VERSION = "GRADLETOKEN_VERSION";
  public static final String MODNAME = "MyTownWeb Authenticator";
  public static final String NICEFOLDERNAME = "MyTownWebAuth";
  public static MTWAConfig MTWACfg = null;
  public static IngameErrorLog AdminLogonErrors = null;
  public static LogHelper Logger = new LogHelper( MODID );
  public static Random Rnd = null;

  @Instance( MODID )
  public static MyTownWebAuth instance;

  @EventHandler
  public void PreInit( FMLPreInitializationEvent pEvent )
  {
    Rnd = new Random( System.currentTimeMillis() );
    MTWACfg = new MTWAConfig( pEvent.getModConfigurationDirectory(), NICEFOLDERNAME, MODID );
    if( !MTWACfg.LoadConfig() )
      Logger.error( String.format( "%s could not load its config file. Things are going to be weird!", MODID ) );

    AdminLogonErrors = new IngameErrorLog();
  }

  @EventHandler
  public void init( FMLInitializationEvent pEvent )
  {
    FMLCommonHandler.instance().bus().register( AdminLogonErrors );
  }

  @EventHandler
  public void serverStart( FMLServerStartingEvent pEvent)
  {
    CommandHandler ch = (CommandHandler) FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager();
    ch.registerCommand( new VerifyAccountCommand() );
  }
}
