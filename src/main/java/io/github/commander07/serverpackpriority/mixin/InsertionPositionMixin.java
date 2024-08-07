package io.github.commander07.serverpackpriority.mixin;

import io.github.commander07.serverpackpriority.ServerpackPriority;
import net.minecraft.resource.ResourcePackPosition;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.function.Function;


@Mixin(ResourcePackProfile.InsertionPosition.class)
public abstract class InsertionPositionMixin {
    @Inject(method = "insert", at= @At(value = "HEAD"), cancellable = true)
    public <T> void insert(List<T> items, T item, Function<T, ResourcePackPosition> profileGetter, boolean listInverted, CallbackInfoReturnable<Integer> cir) {
        int i;
        ResourcePackPosition resourcePackPosition;
        if (((ResourcePackProfile) item).getSource() == ResourcePackSource.SERVER) {
            for(i = 0; i < items.size(); ++i) {
                resourcePackPosition = (ResourcePackPosition)profileGetter.apply(items.get(i));
                if (!resourcePackPosition.fixedPosition() || resourcePackPosition.defaultPosition() != ResourcePackProfile.InsertionPosition.TOP) {
                    break;
                }
            }
            i += 2;
            items.add(i, item);
            cir.setReturnValue(i);
        }
    }
}
