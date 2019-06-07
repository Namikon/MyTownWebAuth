package eu.usrv.mytownwebauth.server;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandBase implements ICommand {
    private List aliases;

    CommandBase()
    {
        this.aliases = new ArrayList();
    }

    private int compareTo(ICommand p_compareTo_1_)
    {
        return this.getCommandName().compareTo(p_compareTo_1_.getCommandName());
    }

    public int compareTo(Object p_compareTo_1_)
    {
        return this.compareTo((ICommand)p_compareTo_1_);
    }

    @Override
    public List getCommandAliases()
    {
        return this.aliases;
    }

    boolean IsConsoleUser(ICommandSender pCmdSender)
    {
        return !( pCmdSender instanceof EntityPlayer );
    }

    boolean IsOppedPlayer(ICommandSender pCmdSender)
    {
        if (pCmdSender instanceof EntityPlayerMP)
            return IsOppedPlayer((EntityPlayerMP) pCmdSender);
        else
            return false;
    }

    boolean IsOppedPlayer(EntityPlayer pPlayer)
    {
        boolean tRet = false;
        if( pPlayer instanceof EntityPlayerMP )
        {
            EntityPlayerMP tEP = (EntityPlayerMP) pPlayer;
            tRet = MinecraftServer.getServer().getConfigurationManager().func_152596_g( tEP.getGameProfile() );
        }
        return tRet;
    }

    public boolean IsPlayerCreative( EntityPlayer pPlayer )
    {
        boolean tRet = false;
        if( pPlayer instanceof EntityPlayerMP )
        {
            EntityPlayerMP tEP = (EntityPlayerMP) pPlayer;
            tRet = tEP.capabilities.isCreativeMode;
        }
        return tRet;
    }

    @Override
    public boolean canCommandSenderUseCommand( ICommandSender pCommandSender )
    {
        return true;
    }

    @Override
    public List addTabCompletionOptions( ICommandSender sender, String[] args )
    {
        return null;
    }

    @Override
    public boolean isUsernameIndex( String[] p_82358_1_, int p_82358_2_ )
    {
        return false;
    }
}
