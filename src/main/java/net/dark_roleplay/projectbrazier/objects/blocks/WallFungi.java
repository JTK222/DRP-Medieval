package net.dark_roleplay.projectbrazier.objects.blocks;

import net.dark_roleplay.projectbrazier.objects.blocks.templates.WallHFacedDecoBlock;
import net.dark_roleplay.projectbrazier.mixin_helper.ICustomOffset;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public class WallFungi extends WallHFacedDecoBlock implements ICustomOffset {

	public WallFungi(Properties properties) {
		super(properties, "hoof_fungus");
	}


	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Vector3d offset = this.getOffset(state, worldIn, pos);
		return super.getShape(state, worldIn, pos, context).withOffset(offset.getX(), offset.getY(), offset.getZ());
	}

	@Override
	public Vector3d getOffset(BlockState state, IBlockReader access, BlockPos pos) {
		long i = MathHelper.getCoordinateRandom(pos.getX(), pos.getY(), pos.getZ());

		if(state.get(HORIZONTAL_FACING).getAxis() == Direction.Axis.X){
			return new Vector3d(0.0D, ((i % 6) / 16F), ((i % 3) / 16F));
		}else{
			return new Vector3d(((i % 3) / 16F), ((i % 6) / 16F), 0.0D);
		}
	}
}