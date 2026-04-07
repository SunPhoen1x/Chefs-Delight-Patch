package com.phoen1x.chefsdelightpatch.mixin;

import net.redstonegames.chefsdelight.villager.ModVillagers;
import eu.pb4.polymer.core.api.utils.PolymerSyncedObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.npc.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModVillagers.class)
public abstract class ChefsDelightVillagersMixin {

    @Inject(method = "registerProfession", at = @At("RETURN"), remap = false)
    private static void polymerifyProfession(String name, net.minecraft.resources.ResourceKey<?> type, net.minecraft.sounds.SoundEvent workSound, CallbackInfoReturnable<VillagerProfession> cir) {
        VillagerProfession profession = cir.getReturnValue();
        PolymerSyncedObject.setSyncedObject(BuiltInRegistries.VILLAGER_PROFESSION, profession, 
            (obj, ctx) -> BuiltInRegistries.VILLAGER_PROFESSION.getOrThrow(VillagerProfession.NONE).value());
    }
}