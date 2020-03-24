package de.schneider_oliver.cheatshit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import de.schneider_oliver.cheatshit.modules.AutoFish;
import de.schneider_oliver.cheatshit.modules.Spectate;
import de.schneider_oliver.cheatshit.modules.XRay;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

public class ModMain implements ModInitializer{

	public static final String MOD_ID = "messiahscheatshit";
	public static final Logger LOGGER = LogManager.getLogger();
	public static FabricKeyBinding bindingOpenConfig;
	public static FabricKeyBinding bindingToggleXRay;
	public static FabricKeyBinding bindingToggleSpectate;
	private static Properties properties;
	private static File configFile;
	
	@Override
	public void onInitialize() {
		loadConfig();
		KeyBindingRegistry.INSTANCE.addCategory("keycat." + MOD_ID + ".general");
		bindingOpenConfig = registerKeyBinding("config", GLFW.GLFW_KEY_SEMICOLON, Config::openConfig);
		bindingToggleXRay = registerKeyBinding("xray", GLFW.GLFW_KEY_APOSTROPHE, XRay::toggleXray);
		bindingToggleSpectate = registerKeyBinding("spectate", GLFW.GLFW_KEY_PERIOD, Spectate::toggleSpectate);
		
		ClientTickCallback.EVENT.register(Spectate::spectate);
		ClientTickCallback.EVENT.register(AutoFish::autoFish);
	}
	
	public FabricKeyBinding registerKeyBinding(String name, int keycode, ClientTickCallback listener) {
		return registerKeyBinding(name, keycode, "keycat." + MOD_ID + ".general", listener);
	}
	
	public FabricKeyBinding registerKeyBinding(String name, int keycode, String category, ClientTickCallback listener) {
		FabricKeyBinding temp = FabricKeyBinding.Builder.create(new Identifier(MOD_ID, name), InputUtil.Type.KEYSYM, keycode, category).build();
		KeyBindingRegistry.INSTANCE.register(temp);
		ClientTickCallback.EVENT.register(listener);
		return temp;
	}
	
	private void loadConfig() {
		File configDir = new File(FabricLoader.getInstance().getConfigDirectory(), "messiahsmods");
		
		if (!configDir.exists()) {
			if (!configDir.mkdir()) {
				LOGGER.warn("[Messiahs Cheat Shit] Could not create configuration directory: " + configDir.getAbsolutePath());
			}
		}
		
		configFile = new File(configDir, "messiahscheatshit.properties");
		properties = new Properties();
		
		if (configFile.exists()) {
			try (FileInputStream stream = new FileInputStream(configFile)) {
				properties.load(stream);
			} catch (IOException e) {
				LOGGER.warn("[Messiahs Cheat Shit] Could not read property file '" + configFile.getAbsolutePath() + "'", e);
			}
		}
		
		Config.loadConfig(properties);
		
		saveConfig();
	}
	
	public static void putConfigValue(String property, String value) {
		properties.put(property, value);
	}
	
	public static void saveConfig() {
		try (FileOutputStream stream = new FileOutputStream(configFile)) {
			properties.store(stream, "Messiahs Cheat Shit");
		} catch (IOException e) {
			LOGGER.warn("[Messiahs Cheat Shit] Could not store property file '" + configFile.getAbsolutePath() + "'", e);
		}
	}
}
