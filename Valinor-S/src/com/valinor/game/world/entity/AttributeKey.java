package com.valinor.game.world.entity;

/**
 * Created by Bart on 8/13/2015.
 */
public enum AttributeKey {

    // The total amount of blood money (w2) risked including our +1 when entering the wilderness. Anti-rag mechanic.
    RISKED_WEALTH("risked_wealth", AttribType.LONG),

    WEAPON_UPGRADES("weapon_upgrades", AttribType.INTEGER),
    DDS_KILLS("dds_kills", AttribType.INTEGER),
    GMAUL_KILLS("gmaul_kills", AttribType.INTEGER),
    WHIP_KILLS("whip_kills", AttribType.INTEGER),
    D_SCIM_KILLS("d_scim_kills", AttribType.INTEGER),
    STAFF_OF_LIGHT_KILLS("staff_of_light_kills", AttribType.INTEGER),
    RUNE_C_BOW_KILLS("rune_c_bow_kills", AttribType.INTEGER),
    D_MACE_KILLS("d_mace_kills", AttribType.INTEGER),
    D_LONG_KILLS("d_long_kills", AttribType.INTEGER),
    MSB_KILLS("msb_kills", AttribType.INTEGER),
    DBOW_KILLS("dbow_kills", AttribType.INTEGER),

    DDS_KILLS_TIER_UNLOCKED("dds_kills_tier_unlock", AttribType.INTEGER),
    GMAUL_KILLS_TIER_UNLOCKED("gmaul_kills_tier_unlock", AttribType.INTEGER),
    WHIP_KILLS_TIER_UNLOCKED("whip_kills_tier_unlock", AttribType.INTEGER),
    D_SCIM_KILLS_TIER_UNLOCKED("d_scim_kills_tier_unlock", AttribType.INTEGER),
    STAFF_OF_LIGHT_KILLS_TIER_UNLOCKED("staff_of_light_kills_tier_unlock", AttribType.INTEGER),
    RUNE_C_BOW_KILLS_TIER_UNLOCKED("rune_c_bow_kills_tier_unlock", AttribType.INTEGER),
    D_MACE_KILLS_TIER_UNLOCKED("d_mace_kills_tier_unlock", AttribType.INTEGER),
    D_LONG_KILLS_TIER_UNLOCKED("d_long_kills_tier_unlock", AttribType.INTEGER),
    MSB_KILLS_TIER_UNLOCKED("msb_kills_tier_unlock", AttribType.INTEGER),
    DBOW_KILLS_TIER_UNLOCKED("dbow_kills_tier_unlock", AttribType.INTEGER),

    CORRUPTED_BOOTS_ATTEMTPS("corrupted_boots_attempts", AttribType.INTEGER),
    ANCIENT_FACEGUARD_ATTEMPTS("ancient_facegaurd_attempts", AttribType.INTEGER),
    TOXIC_STAFF_OF_THE_DEAD_C_ATTEMPTS("toxic_staff_of_the_dead_c_attempts", AttribType.INTEGER),
    SANGUINE_TWISTED_BOW_ATTEMTPS("sanguine_twisted_bow_attempts", AttribType.INTEGER),
    DARK_ELDER_MAUL_ATTEMPTS("dark_elder_maul_attempts", AttribType.INTEGER),
    ANCIENT_VLS_ATTEMPTS("ancient_vls_attempts", AttribType.INTEGER),
    ANCIENT_SWH_ATTEMPTS("ancient_swh_attempts", AttribType.INTEGER),
    ARMADYL_GODSWORD_OR_ATTEMPTS("armadyl_godsword_or_attempts", AttribType.INTEGER),
    BANDOS_GODSWORD_OR_ATTEMPTS("bandos_godsword_or_attempts", AttribType.INTEGER),
    SARADOMIN_GODSWORD_OR_ATTEMPTS("sardomin_godsword_or_attempts", AttribType.INTEGER),
    ZAMORAK_GODSWORD_OR_ATTEMPTS("zamorak_godsword_or_attempts", AttribType.INTEGER),
    FURY_OR_ATTEMPTS("amulet_of_fury_or_attempts", AttribType.INTEGER),
    OCCULT_OR_ATTEMPTS("occult_necklace_or_attempts", AttribType.INTEGER),
    TORTURE_OR_ATTEMPTS("amulet_of_torture_or_attempts", AttribType.INTEGER),
    ANGUISH_OR_ATTEMPTS("amulet_of_anguish_or_attempts", AttribType.INTEGER),
    BERSERKER_NECKLACE_OR_ATTEMPTS("berserker_necklace_or_attempts", AttribType.INTEGER),
    TORMENTED_BRACELET_OR_ATTEMPTS("tormented_bracelet_or_attempts", AttribType.INTEGER),
    GRANITE_MAUL_OR_ATTEMPTS("granite_maul_or_attempts", AttribType.INTEGER),
    RUNE_POUCH_I_ATTEMPTS("rune_pouch_i_attempts", AttribType.INTEGER),
    DRAGON_CLAWS_OR_ATTEMPTS("dragon_claws_or_attempts", AttribType.INTEGER),
    ANCESTRAL_HAT_I_ATTEMPTS("ancestral_hat_I_attempts", AttribType.INTEGER),
    ANCESTRAL_ROBE_TOP_I_ATTEMPTS("ancestral_top_I_attempts", AttribType.INTEGER),
    ANCESTRAL_ROBE_BOTTOM_I_ATTEMPTS("ancestral_bottom_I_attempts", AttribType.INTEGER),
    PRIMORDIAL_BOOTS_OR_ATTEMPTS("primordial_boots_or_attempts", AttribType.INTEGER),
    INFERNAL_CAPE_ATTEMPTS("infernal_cape_attempts", AttribType.INTEGER),
    MAGMA_BLOWPIPE_ATTEMPTS("magma_blowpipe_attempts", AttribType.INTEGER),
    HOLY_SANGUINESTI_STAFF_ATTEMPTS("holy_sanguinesti_attempts", AttribType.INTEGER),
    HOLY_SCYTHE_OF_VITUR_ATTEMPTS("holy_scythe_of_vitur_attempts", AttribType.INTEGER),
    HOLY_GHRAZI_RAPIER_ATTEMPTS("holy_ghrazi_raper_attempts", AttribType.INTEGER),
    SANGUINE_SCYTHE_OF_VITUR_ATTEMPTS("sanguine_scythe_of_vitur_attempts", AttribType.INTEGER),
    PEGASIAN_BOOTS_OR_ATTEMPTS("pegasian_boots_or_attempts", AttribType.INTEGER),
    ETERNAL_BOOTS_OR_ATTEMPTS("eternal_boots_or_attempts", AttribType.INTEGER),
    CORRUPTED_VIGGORAS_CHAINMACE_ATTEMPTS("corrupted_viggoras_chainmace_attempts", AttribType.INTEGER),
    CORRUPTED_CRAWS_BOW_ATTEMPTS("corrupted_craws_bow_attempts", AttribType.INTEGER),
    CORRUPTED_THAMMARONS_STAFF_ATTEMPTS("corrupted_thammarons_staff_attempts", AttribType.INTEGER),

    SWIFT_ABILITY("swift_ability", AttribType.BOOLEAN),
    GOLIATH_ABILITY("goliath_ability", AttribType.BOOLEAN),

    DID_YOU_KNOW("did_you_know_activated", AttribType.BOOLEAN),

    //Bounty hunter
    BOUNTY_HUNTER_TASK,
    BOUNTY_HUNTER_TASK_COMPLETION_AMOUNT("bounty_hunter_task_completion_amount", AttribType.INTEGER),
    BOUNTY_TASKS_COMPLETED("bounty_tasks_completed", AttribType.INTEGER),
    MINIMIZE("minimize", AttribType.BOOLEAN),
    HOTSPOT_TIMER("hotspot_timer", AttribType.INTEGER),
    BOUNTY_TASK_TIMER("bounty_task_timer", AttribType.INTEGER),
    BOUNTY_HUNTER_TASK_NOTIFICATIONS("bounty_task_notifications", AttribType.BOOLEAN),
    FAILED_BOUNTY_HUNTER_TASK("failed_bounty_task", AttribType.BOOLEAN),
    BOUNTY_TASKS_SKIPPED("bounty_hunter_tasks_skipped", AttribType.INTEGER),
    TARGET_IMMUNITY("target_immunity", AttribType.INTEGER),

    /**
     * Wilderness hotspot enter/exit notification
     */
    WILDERNESS_HOTSPOT_NOTIFICATION,

    PETS_SCROLL("pets_scroll", AttribType.BOOLEAN),

    MUTED("muted", AttribType.BOOLEAN),

    RAIDS_BOX_GIVES_TBOW,

    CANNOT_ATTACK_OUT_OF_DIST,
    FENRIR_SPECIAL_ACTIVE,

    // Last time we boosted our prayer
    PRAYERCAPE_BOOST,

    TURMOIL_ACTIVE,

    MAXHIT_OVERRIDE,

    EXP_LAMP_WARNING_SENT("exp_lamp_warning_sent", AttribType.BOOLEAN),

    EXP_LAMP_USED("exp_lamp_used", AttribType.BOOLEAN),

    /**
     * Personal points that you can use to purchase supplies during the raid
     */
    THEATRE_OF_BLOOD_POINTS("tob_points", AttribType.INTEGER),

    BAGGED_COAL("bagged_coal", AttribType.INTEGER),

