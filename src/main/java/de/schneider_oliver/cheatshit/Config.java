package de.schneider_oliver.cheatshit;

import java.util.Locale;
import java.util.Properties;

import com.mojang.blaze3d.systems.RenderSystem;

import de.schneider_oliver.cheatshit.modules.FullBright;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.TextFormat;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class Config extends Screen{

	/*
	 * PUBLIC
	 */
	public static boolean isXRayActive = false;
	public static boolean isFullbrightActive = false;
	public static boolean isOutlineActive = false;
	public static boolean isReachActive = false;
	public static boolean isAutoFishActive = false;
	public static boolean isSpectateActive = false;
	public static String spectateName = "";
	public static String xrayFilter = "";
	public static boolean isBoatFlyActive = false;
	public static float boatSpeedVert = 2;
	public static float boatSpeedHor = 3;
	public static boolean isNofallActive = false;

	/*
	 * PRIVATE
	 */

	private static final int buttonWidth = 20, buttonHeight = 20;
	private static final Identifier TEXTURE = new Identifier(ModMain.MOD_ID, "textures/gui/gui_config.png");
	private TextFieldWidget spectatorField;
	private TextFieldWidget xrayFilterField;
	private TextFieldWidget boatflyHorField;
	private TextFieldWidget boatflyVertField;
	private ButtonWidget btnOutline;
	private ButtonWidget btnReach;
	private ButtonWidget btnFullbright;
	private ButtonWidget btnAutofish;
	private ButtonWidget btnBoatFly;
	private ButtonWidget btnNoFall;
	private static int xSize = 256, ySize = 200, left, top, right;

	public Config() {
		super(new LiteralText("GlobalConfig"));
	}

	@Override
	protected void init() {
		super.init();

		left = (width - xSize) / 2;
		top = (height - ySize) / 2;
		right = (width + xSize) / 2;

		btnOutline = this.addButton(new ButtonWidget(width / 2 - buttonWidth * 2, (int)(top + buttonHeight * 0.25), buttonWidth, buttonHeight, (isOutlineActive ? TextFormat.DARK_GREEN + "\u2714" : TextFormat.DARK_RED + "\u2715"), (button) -> {
			isOutlineActive = !isOutlineActive;
			button.setMessage((isOutlineActive ? TextFormat.DARK_GREEN + "\u2714" : TextFormat.DARK_RED + "\u2715"));
			ModMain.putConfigValue("is-outline-active", isOutlineActive + "");
		}));

		btnReach = this.addButton(new ButtonWidget(right - buttonWidth * 2, (int)(top + buttonHeight * 0.25), buttonWidth, buttonHeight, (isReachActive ? TextFormat.DARK_GREEN + "\u2714" : TextFormat.DARK_RED + "\u2715"), (button) -> {
			isReachActive = !isReachActive;
			button.setMessage((isReachActive ? TextFormat.DARK_GREEN + "\u2714" : TextFormat.DARK_RED + "\u2715"));
			ModMain.putConfigValue("is-reach-active", isReachActive + "");
		}));

		spectatorField = new TextFieldWidget(font, right - buttonWidth * 6, (int)(top + buttonHeight * 1.5), buttonWidth * 5, buttonHeight, I18n.translate("text." + ModMain.MOD_ID + ".spectate"));
		spectatorField.setText(spectateName);

		btnFullbright = this.addButton(new ButtonWidget(width / 2 - buttonWidth * 2, (int)(top + buttonHeight * 2.75), buttonWidth, buttonHeight, (isFullbrightActive ? TextFormat.DARK_GREEN + "\u2714" : TextFormat.DARK_RED + "\u2715"), (button) -> {
			isFullbrightActive = !isFullbrightActive;
			FullBright.applyFullBright();
			button.setMessage((isFullbrightActive ? TextFormat.DARK_GREEN + "\u2714" : TextFormat.DARK_RED + "\u2715"));
			ModMain.putConfigValue("is-fullbright-active", isFullbrightActive + "");
		}));

		btnAutofish = this.addButton(new ButtonWidget(right - buttonWidth * 2, (int)(top + buttonHeight * 2.75), buttonWidth, buttonHeight, (isAutoFishActive ? TextFormat.DARK_GREEN + "\u2714" : TextFormat.DARK_RED + "\u2715"), (button) -> {
			isAutoFishActive = !isAutoFishActive;
			button.setMessage((isAutoFishActive ? TextFormat.DARK_GREEN + "\u2714" : TextFormat.DARK_RED + "\u2715"));
			ModMain.putConfigValue("is-autofish-active", isAutoFishActive + "");
		}));

		xrayFilterField = new TextFieldWidget(font, right - buttonWidth * 6, (int)(top + buttonHeight * 4), buttonWidth * 5, buttonHeight, I18n.translate("text." + ModMain.MOD_ID + ".xray"));
		xrayFilterField.setText(xrayFilter);
		
		btnBoatFly = this.addButton(new ButtonWidget(width / 2 - buttonWidth * 2, (int)(top + buttonHeight * 5.25), buttonWidth, buttonHeight, (isBoatFlyActive ? TextFormat.DARK_GREEN + "\u2714" : TextFormat.DARK_RED + "\u2715"), (button) -> {
			isBoatFlyActive = !isBoatFlyActive;
			button.setMessage((isBoatFlyActive ? TextFormat.DARK_GREEN + "\u2714" : TextFormat.DARK_RED + "\u2715"));
			ModMain.putConfigValue("is-boatfly-active", isBoatFlyActive + "");
		}));

		btnNoFall = this.addButton(new ButtonWidget(right - buttonWidth * 2, (int)(top + buttonHeight * 5.25), buttonWidth, buttonHeight, (isNofallActive ? TextFormat.DARK_GREEN + "\u2714" : TextFormat.DARK_RED + "\u2715"), (button) -> {
			isNofallActive = !isNofallActive;
			button.setMessage((isNofallActive ? TextFormat.DARK_GREEN + "\u2714" : TextFormat.DARK_RED + "\u2715"));
			ModMain.putConfigValue("is-nofall-active", isNofallActive + "");
		}));
		
		boatflyHorField = new TextFieldWidget(font, right - buttonWidth * 6, (int)(top + buttonHeight * 6.5), buttonWidth * 5, buttonHeight, I18n.translate("text." + ModMain.MOD_ID + ".boathor"));
		boatflyHorField.setText(String.format("%1.2f", boatSpeedHor));
		
		boatflyVertField = new TextFieldWidget(font, right - buttonWidth * 6, (int)(top + buttonHeight * 7.75), buttonWidth * 5, buttonHeight, I18n.translate("text." + ModMain.MOD_ID + ".boatvert"));
		boatflyVertField.setText(String.format("%1.2f", boatSpeedVert));
	}

	@Override
	public void render(int mouseX, int mouseY, float delta) {
		this.renderBackground();
		super.render(mouseX, mouseY, delta);
		this.font.draw(I18n.translate("lbl." + ModMain.MOD_ID + ".outline"), left + 20, top + 12, 0x404040);
		this.font.draw(I18n.translate("lbl." + ModMain.MOD_ID + ".reach"), right - buttonWidth * 6, top + 12, 0x404040);
		this.font.draw(I18n.translate("lbl." + ModMain.MOD_ID + ".spectate"), left + 20, top + 37, 0x404040);
		this.font.draw(I18n.translate("lbl." + ModMain.MOD_ID + ".fullbright"), left + 20, top + 62, 0x404040);
		this.font.draw(I18n.translate("lbl." + ModMain.MOD_ID + ".autofish"), right - buttonWidth * 6, top + 62, 0x404040);
		this.font.draw(I18n.translate("lbl." + ModMain.MOD_ID + ".xray"), left + 20, top + 87, 0x404040);
		this.font.draw(I18n.translate("lbl." + ModMain.MOD_ID + ".boatfly.active"), left + 20, top + 112, 0x404040);
		this.font.draw(I18n.translate("lbl." + ModMain.MOD_ID + ".nofall"), right - buttonWidth * 6, top + 112, 0x404040);
		this.font.draw(I18n.translate("lbl." + ModMain.MOD_ID + ".boatfly.hor"), left + 20, top + 137, 0x404040);
		this.font.draw(I18n.translate("lbl." + ModMain.MOD_ID + ".boatfly.vert"), left + 20, top + 162, 0x404040);
		this.spectatorField.render(mouseX, mouseY, delta);
		this.xrayFilterField.render(mouseX, mouseY, delta);
		this.boatflyHorField.render(mouseX, mouseY, delta);
		this.boatflyVertField.render(mouseX, mouseY, delta);
	}

	@Override
	public boolean charTyped(char chr, int keyCode) {
		if(this.spectatorField.isFocused()) {
			this.spectatorField.charTyped(chr, keyCode);
			spectateName = this.spectatorField.getText();
			ModMain.putConfigValue("name-to-spectate", spectateName);
			return true;
		}
		else if(this.xrayFilterField.isFocused()) {
			this.xrayFilterField.charTyped(chr, keyCode);
			xrayFilter = this.xrayFilterField.getText();
			ModMain.putConfigValue("xray-block-filter-regexes", xrayFilter);
			return true;
		}
		else if(this.boatflyHorField.isFocused()) {
			this.boatflyHorField.charTyped(chr, keyCode);
			boatSpeedHor = asFloat(boatflyHorField.getText(), 3.0f);
			ModMain.putConfigValue("boatfly-speed-horizontal", boatSpeedHor + "");
			return true;
		}
		else if(this.boatflyVertField.isFocused()) {
			this.boatflyVertField.charTyped(chr, keyCode);
			boatSpeedVert = asFloat(boatflyVertField.getText(), 3.0f);
			ModMain.putConfigValue("boatfly-speed-vertical", boatSpeedVert + "");
			return true;
		}
		
		return super.charTyped(chr, keyCode);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if(this.spectatorField.isFocused()) {
			this.spectatorField.keyPressed(keyCode, scanCode, modifiers);
			spectateName = this.spectatorField.getText();
			ModMain.putConfigValue("name-to-spectate", spectateName);
			return true;
		}
		else if(this.xrayFilterField.isFocused()) {
			this.xrayFilterField.keyPressed(keyCode, scanCode, modifiers);
			xrayFilter = this.xrayFilterField.getText();
			ModMain.putConfigValue("xray-block-filter-regexes", xrayFilter);
			return true;
		}
		else if(this.boatflyHorField.isFocused()) {
			this.boatflyHorField.keyPressed(keyCode, scanCode, modifiers);
			boatSpeedHor = asFloat(boatflyHorField.getText(), 3.0f);
			ModMain.putConfigValue("boatfly-speed-horizontal", boatSpeedHor + "");
			return true;
		}
		else if(this.boatflyVertField.isFocused()) {
			this.boatflyVertField.keyPressed(keyCode, scanCode, modifiers);
			boatSpeedVert = asFloat(boatflyVertField.getText(), 3.0f);
			ModMain.putConfigValue("boatfly-speed-vertical", boatSpeedVert + "");
			return true;
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (super.mouseClicked(mouseX, mouseY, button)) {
			return true;
		} else if(spectatorField.mouseClicked(mouseX, mouseY, button)){
			return true;
		} else if(xrayFilterField.mouseClicked(mouseX, mouseY, button)){
			return true;
		} else if(boatflyHorField.mouseClicked(mouseX, mouseY, button)){
			return true;
		} else if(boatflyVertField.mouseClicked(mouseX, mouseY, button)){
			return true;
		}
		return false;
	}

	public static void openConfig(MinecraftClient client) {
		if(ModMain.bindingOpenConfig.isPressed() && client.currentScreen == null) {
			KeyBinding.unpressAll();
			client.openScreen(new Config());
		}
	}
	
	@Override
	public void renderBackground() {
		this.fillGradient(0, 0, width, height, -1072689136, -804253680);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		blit(left, top, 0, 0, xSize, ySize);
	}

	public static void loadConfig(Properties properties) {
		isFullbrightActive = asBoolean((String) properties.computeIfAbsent("is-fullbright-active", obj -> "false"), false);
		isOutlineActive = asBoolean((String) properties.computeIfAbsent("is-outline-active", obj -> "false"), false);
		isReachActive = asBoolean((String) properties.computeIfAbsent("is-reach-active", obj -> "false"), false);
		isAutoFishActive = asBoolean((String) properties.computeIfAbsent("is-autofish-active", obj -> "false"), false);
		spectateName = (String) properties.computeIfAbsent("name-to-spectate", obj -> "");
		xrayFilter = (String) properties.computeIfAbsent("xray-block-filter-regexes", obj -> "ore");
		isReachActive = asBoolean((String) properties.computeIfAbsent("is-boatfly-active", obj -> "false"), false);
		isAutoFishActive = asBoolean((String) properties.computeIfAbsent("is-nofall-active", obj -> "false"), false);
		boatSpeedHor = asFloat((String) properties.computeIfAbsent("boatfly-speed-horizontal", obj -> "3.0"), 3.0f);
		boatSpeedVert = asFloat((String) properties.computeIfAbsent("boatfly-speed-vertical", obj -> "2.0"), 2.0f);
	}
	
	private static TriState asTriState(String property) {
		if (property == null || property.isEmpty()) {
			return TriState.DEFAULT;
		} else {
			switch (property.toLowerCase(Locale.ROOT)) {
			case "true":
				return TriState.TRUE;
			case "false":
				return TriState.FALSE;
			case "auto":
			default:
				return TriState.DEFAULT;
			}
		}
	}
	
	private static boolean asBoolean(String property, boolean defValue) {
		switch (asTriState(property)) {
		case TRUE:
			return true;
		case FALSE:
			return false;
		default:
			return defValue;
		}
	}
	
	private static float asFloat(String property, float defValue) {
		try {
			return Float.parseFloat(property);
		}catch(NumberFormatException | NullPointerException e) {
			return defValue;
		}
	}

	@Override
	public void onClose() {
		super.onClose();
		ModMain.saveConfig();
		if(isXRayActive && MinecraftClient.getInstance().worldRenderer != null)MinecraftClient.getInstance().worldRenderer.reload();
	}
	
	@Override
	public boolean isPauseScreen() {
	      return false;
	   }
	
}
