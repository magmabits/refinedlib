package xyz.magmabits.refinedlib.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import xyz.magmabits.refinedlib.utils.SupporterUtils;

import java.util.List;

public class PlushieBlock extends Block implements Waterloggable {
    protected PlushieBlock(Settings settings) {
        super(settings);
    }

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = createCuboidShape(3.0, 0.0, 3.0, 13.0, 15.0, 13.0);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(WATERLOGGED, fluidState.isOf(Fluids.WATER));
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
}
