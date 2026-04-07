package com.phoen1x.chefsdelightpatch.impl;

import com.phoen1x.chefsdelightpatch.impl.entity.BasePolymerEntity;
import com.phoen1x.chefsdelightpatch.impl.res.ResourcePackGenerator;
import eu.pb4.factorytools.api.block.model.generic.BlockStateModelManager;
import eu.pb4.polymer.core.api.entity.PolymerEntityUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.resourcepack.extras.api.ResourcePackExtras;
import eu.pb4.polymer.resourcepack.extras.api.format.item.ItemAsset;
import eu.pb4.polymer.resourcepack.extras.api.format.item.model.BasicItemModel;
import eu.pb4.polymer.resourcepack.extras.api.format.item.tint.MapColorTintSource;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.redstonegames.chefsdelight.ChefsDelight;
import net.redstonegames.chefsdelight.villager.ModVillagers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ChefsDelightPatch implements ModInitializer {
    public static final String MOD_ID = "chefsdelightpatch";

    @Override
    public void onInitialize() {
        ResourcePackGenerator.setup();

        PolymerResourcePackUtils.addModAssets(ChefsDelight.MOD_ID);
        ResourcePackExtras.forDefault().addBridgedModelsFolder(ResourceLocation.fromNamespaceAndPath(ChefsDelight.MOD_ID, "entity"), (id, b) -> {
            return new ItemAsset(new BasicItemModel(id, List.of(new MapColorTintSource(0xFFFFFF))), new ItemAsset.Properties(true, true));
        });
        ResourcePackGenerator.setup();
        PolymerEntityUtils.registerPolymerEntityConstructor(EntityType.VILLAGER, villager -> {
            var profession = villager.getVillagerData().profession().value();
            if (profession == ModVillagers.CHEF || profession == ModVillagers.COOK) {
                return new BasePolymerEntity(villager);
            } else {
                return null;
            }
        });
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}