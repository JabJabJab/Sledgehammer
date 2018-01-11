package sledgehammer.module.discord;

import java.io.File;

import de.btobastian.javacord.entities.Channel;
import sledgehammer.lua.chat.ChatChannel;
import sledgehammer.lua.chat.ChatMessage;
import sledgehammer.plugin.MongoModule;

/**
 * Module designed to load a Discord bot for SledgeHammer logging, and
 * interfacing purposes.
 *
 * @author Jab
 */
public class ModuleDiscord extends MongoModule {

    /**
     * Static boolean to clarify if the Module is in 'debug mode'.
     */
    public static boolean DEBUG = true;

    /**
     * The settings instance the Module is using to define and manage its settings.
     */
    private DiscordSettings settings;

    /**
     * The JavaCord Bot instance.
     */
    private DiscordBot bot;

    private DiscordEventListener eventListener;
    private DiscordLogListener logListener;
    private DiscordExceptionListener exceptionListener;
    private DiscordCommandListener commandHandler;
    private String __debugToken;

    public ModuleDiscord() {
        super(getDefaultDatabase());
    }

    @Override
    public void onLoad() {
        File directory = getModuleDirectory();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        eventListener = new DiscordEventListener(this);
        logListener = new DiscordLogListener(this);
        exceptionListener = new DiscordExceptionListener(this);
        commandHandler = new DiscordCommandListener(this);
        register(commandHandler);
    }

    @Override
    public void onStart() {
        // Initialize the Settings Handler.
        settings = new DiscordSettings(this);
        // Load the settings.
        settings.load();
        DEBUG = settings.isDebug();
        // Grab the token from the settings file.
        String token = settings.getBotAccessToken();
        if (token == null || token.isEmpty()) {
            token = __debugToken;
        }
        // Check if token exists.
        if (token == null || token.isEmpty() || token.equals("<TOKEN_HERE>")) {
            println("Token is invalid!");
            stopModule();
            return;
        } else {
            // Initialize the Bot.
            bot = new DiscordBot(this);
            bot.connect(settings.getBotAccessToken());
        }
        register(eventListener);
        register(logListener);
        register(exceptionListener);
    }

    @Override
    public void onStop() {
        if (bot != null && bot.isConnected()) {
            bot.disconnect();
        }
        unregister(eventListener);
        unregister(logListener);
        unregister(exceptionListener);
    }

    @Override
    public void onUnload() {
        unregister(commandHandler);
    }

    public void start() {
        onStart();
    }

    public void stop() {
        onStop();
    }

    public void broadcast(String channelName, String text) {
        // messageGlobal(message);
        ChatChannel channel = getChatModule().getChatChannel(channelName);
        if (channel == null) {
            println("Channel does not exist: " + channelName + ".");
            return;
        }
        if (text == null || text.isEmpty()) {
            println("Text is invalid.");
            return;
        }
        ChatMessage message = createChatMessage(text);
        // TODO: Implement. ?
    }

    public String getPublicChannelName() {
        return "channel_global";
    }

    public String getConsoleChannelName() {
        return "console";
    }

    public DiscordBot getBot() {
        return bot;
    }

    public String getToken() {
        return settings.getBotAccessToken();
    }

    public DiscordSettings getSettings() {
        return settings;
    }

    public void setDebugToken(String string) {
        this.__debugToken = string;
    }

    public Channel getConsoleChannel() {
        return getBot().getConsoleChannel();
    }

}