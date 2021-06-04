package de.AhegaHOE.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPCHandler {

    private static List<EntityPlayer> NPC = new ArrayList<>();

    public static void createNPC(Player p, String npcName, String skinHolderName, Location loc, UUID uuid) {
        GameProfile profile = new GameProfile(uuid, npcName);
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorld("world")).getHandle();
        EntityPlayer npc = new EntityPlayer(server, world, profile, new PlayerInteractManager(world));
        npc.setLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getYaw(), loc.getPitch());
        String[] name = getSkin(p, skinHolderName);
        profile.getProperties().put("textures", new Property("textures", name[0], name[1]));
        NPC.add(npc);
        sendNPCPacket(npc);
    }

    public static void sendNPCPacket(EntityPlayer npc) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            PlayerConnection con = ((CraftPlayer) all).getHandle().playerConnection;
            con.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            con.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            con.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));

        }
    }

    private static String[] getSkin(Player p, String name) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            String uuid = new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString();

            URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid
                    + "?unsigned=false");
            InputStreamReader reader2 = new InputStreamReader(url2.openStream());
            JsonObject prop = new JsonParser().parse(reader2).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = prop.get("value").getAsString();
            String signature = prop.get("signature").getAsString();
            return new String[]{texture, signature};
        } catch (Exception e) {
            EntityPlayer ep = ((CraftPlayer) p).getHandle();
            GameProfile profile = ((CraftPlayer) p).getProfile();
            Property prop = profile.getProperties().get("textures").iterator().next();
            String texture = prop.getValue();
            String signature = prop.getSignature();
            return new String[]{texture, signature};
        }

    }

    public static void sendNPCJoinPacket(Player p) {
        for (EntityPlayer npc : NPC) {
            PlayerConnection con = ((CraftPlayer) p).getHandle().playerConnection;
            con.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            con.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            con.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));

        }
    }

    public static List<EntityPlayer> getNPCs() {
        return NPC;
    }

    public static void spawnNPCs() {
        // createNPC(Bukkit.getPlayer("searlee34"), "Hans", "searlee34", new Location(Bukkit.getWorld("world"), -102, 81, -876, 90, 0), Bukkit.getPlayerUniqueId("searlee34"));

    }

}
