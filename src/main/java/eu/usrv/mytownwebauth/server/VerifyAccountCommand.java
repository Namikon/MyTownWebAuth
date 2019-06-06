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

package eu.usrv.mytownwebauth.server;


import eu.usrv.mytownwebauth.mongo.MongoController;
import eu.usrv.yamcore.auxiliary.PlayerChatHelper;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class VerifyAccountCommand extends CommandBase
{
  public VerifyAccountCommand() { }

  @Override
  public String getCommandName()
  {
    return "mytownweb";
  }

  @Override
  public String getCommandUsage( ICommandSender pCommandSender )
  {
    return "Type /mytownweb verify [AccountSecret] to activate your Account";
  }

  @Override
  public void processCommand( ICommandSender pCmdSender, String[] pArgs )
  {
    if( IsConsoleUser( pCmdSender ) )
    {
      PlayerChatHelper.SendPlain( pCmdSender, "Must be online to use this command" );
      return;
    }

    EntityPlayer tEP = (EntityPlayer) pCmdSender;

    if( pArgs.length < 2 )
      PlayerChatHelper.SendNotifyPositive( tEP, getCommandUsage( null ) );
    else
    {
      String tSubCommand = pArgs[0];
      String tAccountToken = pArgs[1];
      if( tSubCommand.equalsIgnoreCase( "verify" ) )
      {
        if (!tAccountToken.matches( "[a-zA-Z0-9]+" )) {
          PlayerChatHelper.SendError( tEP, "Illegal Characters in Account Secret" );
        } else{
          if (MongoController.getInstance().unlockAccount(tAccountToken ,tEP.getGameProfile().getId().toString()))
            PlayerChatHelper.SendNotifyPositive ( tEP, "Account has been verified" );
          else
            PlayerChatHelper.SendError( tEP, "Something went wrong. Contact a Server Admin" );
        }
      }
      else
      {
        PlayerChatHelper.SendNotifyPositive( tEP, getCommandUsage( null ) );
      }
    }
  }
}