    ACCEPT_AID("accept_aid", AttribType.BOOLEAN),

    /**
     * Determines if Tztok Jad has spawned his healers or not
     */
    JAD_SPAWNED_HEALERS,

    NEXT_ATTACK,
    NIGHTMARE_CURSE,
    NIGHTMARE_BABY_DADY,
    NIGHTMARE_SPORE,

    KYLIE_MINNOW_DIALOGUE_STARTED("kylie_minnow_dialogue_started", AttribType.BOOLEAN),

    DONATOR_MYSTERY_BOXES_OPENED("donator_mystery_boxes_opened", AttribType.INTEGER),
    MYSTERY_TICKETS_OPENED("mystery_tickets_opened", AttribType.INTEGER),
    MYSTERY_CHESTS_OPENED("mystery_chests_opened", AttribType.INTEGER),
    PETS_MYSTERY_BOXES_OPENED("pets_mystery_boxes_opened", AttribType.INTEGER),

    SARACHNIS_RANDOM_MOVE_TILE,

    EXP_LAMP_SKILL_SELECTED,

    FIGHT_CAVES_WAVE("current_fightcave_wave", AttribType.INTEGER),

    BRIMSTONE_KEYS_OPENED("brimstone_keys_opened", AttribType.INTEGER),

    COMBAT_MAXED("combat_maxed", AttribType.INTEGER),

    /**
     * Key used to store amount of compost we can collect
     */
    COMPOST_BIN("compost_bin", AttribType.INTEGER),

    /**
     * The recently listed items trading post page
     */
    TRADING_POST_RECENT_PAGE,

    /**
     * The buy items trading post page
     */
    TRADING_POST_BUY_PAGE,

    HP_EVENT_ACTIVE,
    MUTTADILE_EATING_STATE,
    MUTTADILE_HEAL_COUNT,

    HWEEN_EVENT_TOKENS_SPENT("hween_event_tokens_spent", AttribType.INTEGER),
    WINTER_EVENT_TOKENS_SPENT("winter_event_tokens_spent", AttribType.INTEGER),

    CANDIES_TRADED("candiestraded", AttribType.INTEGER),
    FINISHED_HALLOWEEN_TEACHER_DIALOGUE("finished_halloween_teacher_dialogue", AttribType.BOOLEAN),
    LAST_DAILY_RESET("lastdailyreset", AttribType.INTEGER),

    HUNLESS_PREVIOUS_STYLE,

    GAME_MODE_SELECTED,
    EXP_MODE_SELECTED,

    CURRENT_GAME_MODE_SELECTED,
    CURRENT_EXP_MODE_SELECTED,

    VIEWING_COLLECTION_LOG,

    //Collection log reward attributes
    ALCHEMICAL_HYDRA_LOG_CLAIMED,
    ANCIENT_BARRELCHEST_LOG_CLAIMED,
    ANCIENT_CHAOS_ELEMENTAL_LOG_CLAIMED,
    ANCIENT_KING_BLACK_DRAGON_LOG_CLAIMED,
    ARACHNE_LOG_CLAIMED,
    ARTIO_LOG_CLAIMED,
    ABYSSALSIRE_LOG_CLAIMED,
    BARROWS_LOG_CLAIMED,
    BARRELCHEST_LOG_CLAIMED,
    BRUTAL_LAVA_DRAGON_LOG_CLAIMED,
    CALLISTO_LOG_CLAIMED,
    BRYOPHYTA_LOG_CLAIMED,
    CERBERUS_LOG_CLAIMED,
    CHAOS_ELEMENTAL_LOG_CLAIMED,
    COMMANDER_ZILYANA_LOG_CLAIMED,
    CHAOS_FANATIC_LOG_CLAIMED,
    DAGANNOTH_KINGS_LOG_CLAIMED,
    GENERAL_GRAARDOR_LOG_CLAIMED,
    CORPOREAL_BEAST_LOG_CLAIMED,
    CORRUPTED_NECHRYARCH_LOG_CLAIMED,
    CRAZY_ARCHAEOLOGIST_LOG_CLAIMED,
    DEMONIC_GORILLA_LOG_CLAIMED,
    GIANT_MOLE_LOG_CLAIMED,
    GROTESQUE_GUARDIANS_LOG_CLAIMED,
    KRIL_TSUTSAROTH_LOG_CLAIMED,
    KALPHITE_QUEEN_LOG_CLAIMED,
    KREEARRA_LOG_CLAIMED,
    KERBEROS_LOG_CLAIMED,
    KING_BLACK_DRAGON_LOG_CLAIMED,
    KRAKEN_LOG_CLAIMED,
    LAVA_DRAGON_LOG_CLAIMED,
    LIZARDMAN_SHAMAN_LOG_CLAIMED,
    SCORPIA_LOG_CLAIMED,
    SKORPIOS_LOG_CLAIMED,
    SKOTIZO_LOG_CLAIMED,
    TEKTON_LOG_CLAIMED,
    THERMONUCLEAR_SMOKE_DEVIL_LOG_CLAIMED,
    NEX_LOG_CLAIMED,
    THE_NIGTHMARE_LOG_CLAIMED,
    SARACHNIS_LOG_CLAIMED,
    CORRUPTED_HUNLEFF_LOG_CLAIMED,
    MEN_IN_BLACK_LOG_CLAIMED,
    TZTOK_JAD_LOG_CLAIMED,
    VENENATIS_LOG_CLAIMED,
    VETION_LOG_CLAIMED,
    VORKATH_LOG_CLAIMED,
    ZOMBIES_CHAMPION_LOG_CLAIMED,
    ZULRAH_LOG_CLAIMED,
    ANCIENT_REVENANTS_LOG_CLAIMED,
    CHAMBER_OF_SECRETS_LOG_CLAIMED,
    CHAMBER_OF_XERIC_LOG_CLAIMED,
    THEATRE_OF_BLOOD_LOG_CLAIMED,
    CRYSTALLINE_HUNLLEF_LOG_CLAIMED,
    FRAGMENT_OF_SEREN_LOG_CLAIMED,
    BLOOD_FURY_HESPORI_LOG_CLAIMED,
    TINFERNAL_SPIDER_LOG_CLAIMED,
    DONATOR_MYSTERY_BOX_LOG_CLAIMED,
    MYSTERY_TICKET_LOG_CLAIMED,
    MYSTERY_CHEST_LOG_CLAIMED,
    PETS_MYSTERY_BOX_LOG_CLAIMED,
    REVENANTS_LOG_CLAIMED,
    SLAYER_LOG_CLAIMED,
    LARRANS_KEY_LOG_CLAIMED,
    CRYSTAL_KEY_LOG_CLAIMED,
    SLAYER_KEY_LOG_CLAIMED,
    BRIMSTONE_KEY_LOG_CLAIMED,
    COLLECTION_KEY_LOG_CLAIMED,
    WILDERNESS_KEY_LOG_CLAIMED,

    VETERAN("veteran", AttribType.BOOLEAN),

    IS_BETA_TESTER("is_beta_tester", AttribType.BOOLEAN),

    HERB_BOX_CHARGES("herb_box_charges", AttribType.INTEGER),

    RECEIVED_MONTHLY_SPONSOR_REWARDS("received_monthly_sponsor_rewards", AttribType.BOOLEAN),

    GAMBLER("gambler", AttribType.BOOLEAN),

    CUSTOM_DUEL_RULE,

    SEND_DUEL_REQUEST,

    WHIP_ONLY,

    WHIP_AND_DDS,

    STARTER_BOX_CLAIMED("starter_box_claimed", AttribType.BOOLEAN),

    PROMO_CODE_CLAIMED("promo_code_claimed", AttribType.BOOLEAN),

    THE_NIGHTMARE_KC("the_nightmare_kc", AttribType.INTEGER),

    NEX_KC("nex_kc", AttribType.INTEGER),

    CRYSTALLINE_HUNLEFF_KC("crystalline_hunleff_kc", AttribType.INTEGER),
    FRAGMENT_OF_SEREN_KC("fragment_of_seren_kc", AttribType.INTEGER),
    BLOOD_FURY_HESPORI_KC("blood_fury_hespori_kc", AttribType.INTEGER),
    INFERNAL_SPIDER_KC("infernal_spider_kc", AttribType.INTEGER),

    USING_TRADING_POST("using_trading_post", AttribType.BOOLEAN),

    /**
     * special attacks queued. 1:1 with how many times you click the specbar when wearing gmaul.
     */
    GRANITE_MAUL_SPECIALS,

    /**
     * set to 5 ticks when spec is activated.
     */
    GRANITE_MAUL_TIMEOUT_TICKS,

    DEFENSIVE_AUTOCAST("defensive_autocast", AttribType.BOOLEAN),

    AUTOCAST_SELECTED("autocast_selected", AttribType.BOOLEAN),

    // How much BM we last died with. Used to combat raggers/no risk suicide accounts.
    LASTDEATH_VALUE,

    PET_SHOUT_ABILITY("pet_ability_shout", AttribType.BOOLEAN),

    /*
     * Flag if a double death occured during a stake.
     */
    STAKING_DOUBLE_DEATH,

    /*
     * Flag if double death has been checked be the first person to die (down to PID)
     */
    ARENA_DEATH_TICK,

    DAILY_TASK_CATEGORY,

    DAILY_TASK_SELECTED,

    VIEWING_RUNE_POUCH_I,

