package eu.usrv.mytownwebauth.server;

import eu.usrv.mytownwebauth.mongo.MongoController;
import eu.usrv.yamcore.auxiliary.PlayerChatHelper;
import net.minecraft.command.ICommandSender;

public class AdminCommand extends CommandBase {
  public AdminCommand() {
  }

  @Override
  public String getCommandName() {
    return "mytownwebadmin";
  }

  @Override
  public String getCommandUsage(ICommandSender pCommandSender) {
    return "/mytownwebadmin set <option> <data1> <data2>";
  }

  @Override
  public boolean canCommandSenderUseCommand(ICommandSender pCommandSender) {
    return IsConsoleUser(pCommandSender) || IsOppedPlayer(pCommandSender);
  }

  @Override
  public void processCommand(ICommandSender pCmdSender, String[] pArgs) {
    if (pArgs.length == 4) {
      String tSubCommand = pArgs[0];
      String tSubSubCommand = pArgs[1];
      String tAccountToken = pArgs[2];
      String tArg = pArgs[3];

      // /mytownwebadmin set uid [accountToken] [UUID]
      // /mytownwebadmin set staff [UUID] [0/1]
      if (tSubCommand.equalsIgnoreCase("set")) {

        if (tSubSubCommand.equalsIgnoreCase("uid")) {
          if (MongoController.getInstance().unlockAccount(tAccountToken, tArg))
            PlayerChatHelper.SendPlain(pCmdSender, String.format("[OK] Account UID set to %s", tArg));
          else
            PlayerChatHelper.SendPlain(pCmdSender, "[ERROR] Failed to set UID");
        } else if (tSubSubCommand.equalsIgnoreCase("staff")) {
          if (MongoController.getInstance().setStaff(tAccountToken, tArg))
            PlayerChatHelper.SendPlain(pCmdSender, String.format("[OK] Staff-Flag set to %s", tArg));
          else
            PlayerChatHelper.SendPlain(pCmdSender, "[ERROR] Failed to set Staff-Flag");
        } else
          PlayerChatHelper.SendPlain(pCmdSender, "Syntax Error");
      } else {
        PlayerChatHelper.SendPlain(pCmdSender, "Syntax Error");
      }
    }
    else if (pArgs.length == 1)
    {
      String tSubCommand = pArgs[0];
      if (tSubCommand.equalsIgnoreCase("help"))
      {
        PlayerChatHelper.SendPlain(pCmdSender, "Valid options are:");
        PlayerChatHelper.SendPlain(pCmdSender, "1] set uid [accountToken] [UUID]");
        PlayerChatHelper.SendPlain(pCmdSender, "1] This will attach given UUID to Account with given AccountToken");
        PlayerChatHelper.SendPlain(pCmdSender, "2] set staff [UUID] [1/0]");
        PlayerChatHelper.SendPlain(pCmdSender, "2] Changes Staff-Flag for given UUID");
      }
    }
    else
      PlayerChatHelper.SendPlain(pCmdSender, "Type /mytownwebadmin help for help");
  }
}
