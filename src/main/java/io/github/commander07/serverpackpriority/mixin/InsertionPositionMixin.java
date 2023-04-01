package io.github.commander07.serverpackpriority.mixin;

import io.github.commander07.serverpackpriority.ServerpackPriority;
import net.minecraft.resource.ResourcePackProfile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.function.Function;


@Mixin(ResourcePackProfile.InsertionPosition.class)
public class InsertionPositionMixin {
    @Inject(method = "insert", at= @At(value = "INVOKE", target = "Ljava/util/List;add(ILjava/lang/Object;)V"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public <T> void insert(List<T> items, T item, Function<T, ResourcePackProfile> profileGetter, boolean listInverted, CallbackInfoReturnable<Integer> cir, ResourcePackProfile.InsertionPosition insertionPosition, int i) {
        ResourcePackProfile rpp = (ResourcePackProfile) item;
        if ((insertionPosition == ResourcePackProfile.InsertionPosition.BOTTOM) || (rpp.getName().equals("server"))) {
            ServerpackPriority.LOGGER.info("Found server resource pack changing priority to be just above Minecraft)");
            i += 1;
            items.add(i, item);
            cir.setReturnValue(i);
        }
    }
}
