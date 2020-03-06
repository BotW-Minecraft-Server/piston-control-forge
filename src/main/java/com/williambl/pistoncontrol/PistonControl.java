package com.williambl.pistoncontrol;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod(PistonControl.MODID)
public class PistonControl {

	public static final String MODID = "pistoncontrol";

	public static Tag<Block> PISTON_BEHAVIOR_NORMAL = new BlockTags.Wrapper(new ResourceLocation(MODID, "piston_behavior_normal"));
	public static Tag<Block> PISTON_BEHAVIOR_DESTROY = new BlockTags.Wrapper(new ResourceLocation(MODID, "piston_behavior_destroy"));
	public static Tag<Block> PISTON_BEHAVIOR_BLOCK = new BlockTags.Wrapper(new ResourceLocation(MODID, "piston_behavior_block"));
	public static Tag<Block> PISTON_BEHAVIOR_PUSH_ONLY = new BlockTags.Wrapper(new ResourceLocation(MODID, "piston_behavior_push_only"));
	public static Tag<Block> STICKY_BLOCKS = new BlockTags.Wrapper(new ResourceLocation(MODID, "sticky_blocks"));

	public static Tag<EntityType<?>> ENTITY_PISTON_BEHAVIOR_NORMAL = new EntityTypeTags.Wrapper(new ResourceLocation(MODID, "piston_behavior_normal"));
	public static Tag<EntityType<?>> PISTON_BEHAVIOR_IGNORE = new EntityTypeTags.Wrapper(new ResourceLocation(MODID, "piston_behavior_ignore"));

}

