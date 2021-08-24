package de.AhegaHOE.util.ts;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.ClientProperty;
import com.github.theholywaffle.teamspeak3.api.event.*;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.main.Main;
import de.AhegaHOE.util.UUIDFetcher;

import java.util.UUID;


public class TeamspeakEventHandler {


    public static void loadEvents() {
        Main.ts3Api.registerAllEvents();

        Main.ts3Api.addTS3Listeners(new TS3Listener() {

            @Override
            public void onTextMessage(TextMessageEvent textMessageEvent) {
                String message = textMessageEvent.getMessage().trim();
                int uid = textMessageEvent.getInvokerId();
                Client c = Main.ts3Api.getClientInfo(uid);
                if (message.length() <= 7) {
                    Main.ts3Api.sendPrivateMessage(c.getId(), "[COLOR=#FF5555]Fehler: Benutze !verify <UUID> | Um deine UUID zu bekommen benutze Ingame /uuid[/COLOR]");
                    return;
                }
                String uuid = ((message) == null) ? "" : message.substring(8);

                if (!TSMySQLPointer.isUserExist(c.getUniqueIdentifier())) {
                    if (c.isServerQueryClient()) {
                        return;
                    }
                    Main.ts3Api.sendPrivateMessage(c.getId(), "[COLOR=#FF5555]Fehler: Du musst vorher in Minecraft \"/verify " + c.getUniqueIdentifier() + "\" eingeben.[/COLOR]");
                    return;
                }
                // !verify <UUID>
                boolean uuidCheck = TSMySQLPointer.getUUIDbyUID(c.getUniqueIdentifier()).equalsIgnoreCase(uuid);


                if (message.startsWith("!verify") && message.endsWith(uuid) && uuidCheck) {
                    if ((TSMySQLPointer.isConfirmed(c.getUniqueIdentifier()))) {
                        Main.ts3Api.sendPrivateMessage(c.getId(), "[COLOR=#FF5555]Fehler: Dein Minecraft-Account ist bereits sychronisiert.[/COLOR]");
                        return;
                    }
                    TSMySQLPointer.linkAccount(c, uuid);
                    TSMySQLPointer.setConfirmed(c.getUniqueIdentifier(), true);
                    updateRank(c);
                    Main.ts3Api.sendPrivateMessage(c.getId(), "[COLOR=#55FF55]Dein Minecraft-Account wurde erfolgreich sychronisiert.[/COLOR]");
                    return;
                }

                Main.ts3Api.sendPrivateMessage(c.getId(), "[COLOR=#FF5555]Fehler: Benutze !verify <UUID> | Um deine UUID zu bekommen benutze Ingame /uuid[/COLOR]");


            }

            @Override
            public void onClientJoin(ClientJoinEvent clientJoinEvent) {
                Client c = Main.ts3Api.getClientByUId(clientJoinEvent.getUniqueClientIdentifier());
                String uid = c.getUniqueIdentifier();
                TSMySQLPointer.sendVerificationMessageTeamspeak(TSMySQLPointer.getUUIDbyUID(uid), uid);
                updateRank(c);
            }

            @Override
            public void onClientLeave(ClientLeaveEvent clientLeaveEvent) {

            }

            @Override
            public void onServerEdit(ServerEditedEvent serverEditedEvent) {

            }

            @Override
            public void onChannelEdit(ChannelEditedEvent channelEditedEvent) {

            }

            @Override
            public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent channelDescriptionEditedEvent) {

            }

            @Override
            public void onClientMoved(ClientMovedEvent clientMovedEvent) {

            }

            @Override
            public void onChannelCreate(ChannelCreateEvent channelCreateEvent) {

            }

            @Override
            public void onChannelDeleted(ChannelDeletedEvent channelDeletedEvent) {

            }

            @Override
            public void onChannelMoved(ChannelMovedEvent channelMovedEvent) {

            }

            @Override
            public void onChannelPasswordChanged(ChannelPasswordChangedEvent channelPasswordChangedEvent) {

            }

            @Override
            public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent privilegeKeyUsedEvent) {

            }
        });
    }


    public static void removeAllGroups(Client c) {
        int[] sg = c.getServerGroups();
        for (int i = 0; i < sg.length; i++) {
            Main.ts3Api.removeClientFromServerGroup(sg[i], c.getDatabaseId());
        }
    }

    public static void removeAllGroupsExcept(Client c) {
        int[] sg = c.getServerGroups();
        for (int i = 0; i < sg.length; i++) {
            if (Main.ts3Api.getServerInfo().getDefaultServerGroup() != sg[i] && sg[i] != 2) {
                Main.ts3Api.removeClientFromServerGroup(sg[i], c.getDatabaseId());
            }
        }
    }

    public static void updateRank(Client c) {
        System.out.println("RANK SET");
        String rank = (TSMySQLPointer.getRankByUID(c.getUniqueIdentifier()) == null) ? "Nicht Registriert" : TSMySQLPointer.getRankByUID(c.getUniqueIdentifier());
        System.out.println(rank);
        String description = (TSMySQLPointer.getUUIDbyUID(c.getUniqueIdentifier()) == null) ? "" : UUIDFetcher.getName(UUID.fromString(TSMySQLPointer.getUUIDbyUID(c.getUniqueIdentifier())));
        Main.ts3Api.editClient(c.getId(), ClientProperty.CLIENT_DESCRIPTION, description);
        switch (rank) {

            case "Mentor": {
                if (TSMySQLPointer.isUserExist(c.getUniqueIdentifier()) && !TSMySQLPointer.isConfirmed(c.getUniqueIdentifier())) {
                    return;
                }
                if (!c.isInServerGroup(6)) {
                    removeAllGroupsExcept(c);
                    Main.ts3Api.addClientToServerGroup(6, c.getDatabaseId());
                }
            }
            break;

            case "Moderator": {
                if (TSMySQLPointer.isUserExist(c.getUniqueIdentifier()) && !TSMySQLPointer.isConfirmed(c.getUniqueIdentifier())) {
                    return;
                }
                if (!c.isInServerGroup(41)) {
                    removeAllGroupsExcept(c);
                    Main.ts3Api.addClientToServerGroup(41, c.getDatabaseId());
                }
            }
            break;

            case "Supporter": {
                if (TSMySQLPointer.isUserExist(c.getUniqueIdentifier()) && !TSMySQLPointer.isConfirmed(c.getUniqueIdentifier())) {
                    return;
                }
                if (!c.isInServerGroup(11)) {
                    removeAllGroupsExcept(c);
                    Main.ts3Api.addClientToServerGroup(11, c.getDatabaseId());
                }
            }
            break;

            case "Youtuber": {
                if (TSMySQLPointer.isUserExist(c.getUniqueIdentifier()) && !TSMySQLPointer.isConfirmed(c.getUniqueIdentifier())) {
                    return;
                }
                if (!c.isInServerGroup(12)) {
                    removeAllGroupsExcept(c);
                    Main.ts3Api.addClientToServerGroup(12, c.getDatabaseId());
                }
            }
            break;

            default:
                removeAllGroupsExcept(c);
                break;
        }
    }

    public static void isOnline(UUID uuid) {
        // TODO: Online check per MC ob der jenige in TS online ist. falls ja dann infos über den aktuellen channel und den client.
    }

}