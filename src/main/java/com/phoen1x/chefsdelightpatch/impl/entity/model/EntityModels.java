package com.phoen1x.chefsdelightpatch.impl.entity.model;

import com.phoen1x.chefsdelightpatch.impl.entity.model.emuvanilla.PolyModelInstance;
import com.phoen1x.chefsdelightpatch.impl.entity.model.emuvanilla.model.EntityModel;
import com.phoen1x.chefsdelightpatch.impl.entity.model.emuvanilla.model.LayerDefinition;
import com.phoen1x.chefsdelightpatch.impl.entity.model.emuvanilla.model.MeshTransformer;
import com.phoen1x.chefsdelightpatch.impl.entity.model.emuvanilla.model.ModelPart;
import com.phoen1x.chefsdelightpatch.impl.entity.model.entity.VillagerModel;
import com.phoen1x.chefsdelightpatch.impl.res.ResourcePackGenerator;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface EntityModels {
    List<PolyModelInstance<?>> ALL = new ArrayList<>();

    MeshTransformer humanLikeScaling = MeshTransformer.scaling(0.9375F);
    LayerDefinition villagerData = LayerDefinition.create(VillagerModel.createBodyModel(), 64, 64).apply(humanLikeScaling);
    PolyModelInstance<VillagerModel> VILLAGER = create(modelPart -> new VillagerModel(modelPart, true), villagerData, ResourceLocation.fromNamespaceAndPath("chefsdelight", "entity/villager/villager"));
    Map<ResourceLocation, PolyModelInstance<VillagerModel>> VILLAGER_PROFESSION = Util.make(new HashMap<>(), m -> {
        var chefInstance = create(modelPart -> new VillagerModel(modelPart, true),
                villagerData, ResourceLocation.fromNamespaceAndPath("chefsdelight", "entity/villager/profession/chef"));
        m.put(ResourceLocation.fromNamespaceAndPath("chefsdelight", "chef"), chefInstance);

        var cookInstance = create(modelPart -> new VillagerModel(modelPart, true),
                villagerData, ResourceLocation.fromNamespaceAndPath("chefsdelight", "entity/villager/profession/cook"));
        m.put(ResourceLocation.fromNamespaceAndPath("chefsdelight", "cook"), cookInstance);
    });

    Int2ObjectMap<PolyModelInstance<VillagerModel>> VILLAGER_PROFESSION_LEVEL = Util.make(new Int2ObjectOpenHashMap<>(), m -> {
        ResourcePackGenerator.LEVEL_LOCATIONS.forEach((level, resourceLocation) -> {
            var instance = create(modelPart -> new VillagerModel(modelPart, false), villagerData, ResourceLocation.fromNamespaceAndPath("chefsdelight","entity/villager/profession_level/" + resourceLocation.getPath()));
            m.put(level, instance);
        });
    });
    Map<ResourceLocation, PolyModelInstance<VillagerModel>> VILLAGER_TYPE = Util.make(new HashMap<>(), m -> {
        for (ResourceLocation resourceLocation : BuiltInRegistries.VILLAGER_TYPE.keySet()) {
            var instance = create(modelPart -> new VillagerModel(modelPart, false), villagerData, ResourceLocation.fromNamespaceAndPath("chefsdelight","entity/villager/type/" + resourceLocation.getPath()));
            m.put(resourceLocation, instance);
        }
    });


    static <T extends EntityModel<?>> PolyModelInstance<T> create(Function<ModelPart, T> modelCreator, LayerDefinition data, ResourceLocation texture) {
        var instance = PolyModelInstance.create(modelCreator, data, texture);
        ALL.add(instance);
        return instance;
    }
}
