package xyz.magmabits.refinedlib.utils;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.HoveredTooltipPositioner;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipPositioner;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.joml.Vector2ic;

import java.util.List;
import java.util.Optional;

public final class TooltipUtils {
    final int scaledWindowWidth;
    final int scaledWindowHeight;
    final MatrixStack matrices;
    final DrawContext context;
    final VertexConsumerProvider.Immediate vertexConsumers;

    public TooltipUtils(int width, int height, MatrixStack matrices, DrawContext context, VertexConsumerProvider.Immediate vertexConsumers){
        this.scaledWindowWidth = width;
        this.scaledWindowHeight = height;
        this.matrices = matrices;
        this.context = context;
        this.vertexConsumers = vertexConsumers;
    }

    public void drawTooltip(TextRenderer textRenderer, List<Text> text, Optional<TooltipData> data, int x, int y, ItemStack stack) {
        List list = (List)text.stream().map(Text::asOrderedText).map(TooltipComponent::of).collect(Util.toArrayList());
        data.ifPresent((datax) -> list.add(list.isEmpty() ? 0 : 1, TooltipComponent.of(datax)));
        this.drawTooltip(textRenderer, list, x, y, HoveredTooltipPositioner.INSTANCE, stack);
    }

    private void drawTooltip(TextRenderer textRenderer, List<TooltipComponent> components, int x, int y, TooltipPositioner positioner, ItemStack stack) {
        if (components.isEmpty()) return;

        int width = 0;
        int height = components.size() == 1 ? -2 : 0;

        for (TooltipComponent c : components) {
            width = Math.max(width, c.getWidth(textRenderer));
            height += c.getHeight();
        }

        Vector2ic pos = positioner.getPosition(scaledWindowWidth, scaledWindowHeight, x, y, width, height);

        int n = pos.x();
        int o = pos.y();

        int finalWidth = width;
        int finalHeight = height;
        context.draw(() -> render(context, n, o, finalWidth, finalHeight, 400, stack));

        this.matrices.push();
        this.matrices.translate(0.0F, 0.0F, 400.0F);

        int yOffset = o;

        for (int i = 0; i < components.size(); i++) {
            TooltipComponent c = components.get(i);

            c.drawText(textRenderer, n, yOffset, this.matrices.peek().getPositionMatrix(), this.vertexConsumers);

            yOffset += c.getHeight() + (i == 0 ? 2 : 0);
        }

        yOffset = o;

        for (int i = 0; i < components.size(); i++) {
            TooltipComponent c = components.get(i);
            c.drawItems(textRenderer, n, yOffset, context);

            yOffset += c.getHeight() + (i == 0 ? 2 : 0);
        }

        this.matrices.pop();

        this.vertexConsumers.draw();
    }

    public static void render(DrawContext context, int x, int y, int width, int height, int z, ItemStack stack) {
        int i = x - 3;
        int j = y - 3;
        int k = width + 3 + 3;
        int l = height + 3 + 3;
        if (stack.getItem() instanceof ColorableItem colorableItem){
            int startColor = colorableItem.startColor(stack);
            int endColor = colorableItem.endColor(stack);
            int bgColor = colorableItem.backgroundColor(stack);

            renderHorizontalLine(context, i, j - 1, k, z, bgColor);
            renderHorizontalLine(context, i, j + l, k, z, bgColor);
            renderRectangle(context, i, j, k, l, z, bgColor);
            renderVerticalLine(context, i - 1, j, l, z, bgColor);
            renderVerticalLine(context, i + k, j, l, z, bgColor);
            renderBorder(context, i, j + 1, k, l, z, startColor, endColor);
        }
    }

    private static void renderBorder(DrawContext context, int x, int y, int width, int height, int z, int startColor, int endColor) {
        renderVerticalLine(context, x, y, height - 2, z, startColor, endColor);
        renderVerticalLine(context, x + width - 1, y, height - 2, z, startColor, endColor);
        renderHorizontalLine(context, x, y - 1, width, z, startColor);
        renderHorizontalLine(context, x, y - 1 + height - 1, width, z, endColor);
    }

    private static void renderVerticalLine(DrawContext context, int x, int y, int height, int z, int color) {
        context.fill(x, y, x + 1, y + height, z, color);
    }

    private static void renderVerticalLine(DrawContext context, int x, int y, int height, int z, int startColor, int endColor) {
        context.fillGradient(x, y, x + 1, y + height, z, startColor, endColor);
    }

    private static void renderHorizontalLine(DrawContext context, int x, int y, int width, int z, int color) {
        context.fill(x, y, x + width, y + 1, z, color);
    }

    private static void renderRectangle(DrawContext context, int x, int y, int width, int height, int z, int color) {
        context.fill(x, y, x + width, y + height, z, color);
    }
}