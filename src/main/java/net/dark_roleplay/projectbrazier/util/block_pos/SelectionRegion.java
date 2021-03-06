package net.dark_roleplay.projectbrazier.util.block_pos;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

public class SelectionRegion implements INBTSerializable<CompoundNBT> {

    private BlockPos start;
    private BlockPos target;

    private int width;
    private int height;
    private int length;

    public SelectionRegion(){}


    public SelectionRegion(BlockPos start, BlockPos target){
        this.setRegion(start, target);
    }

    public void calcSize(){
        if(start == null || target == null) return;

        this.width = target.getX() - start.getX() + 1;
        this.height = target.getY() - start.getY() + 1;
        this.length = target.getZ() - start.getZ() + 1;
    }

    public BlockPos getStart() {
        return start;
    }

    public void setStart(BlockPos start) {
        this.start = start;
        this.calcSize();
    }

    public BlockPos getTarget() {
        return target;
    }

    public void setTarget(BlockPos target) {
        this.target = target;
        this.calcSize();
    }

    public void setRegion(BlockPos posA, BlockPos posB){
        if(posA != null && posB != null){
            this.start = BlockPosUtil.getMin(posA, posB);
            this.target = BlockPosUtil.getMax(posA, posB);
            this.calcSize();
        }else if(posA != null){
            this.start = posA;
        }else if(posB != null){
            this.target = posB;
        }

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();

        if(this.start != null)
            tag.put("S", NBTUtil.writeBlockPos(this.start));
        if(this.target != null)
            tag.put("T", NBTUtil.writeBlockPos(this.target));

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if(nbt.contains("S"))
            this.start = NBTUtil.readBlockPos(nbt.getCompound("S"));
        if(nbt.contains("T"))
            this.target = NBTUtil.readBlockPos(nbt.getCompound("T"));

        this.calcSize();
    }
}
