package de.ljfa.iceshards.items;

import java.util.Collections;

import ljfa.glassshards.items.IModeledItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import de.ljfa.iceshards.IceShards;
import de.ljfa.iceshards.Reference;

public class ItemFrozenPickaxe extends ItemTool implements IModeledItem {
    public static final String name = "frozen_pickaxe";

    public ItemFrozenPickaxe() {
        super(0.0f, IceShards.toolMatPackedIce, Collections.EMPTY_SET);
        setUnlocalizedName(Reference.MODID + ":" + name);
        GameRegistry.registerItem(this, name);
    }

    @Override
    public float getDigSpeed(ItemStack stack, IBlockState state) {
        if(isMaterialIce(state.getBlock().getMaterial()))
            return efficiencyOnProperMaterial;
        else
            return super.getDigSpeed(stack, state);
    }
    
    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        if(player.capabilities.isCreativeMode || EnchantmentHelper.getSilkTouchModifier(player))
            return super.onBlockStartBreak(stack, pos, player);
        World world = player.worldObj;
        IBlockState state = world.getBlockState(pos);
        if(isMaterialIce(state.getBlock().getMaterial())) {
            emulateBlockHarvest(stack, world, pos, state, player);
            return true;
        } else
            return super.onBlockStartBreak(stack, pos, player);
    }
    
    private boolean isMaterialIce(Material mat) {
        return mat == Material.ice || mat == Material.packedIce;
    }
    
    private void emulateBlockHarvest(ItemStack stack, World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        world.playAuxSFXAtEntity(player, 2001, pos, Block.getStateId(state));
        stack.onBlockDestroyed(world, state.getBlock(), pos, player);
        if(stack.stackSize == 0)
            player.destroyCurrentEquippedItem();
        if(state.getBlock().removedByPlayer(world, pos, player, false)) {
            state.getBlock().dropBlockAsItem(world, pos, state, EnchantmentHelper.getFortuneModifier(player));
        }
    }

    @Override
    public void registerModels(ItemModelMesher mesher) {
        mesher.register(this, 0, new ModelResourceLocation(Reference.MODID + ":" + name, "inventory"));
    }
}
