package org.dynmap.forge_1_12_2.permissions;

import java.util.HashSet;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.dynmap.Log;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class Sponge7Permissions implements PermissionProvider {

    public static Sponge7Permissions create() {
        try {
            Class.forName("org.spongepowered.api.Sponge");    /* See if class exists */
        } catch (ClassNotFoundException cnfx) {
            return null;
        }
        Log.info("Using Sponge Permissions for access control");
        Log.info("Web interface permissions only available for online users");
        return new Sponge7Permissions();
    }

    private Sponge7Permissions() {
    }

    @Override
    public boolean has(ICommandSender sender, String permission) {
        if(sender instanceof EntityPlayerMP) {
        	boolean rc = false;
        	EntityPlayerMP player = (EntityPlayerMP) sender;
        	Optional<Player> p = Sponge.getServer().getPlayer(player.getUniqueID());
        	if (p.isPresent()) {
        		
        		rc = p.get().hasPermission("dynmap." + permission);
        	}
        	return rc;
        }
        return true;
    }
    
    @Override
    public boolean hasPermissionNode(ICommandSender sender, String permission) {
        if(sender instanceof EntityPlayerMP) {
        	boolean rc = false;
        	EntityPlayerMP player = (EntityPlayerMP) sender;
        	Optional<Player> p = Sponge.getServer().getPlayer(player.getUniqueID());
        	if (p.isPresent()) {
        		rc = p.get().hasPermission("dynmap." + permission);
        	}
        	return rc;
        }
        return true;    	
    }
    
    @Override
    public Set<String> hasOfflinePermissions(String player, Set<String> perms) {
        HashSet<String> rslt = new HashSet<String>();
    	Optional<Player> p = Sponge.getServer().getPlayer(player);
    	if (p.isPresent()) {
    		Player plyr = p.get();
    		for (String perm : perms) {
    			if (plyr.hasPermission("dynmap." + perm)) {
    				rslt.add(perm);
    			}
    		}
        }
        return rslt;
    }

    @Override
    public boolean hasOfflinePermission(String player, String perm) {
    	Optional<Player> p = Sponge.getServer().getPlayer(player);
    	if (p.isPresent()) {
    		Player plyr = p.get();
    		return plyr.hasPermission("dynmap." + perm);
        }
    	return false;
    }
}