    ROCK_CRABS_DAILY_TASK_COMPLETION_AMOUNT("daily_rock_crabs_task_completion_amount", AttribType.INTEGER),
    EXPERIMENTS_DAILY_TASK_COMPLETION_AMOUNT("daily_experiments_task_completion_amount", AttribType.INTEGER),
    THIEVING_DAILY_TASK_COMPLETION_AMOUNT("daily_thieving_task_completion_amount", AttribType.INTEGER),
    VOTING_DAILY_TASK_COMPLETION_AMOUNT("daily_voting_task_completion_amount", AttribType.INTEGER),
    SKILLING_DAILY_TASK_COMPLETION_AMOUNT("daily_skilling_task_completion_amount", AttribType.INTEGER),
    PVMING_DAILY_TASK_COMPLETION_AMOUNT("daily_pvming_task_completion_amount", AttribType.INTEGER),
    BARROWS_TASK_COMPLETION_AMOUNT("daily_barrows_task_completion_amount", AttribType.INTEGER),
    SLAYER_DAILY_TASK_COMPLETION_AMOUNT("daily_slayer_task_completion_amount", AttribType.INTEGER),
    GREEN_DRAGONS_DAILY_TASK_COMPLETION_AMOUNT("daily_green_dragons_task_completion_amount", AttribType.INTEGER),
    IMPLING_DAILY_TASK_COMPLETION_AMOUNT("daily_impling_task_completion_amount", AttribType.INTEGER),
    RAIDS_DAILY_TASK_COMPLETION_AMOUNT("daily_raids_task_completion_amount", AttribType.INTEGER),
    ZULRAH_DAILY_TASK_COMPLETION_AMOUNT("daily_zulrah_task_completion_amount", AttribType.INTEGER),
    CORRUPTED_NECHRYARCH_DAILY_TASK_COMPLETION_AMOUNT("daily_corrupted_nechryarch_task_completion_amount", AttribType.INTEGER),
    VALINOR_COINS_DAILY_TASK_COMPLETION_AMOUNT("daily_valinor_coins_task_completion_amount", AttribType.INTEGER),
    TOURNY_DAILY_TASK_COMPLETION_AMOUNT("daily_tourny_task_completion_amount", AttribType.INTEGER),
    STARDUST_DAILY_TASK_COMPLETION_AMOUNT("daily_stardust_task_completion_amount", AttribType.INTEGER),
    WORLD_BOSS_DAILY_TASK_COMPLETION_AMOUNT("daily_world_boss_task_completion_amount", AttribType.INTEGER),
    REVENANTS_DAILY_TASK_COMPLETION_AMOUNT("daily_revenants_task_completion_amount", AttribType.INTEGER),
    KRAKEN_DAILY_TASK_COMPLETION_AMOUNT("daily_kraken_task_completion_amount", AttribType.INTEGER),
    BATTLE_MAGE_DAILY_TASK_COMPLETION_AMOUNT("daily_battle_mages_task_completion_amount", AttribType.INTEGER),
    NEX_DAILY_TASK_COMPLETION_AMOUNT("daily_nex_task_completion_amount", AttribType.INTEGER),
    DEMONIC_GORILLA_DAILY_TASK_COMPLETION_AMOUNT("daily_demonic_gorilla_task_completion_amount", AttribType.INTEGER),
    WIN_PVP_TOURNAMENT_DAILY_TASK_COMPLETION_AMOUNT("daily_win_pvp_tournament_task_completion_amount", AttribType.INTEGER),
    WILDY_KEY_ESCAPE_DAILY_TASK_COMPLETION_AMOUNT("daily_wildy_key_escape_task_completion_amount", AttribType.INTEGER),
    KILL_PLAYERS_REV_CAVE_DAILY_TASK_COMPLETION_AMOUNT("daily_kill_players_task_completion_amount", AttribType.INTEGER),
    DEEP_WILD_DAILY_TASK_COMPLETION_AMOUNT("daily_deep_wild_task_completion_amount", AttribType.INTEGER),
    KILLSTREAK_DAILY_TASK_COMPLETION_AMOUNT("daily_killstreak_task_completion_amount", AttribType.INTEGER),
    EDGEVILE_KILLS_DAILY_TASK_COMPLETION_AMOUNT("daily_edgevile_kills_task_completion_amount", AttribType.INTEGER),
    MAGE_BANK_KILLS_DAILY_TASK_COMPLETION_AMOUNT("daily_mage_bank_kills_task_completion_amount", AttribType.INTEGER),
    MEMBER_CAVE_KILLS_DAILY_TASK_COMPLETION_AMOUNT("daily_member_cave_kills_task_completion_amount", AttribType.INTEGER),

    ROCK_CRABS_DAILY_TASK_COMPLETED("daily_rock_crabs_task_completed", AttribType.BOOLEAN),
    EXPERIMENTS_DAILY_TASK_COMPLETED("daily_experiments_task_completed", AttribType.BOOLEAN),
    THIEVING_DAILY_TASK_COMPLETED("daily_thieving_task_completed", AttribType.BOOLEAN),
    VOTING_DAILY_TASK_COMPLETED("daily_voting_task_completed", AttribType.BOOLEAN),
    SKILLING_DAILY_TASK_COMPLETED("daily_skilling_task_completed", AttribType.BOOLEAN),
    PVMING_DAILY_TASK_COMPLETED("daily_pvming_task_completed", AttribType.BOOLEAN),
    BARROWS_DAILY_TASK_COMPLETED("daily_barrows_task_completed", AttribType.BOOLEAN),
    SLAYER_DAILY_TASK_COMPLETED("daily_slayer_task_completed", AttribType.BOOLEAN),
    GREEN_DRAGONS_DAILY_TASK_COMPLETED("daily_green_dragons_task_completed", AttribType.BOOLEAN),
    IMPLING_DAILY_TASK_COMPLETED("daily_impling_task_completed", AttribType.BOOLEAN),
    RAIDS_DAILY_TASK_COMPLETED("daily_raids_task_completed", AttribType.BOOLEAN),
    ZULRAH_DAILY_TASK_COMPLETED("daily_zulrah_task_completed", AttribType.BOOLEAN),
    CORRUPTED_NECHRYARCH_DAILY_TASK_COMPLETED("daily_corrupted_nechryarch_task_completed", AttribType.BOOLEAN),
    VALINOR_COINS_DAILY_TASK_COMPLETED("daily_valinor_coins_task_completed", AttribType.BOOLEAN),
    TOURNY_DAILY_TASK_COMPLETED("daily_tourny_task_completed", AttribType.BOOLEAN),
    STARDUST_DAILY_TASK_COMPLETED("daily_stardust_task_completed", AttribType.BOOLEAN),
    WORLD_BOSS_DAILY_TASK_COMPLETED("daily_world_boss_task_completed", AttribType.BOOLEAN),
    REVENANTS_DAILY_TASK_COMPLETED("daily_revenants_task_completed", AttribType.BOOLEAN),
    KRAKEN_DAILY_TASK_COMPLETED("daily_kraken_task_completed", AttribType.BOOLEAN),
    BATTLE_MAGE_DAILY_TASK_COMPLETED("daily_battle_mages_task_completed", AttribType.BOOLEAN),
    NEX_DAILY_TASK_COMPLETED("daily_nex_task_completed", AttribType.BOOLEAN),
    DEMONIC_GORILLA_DAILY_TASK_COMPLETED("daily_demonic_gorilla_task_completed", AttribType.BOOLEAN),
    WIN_PVP_TOURNAMENT_DAILY_TASK_COMPLETED("daily_win_pvp_tournament_task_completed", AttribType.BOOLEAN),
    WILDY_KEY_ESCAPE_DAILY_TASK_COMPLETED("daily_wildy_key_escape_task_completed", AttribType.BOOLEAN),
    KILL_PLAYERS_REV_CAVE_DAILY_TASK_COMPLETED("daily_kill_players_task_completed", AttribType.BOOLEAN),
    DEEP_WILD_DAILY_TASK_COMPLETED("daily_deep_wild_task_completed", AttribType.BOOLEAN),
    KILLSTREAK_DAILY_TASK_COMPLETED("daily_killstreak_task_completed", AttribType.BOOLEAN),
    EDGEVILE_KILLS_DAILY_TASK_COMPLETED("daily_edgevile_kills_task_completed", AttribType.BOOLEAN),
    MAGE_BANK_KILLS_DAILY_TASK_COMPLETED("daily_mage_bank_kills_task_completed", AttribType.BOOLEAN),
    MEMBER_CAVE_KILLS_DAILY_TASK_COMPLETED("daily_member_cave_kills_task_completed", AttribType.BOOLEAN),

