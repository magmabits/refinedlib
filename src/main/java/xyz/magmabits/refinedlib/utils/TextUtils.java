package xyz.magmabits.refinedlib.utils;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class TextUtils {
    public static MutableText applyTextGradient(String name, int start, int end) {
        MutableText text = Text.empty();
        int len = name.length();

        for (int i = 0; i < len; i++) {
            float t;
            if (i < len / 2f) {
                t = i / (len / 2f);
            } else {
                t = (len - i) / (len / 2f);
            }

            int color = lerpColor(start, end, t);

            text.append(Text.literal(String.valueOf(name.charAt(i))).styled(style -> style.withColor(color)));
        }
        return text;
    }

    private static int lerpColor(int a, int b, float t) {
        t = MathHelper.clamp(t, 0f, 1f);

        int ar = (a >> 16) & 0xFF;
        int ag = (a >> 8) & 0xFF;
        int ab = a & 0xFF;

        int br = (b >> 16) & 0xFF;
        int bg = (b >> 8) & 0xFF;
        int bb = b & 0xFF;

        int r = (int)(ar + (br - ar) * t);
        int g = (int)(ag + (bg - ag) * t);
        int b2 = (int)(ab + (bb - ab) * t);

        return (r << 16) | (g << 8) | b2;
    }
}
