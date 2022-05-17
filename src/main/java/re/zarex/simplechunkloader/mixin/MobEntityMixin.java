package re.zarex.simplechunkloader.mixin;


import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import re.zarex.simplechunkloader.SimpleChunkLoader;

@Mixin(MobEntity.class)
public class MobEntityMixin {

    @Inject(method = "checkDespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;squaredDistanceTo(Lnet/minecraft/entity/Entity;)D", shift = At.Shift.AFTER), cancellable = true)
    private void checkDespawn(CallbackInfo cir) {
        MobEntity ent = (MobEntity) (Object) this;
        Chunk chunk = ((ServerWorld)ent.getWorld()).getChunk(ent.getBlockPos());
        if (SimpleChunkLoader.FORCELOADEDCHUNKS.contains(chunk.getPos().toLong())) {
            cir.cancel();
        }
    }


}