    ROCK_CRABS_DAILY_TASK_REWARD_CLAIMED("daily_rock_crabs_task_reward_claimed", AttribType.BOOLEAN),
    EXPERIMENTS_DAILY_TASK_REWARD_CLAIMED("daily_experiments_task_reward_claimed", AttribType.BOOLEAN),
    THIEVING_DAILY_TASK_REWARD_CLAIMED("daily_thieving_task_reward_claimed", AttribType.BOOLEAN),
    VOTING_DAILY_TASK_REWARD_CLAIMED("daily_voting_task_reward_claimed", AttribType.BOOLEAN),
    SKILLING_DAILY_TASK_REWARD_CLAIMED("daily_skilling_task_reward_claimed", AttribType.BOOLEAN),
    PVMING_DAILY_TASK_REWARD_CLAIMED("daily_pvming_task_reward_claimed", AttribType.BOOLEAN),
    BARROWS_DAILY_TASK_REWARD_CLAIMED("daily_barrows_task_reward_claimed", AttribType.BOOLEAN),
    SLAYER_DAILY_TASK_REWARD_CLAIMED("daily_slayer_task_reward_claimed", AttribType.BOOLEAN),
    GREEN_DRAGONS_DAILY_TASK_REWARD_CLAIMED("daily_green_dragons_task_reward_claimed", AttribType.BOOLEAN),
    IMPLING_DAILY_TASK_REWARD_CLAIMED("daily_impling_task_reward_claimed", AttribType.BOOLEAN),
    RAIDS_DAILY_TASK_REWARD_CLAIMED("daily_raids_task_reward_claimed", AttribType.BOOLEAN),
    ZULRAH_DAILY_TASK_REWARD_CLAIMED("daily_zulrah_task_reward_claimed", AttribType.BOOLEAN),
    CORRUPTED_NECHRYARCH_DAILY_TASK_REWARD_CLAIMED("daily_corrupted_nechryarch_task_reward_claimed", AttribType.BOOLEAN),
    VALINOR_COINS_DAILY_TASK_REWARD_CLAIMED("daily_valinor_coins_task_reward_claimed", AttribType.BOOLEAN),
    TOURNY_DAILY_TASK_REWARD_CLAIMED("daily_tourny_task_reward_claimed", AttribType.BOOLEAN),
    STARDUST_DAILY_TASK_REWARD_CLAIMED("daily_stardust_task_reward_claimed", AttribType.BOOLEAN),
    WORLD_BOSS_DAILY_TASK_REWARD_CLAIMED("daily_world_boss_task_reward_claimed", AttribType.BOOLEAN),
    REVENANTS_DAILY_TASK_REWARD_CLAIMED("daily_revenants_task_reward_claimed", AttribType.BOOLEAN),
    KRAKEN_DAILY_TASK_REWARD_CLAIMED("daily_kraken_task_reward_claimed", AttribType.BOOLEAN),
    BATTLE_MAGE_DAILY_TASK_REWARD_CLAIMED("daily_battle_mages_task_reward_claimed", AttribType.BOOLEAN),
    NEX_DAILY_TASK_REWARD_CLAIMED("daily_nex_task_reward_claimed", AttribType.BOOLEAN),
    DEMONIC_GORILLA_DAILY_TASK_REWARD_CLAIMED("daily_demonic_gorilla_task_reward_claimed", AttribType.BOOLEAN),
    WIN_PVP_TOURNAMENT_DAILY_TASK_REWARD_CLAIMED("daily_win_pvp_tournament_task_reward_claimed", AttribType.BOOLEAN),
    WILDY_KEY_ESCAPE_DAILY_TASK_REWARD_CLAIMED("daily_wildy_key_escape_task_reward_claimed", AttribType.BOOLEAN),
    KILL_PLAYERS_REV_CAVE_DAILY_TASK_REWARD_CLAIMED("daily_kill_players_task_reward_claimed", AttribType.BOOLEAN),
    DEEP_WILD_DAILY_TASK_REWARD_CLAIMED("daily_deep_wild_task_reward_claimed", AttribType.BOOLEAN),
    KILLSTREAK_DAILY_TASK_REWARD_CLAIMED("daily_killstreak_task_reward_claimed", AttribType.BOOLEAN),
    EDGEVILE_KILLS_DAILY_TASK_REWARD_CLAIMED("daily_edgevile_kills_task_reward_claimed", AttribType.BOOLEAN),
    MAGE_BANK_KILLS_DAILY_TASK_REWARD_CLAIMED("daily_mage_bank_kills_task_reward_claimed", AttribType.BOOLEAN),
    MEMBER_CAVE_KILLS_DAILY_TASK_REWARD_CLAIMED("daily_member_cave_kills_task_reward_claimed", AttribType.BOOLEAN),

    RESET_LAST_VOTE_TIMESTAMP("reset_last_vote_timestamp", AttribType.BOOLEAN),

    BOSS_POINTS("boss_points", AttribType.INTEGER),

    PK_POINTS("pk_points", AttribType.INTEGER),

    // The original amount being listed for this specific listing. (Before any edits were done)
    TRADING_POST_ORIGINAL_AMOUNT,

    // The original price being listed for this specific listing. (Before any edits were done)
    TRADING_POST_ORIGINAL_PRICE,

    RAIDS_NO_POINTS("raids_no_points", AttribType.BOOLEAN),

    PERSONAL_POINTS("personal_points", AttribType.INTEGER),

    DRAGON_DEFENDER_DROPS("dragon_defender_drops", AttribType.INTEGER),

    ACCOUNT_PIN_ATTEMPTS_LEFT("account_pin_attempts_left", AttribType.INTEGER),

    ACCOUNT_PIN_FREEZE_TICKS("account_pin_freeze_ticks", AttribType.INTEGER),

    ASK_FOR_ACCOUNT_PIN("ask_for_account_pin", AttribType.BOOLEAN),

    ACCOUNT_PIN("account_pin", AttribType.INTEGER),

    TEMP_ACCOUNT_PIN("temp_account_pin", AttribType.INTEGER),

    GROUP_NAME_SET("group_name_set", AttribType.BOOLEAN),

    IRONMAN_INTERFACE("ironman_interface", AttribType.BOOLEAN),

    CHOSEN_PK_MODE("chosen_pk_mode", AttribType.BOOLEAN),

    HARD_EXP_MODE("hard_exp_mode", AttribType.BOOLEAN),

    LOST_CANNON("lost_cannon", AttribType.BOOLEAN),

    CLEARED_GRANITE_MAULS_AGAIN("cleared_gmauls", AttribType.BOOLEAN),
    CLEARED_SKILLING_ITEMS("cleared_skilling_items", AttribType.BOOLEAN),

    EMBLEM_WEALTH("emblem_wealth", AttribType.STRING),

    TOURNAMENT_WINS("tournament_wins", AttribType.INTEGER),

    TOURNAMENT_POINTS("tournament_points", AttribType.INTEGER),

    MULTIWAY_AREA,

    // ID of the last region we were in
    LAST_REGION,

    FIRST_KILL_OF_THE_DAY("first_kill_otd", AttribType.LONG),

    ELO_RATING("elo_rating", AttribType.INTEGER),

    /**
     * Number of players killed in the wilderness without leaving.
     */
    WILDERNESS_KILLSTREAK("wilderness_killstreak", AttribType.INTEGER),

    /**
     * Number of players killed in the process of murdering.
     */
    KILLSTREAK("killstreak", AttribType.INTEGER),

    /**
     * Highest shutdown player ever achieved. Doesn't get replaced unless it's superceded.
     */
    KILLSTREAK_RECORD("ksrec", AttribType.INTEGER),

    /**
     * The highest shutdown this player has ever achieved. Does not get cleared upon death and is permanent until it's superceded.
     */
    SHUTDOWN_RECORD("sdrec", AttribType.INTEGER),

    /**
     * 8x8 areas
     */
    LAST_CHUNK,

    IMPLINGS_CAUGHT("implings_caught", AttribType.INTEGER),

    IRONMAN_BUTTON_SELECTED,

    /**
     * Amount of warnings the server gave you before you get auto muted.
     */
    MUTE_WARNINGS("mute_warnings", AttribType.INTEGER),

    /**
     * Just to inform users.
     */
    IN_ANTIRAG_ZONE,

    /**
     * The items stored in the cart from the item dispenser.
     */
    CART_ITEMS,

    CART_ITEMS_TOTAL_VALUE("cart_items_exchange_value", AttribType.INTEGER),

    /**
     * Auto repairs broken items on death.
     */
    REPAIR_BROKEN_ITEMS_ON_DEATH("repair_on_death", AttribType.BOOLEAN),

    /**
     * Key used in poisoning.
     */
    POISON_TICKS,

    /**
     * The computer's mac address.
     */
    MAC_ADDRESS("mac_address", AttribType.STRING),

    /**
     * The entity which last attacked us. Set at time of attack. Not delayed/set when a hit finally appears
     */
    LAST_DAMAGER,

    /**
     * The last person we attacked. Required for the PvP world PJ mechanic.
     * - NOTE: this different from the TARGET attribute, as TARGET is cancelled/removed in various situations or when you change targets.
     */
    LAST_TARGET,

    /**
     * Time (MS) when a player was last attacked. This is when an attack is initated (animation) NOT when damage appears.
     * Used in checking for in/out of combat.
     */
    LAST_WAS_ATTACKED_TIME,

    /**
     * The last time we attacked some other entity
     */
    LAST_ATTACK_TIME,

    /**
     * When we last used a quick setup. Anti-rag feature
     */
    DEATH_TELEPORT_TIMER("lastSpawnSetupTime", AttribType.STRING),

    /**
     * Save's the NPC's spawned in the for the Abyssal Sire
     */
    ABYSSAL_SIRE_TENTACLES,
    ABYSSAL_SIRE_RESPIRATORY_SYSTEMS,

    /**
     * Handles Abyssal Sire's combat state
     */
    ABYSSAL_SIRE_STATE,

    /**
     * Handles Abyssal Sire's combat phase
     */
    ABYSSAL_SIRE_PHASE,

