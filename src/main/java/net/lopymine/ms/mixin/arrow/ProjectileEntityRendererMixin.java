package net.lopymine.ms.mixin.arrow;

//? if <=1.21.1 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.entity.Entity;
import net.lopymine.ms.render.TransparencyManager;
import net.lopymine.ms.render.MoreSpaceLayers;
import net.lopymine.ms.entity.EntityCaptures;

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