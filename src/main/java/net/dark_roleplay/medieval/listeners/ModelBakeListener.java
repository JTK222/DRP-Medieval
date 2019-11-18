package net.dark_roleplay.medieval.listeners;

import com.google.common.collect.ImmutableMap;
import net.dark_roleplay.marg.api.Constants;
import net.dark_roleplay.marg.api.materials.MaterialRequirement;
import net.dark_roleplay.medieval.DarkRoleplayMedieval;
import net.dark_roleplay.medieval.objects.blocks.decoration.road_sign.RoadSignTileEntityRenderer;
import net.dark_roleplay.medieval.objects.enums.TimberedClayEnums;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.BasicState;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;


//@EventBusSubscriber(modid = DarkRoleplayMedieval.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelBakeListener {

    private static Logger LOGGER = LogManager.getLogger();

    private static Function<ResourceLocation, TextureAtlasSprite> textureGetter = location -> {
        assert location != null;
        return Minecraft.getInstance().getTextureMap().getAtlasSprite(location.toString());
    };

    private static ResourceLocation inputEmpty = new ResourceLocation("drpmedieval:block/timbered_clay/templates/shape");
    private static ResourceLocation inputFull = new ResourceLocation("drpmedieval:block/timbered_clay/templates/full");


//    @SubscribeEvent
//    public static void textureStitching(TextureStitchEvent.Pre event){
//        if(event.getMap().getBasePath().equals("textures")){
//
//            MaterialRequirement planks = new MaterialRequirement("wood", "planks");
//            event.addSprite(new ResourceLocation("drpmedieval:blocks/timbered_clay/timbered_clay_empty"));
//
//            planks.execute(material -> {
//                for(int i = 1; i <= 15; i++){
//                    event.addSprite(new ResourceLocation(material.getTextProv().searchAndReplace("drpmedieval:block/timbered_clay/borders/${material}_") + i));
//                }
//                for(TimberedClayEnums.TimberedClayType type : TimberedClayEnums.TimberedClayType.values()) {
//                    if (type == TimberedClayEnums.TimberedClayType.CLEAN) continue;
//                    event.addSprite(new ResourceLocation(material.getTextProv().searchAndReplace("drpmedieval:block/timbered_clay/shapes/${material}_") + type.getName()));
//                }
//            });
//        }
//    }
}
