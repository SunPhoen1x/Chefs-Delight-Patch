package com.phoen1x.chefsdelightpatch.mixin;

import net.redstonegames.chefsdelight.villager.ModVillagers;
import eu.pb4.polymer.rsm.api.RegistrySyncUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModVillagers.class)
public abstract class ChefsDelightPoiMixin {

    /**
     * Міксін для реєстрації POI через Polymer.
     * Дозволяє серверу синхронізувати робочі станції без наявності моду у гравця.
     */
    @Inject(method = "registerPOI", at = @At("RETURN"), remap = false)
    private static void polymerifyPoi(String name, net.minecraft.world.level.block.Block block, CallbackInfoReturnable<PoiType> cir) {
        PoiType poi = cir.getReturnValue();
        if (poi != null) {
            // Використовуємо RegistrySyncUtils для позначення POI як суто серверного запису
            RegistrySyncUtils.setServerEntry(BuiltInRegistries.POINT_OF_INTEREST_TYPE, poi);
        }
    }
}