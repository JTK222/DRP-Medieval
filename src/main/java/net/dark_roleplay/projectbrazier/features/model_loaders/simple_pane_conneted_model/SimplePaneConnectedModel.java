package net.dark_roleplay.projectbrazier.features.model_loaders.simple_pane_conneted_model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.dark_roleplay.projectbrazier.features.model_loaders.axis_connected_models.AxisConnectionType;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.resources.IResourceManager;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class SimplePaneConnectedModel implements IModelGeometry {

	private final IUnbakedModel baseModel, priPosModel, priNegModel, secPosModel, secNegModel;

	public SimplePaneConnectedModel(IUnbakedModel baseModel, IUnbakedModel priPosModel, IUnbakedModel priNegModel, IUnbakedModel secPosModel, IUnbakedModel secNegModel) {
		this.baseModel = baseModel;
		this.priPosModel = priPosModel;
		this.priNegModel = priNegModel;
		this.secPosModel = secPosModel;
		this.secNegModel = secNegModel;
	}

	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
		return new ConnectedBakedModel(
				this.baseModel.bakeModel(bakery, spriteGetter, modelTransform, modelLocation),
				this.priPosModel.bakeModel(bakery, spriteGetter, modelTransform, modelLocation),
				this.priNegModel.bakeModel(bakery, spriteGetter, modelTransform, modelLocation),
				this.secPosModel.bakeModel(bakery, spriteGetter, modelTransform, modelLocation),
				this.secNegModel.bakeModel(bakery, spriteGetter, modelTransform, modelLocation));
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner, Function modelGetter, Set missingTextureErrors) {

		Set<Material> textures = new HashSet<>();

		textures.addAll(baseModel.getTextures(modelGetter, missingTextureErrors));
		textures.addAll(priPosModel.getTextures(modelGetter, missingTextureErrors));
		textures.addAll(priNegModel.getTextures(modelGetter, missingTextureErrors));
		textures.addAll(secPosModel.getTextures(modelGetter, missingTextureErrors));
		textures.addAll(secNegModel.getTextures(modelGetter, missingTextureErrors));

		return textures;
	}

	public static class ConnectedBakedModel extends BakedModelWrapper {

		protected final IBakedModel priPosModel, priNegModel, secPosModel, secNegModel;

		public ConnectedBakedModel(IBakedModel baseModel, IBakedModel priPosModel, IBakedModel priNegModel, IBakedModel secPosModel, IBakedModel secNegModel) {
			super(baseModel);
			this.priPosModel = priPosModel;
			this.priNegModel = priNegModel;
			this.secPosModel = secPosModel;
			this.secNegModel = secNegModel;
		}


		@Nonnull
		@Override
		public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
			Direction facing = state.has(BlockStateProperties.HORIZONTAL_FACING) ? state.get(BlockStateProperties.HORIZONTAL_FACING) :
					state.has(BlockStateProperties.FACING) ? state.get(BlockStateProperties.FACING) : null;
			boolean flag = facing == Direction.NORTH || facing == Direction.EAST || facing == Direction.UP || facing == Direction.DOWN;
			boolean flag2 = facing == Direction.DOWN;


			if (extraData instanceof SimplePaneModelData) {
				List<BakedQuad> totalQuads = new ArrayList<>();
				totalQuads.addAll(this.originalModel.getQuads(state, side, rand, extraData));

				AxisConnectionType primaryConnection = extraData.getData(SimplePaneModelData.PRIMARY_CONNECTION);
				if(!flag2 ? !primaryConnection.isPositive() : !primaryConnection.isNegative())
					totalQuads.addAll(this.priPosModel.getQuads(state, side, rand, extraData));
				if(!flag2 ? !primaryConnection.isNegative() : !primaryConnection.isPositive())
					totalQuads.addAll(this.priNegModel.getQuads(state, side, rand, extraData));

				AxisConnectionType secondaryConnection = extraData.getData(SimplePaneModelData.SECONDARY_CONNECTION);
				if(flag && !secondaryConnection.isPositive() || !flag && !secondaryConnection.isNegative())
					totalQuads.addAll(this.secPosModel.getQuads(state, side, rand, extraData));
				if(flag && !secondaryConnection.isNegative() || !flag && !secondaryConnection.isPositive())
					totalQuads.addAll(this.secNegModel.getQuads(state, side, rand, extraData));
				return totalQuads;
			}

			return this.originalModel.getQuads(state, side, rand, extraData);
		}

		@Nonnull
		@Override
		public IModelData getModelData(@Nonnull ILightReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
			IModelData data = new SimplePaneModelData();

			boolean flag, flag2 = false, flag3 = false;
			if ((flag = state.has(BlockStateProperties.HORIZONTAL_AXIS)) ||
					(flag2 = state.has(BlockStateProperties.AXIS)) ||
					(flag3 = state.has(BlockStateProperties.HORIZONTAL_FACING)) ||
					state.has(BlockStateProperties.FACING)
			) {
				Direction.Axis axis = flag ?
						state.get(BlockStateProperties.HORIZONTAL_AXIS) :
						flag2 ?
								state.get(BlockStateProperties.AXIS) :
								flag3 ? state.get(BlockStateProperties.HORIZONTAL_FACING).getAxis() :
										state.get(BlockStateProperties.FACING).getAxis();

				switch(axis){
					case X:
						data.setData(SimplePaneModelData.PRIMARY_CONNECTION, AxisConnectionType.getConnections(world, pos, state, Direction.Axis.Y));
						data.setData(SimplePaneModelData.SECONDARY_CONNECTION, AxisConnectionType.getConnections(world, pos, state, Direction.Axis.Z));
						break;
					case Z:
						data.setData(SimplePaneModelData.PRIMARY_CONNECTION, AxisConnectionType.getConnections(world, pos, state, Direction.Axis.Y));
						data.setData(SimplePaneModelData.SECONDARY_CONNECTION, AxisConnectionType.getConnections(world, pos, state, Direction.Axis.X));
						break;
					case Y:
						data.setData(SimplePaneModelData.PRIMARY_CONNECTION, AxisConnectionType.getConnections(world, pos, state, Direction.Axis.Z));
						data.setData(SimplePaneModelData.SECONDARY_CONNECTION, AxisConnectionType.getConnections(world, pos, state, Direction.Axis.X));
						break;
				}
			}

			return data;
		}
	}

	public static class Loader implements IModelLoader {
		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {}

		@Override
		public IModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
			IUnbakedModel baseModel, priPosModel, priNegModel, secPosModel, secNegModel;

			JsonObject primarySubContents = JSONUtils.getJsonObject(modelContents, "primary");
			JsonObject secondarySubContents = JSONUtils.getJsonObject(modelContents, "secondary");

			baseModel = deserializationContext.deserialize(JSONUtils.getJsonObject(modelContents,"base"), BlockModel.class);
			priPosModel = deserializationContext.deserialize(JSONUtils.getJsonObject(primarySubContents,"positive"), BlockModel.class);
			priNegModel = deserializationContext.deserialize(JSONUtils.getJsonObject(primarySubContents,"negative"), BlockModel.class);
			secPosModel = deserializationContext.deserialize(JSONUtils.getJsonObject(secondarySubContents,"positive"), BlockModel.class);
			secNegModel = deserializationContext.deserialize(JSONUtils.getJsonObject(secondarySubContents,"negative"), BlockModel.class);

			return new SimplePaneConnectedModel(baseModel, priPosModel, priNegModel, secPosModel, secNegModel);
		}
	}
}
