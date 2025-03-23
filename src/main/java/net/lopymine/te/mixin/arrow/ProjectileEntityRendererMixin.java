package net.lopymine.te.mixin.arrow;

//? if <=1.21.1 {
/*import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.entity.Entity;
import net.lopymine.te.render.TransparencyManager;
import net.lopymine.te.entity.EntityCaptures;

@Mixin(ProjectileEntityRenderer.class)
public class ProjectileEntityRendererMixin {

	@ModifyArg(method = "vertex", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumer;color(I)Lnet/minecraft/client/render/VertexConsumer;"))
    private int modifyVertexAlpha(int argb) {
        Entity entity = EntityCaptures.MAIN.getEntity();
        if (entity != null) {
            return TransparencyManager.getTranslucentArgb(entity, argb);
        }
        return argb;
    }
}
*///?}