package com.newera.docs.entity.ai.goal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class ChickenFlyGoal extends Goal {
    private final Chicken chicken;
    private int flyDuration;
    private int cooldown;

    public ChickenFlyGoal(Chicken chicken) {
        this.chicken = chicken;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.chicken.isBaby()) {
            return false;
        }
        if (this.chicken.isInWater() || this.chicken.getLastHurtByMob() != null) {
            return false;
        }
        if (this.cooldown > 0) {
            this.cooldown--;
            return false;
        }
        if (this.chicken instanceof com.newera.docs.entity.animation.INEChickenAnimation anim) {
            if (anim.getBodyState() != 0) return false;
        }
        if (!this.chicken.onGround()) {
            return false;
        }
        if (this.chicken.getNavigation().isDone()) {
            return false; // Only fly when moving
        }

        // Tình huống 1: Bị kẹt vật cản phía trước
        if (this.chicken.horizontalCollision) {
            return true;
        }

        // Tình huống 2: Đang chạy nhanh (vận tốc ngang lớn do hoảng sợ/chạy trốn)
        double horizontalSpeedSqr = this.chicken.getDeltaMovement().horizontalDistanceSqr();
        if (horizontalSpeedSqr > 0.01) { // 0.1^2 = 0.01
            // Tốc độ cao thì tỷ lệ ngẫu nhiên bay lên lướt đi
            return this.chicken.getRandom().nextInt(40) == 0;
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.flyDuration <= 0 || this.chicken.isInWater()) return false;
        // Nếu đã bay được một lúc (5 ticks) mà chạm đất thì dừng ngay lập tức
        if (this.flyDuration < 15 && this.chicken.onGround()) return false;
        return true;
    }

    @Override
    public void start() {
        this.flyDuration = 20; 
        
        Vec3 currentDelta = this.chicken.getDeltaMovement();
        double speed = currentDelta.horizontalDistance();
        
        // Cấp lực nhảy (nhảy lên). Nếu đang chạy nhanh thì nhảy bật cao hơn một chút.
        double jumpStrength = 0.45;
        if (speed > 0.1) {
            jumpStrength = 0.55;
        }

        // Vector hướng nhìn để xác định hướng lướt tới
        Vec3 forward = this.chicken.getLookAngle().multiply(1.0, 0.0, 1.0).normalize();
        
        // Nhân thêm đà tiến lên dựa trên tốc độ hiện tại. Chạy càng nhanh bay càng xa.
        double boostMultiplier = speed * 2.5; 
        if (boostMultiplier < 0.2) boostMultiplier = 0.2; // Đảm bảo lực đẩy tối thiểu

        this.chicken.setDeltaMovement(
                currentDelta.x + forward.x * boostMultiplier,
                jumpStrength,
                currentDelta.z + forward.z * boostMultiplier
        );
    }

    @Override
    public void tick() {
        if (this.flyDuration > 0) {
            this.flyDuration--;
            
            // Trong lúc rơi xuống (y < 0), bổ sung một lực lướt nhẹ về phía trước
            Vec3 currentDelta = this.chicken.getDeltaMovement();
            if (currentDelta.y < 0) {
                Vec3 forward = this.chicken.getLookAngle().multiply(1.0, 0.0, 1.0).normalize();
                this.chicken.setDeltaMovement(
                    currentDelta.x + forward.x * 0.05,
                    currentDelta.y,
                    currentDelta.z + forward.z * 0.05
                );
            }
        }
    }

    @Override
    public void stop() {
        this.flyDuration = 0;
        this.cooldown = 60 + this.chicken.getRandom().nextInt(100);
    }
}
