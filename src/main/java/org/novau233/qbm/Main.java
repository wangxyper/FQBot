package org.novau233.qbm;

import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.novau233.qbm.commands.JavaScriptCommand;
import org.novau233.qbm.manager.BotDataManager;
import org.novau233.qbm.manager.BotManager;
import org.novau233.qbm.manager.ConfigManager;
import org.novau233.qbm.processors.JavaScriptCommandLoader;
import org.novau233.qbm.utils.SeXResponse;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BotDataManager.init();
        if (SeXResponse.URLs.getCache().exists()){
            for (File file : SeXResponse.URLs.getCache().listFiles()){
                file.delete();
            }
        }
        File jsDir = new File("commands");
        if (!jsDir.exists()){
            jsDir.mkdir();
        }
        JavaScriptCommandLoader.loadAll(jsDir);
        ConfigManager.init();
        BotManager.init();
        try (Scanner scanner = new Scanner(System.in)){
            while (scanner.hasNext()){
                String read = scanner.nextLine();
                MessageChainBuilder builder = new MessageChainBuilder();
                builder.add(read);
                BotManager.multiSender.send(builder.build(), ConfigManager.CONFIG_FILE_READ.getListeningGroup());
            }
        }
    }
}
