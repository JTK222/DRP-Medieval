package net.dark_roleplay.project_brazier.features.blocks;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.project_brazier.features.blocks.templates.AxisDecoBlock;
import net.dark_roleplay.project_brazier.features.blocks.templates.ConnectedAxisDecoBlock;
import net.dark_roleplay.project_brazier.features.blocks.templates.DecoBlock;
import net.dark_roleplay.project_brazier.features.blocks.templates.FacedDecoBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class BlockCreators {

	public static Block createClosedBarrel(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD);
		return new DecoBlock(properties, "closed_barrel");
	}


	public static Block createOpenBarrel(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD);
		return new DecoBlock(properties, "open_barrel");
	}

	public static Block createWoodPlatform(IMaterial material, boolean isTop){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD);
		return new AxisDecoBlock(properties, isTop ? "top_wood_platform" : "bottom_wood_platform");
	}


	public static Block createWoodBench(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD);
		return new ConnectedAxisDecoBlock(properties, "default_wood_bench", "positive_wood_bench", "negative_wood_bench", "centered_wood_bench");
	}

	public static Block createPolsteredWoodBench(IMaterial material){
		Block.Properties properties =
				Block.Properties.create(Material.WOOD, material.getProperties().getMaterialColor())
						.hardnessAndResistance(2.0F, 3.0F)
						.sound(SoundType.WOOD);
		return new ConnectedAxisDecoBlock(properties, "default_polstered_wood_bench", "positive_polstered_wood_bench", "negative_polstered_wood_bench", "centered_polstered_wood_bench");
	}

	public static Block createJailLattice(){
		Block.Properties properties =
				Block.Properties.create(Material.IRON, MaterialColor.IRON)
						.hardnessAndResistance(5.0F, 1200.0F)
						.sound(SoundType.ANVIL);
		return new FacedDecoBlock(properties, "jail_lattice");
	}
}