    /**
     * Handles if Abyssal Sire is currently spawning a minion
     */
    ABYSSAL_SIRE_SPAWNING_MINION,

    /**
     * Handles if Abyssal Sire is currently spawning poisonous fumes
     */
    ABYSSAL_SIRE_SPAWNING_FUMES,

    /**
     * Key used to store the Abyssal Sire owning the NPC
     */
    OWNING_ABYSSAL_SIRE,

    /**
     * Key used to store Abyssal Sire's challenging player
     */
    ABYSSAL_SIRE_CHALLENGING_PLAYER,

    /**
     * Key used to store Abyssal Sire's spawns NPCs
     */
    ABYSSAL_SIRE_SPAWNS,

    /**
     * Key used to store which Abyssal Sire the spawn belongs too
     */
    ABYSSAL_SIRE_SPAWN_OWNER,

    DIALOGUE_PHASE("dialogue_phase", AttribType.INTEGER),

    DOUBLE_DROP_LAMP_TICKS("double_drops_lamp_ticks", AttribType.INTEGER),

    DOUBLE_EXP_TICKS("double_exp_ticks", AttribType.INTEGER),

    CRYSTAL_KEYS_OPENED("crystal_keys_opened", AttribType.INTEGER),
    WILDY_KEYS_OPENED("wildy_keys_opened", AttribType.INTEGER),

    LAST_RECOVER_SPECIAL_POTION("Recover_Special_Pot", AttribType.LONG),
    STAKES_WON("stakes_won", AttribType.INTEGER),
    STAKES_LOST("stakes_lost", AttribType.INTEGER),

    TITLE("title", AttribType.STRING),
    TITLE_COLOR("title_colour", AttribType.STRING),

    //Divine bastion potion attributes
    DIVINE_BASTION_POTION_TICKS("divine_bastion_potion_time_elapsed", AttribType.INTEGER),
    DIVINE_BASTION_POTION_EFFECT_ACTIVE("divine_bastion_potion_effect_active", AttribType.BOOLEAN),

    //Divine battlemage potion attributes
    DIVINE_BATTLEMAGE_POTION_TICKS,
    DIVINE_BATTLEMAGE_POTION_EFFECT_ACTIVE,

    //Divine magic potion attributes
    DIVINE_MAGIC_POTION_TICKS,
    DIVINE_MAGIC_POTION_EFFECT_ACTIVE,

    //Divine ranging potion attributes
    DIVINE_RANGING_POTION_TICKS,
    DIVINE_RANGING_POTION_EFFECT_ACTIVE,

    //Divine super attack potion attributes
    DIVINE_SUPER_ATTACK_POTION_TICKS,
    DIVINE_SUPER_ATTACK_POTION_EFFECT_ACTIVE,

    //Divine super combat potion attributes
    DIVINE_SUPER_COMBAT_POTION_TICKS,
    DIVINE_SUPER_COMBAT_POTION_EFFECT_ACTIVE,

    //Divine super defence potion attributes
    DIVINE_SUPER_DEFENCE_POTION_TICKS,
    DIVINE_SUPER_DEFENCE_POTION_EFFECT_ACTIVE,

    //Divine super strength potion attributes
    DIVINE_SUPER_STRENGTH_POTION_TICKS,
    DIVINE_SUPER_STRENGTH_POTION_EFFECT_ACTIVE,

    BARROWS_MONSTER_KC("barrows_monster_kc", AttribType.INTEGER),
    AHRIM("ahrim_killed", AttribType.INTEGER),
    DHAROK("dharok_killed", AttribType.INTEGER),
    GUTHAN("guthan_killed", AttribType.INTEGER),
    KARIL("karil_killed", AttribType.INTEGER),
    TORAG("torag_killed", AttribType.INTEGER),
    VERAC("verac_killed", AttribType.INTEGER),

    // Which chest leads to the tombs
    FINAL_BARROWS_BRO_COFFINID("barrowsBroCoffinToChest", AttribType.INTEGER),

    // Current brother spawned
    barrowsBroSpawned,

    NIFFLER_ITEMS_STORED,

    /**
     * Is Barrelchest in his jump state?
     */
    BARRELCHEST_JUMP_STATE,

    STORE_X("store_x", AttribType.INTEGER),

    /**
     * 0 = bosses
     * 1 = pvm and training
     * 2 = pking
     */
    CATEGORY_OPEN("open_page", AttribType.INTEGER),

    CURRENT_SELECTED_TELEPORT("current_selected_teleport", AttribType.INTEGER),

    PROMO_PAYMENT_AMOUNT("promo_payment_amount", AttribType.DOUBLE),

    TOTAL_PAYMENT_AMOUNT("total_payment_amount", AttribType.DOUBLE),

    /**
     * Boolean key indicating if the account was freshly made (used for introductory dialogue).
     */
    NEW_ACCOUNT,

    CONTINUE_STARTER_TUTORIAL,

    /**
     * This attribute stores the latest damage being dealt to a player
     */
    LATEST_DAMAGE("latest_damage", AttribType.INTEGER),

    SLAYER_KEYS_RECEIVED("slayer_keys_received", AttribType.INTEGER),
    SLAYER_KEYS_OPENED("slayer_keys_opened", AttribType.INTEGER),

    COLLECTION_LOG_KEYS_OPENED("collection_log_keys_opened", AttribType.INTEGER),

    BOTTLE_TASK,
    BOTTLE_TASK_AMOUNT("bottle_task_amount", AttribType.INTEGER),
    TASK_COMPLETE_AMOUNT("bottle_task_complete_amount", AttribType.INTEGER),
    TASKS_COMPLETED("bottle_tasks_completed", AttribType.INTEGER),
    CAN_CLAIM_TASK_REWARD("can_claim_bottle_scroll_reward", AttribType.BOOLEAN),

    ALWAYS_HIT,

    ONE_HIT_MOB("one_hit_mobs", AttribType.BOOLEAN),

    /*
     * Used for hunter. The exact instances of MapObjs change, however the attributes
     * are retained (copied from source to destination) for new mapobj instances, so we'll
     * use this an an identifier.
     */
    MAPOBJ_UUID,

    /**
     * Ores given to the nigga to escape jail
     */
    JAIL_ORES_MINED("jailOresMined", AttribType.INTEGER),

    // It can take from time to run somewhere on the eco server, so let's put them back where they were before getting jailed :)
    LOC_BEFORE_JAIL,

    // If we are jailed.
    JAILED("jailed", AttribType.INTEGER),

    // Required total of ores to mine before you can escape jail. So you can specify per offense.
    JAIL_ORES_TO_ESCAPE("jail_ores_to_escape", AttribType.INTEGER),

    // How many cycles remain for this venom task. NOT game cycle. One venom cycle = 20 game ticks
    VENOM_TICKS("venom_ticks", AttribType.INTEGER),

    //The mob that venomed us
    VENOMED_BY,

    /**
     * Key used in fishing to indicate which tiles an npc may use. Type: Array<Tile>.
     */
    POSSIBLE_FISH_TILES,

    XP_LOCKED("lock_exp", AttribType.BOOLEAN),

    // The value of the face-entity mask.
    LAST_FACE_ENTITY_IDX,

    // Value of the last face tile mask
    LAST_FACE_TILE,

    DROP_ITEM_WARNING("drop_item_warning", AttribType.BOOLEAN),

    DROP_ITEMS_WARNING_VALUE("drop_items_warning_value", AttribType.INTEGER),

    /**
     * Each amulet begins with five charges. While the amulet is equipped, there is a 5% chance that you will
     * create a 4-dose potion rather than a 3-dose potion when brewing potions (but with no extra experience),
     * which consumes one charge. Once all of the amulet's charges are used up, the amulet crumbles to dust.
     */
    AMULET_OF_CHEMISTRY_CHARGES,

    /**
     * Current Pet enum type associated with this spawned pet.
     */
    PET_TYPE,

    /**
     * Player key used to link a pet to a player. If set, it will be an Npc that follows this player.
     */
    ACTIVE_PET,

    /**
     * The Item ID of our active pet, saved over logout, so we can respawn it on login.
     */
    ACTIVE_PET_ITEM_ID("active_pet_item_id", AttribType.INTEGER),

    //Determines if this is a reanimated NPC spawned through the Arceuus spellbook
    IS_REANIMATED_MONSTER,

    //Determines if our player has already spawned a reanimated NPC
    HAS_REANIMATED_MONSTER,

    /**
     * The direction (can be diagonal) which we are facing after our next move this game cycle has been established and set in Player Updating.
     * This direction is then sent when a new entity is added to our local list :)
     */
    FACING_DIRECTION,

    /**
     * Key to handle overload potions.
     */
    OVERLOAD_POTION("overload_potion", AttribType.INTEGER),

    /**
     * Key to handle antifire potions.
     */
    ANTIFIRE_POTION("antifire_potion", AttribType.INTEGER),

    /**
     * Key to handle super antifire potions.
     */
    SUPER_ANTIFIRE_POTION("super_antifire_potion", AttribType.BOOLEAN),

    SLAYER_PARTNER("slayerpartner:name", AttribType.STRING),

    /**
     * Amount of npcs left to kill on this task
     */
    SLAYER_TASK_AMT("slayertask:amount", AttribType.INTEGER),

    /**
     * The creature UID of this slayer task {@code SlayerTaskDef}
     */
    SLAYER_TASK_ID("slayertask:id", AttribType.INTEGER),

