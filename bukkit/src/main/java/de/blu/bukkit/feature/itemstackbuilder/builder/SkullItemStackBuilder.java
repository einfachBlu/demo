package de.blu.bukkit.feature.itemstackbuilder.builder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.util.UUIDTypeAdapter;
import de.blu.bukkit.feature.itemstackbuilder.converter.GameProfileSerializer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import org.bukkit.Material;

public final class SkullItemStackBuilder extends ItemStackBuilder {

  public static final String MHF_ARROWLEFT = "{\"id\":\"a68f0b648d144000a95f4b9ba14f8df9\",\"name\":\"MHF_ArrowLeft\",\"properties\":[{\"name\":\"textures\",\"value\":\"eyJ0aW1lc3RhbXAiOjE1ODE4MDM1NTY0ODUsInByb2ZpbGVJZCI6ImE2OGYwYjY0OGQxNDQwMDBhOTVmNGI5YmExNGY4ZGY5IiwicHJvZmlsZU5hbWUiOiJNSEZfQXJyb3dMZWZ0IiwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2Y3YWFjYWQxOTNlMjIyNjk3MWVkOTUzMDJkYmE0MzM0MzhiZTQ2NDRmYmFiNWViZjgxODA1NDA2MTY2N2ZiZTIifX19\"}]}";
  public static final String MHF_ARROWRIGHT = "{\"id\":\"50c8510b5ea04d60be9a7d542d6cd156\",\"name\":\"MHF_ArrowRight\",\"properties\":[{\"name\":\"textures\",\"value\":\"eyJ0aW1lc3RhbXAiOjE1ODE4MDM3MDUyNTIsInByb2ZpbGVJZCI6IjUwYzg1MTBiNWVhMDRkNjBiZTlhN2Q1NDJkNmNkMTU2IiwicHJvZmlsZU5hbWUiOiJNSEZfQXJyb3dSaWdodCIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kMzRlZjA2Mzg1MzcyMjJiMjBmNDgwNjk0ZGFkYzBmODVmYmUwNzU5ZDU4MWFhN2ZjZGYyZTQzMTM5Mzc3MTU4In19fQ==\"}]}";
  public static final String MHF_EXCLAMATION = "{\"id\":\"d3c47f6fae3a45c1ad7ce2c762b03ae6\",\"name\":\"MHF_Exclamation\",\"properties\":[{\"name\":\"textures\",\"value\":\"eyJ0aW1lc3RhbXAiOjE1ODE4MDM4MDYwNDYsInByb2ZpbGVJZCI6ImQzYzQ3ZjZmYWUzYTQ1YzFhZDdjZTJjNzYyYjAzYWU2IiwicHJvZmlsZU5hbWUiOiJNSEZfRXhjbGFtYXRpb24iLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDBiMDVlNjk5ZDI4YjNhMjc4YTkyZDE2OWRjYTlkNTdjMDc5MWQwNzk5NGQ4MmRlM2Y5ZWQ0YTQ4YWZlMGUxZCJ9fX0=\"}]}";
  public static final String MHF_ALEX = "{\"id\":\"6ab4317889fd490597f60f67d9d76fd9\",\"name\":\"MHF_Alex\",\"properties\":[{\"name\":\"textures\",\"value\":\"eyJ0aW1lc3RhbXAiOjE1ODE4MDM4ODE1OTcsInByb2ZpbGVJZCI6IjZhYjQzMTc4ODlmZDQ5MDU5N2Y2MGY2N2Q5ZDc2ZmQ5IiwicHJvZmlsZU5hbWUiOiJNSEZfQWxleCIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS84M2NlZTVjYTZhZmNkYjE3MTI4NWFhMDBlODA0OWMyOTdiMmRiZWJhMGVmYjhmZjk3MGE1Njc3YTFiNjQ0MDMyIiwibWV0YWRhdGEiOnsibW9kZWwiOiJzbGltIn19fX0=\"}]}";
  public static final String MHF_STEVE = "{\"id\":\"c06f89064c8a49119c29ea1dbd1aab82\",\"name\":\"MHF_Steve\",\"properties\":[{\"name\":\"textures\",\"value\":\"eyJ0aW1lc3RhbXAiOjE1ODE4MDM5ODUxMzUsInByb2ZpbGVJZCI6ImMwNmY4OTA2NGM4YTQ5MTE5YzI5ZWExZGJkMWFhYjgyIiwicHJvZmlsZU5hbWUiOiJNSEZfU3RldmUiLCJ0ZXh0dXJlcyI6e319\"}]}";
  // public static final String MHF_TEMPLATE = "";

  private static final Gson GSON = new GsonBuilder().disableHtmlEscaping()
      .registerTypeAdapter(UUID.class, new UUIDTypeAdapter())
      .registerTypeAdapter(GameProfile.class, new GameProfileSerializer())
      .registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer()).create();

  private static Method metaSetProfileMethod;

  public SkullItemStackBuilder() {
    super();
    this.type(Material.PLAYER_HEAD);
  }

  public static SkullItemStackBuilder of(ItemStackBuilder itemStackBuilder) {
    itemStackBuilder.build();
    SkullItemStackBuilder skullItemStackBuilder = new SkullItemStackBuilder();
    skullItemStackBuilder.itemStack = itemStackBuilder.itemStack;
    skullItemStackBuilder.itemMeta = itemStackBuilder.itemMeta;
    return skullItemStackBuilder;
  }

  public SkullItemStackBuilder profile(GameProfile profile) {
    if (profile == null) {
      return this;
    }

    if (!this.getItemStack().getType().equals(Material.PLAYER_HEAD) && !this.getItemStack()
        .getType().equals(Material.PLAYER_WALL_HEAD)) {
      return this;
    }

    try {
      if (metaSetProfileMethod == null) {
        metaSetProfileMethod = this.getItemMeta().getClass()
            .getDeclaredMethod("setProfile", GameProfile.class);
        metaSetProfileMethod.setAccessible(true);
      }
      metaSetProfileMethod.invoke(this.getItemMeta(), profile);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }

    return this;
  }

  public SkullItemStackBuilder profile(String jsonProfile) {
    return this.profile(GSON.fromJson(jsonProfile, GameProfile.class));
  }

  public SkullItemStackBuilder texture(String value) {
    return this.texture(null, null, value, null);
  }

  public SkullItemStackBuilder texture(String value, String signature) {
    return this.texture(null, null, value, signature);
  }

  public SkullItemStackBuilder texture(UUID uuid, String value, String signature) {
    return this.texture(uuid, null, value, signature);
  }

  public SkullItemStackBuilder texture(UUID uuid, String value) {
    return this.texture(uuid, null, value, null);
  }

  public SkullItemStackBuilder texture(UUID profileId, String name, String value,
      String signature) {
    // if uuid is null, create a new one
    if (profileId == null) {
      profileId = UUID.randomUUID();
    }
    // Now we can create the GameProfile from nms
    GameProfile profile = new GameProfile(profileId, name);
    // Next we check and create the texture Property
    Property property;
    if (signature == null) {
      property = new Property("textures", value);
    } else {
      property = new Property("textures", value, signature);
    }
    // And put them into the profile
    profile.getProperties().put("textures", property);
    return this.profile(profile);
  }
}
