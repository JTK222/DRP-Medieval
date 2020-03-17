package net.dark_roleplay.medieval.listeners;

import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.handler.MedievalItems;
import net.dark_roleplay.medieval.objects.timbered_clay.util.TimberingNotesRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, value = Dist.CLIENT)
public class WorldRenderListener {

    @SubscribeEvent
    public static void worldRenderer(RenderWorldLastEvent event){
        PlayerEntity player = Minecraft.getInstance().player;
        ItemStack heldItem = player.getHeldItemMainhand();
        if(heldItem.getItem() == MedievalItems.TIMBERING_NOTES.get()){
            TimberingNotesRenderer.INSTANCE.renderWorld(heldItem);
        }
    }
}