    SLAYER_MASTER("slayermaster:id", AttribType.INTEGER),

    SLAYER_REWARD_POINTS("slayer_points", AttribType.INTEGER),

    SLAYER_TASK_SPREE("slayerspree", AttribType.INTEGER),

    SLAYER_TASK_SPREE_RECORD("slayer_task_spree_record", AttribType.INTEGER),

    WILDERNESS_SLAYER_DESCRIBED("wilderness_task_described", AttribType.BOOLEAN),

    COMPLETED_SLAYER_TASKS("completed_slayer_tasks", AttribType.INTEGER),

    SLAYER_UI_ACTION("slayer_ui_action", AttribType.INTEGER),

    SLAYER_WIDGET_BUTTON_ID("slayer_selection", AttribType.INTEGER),

    SLAYER_WIDGET_NAME("slayer_selected_string", AttribType.STRING),

    SLAYER_WIDGET_CONFIG("slayer_widget_config", AttribType.INTEGER),

    SLAYER_WIDGET_TYPE("slayer_widget_type", AttribType.INTEGER),

    /**
     * Key used to store if we've talked to Brian O' Richard or not
     */
    BRIAN_O_RICHARD_DIALOGUE("brian_o_richard_dialogue", AttribType.INTEGER),

    COLLECTION_LOG_OPEN,

    /**
     * The maximum distance that an entity can travel before they travel
     * back to their spawn location.
     */
    MAX_DISTANCE_FROM_SPAWN,

    // the key representing pest control points for the pest control minigame
    PEST_CONTROL_POINTS("pest_control_points", AttribType.INTEGER),

    // represents the points obtained whilst within the pest control minigame.
    TEMPORARY_PEST_CONTROL_POINTS,

    // when a player is not in the group shipped off they are given a priority
    // of 1 where as the default is 0.
    PEST_CONTROL_LANDER_PRIORITY,

    /**
     * Key to handle which defenders are currently being dropped.
     */
    WARRIORS_GUILD_CYCLOPS_ROOM_DEFENDER("wguild_defender", AttribType.INTEGER),
    FARMSTATE_CAMELOT("farm_cam", AttribType.INTEGER),
    FARMSTATE_ARDOUGNE("farm_ardy", AttribType.INTEGER),
    FARMSTATE_FALADOR("farm_fally", AttribType.INTEGER),
    FARMSTATE_CANIFIS("farm_canifis", AttribType.INTEGER),
    FARMSTATE_PRIF("farm_prif", AttribType.INTEGER),
    FARMSTATE_ZEAH("farm_zeah", AttribType.INTEGER),

    UNTRADABLE_LOOT_NOTIFICATIONS("untradeable_notifications", AttribType.BOOLEAN),

    ENABLE_LOOT_NOTIFICATIONS_BUTTONS("loot_notifications_button", AttribType.BOOLEAN),

    LOOT_DROP_THRESHOLD_VALUE("loot_drop_threshold_value", AttribType.INTEGER),

    ACHIEVEMENTS_COMPLETED("achievements_completed", AttribType.INTEGER),

    ACHIEVEMENT_POINTS("achievements_points", AttribType.INTEGER),

    ACHIEVEMENT_DIFFICULTY,

    VIEWING_FORGING_CATEGORY,

    /**
     * If, when we are being force-moved, we ignore being frozen
     */
    IGNORE_FREEZE_MOVE,

    /**
     * Key used to store how many iron ores before our ring melts.
     */
    RING_OF_FORGING_CHARGES("ring_of_forging_charges", AttribType.INTEGER),

    CRAFTABLE_KEY,
    FLETCHABLE_KEY,

    //Perk attributes
    PERK_STORE_BUTTON("perk_store_button", AttribType.INTEGER),
    PRESERVE("preserve", AttribType.BOOLEAN),
    RIGOUR("rigour", AttribType.BOOLEAN),
    AUGURY("augury", AttribType.BOOLEAN),

    AMOUNT_IN_MONEY_POUCH("money_pouch", AttribType.LONG),

    DROP_DISPLAY_KEY,

    /**
     * Key used to have a 0..100 value which holds the current prayer incremental status to have sub-tick increments.
     */
    PRAYERINCREMENT,

    /**
     * Key used to have a 0..100 value which holds the current prayer incremental status to have sub-tick increments.
     */
    PRAYERINCREMENT_CURSES,

    /**
     * Tick/cycle number - prayer doesn't drain the 1st tick of being active. Allows flicking
     */
    PRAYER_ON_TICK,

    /**
     * The flag that checks wether we have limited our potential BH targs or not.
     */
    LIMIT_POTENTIAL_BH_TARGETS("limit_potential_bh_targets", AttribType.BOOLEAN),

    /**
     * Key used to determine if we lose prayer points or not.
     */
    INF_PRAY("inf_pray", AttribType.BOOLEAN),

    /**
     * Attribute key used to determine if our player wants to keep vials or not
     */
    GIVE_EMPTY_POTION_VIALS("remove_potions", AttribType.BOOLEAN),

    VOTE_POINTS("vote_points", AttribType.INTEGER),

    /**
     * Key used in callisto combat which keeps track of when to use the healing when he gets hit.
     */
    CALLISTO_DMG_HEAL,

    /**
     * Key used in the callisto combat script
     */
    CALLISTO_ROAR,

    REFRESH_SLAYER_UNLOCKS("refresh_slayer_unlocks", AttribType.BOOLEAN),

    CURRENCY_COLLECTION("currency_collection", AttribType.BOOLEAN),

    SKULL_ENTRIES_TRACKER,

    /**
     * A save key to track our skull cycles remaining.
     */
    SKULL_CYCLES("skull_cycles", AttribType.INTEGER),

    /**
     * A map containing player.id : game cycle # when you began attacking someone. Used to stop rushing, such as using special 16 ticks after initiating combat.
     */
    PVP_WILDY_AGGRESSION_TRACKER,

    /**
     * Used for sending the wild level info at correct coordinate
     */
    INWILD,
    LAST_WILD_LVL,

    INFERNAL_PICKAXE_CHARGES("infernal_pickaxe_charges", AttribType.INTEGER),

    RING_OF_SUFFERING_RECOIL_DISABLED("ring_of_suffering_disabled", AttribType.BOOLEAN),

    // last engine cycle we changed agro targets in gwd.
    LAST_AGRO_SWITCH,

    /**
     * Key used to store Demonic Gorilla hits
     */
    DEMONIC_GORILLA_MELEE,
    DEMONIC_GORILLA_RANGE,
    DEMONIC_GORILLA_MAGE,

    /**
     * Key used to store Demonic Gorilla protection boolean
     */
    DEMONIC_GORILLA_PRAY_MELEE,
    DEMONIC_GORILLA_PRAY_RANGE,
    DEMONIC_GORILLA_PRAY_MAGE,

    /**
     * Key used to store how many times the Demonic Gorilla has attacked
     */
    DEMONIC_GORILLA_TIMES_HIT,

    MAGEBANK_MAGIC_ONLY("mage_bank_magic_only", AttribType.BOOLEAN),
    RING_OF_RECOIL_CHARGES("recoil_charges", AttribType.INTEGER),
    ALLOWED_TO_LOGOUT("can_logout", AttribType.BOOLEAN),
    DEATH_SPAWNS_SPAWNED,
    SAPPHIRE_MEMBER_UNLOCKED,
    EMERALD_MEMBER_UNLOCKED,
    RUBY_MEMBER_UNLOCKED,
    DIAMOND_MEMBER_UNLOCKED,
    DRAGONSTONE_MEMBER_UNLOCKED,
    ONYX_MEMBER_UNLOCKED,
    ZENYTE_MEMBER_UNLOCKED,
    VETION_REBORN_ACTIVE,

    VETION_HELLHOUND_SPAWNED,

    // A map linking minions together used in group respawning.
    MINION_LIST,
    
    /**
     * Key used to keep track of how far in the gnome course we are.
     */
    GNOME_COURSE_STATE("gnomecourse", AttribType.INTEGER),
    SEERS_ROOFTOP_COURSE_STATE("seerscourse", AttribType.INTEGER),
    BARBARIAN_COURSE_STATE("barbcourse", AttribType.INTEGER),
    WILDY_COURSE_STATE("wildycourse", AttribType.INTEGER),

    MUSIC_VOLUME,

    SOUND_VOLUME,

    DEBUG_MESSAGES("debug_messages_activated", AttribType.BOOLEAN),

    /**
     * Used to store the GWD varbit
     */
    GOD_WARS_DUNGEON("godwars_dungeon", AttribType.BOOLEAN),

    /**
     * Key used to check if the kandarin hard diary has been completed
     */
    KANDARIN_HARD_DIARY_COMPLETED("kandarin_hard_diary_completed", AttribType.BOOLEAN),

    /**
     * Key used to store clue scroll container
     */
    TREASURE_TRAIL_REWARD,

    /**
     * Container holding the players cluescroll reward
     */
    CLUE_SCROLL_REWARD,

    /**
     * Container holding the players cluescroll reward
     */
    TREASURE_TRAIL_PUZZLE,

    GWD_ZAMORAK_KC("zamorak_kc", AttribType.INTEGER),
    GWD_BANDOS_KC("bandos_kc", AttribType.INTEGER),
    GWD_SARADOMIN_KC("saradomin_kc", AttribType.INTEGER),
    GWD_ARMADYL_KC("armadyl_kc", AttribType.INTEGER),

