package com.valinor.game.world.entity.combat.ranged;

public enum CbowReqs {

    //CROSSBOWS
    CROSSBOW(9174,877),
    BLURITE_CROSSBOW(9174,877, 9139, 9140),
    DORGESHUUN_CBOW(9174,8882),
    BRONZE_CROSSBOW(9174,877),
    IRON_CROSSBOW(9177,877, 9140),
    STEEL_CROSSBOW(9179,877, 9140, 9141),
    MITHRIL_CROSSBOW(9181,877, 9140, 9141, 9142),
    ADAMANT_CROSSBOW(9183,877, 9140, 9141, 9142, 9143),
    RUNE_CROSSBOW(9185,877, 9140, 9141, 9142, 9143, 9144, 9236, 9237, 9238, 9239, 9240, 9241, 9242, 9243, 9244, 9245, 9335, 9336, 9337, 9338, 9339, 9340, 9341, 9342),
    ARMADYL_CROSSBOW(11785,877, 9140, 9141, 9142, 9143, 9144, 9236, 9237, 9238, 9239, 9240, 9241, 9242, 9243, 9244, 9245, 9335, 9336, 9337, 9338, 9339, 9340, 9341, 9342, 21905, 21955, 21957, 21959, 21961, 21963, 21965, 21967, 21969, 21971, 21973, 21932, 21934, 21936, 21938, 21940, 21942, 21944, 21946, 21948, 21950),
    DRAGON_CROSSBOW(21902,877, 9140, 9141, 9142, 9143, 9144, 9236, 9237, 9238, 9239, 9240, 9241, 9242, 9243, 9244, 9245, 9335, 9336, 9337, 9338, 9339, 9340, 9341, 9342, 21905, 21955, 21957, 21959, 21961, 21963, 21965, 21967, 21969, 21971, 21973, 21932, 21934, 21936, 21938, 21940, 21942, 21944, 21946, 21948, 21950),
    DRAGON_HUNTER_CROSSBOW(21012,877, 9140, 9141, 9142, 9143, 9144, 9236, 9237, 9238, 9239, 9240, 9241, 9242, 9243, 9244, 9245, 9335, 9336, 9337, 9338, 9339, 9340, 9341, 9342, 21905, 21955, 21957, 21959, 21961, 21963, 21965, 21967, 21969, 21971, 21973, 21932, 21934, 21936, 21938, 21940, 21942, 21944, 21946, 21948, 21950),
    DRAGON_HUNTER_CROSSBOW_T(25916,877, 9140, 9141, 9142, 9143, 9144, 9236, 9237, 9238, 9239, 9240, 9241, 9242, 9243, 9244, 9245, 9335, 9336, 9337, 9338, 9339, 9340, 9341, 9342, 21905, 21955, 21957, 21959, 21961, 21963, 21965, 21967, 21969, 21971, 21973, 21932, 21934, 21936, 21938, 21940, 21942, 21944, 21946, 21948, 21950),
    DRAGON_HUNTER_CROSSBOW_B(25918,877, 9140, 9141, 9142, 9143, 9144, 9236, 9237, 9238, 9239, 9240, 9241, 9242, 9243, 9244, 9245, 9335, 9336, 9337, 9338, 9339, 9340, 9341, 9342, 21905, 21955, 21957, 21959, 21961, 21963, 21965, 21967, 21969, 21971, 21973, 21932, 21934, 21936, 21938, 21940, 21942, 21944, 21946, 21948, 21950),
    HUNTERS_CROSSBOW(10156,10158),
    KARIL_CROSSBOW(4734,4740),
    TALONHAWK_CROSSBOW(32641,877, 9140, 9141, 9142, 9143, 9144, 9236, 9237, 9238, 9239, 9240, 9241, 9242, 9243, 9244, 9245, 9335, 9336, 9337, 9338, 9339, 9340, 9341, 9342, 21905, 21955, 21957, 21959, 21961, 21963, 21965, 21967, 21969, 21971, 21973, 21932, 21934, 21936, 21938, 21940, 21942, 21944, 21946, 21948, 21950),
    ZARYTE_CROSSBOW(26374,877, 9140, 9141, 9142, 9143, 9144, 9236, 9237, 9238, 9239, 9240, 9241, 9242, 9243, 9244, 9245, 9335, 9336, 9337, 9338, 9339, 9340, 9341, 9342, 21905, 21955, 21957, 21959, 21961, 21963, 21965, 21967, 21969, 21971, 21973, 21932, 21934, 21936, 21938, 21940, 21942, 21944, 21946, 21948, 21950),
    ;

    public int bow;
    public int[] ammo;

    CbowReqs(int bow, int... ammo) {
        this.bow = bow;
        this.ammo = ammo;
    }
}
