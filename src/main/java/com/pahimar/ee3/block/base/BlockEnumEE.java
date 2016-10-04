package com.pahimar.ee3.block.base;

import com.pahimar.ee3.EquivalentExchange3;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockEnumEE extends BlockEE {

    private boolean hasSubtypes;
    private final IEnumMeta[] VARIANTS;

    public BlockEnumEE(String name, IEnumMeta[] variants) {
        this(name, Material.ROCK, variants);
    }

    public BlockEnumEE(String name, Material material, IEnumMeta[] variants) {
        super(name, material);
        hasSubtypes = false;
        if (variants.length > 0) {
            VARIANTS = variants;
        }
        else {
            VARIANTS = new IEnumMeta[0];
        }
    }

    public boolean getHasSubtypes() {
        return hasSubtypes;
    }

    public void setHasSubtypes(boolean hasSubtypes) {
        this.hasSubtypes = hasSubtypes;
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem() != null && Block.getBlockFromItem(itemStack.getItem()) instanceof BlockEE) {
            if (getHasSubtypes() && VARIANTS.length > 0) {
                return String.format("tile.%s:%s", EquivalentExchange3.MOD_ID, VARIANTS[Math.abs(itemStack.getMetadata() % VARIANTS.length)].getName());
            }
        }

        return super.getUnlocalizedName();
    }

    @SideOnly(Side.CLIENT)
    public void initModelsAndVariants() {

        if (Item.getItemFromBlock(this) != null) {
            if (getHasSubtypes() && VARIANTS.length > 0) {
                for (IEnumMeta variant : VARIANTS) {
                    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), variant.getMeta(), new ModelResourceLocation(getRegistryName(), "variant=" + variant.getName()));
                }
            }
            else {
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName().toString()));
            }
        }
    }
}
