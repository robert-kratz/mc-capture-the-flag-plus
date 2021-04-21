package us.rjks.utils;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import us.rjks.game.Game;
import us.rjks.game.GameManager;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 19.04.2021 / 17:52
 *
 **************************************************************************/

public class ScoreBoardManager {

    public static void setScoreboard(Player p) {
        if (GameManager.getGame().equals(Game.LOBBY)) {

            Scoreboard scoreboard = new Scoreboard();
            ScoreboardObjective obj = scoreboard.registerObjective("zagd", IScoreboardCriteria.b);
            obj.setDisplayName(Config.getScoreBoardAsString("lobby_title"));
            PacketPlayOutScoreboardObjective createPacket = new PacketPlayOutScoreboardObjective(obj, 0);
            PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);

            String team = "§8-";
            if (TeamManager.getTeamFromPlayer(p) != null) {
                team = TeamManager.getTeamFromPlayer(p).getColor() + "" + TeamManager.getTeamFromPlayer(p).getName();
            }

            String map = "§eVoting...";
            if (GameManager.getCurrentMap() != null) {
                map =  GameManager.getCurrentMap().getName();
            }

            ScoreboardScore s1 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("lobby_title_line_01")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s2 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("lobby_title_line_02")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s3 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("lobby_title_line_03")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s4 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("lobby_title_line_04")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s5 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("lobby_title_line_05")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s6 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("lobby_title_line_06")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s7 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("lobby_title_line_07")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));

            ScoreboardScore s8 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("lobby_title_line_08")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s9 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("lobby_title_line_09")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));

            s1.setScore(9);
            s2.setScore(8);
            s3.setScore(7);
            s4.setScore(6);
            s5.setScore(5);
            s6.setScore(4);
            s7.setScore(3);
            s8.setScore(2);
            s9.setScore(1);

            PacketPlayOutScoreboardObjective removePacket = new PacketPlayOutScoreboardObjective(obj, 1);
            PacketPlayOutScoreboardScore pa1 = new PacketPlayOutScoreboardScore(s1);
            PacketPlayOutScoreboardScore pa2 = new PacketPlayOutScoreboardScore(s2);
            PacketPlayOutScoreboardScore pa3 = new PacketPlayOutScoreboardScore(s3);
            PacketPlayOutScoreboardScore pa4 = new PacketPlayOutScoreboardScore(s4);
            PacketPlayOutScoreboardScore pa5 = new PacketPlayOutScoreboardScore(s5);
            PacketPlayOutScoreboardScore pa6 = new PacketPlayOutScoreboardScore(s6);
            PacketPlayOutScoreboardScore pa7 = new PacketPlayOutScoreboardScore(s7);
            PacketPlayOutScoreboardScore pa8 = new PacketPlayOutScoreboardScore(s8);
            PacketPlayOutScoreboardScore pa9 = new PacketPlayOutScoreboardScore(s9);


            sendPacket(removePacket, p);
            sendPacket(createPacket, p);
            sendPacket(display, p);

            sendPacket(pa1, p);
            sendPacket(pa2, p);
            sendPacket(pa3, p);
            sendPacket(pa4, p);
            sendPacket(pa5, p);
            sendPacket(pa6, p);
            sendPacket(pa7, p);
            sendPacket(pa8, p);
            sendPacket(pa9, p);

        } else if (GameManager.getGame().equals(Game.INGAME)) {

            Scoreboard scoreboard = new Scoreboard();
            ScoreboardObjective obj = scoreboard.registerObjective("zagd", IScoreboardCriteria.b);
            obj.setDisplayName(Config.getScoreBoardAsString("ingame_title"));
            PacketPlayOutScoreboardObjective createPacket = new PacketPlayOutScoreboardObjective(obj, 0);
            PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);

            String team = "§8-";
            if (TeamManager.getTeamFromPlayer(p) != null) {
                team = TeamManager.getTeamFromPlayer(p).getColor() + "" + TeamManager.getTeamFromPlayer(p).getName();
            } else if (GameManager.getSpectatorPlayers().contains(p)) {
                team = "§7Spectator";
            }

            String map = "§eVoting...";
            if (GameManager.getCurrentMap() != null) {
                map =  GameManager.getCurrentMap().getName();
            }

            ScoreboardScore s1 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("ingame_title_line_01")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s2 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("ingame_title_line_02")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s3 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("ingame_title_line_03")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s4 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("ingame_title_line_04")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s5 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("ingame_title_line_05")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s6 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("ingame_title_line_06")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s7 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("ingame_title_line_07")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));

            ScoreboardScore s8 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("ingame_title_line_08")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s9 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("ingame_title_line_09")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));

            s1.setScore(9);
            s2.setScore(8);
            s3.setScore(7);
            s4.setScore(6);
            s5.setScore(5);
            s6.setScore(4);
            s7.setScore(3);
            s8.setScore(2);
            s9.setScore(1);

            PacketPlayOutScoreboardObjective removePacket = new PacketPlayOutScoreboardObjective(obj, 1);
            PacketPlayOutScoreboardScore pa1 = new PacketPlayOutScoreboardScore(s1);
            PacketPlayOutScoreboardScore pa2 = new PacketPlayOutScoreboardScore(s2);
            PacketPlayOutScoreboardScore pa3 = new PacketPlayOutScoreboardScore(s3);
            PacketPlayOutScoreboardScore pa4 = new PacketPlayOutScoreboardScore(s4);
            PacketPlayOutScoreboardScore pa5 = new PacketPlayOutScoreboardScore(s5);
            PacketPlayOutScoreboardScore pa6 = new PacketPlayOutScoreboardScore(s6);
            PacketPlayOutScoreboardScore pa7 = new PacketPlayOutScoreboardScore(s7);
            PacketPlayOutScoreboardScore pa8 = new PacketPlayOutScoreboardScore(s8);
            PacketPlayOutScoreboardScore pa9 = new PacketPlayOutScoreboardScore(s9);


            sendPacket(removePacket, p);
            sendPacket(createPacket, p);
            sendPacket(display, p);

            sendPacket(pa1, p);
            sendPacket(pa2, p);
            sendPacket(pa3, p);
            sendPacket(pa4, p);
            sendPacket(pa5, p);
            sendPacket(pa6, p);
            sendPacket(pa7, p);
            sendPacket(pa8, p);
            sendPacket(pa9, p);

        } else if (GameManager.getGame().equals(Game.END)) {

            Scoreboard scoreboard = new Scoreboard();
            ScoreboardObjective obj = scoreboard.registerObjective("zagd", IScoreboardCriteria.b);
            obj.setDisplayName(Config.getScoreBoardAsString("end_title"));
            PacketPlayOutScoreboardObjective createPacket = new PacketPlayOutScoreboardObjective(obj, 0);
            PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);

            String team = "§8-";
            if (TeamManager.getTeamFromPlayer(p) != null) {
                team = TeamManager.getTeamFromPlayer(p).getColor() + "" + TeamManager.getTeamFromPlayer(p).getName();
            }

            String map = "§eVoting...";
            if (GameManager.getCurrentMap() != null) {
                map =  GameManager.getCurrentMap().getName();
            }

            ScoreboardScore s1 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("end_title_line_01")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s2 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("end_title_line_02")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s3 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("end_title_line_03")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s4 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("end_title_line_04")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s5 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("end_title_line_05")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s6 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("end_title_line_06")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s7 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("end_title_line_07")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));

            ScoreboardScore s8 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("end_title_line_08")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));
            ScoreboardScore s9 = new ScoreboardScore(scoreboard, obj, Config.getScoreBoardAsString("end_title_line_09")
                    .replaceAll("%mapname%", map)
                    .replaceAll("%stats_Rank%", 3 + "")
                    .replaceAll("%game%", GameManager.getTeamAmount() + "x" + GameManager.getPlayersPerTeam())
                    .replaceAll("%team%", team));

            s1.setScore(9);
            s2.setScore(8);
            s3.setScore(7);
            s4.setScore(6);
            s5.setScore(5);
            s6.setScore(4);
            s7.setScore(3);
            s8.setScore(2);
            s9.setScore(1);

            PacketPlayOutScoreboardObjective removePacket = new PacketPlayOutScoreboardObjective(obj, 1);
            PacketPlayOutScoreboardScore pa1 = new PacketPlayOutScoreboardScore(s1);
            PacketPlayOutScoreboardScore pa2 = new PacketPlayOutScoreboardScore(s2);
            PacketPlayOutScoreboardScore pa3 = new PacketPlayOutScoreboardScore(s3);
            PacketPlayOutScoreboardScore pa4 = new PacketPlayOutScoreboardScore(s4);
            PacketPlayOutScoreboardScore pa5 = new PacketPlayOutScoreboardScore(s5);
            PacketPlayOutScoreboardScore pa6 = new PacketPlayOutScoreboardScore(s6);
            PacketPlayOutScoreboardScore pa7 = new PacketPlayOutScoreboardScore(s7);
            PacketPlayOutScoreboardScore pa8 = new PacketPlayOutScoreboardScore(s8);
            PacketPlayOutScoreboardScore pa9 = new PacketPlayOutScoreboardScore(s9);


            sendPacket(removePacket, p);
            sendPacket(createPacket, p);
            sendPacket(display, p);

            sendPacket(pa1, p);
            sendPacket(pa2, p);
            sendPacket(pa3, p);
            sendPacket(pa4, p);
            sendPacket(pa5, p);
            sendPacket(pa6, p);
            sendPacket(pa7, p);
            sendPacket(pa8, p);
            sendPacket(pa9, p);

        }
    }

    private static void sendPacket(Packet packet, Player p) {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }
}
