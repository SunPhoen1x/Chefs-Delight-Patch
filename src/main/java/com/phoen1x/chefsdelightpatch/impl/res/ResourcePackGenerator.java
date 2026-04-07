package com.phoen1x.chefsdelightpatch.impl.res;

import com.phoen1x.chefsdelightpatch.impl.ChefsDelightPatch;
import com.phoen1x.chefsdelightpatch.impl.entity.model.EntityModels;
import eu.pb4.polymer.common.api.PolymerCommonUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.resourcepack.api.ResourcePackBuilder;
import eu.pb4.polymer.resourcepack.extras.api.format.atlas.AtlasAsset;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Files;

public class ResourcePackGenerator {
    public static final Int2ObjectMap<ResourceLocation> LEVEL_LOCATIONS = Util.make(new Int2ObjectOpenHashMap<>(), int2ObjectOpenHashMap -> {
        int2ObjectOpenHashMap.put(1, ResourceLocation.withDefaultNamespace("stone"));
        int2ObjectOpenHashMap.put(2, ResourceLocation.withDefaultNamespace("iron"));
        int2ObjectOpenHashMap.put(3, ResourceLocation.withDefaultNamespace("gold"));
        int2ObjectOpenHashMap.put(4, ResourceLocation.withDefaultNamespace("emerald"));
        int2ObjectOpenHashMap.put(5, ResourceLocation.withDefaultNamespace("diamond"));
    });

    public static void setup() {
        PolymerResourcePackUtils.RESOURCE_PACK_AFTER_INITIAL_CREATION_EVENT.register(ResourcePackGenerator::build);
    }

    private static void build(ResourcePackBuilder builder) {
        var atlas = AtlasAsset.builder();
        builder.addAssetsSource(ChefsDelightPatch.MOD_ID);
        builder.addAssetsSource("eastersdelight");
        copyVanillaAssets(builder,
                "assets/minecraft/textures/entity/villager/villager.png",
                "assets/chefsdelight/textures/entity/villager/villager.png"
        );

        for (ResourceLocation resourceLocation : BuiltInRegistries.VILLAGER_TYPE.keySet()) {
            copyVanillaAssets(builder,
                    "assets/minecraft/textures/entity/villager/type/" + resourceLocation.getPath() + ".png",
                    "assets/chefsdelight/textures/entity/villager/type/" + resourceLocation.getPath() + ".png"
            );
        }

        LEVEL_LOCATIONS.forEach((level, resourceLocation) -> {
            copyVanillaAssets(builder,
                    "assets/minecraft/textures/entity/villager/profession_level/" + resourceLocation.getPath() + ".png",
                    "assets/chefsdelight/textures/entity/villager/profession_level/" + resourceLocation.getPath() + ".png"
            );
        });
        for (var model : EntityModels.ALL) {
            model.generateAssets(builder::addData, atlas);
        }

        builder.addData("assets/minecraft/atlases/blocks.json", atlas.build());
    }

    private static void copyVanillaAssets(ResourcePackBuilder builder, String vanillaPath, String outputPath) {
        try {
            builder.addData(outputPath, Files.readAllBytes(PolymerCommonUtils.getClientJarRoot().resolve(vanillaPath)));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}