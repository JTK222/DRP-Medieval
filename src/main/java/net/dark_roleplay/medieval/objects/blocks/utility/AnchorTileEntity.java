package net.dark_roleplay.medieval.objects.blocks.utility;

import net.dark_roleplay.medieval.holders.MedievalTileEntities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class AnchorTileEntity extends TileEntity{

    private int remainingLength = 0;

    public AnchorTileEntity() {
        this(16);
    }

    public AnchorTileEntity(int length) {
        super(MedievalTileEntities.ANCHOR);
        remainingLength = length;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);

        if (compound.contains("remainingLength"))
            this.remainingLength = compound.getInt("remainingLength");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound = super.write(compound);
        compound.putInt("remainingLength", remainingLength);
        return compound;
    }

    public int getRemaining(){
        return this.remainingLength;
    }

    public void shrink(){
        this.remainingLength--;
        this.markDirty();
    }

    public void grow(){
        this.remainingLength++;
        this.markDirty();
    }
}