package xyz.magmabits.refinedlib.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xyz.magmabits.refinedlib.RefinedLib;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class SupporterUtils {

    public record PlayerInfo(String uuid, String username, String type) {}

    private static final String GITHUB_API_URL =
            "https://api.github.com/repos/magmabits/supporters/contents/data.json";

    private static final String GITHUB_TOKEN = loadToken();

    private static List<PlayerInfo> cachedPlayers = new ArrayList<>();
    private static long lastFetchTime = 0;
    private static final long CACHE_DURATION = 5 * 60 * 1000;

    private SupporterUtils() {}

    private static String loadToken() {
        try {
            Properties secrets = new Properties();
            secrets.load(SupporterUtils.class.getResourceAsStream("/secrets.properties"));
            return secrets.getProperty("github.token", "");
        } catch (IOException | NullPointerException e) {
            RefinedLib.LOGGER.error("Could not load secrets.properties: " + e.getMessage());
            return "";
        }
    }

    public static List<PlayerInfo> fetchPlayers() {
        long now = System.currentTimeMillis();
        if (!cachedPlayers.isEmpty() && (now - lastFetchTime < CACHE_DURATION)) {
            return cachedPlayers;
        }

        List<PlayerInfo> players = new ArrayList<>();

        try {
            HttpURLConnection connection = (HttpURLConnection) URI.create(GITHUB_API_URL).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/vnd.github+json");
            connection.setRequestProperty("Authorization", "Bearer " + GITHUB_TOKEN);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            if (status == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                JsonObject response = JsonParser.parseReader(reader).getAsJsonObject();
                reader.close();

                String encoded = response.get("content").getAsString().replaceAll("\\s", "");
                String decoded = new String(Base64.getDecoder().decode(encoded));
                JsonObject dataJson = JsonParser.parseString(decoded).getAsJsonObject();

                if (dataJson.has("supporters") && dataJson.get("supporters").isJsonArray()) {
                    JsonArray supporterArray = dataJson.getAsJsonArray("supporters");
                    for (var element : supporterArray) {
                        JsonObject obj = element.getAsJsonObject();
                        String uuid     = obj.get("uuid").getAsString();
                        String username = obj.get("username").getAsString();
                        String type     = obj.has("type") ? obj.get("type").getAsString() : "unknown";
                        players.add(new PlayerInfo(uuid, username, type));
                    }
                    cachedPlayers = players;
                    lastFetchTime = now;
                } else {
                    RefinedLib.LOGGER.error("SupporterUtils: 'supporters' field missing or not an array in data.json");
                }
            } else {
                RefinedLib.LOGGER.error("SupporterUtils: HTTP error " + status);
            }

            connection.disconnect();
        } catch (IOException e) {
            RefinedLib.LOGGER.error("SupporterUtils: Failed to fetch supporters — " + e.getMessage());
        }

        return cachedPlayers;
    }

    private static String normalize(UUID uuid) {
        return uuid.toString().replace("-", "");
    }

    public static boolean isSupporter(UUID uuid) {
        String normalized = normalize(uuid);
        return fetchPlayers().stream().anyMatch(p -> p.uuid().equals(normalized));
    }

    public static boolean isSupporterOfType(UUID uuid, String type) {
        String normalized = normalize(uuid);
        return fetchPlayers().stream()
                .anyMatch(p -> p.uuid().equals(normalized) && p.type().equals(type));
    }

    public static boolean isCurator(UUID uuid) {
        return isSupporterOfType(uuid, "curators");
    }

    public static boolean isModerator(UUID uuid) {
        return isSupporterOfType(uuid, "moderators");
    }

    public static boolean isMember(UUID uuid) {
        return isSupporterOfType(uuid, "membership");
    }

    public static Text getSupporterStylisedName(UUID playerUuid, Text text) {
        if (text == null) return null;
        if (isSupporter(playerUuid)) {
            MutableText result = Text.empty();
            result.append(Text.literal("\uE780")
                    .styled(style -> style
                            .withColor(0x8bcc7f)
                            .withFont(Identifier.of("refinedlib", "default"))));
            result.append(Text.literal(" ")); // plain space in default font
            result.append(text.copy());
            return result;
        }
        return text;
    }
}