    /**
     * Key to store how many clue scrolls our player has completed
     */
    EASY_CLUE_SCROLL("easy_clue", AttribType.INTEGER),
    MEDIUM_CLUE_SCROLL("med_clue", AttribType.INTEGER),
    HARD_CLUE_SCROLL("hrd_clue", AttribType.INTEGER),
    ELITE_CLUE_SCROLL("elite_clue", AttribType.INTEGER),
    MASTER_CLUE_SCROLL("mstr_clue", AttribType.INTEGER),

    /**
     * For gwd - tracking how long ago certain players attached us.
     */
    LAST_ATTACKED_MAP,

    /**
     * Bracelet of Ethereum absorption toggle
     */
    ETHEREUM_ABSORPTION("eth_aborption", AttribType.BOOLEAN),

    /**
     * Checks if the player is currently in the bank.
     */
    BANKING,

    /**
     * Checks if the player is currently using the price checker.
     */
    PRICE_CHECKING,

    /**
     * The entity which froze use.
     */
    FROZEN_BY,

    /**
     * Direction we'll move next after current spear action ends.
     */
    SPEAR_MOVES,

    /**
     * Key used to determine if we have the teleport to bounty spell unlocked
     */
    BOUNTY_HUNTER_TARGET_TELEPORT_UNLOCKED("teleport_to_bounty_target", AttribType.BOOLEAN),

    /*
     * Key used to indicate that the vengeance spell is active
     */
    VENGEANCE_ACTIVE,

    /**
     * Key used to indicate which option we're using on the object
     */
    INTERACTION_OPTION,

    /**
     * Key used to indicate which ground item we are trying to take.
     */
    INTERACTED_GROUNDITEM,

    // If for example - item on item, this is the slot of item 1
    ITEM_SLOT,

    // If we're doing item on item, this is the slot of the 2nd item
    ALT_ITEM_SLOT,

    // Item instance
    FROM_ITEM,

    // Item instance
    TO_ITEM,

    // The interface and child id of a spell when used on an entity. For interactions.
    INTERACTED_WIDGET_INFO,

    BUTTON_SLOT,

    CHILD_ID,

    /**
     * Key used to indicate which action (1..10) was used when button pressing.
     */
    BUTTON_ACTION,

    // Item ID of the Item we're interacting with
    ITEM_ID,

    // Item ID of the secondary item we're interacting with
    ALT_ITEM_ID,

    /**
     * Key used to indicate which map object we are interacting with.
     */
    INTERACTION_OBJECT,

    /**
     * Key used to indicate which map object we interacted with, before it was overwritten by Jak's odd system.
     */
    ORIGINAL_INTERACTION_OBJECT,

    // A weakreference to an entity
    TARGET,

    /**
     * Game time, in ticks played.
     */
    GAME_TIME("game_time", AttribType.INTEGER),

    /**
     * Key to handle the players energy %.
     */
    RUN_ENERGY("runenergy", AttribType.DOUBLE),
    /**
     * Key to handle stamina potions.
     */
    STAMINA_POTION_TICKS("staminapotion", AttribType.INTEGER),
    /**
     * Key to determine if the player is currently walking or running
     */
    IS_RUNNING("is_running", AttribType.BOOLEAN),
    YELL_COLOUR("yell_colour", AttribType.STRING),
    PLAYER_KILLS("player_kills", AttribType.INTEGER),
    PLAYER_DEATHS("player_deaths", AttribType.INTEGER),

    TOP_PKER_REWARD_UNCLAIMED("top_pker_unclaimed_reward", AttribType.BOOLEAN),

    TOP_PKER_REWARD,

    /**
     * The player that owns this very NPC spawn. Think of barrows brothers, or animated armours.
     */
    OWNING_PLAYER,

    /**
     * Tracks the instance which 'owns' an entity - such as the Kraken boss owns its minions.
     */
    BOSS_OWNER,

    /**
     * Key used to indicate which shop we currently are viewing.
     */
    SHOP,

    GROUP_SPAWN_MAP,

    TOURNAMENT_COUNTDOWN("tourny_countdown_last", AttribType.INTEGER),
    ARMOUR_PIERCING("armour_piercing", AttribType.BOOLEAN),
    TENTACLES_DISTURBED("tentacles_disturbed", AttribType.INTEGER),

    // the perk used to toggle the ROL effect from the cape
    DEFENCE_PERK_TOGGLE("defence_perk_toggle", AttribType.INTEGER),

    // the perk used to toggle the ROL effect from the cape
    MAXCAPE_ROL_ON("max_cape_perk_toggle", AttribType.INTEGER),

    /**
     * The amount of BH points we have collected.
     */
    BOUNTY_HUNTER_POINTS("bounty_hunter_points", AttribType.INTEGER),

    // NPC killcounts shown on the slayer log of the Slayer gem.
    KC_CRAWL_HAND("npckc1", AttribType.INTEGER),
    KC_CAVE_BUG("npckc2", AttribType.INTEGER),
    KC_CAVE_CRAWLER("npckc3", AttribType.INTEGER),
    KC_BANSHEE("npckc4", AttribType.INTEGER),
    KC_CAVE_SLIME("npckc5", AttribType.INTEGER),
    KC_ROCKSLUG("npckc6", AttribType.INTEGER),
    KC_DESERT_LIZARD("npckc7", AttribType.INTEGER),
    KC_COCKATRICE("npckc8", AttribType.INTEGER),
    KC_PYREFRIEND("npckc9", AttribType.INTEGER),
    KC_MOGRE("npckc10", AttribType.INTEGER),
    KC_HARPIE_BUG("npckc11", AttribType.INTEGER),
    KC_WALL_BEAST("npckc12", AttribType.INTEGER),
    KC_KILLERWATT("npckc13", AttribType.INTEGER),
    KC_MOLANISK("npckc14", AttribType.INTEGER),
    KC_BASILISK("npckc15", AttribType.INTEGER),
    KC_SEASNAKE("npckc16", AttribType.INTEGER),
    KC_TERRORDOG("npckc17", AttribType.INTEGER),
    KC_FEVER_SPIDER("npckc18", AttribType.INTEGER),
    KC_INFERNAL_MAGE("npckc19", AttribType.INTEGER),
    KC_BRINERAT("npckc20", AttribType.INTEGER),
    KC_BLOODVELD("npckc21", AttribType.INTEGER),
    KC_JELLY("npckc22", AttribType.INTEGER),
    KC_TUROTH("npckc23", AttribType.INTEGER),
    KC_ZYGOMITE("npckc24", AttribType.INTEGER),
    KC_CAVEHORROR("npckc25", AttribType.INTEGER),
    KC_ABERRANT_SPECTRE("npckc26", AttribType.INTEGER),
    KC_SPIRITUAL_RANGER("npckc27", AttribType.INTEGER),
    KC_DUSTDEVIL("npckc_dustdevil", AttribType.INTEGER),
    KC_SPIRITUAL_WARRIOR("npckc28", AttribType.INTEGER),
    KC_KURASK("npckc29", AttribType.INTEGER),
    KC_SKELETAL_WYVERN("npckc30", AttribType.INTEGER),
    KC_GARGOYLE("npckc31", AttribType.INTEGER),
    KC_NECHRYAEL("npckc32", AttribType.INTEGER),
    KC_SPIRITUAL_MAGE("npckc33", AttribType.INTEGER),
    KC_ABYSSALDEMON("npckc34", AttribType.INTEGER),
    KC_CAVEKRAKEN("npckc35", AttribType.INTEGER),
    KC_DARKBEAST("npckc36", AttribType.INTEGER),
    KC_SMOKEDEVIL("npckc37", AttribType.INTEGER),
    SUPERIOR("npckc38", AttribType.INTEGER),
    BRUTAL_BLACK_DRAGON ("npckc39", AttribType.INTEGER),
    FOSSIL_WYVERN("npckc40", AttribType.INTEGER),
    WYRM("npckc42", AttribType.INTEGER),
    DRAKE("npckc43", AttribType.INTEGER),
    HYDRA("npckc44", AttribType.INTEGER),
    BASILISK_KNIGHT("npckc45", AttribType.INTEGER),

    AGS_GFX_GOLD("ags_gfx_gold", AttribType.BOOLEAN),
    BGS_GFX_GOLD("ags_gfx_gold", AttribType.BOOLEAN),
    SGS_GFX_GOLD("ags_gfx_gold", AttribType.BOOLEAN),
    ZGS_GFX_GOLD("ags_gfx_gold", AttribType.BOOLEAN),
    VOLATILE_NIGHTMARE_STAFF_QUESTION("volatile_nightmare_staff_question", AttribType.BOOLEAN),
    ELDRITCH_NIGHTMARE_STAFF_QUESTION("eldritch_nightmare_staff_question", AttribType.BOOLEAN),
    HARMONISED_NIGHTMARE_STAFF_QUESTION("harmonised_nightmare_staff_question", AttribType.BOOLEAN),
    VORKATH_CB_COOLDOWN,
    VORKATH_LINEAR_ATTACKS,
    VORKATH_NORMAL_ATTACK_COUNT,
    VORKATH_LAST_MAJOR_ATTACK,
    SMITHING_EQUIPMENT,

    WEALTHY_PLAYER("wealthy_player", AttribType.BOOLEAN),
    TREASURE_CHESTS_OPENED("treasure_collector", AttribType.INTEGER),
    JADS_KILLED("jad_kills", AttribType.INTEGER),

    KC_REX("kc_rex", AttribType.INTEGER),
    KC_PRIME("kc_prime", AttribType.INTEGER),
    KC_SUPREME("kc_supreme", AttribType.INTEGER),
    KC_GIANTMOLE("kc_mole", AttribType.INTEGER),
    KC_ARAGOG("kc_aragog", AttribType.INTEGER),
    KC_KQ("kc_kq", AttribType.INTEGER),
    KC_TZKAL_ZUK("kc_zuk", AttribType.INTEGER),
    KC_ABYSSALSIRE("kc_sire", AttribType.INTEGER),
    KC_BRYOPHYTA("kc_bryophyta", AttribType.INTEGER),
    WINTERTODT("wintertodt", AttribType.INTEGER),
    OBOR("obor", AttribType.INTEGER),
    CHAMBER_OF_SECRET_RUNS_COMPLETED("xeric_runs", AttribType.INTEGER),
    CHAMBER_OF_XERIC_RUNS_COMPLETED("xeric_runs", AttribType.INTEGER),
    DERANGED_ARCH("deranged_arch", AttribType.INTEGER),
    GROTESQUE_GUARDIANS("grotesque_guardians", AttribType.INTEGER),
    BYROPHYTA("byrophyta", AttribType.INTEGER),
    THEATRE_OF_BLOOD_RUNS_COMPLETED("TOB", AttribType.INTEGER),
    ZARYTE_CROSSBOW_SPEC_ACTIVE("zaryte_crossbow_spec_active", AttribType.BOOLEAN),
    HESPORI("hespori", AttribType.INTEGER),
    MIMIC("mimic", AttribType.INTEGER),
    SARACHNIS("sarachnis", AttribType.INTEGER),
    ZALCANO("zalcano", AttribType.INTEGER),
    THE_GAUNTLET("the_guantlet", AttribType.INTEGER),
    THE_CORRUPTED_GAUNTLET("the_corrupted_guantlet", AttribType.INTEGER),
    GENERAL_GRAARDOR_KILLED("gen_graardors_killed", AttribType.INTEGER),
    KREE_ARRA_KILLED("kree_arras_killed", AttribType.INTEGER),
    COMMANDER_ZILYANA_KILLED("commander_zillys_killed", AttribType.INTEGER),
    KRIL_TSUTSAROTHS_KILLED("kril_tsutsaroths_killed", AttribType.INTEGER),
    SCORPIAS_KILLED("scorpias_killed", AttribType.INTEGER),
    CALLISTOS_KILLED("callistos_killed", AttribType.INTEGER),
    ADAMANT_DRAGONS_KILLED("adamant_dragons_killed", AttribType.INTEGER),
    RUNE_DRAGONS_KILLED("rune_dragons_killed", AttribType.INTEGER),
    LAVA_DRAGONS_KILLED("lava_dragons_killed", AttribType.INTEGER),
    MEN_IN_BLACK_KILLED("men_in_black_killed", AttribType.INTEGER),
    SKOTIZOS_KILLED("skotizos_killed", AttribType.INTEGER),
    ZOMBIES_CHAMPIONS_KILLED("zombies_champions_killed", AttribType.INTEGER),
    BRUTAL_LAVA_DRAGONS_KILLED("brutal_lava_dragons_killed", AttribType.INTEGER),
    CENTAURS_KILLED("centaurs_killed", AttribType.INTEGER),
    DEMENTORS_KILLED("dementors_killed", AttribType.INTEGER),
    FLUFFYS_KILLED("fluffys_killed", AttribType.INTEGER),
    HUNGARIAN_HORNTAILS_KILLED("horntails_killed", AttribType.INTEGER),
    FENRIR_GREYBACKS_KILLED("fenrir_greybacks_killed", AttribType.INTEGER),
    ANCIENT_REVENANTS_KILLED("ancient_revenants_killed", AttribType.INTEGER),
    KERBEROS_KILLED("kerberos_killed", AttribType.INTEGER),
    ARACHNE_KILLED("arachne_killed", AttribType.INTEGER),
    SKORPIOS_KILLED("skorpios_killed", AttribType.INTEGER),
    ARTIO_KILLED("artio_killed", AttribType.INTEGER),
    ANCIENT_BARRELCHESTS_KILLED("ancient_barrelchests_killed", AttribType.INTEGER),
    ANCIENT_CHAOS_ELEMENTALS_KILLED("ancient_chaos_elementals_killed", AttribType.INTEGER),
    ANCIENT_KING_BLACK_DRAGONS_KILLED("ancient_king_black_dragons_killed", AttribType.INTEGER),
    CORRUPTED_HUNLEFFS_KILLED("corrupted_hunleffs_killed", AttribType.INTEGER),
    TEKTONS_KILLED("tektons_killed", AttribType.INTEGER),
    CHAOS_FANATICS_KILLED("chaos_fanatics_killed", AttribType.INTEGER),
    THERMONUCLEAR_SMOKE_DEVILS_KILLED("thermonuclear_smoke_devil_killed", AttribType.INTEGER),
    KING_BLACK_DRAGONS_KILLED("king_black_dragons_killed", AttribType.INTEGER),
    VENENATIS_KILLED("venenatis_killed", AttribType.INTEGER),
    VETIONS_KILLED("vetions_killed", AttribType.INTEGER),
    CRAZY_ARCHAEOLOGISTS_KILLED("crazy_archaeologists_killed", AttribType.INTEGER),
    ZULRAHS_KILLED("zulrahs_killed", AttribType.INTEGER),
    ALCHY_KILLED("alchy_killed", AttribType.INTEGER),
    KRAKENS_KILLED("krakens_killed", AttribType.INTEGER),
    REVENANTS_KILLED("revenants_killed", AttribType.INTEGER),
    CHAOS_ELEMENTALS_KILLED("chaos_elementals_killed", AttribType.INTEGER),
    DEMONIC_GORILLAS_KILLED("demonic_gorillas_killed", AttribType.INTEGER),
    BARRELCHESTS_KILLED("barrelchests_killed", AttribType.INTEGER),
    CORPOREAL_BEASTS_KILLED("corporeal_beasts_killed", AttribType.INTEGER),
    CERBERUS_KILLED("abyssal_sires_killed", AttribType.INTEGER),
    VORKATHS_KILLED("vorkaths_killed", AttribType.INTEGER),
    LIZARDMAN_SHAMANS_KILLED("lizardman_shamans_killed", AttribType.INTEGER),
    BARROWS_CHESTS_OPENED("barrows_chests_opened", AttribType.INTEGER),
    CORRUPTED_NECHRYARCHS_KILLED("corrupted_nechryarchs_killed", AttribType.INTEGER),
    REFERRAL_MILESTONE_10HOURS("referral_milestone_1hours", AttribType.BOOLEAN),
    REFERRAL_MILESTONE_1_DAY("referral_milestone_1day", AttribType.BOOLEAN),
    REFERRER_USERNAME("referrer_username", AttribType.STRING),
    REFERRALS_COUNT("referrals_count", AttribType.INTEGER),
    DATABASE_PLAYER_ID("database_player_id", AttribType.INTEGER),
    REFERRAL_MILESTONE_THREE_REFERRALS("referral_milestone_three_refs", AttribType.BOOLEAN),
    LAST_ENCHANT_SELECTED,
    LARRANS_KEYS_OPENED("larrans_keys_opened", AttribType.INTEGER),
    MBOX_REWARDS_VISIBLE,
    FILLER_AMT, RC_DIALOGUE, RUNECRAFTING,
    VENOM_TASK_RUNNING, PRAYER_DELAYED_ACTIVATION_TASK, PRAYER_DELAYED_ACTIVATION_CLICKS, MOVEMENT_PACKET_STEPS,
    IS_BOT, OVERLOAD_TASK_RUNNING, ANTIFIRE_TASK_RUNNING, DIVINE_BASTION_POTION_TASK_RUNNING, DIVINE_BATTLEMAGE_POTION_TASK_RUNNING, DIVINE_RANGING_POTION_TASK_RUNNING,
    DIVINE_SUPER_ATTACK_POTION_TASK_RUNNING, DIVINE_SUPER_STRENGTH_POTION_TASK_RUNNING, DIVINE_SUPER_DEFENCE_POTION_TASK_RUNNING, DIVINE_SUPER_COMBAT_POTION_TASK_RUNNING,

    DIVINE_MAGIC_POTION_TASK_RUNNING, CACHED_PROJECTILE_STATE, POISON_TASK_RUNNING,
    LOGOUT_CLICKED,

    PLAYER_AUTO_SAVE_TASK_RUNNING,

    LAST_GAMBLE_REQUEST,
    ELEMENTAL_BOW_SPECIAL_COOLDOWN,

    // Attribute used by doors/gates to record what cycles they were opened/closed on.
    DOOR_USES,

    RECENT_LISTING_RESULTS, BUY_LISTING_RESULTS,
    HARDCORE_GROUP_FALLEN("hardcore_group_fallen", AttribType.BOOLEAN), LOGGED_IN_AT_TIME;

    private String saveName;
    private AttribType type;

    AttributeKey() {

    }

    AttributeKey(String name, AttribType persistType) {
        this.saveName = name;
        this.type = persistType;
    }

    public String saveName() {
        return saveName;
    }

    public AttribType saveType() {
        return type;
    }

}
