package com.valinor.game.content.items;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.items.Item;
import com.valinor.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class MysteryBox {

    private static final int EXTREME_ROLL = 500;
    private static final int RARE_ROLL = 50;
    private static final int UNCOMMON_ROLL = 20;

    private static final Item[] EXTREMELY_RARE = new Item[]{
        new Item(1053), //Green halloween mask
        new Item(1055), //Blue halloween mask
        new Item(1057), //Red halloween mask
        new Item(11847), //Black halloween mask
        new Item(1050), //Santa hat
        new Item(13343), //Black santa hat
        new Item(13344), //Inverted santa hat
        new Item(1038), //Red party hat
        new Item(1040), //Yellow party hat
        new Item(1042), //Blue party hat
        new Item(1044), //Green party hat
        new Item(1046), //Purple party hat
        new Item(1048), //White  party hat
        new Item(11862), //Black party hat
        new Item(11863), //Rainbow party hat
        new Item(12399), //Partyhat & specs
        new Item(962), // Xmas cracker
        new Item(1050), // Santa hat
        new Item(12422), // 3rd age wand
        new Item(12424), // 3rd age bow
        new Item(12426), // 3rd age sword
        new Item(12437), // 3rd age cloak
        new Item(10330), // 3rd age range top
        new Item(10332), // 3rd age range legs
        new Item(10334), // 3rd age range coif
        new Item(10336), // 3rd age range vanbraces
        new Item(10338), // 3rd age robe top
        new Item(10340), // 3rd age robe
        new Item(10342), // 3rd age mage hat
        new Item(10344), // 3rd age amulet
        new Item(10346), // 3rd age platelegs
        new Item(10348), // 3rd age platebody
        new Item(10350), // 3rd age fullhelm
        new Item(10352), // 3rd age kiteshield
    };

    private static final Item[] RARE = new Item[]{
        new Item(12825), //Arcane spirit shield
        new Item(12821), //Spectral spirit shield
        new Item(22981), //Ferocious gloves
        new Item(22978), //Dragon hunter lance
        new Item(11806), //Armadyl godsword
        new Item(12821), //Spectral spirit shield
        new Item(13576), //Dragon warhammer
        new Item(11785), //Armadyl crossbow
        new Item(12902), //Toxic staff of the dead
        new Item(11791), //Staff of the dead
        new Item(12931), //Serpentine helm
        new Item(13235), //Eternal boots
        new Item(13237), //Pegasian boots
        new Item(13239), //Primordial boots
        new Item(11828), //Armadyl chestplate
        new Item(11830), //Armadyl chainskirt
        new Item(11826), //Armadyl helmet
        new Item(11834), //Bandos tassets
        new Item(11832), //Bandos chestplate
        new Item(11808), //Zamorak godsword
        new Item(11806), //Saradomin godsword
        new Item(11804), //Bandos godsword
        new Item(12696, World.getWorld().random(4000, 5000)), //5000 Super combat potions
        new Item(13442, World.getWorld().random(4000, 5000)) //5000 Anglerfish
    };

    private static final Item[] UNCOMMON = new Item[]{
        new Item(6737), //Berserker ring
        new Item(6731), //Seers ring
        new Item(13271), //Abyssal dagger
        new Item(12791), //Rune pouch
        new Item(2581), //Robin hood hat
        new Item(12596), //Rangers' tunic
        new Item(22975), //Brimstone ring
        new Item(12371), //Lava dragon mask
        new Item(11283), //Dragonfire shield
        new Item(11791), //Staff of the dead
        new Item(4224), //New crystal shield
        new Item(12831), //Blessed spirit shield
        new Item(11926), //Odium ward
        new Item(11924), //Malediction ward
        new Item(6735), //Warriors ring
        new Item(6733), //Archers ring
        new Item(6731), //Seers ring
        new Item(11773), //Berserker ring (i)
        new Item(11772), //Warriors ring (i)
        new Item(11771), //Archers ring (i)
        new Item(11770), //Seers ring (i))
        new Item(12002), //Occult necklace
        new Item(6585), //Amulet of fury
        new Item(1419), // Scythe
        new Item(1037), // Bunny ears
        new Item(19988), // Blacksmith's helm
        new Item(19991), // Bucket helm
        new Item(20059), // Bucket helm (g)
        new Item(20050), // Obsidian cape (f)
    };

    private static final Item[] COMMON = new Item[]{
        new Item(4708), //Ahrim's hood
        new Item(4712), //Ahrim's robetop
        new Item(4714), //Ahrim's robeskirt
        new Item(4716), //Dharok's helm
        new Item(4720), //Dharok's platebody
        new Item(4722), //Dharok's platelegs
        new Item(4718), //Dharok's greataxe
        new Item(4736), //Karil's leathertop
        new Item(13442, 100), //100 Anglerfish
        new Item(11235), //Dark bow
        new Item(8927), //Bandana eyepatch
        new Item(2643), //Dark cavalier
        new Item(12301), //Blue headband
        new Item(2577), //Ranger boots
        new Item(12639), //Guthix halo
        new Item(12430), //Afro
        new Item(12245), //Beanie
        new Item(12638), //Zamorak halo
        new Item(12637), //Saraodmin halo
        new Item(11899), //Decorative platebody (Melee)
        new Item(11900), //Decorative platelegs (Melee)
        new Item(11898), //Decorative hat (Magic)
        new Item(11896), //Decorative robe top (Magic)
        new Item(11897), //Decorative bottoms (Magic)
        new Item(12375), //Black cane
        new Item(12377), //Adamant cane
        new Item(12365), //Iron dragon mask
        new Item(12367), //Steel dragon mask
        new Item(12369), //Mithril dragon mask
        new Item(12518), //Green dragon mask
        new Item(12522), //Red dragon mask
        new Item(12524), //Black dragon mask
        new Item(12763), //White dark bow paint
        new Item(12761), //Yellow dark bow paint
        new Item(12759), //Green dark bow paint
        new Item(12757), //Blue dark bow paint
        new Item(12769), //Frozen whip mix
        new Item(12771), //Volcanic whip mix
        new Item(12829), //Spirit shield
        new Item(6922), //Infinity gloves
        new Item(6918), //Infinity hat
        new Item(6916), //Infinity top
        new Item(6924), //Infinity bottoms
        new Item(6528), //Tzhaar-ket-om
        new Item(6525), //Toktz-xil-ek
        new Item(4151), //Abyssal whip
        new Item(24225), //Granite maul
        new Item(6920), //Infinity boots
        new Item(11128), //Berseker necklace
        new Item(12696, 250), //250 Super combat potions
        new Item(13442, 250), //250 Anglerfish
        new Item(12696, 500), //500 Super combat potions
        new Item(13442, 500), //500 Anglerfish
        new Item(1037), //Bunny ears
        new Item(6666), //Flippers
        new Item(4566), //rubber chicken
        new Item(13182), //Bunny feat
        new Item(12006), //Abyssal tentacle
        new Item(6665), //Mudskipper hat
        new Item(11919), //Cow mask
        new Item(12956), //Cow top
        new Item(12957), //Cow bottoms
        new Item(12958), //Cow gloves
        new Item(12959), //Cow boots
        new Item(12379), //Rune cane
        new Item(12373), //Dragon cane
        new Item(12363), //Bronze dragon mask
        new Item(20517), //Elder chaos top
        new Item(20520), //Elder chaos bottom
        new Item(20595), //Elder chaos hood
        new Item(12696, 1000), //1000 Super combat potions
        new Item(13442, 1000), //1000 Anglerfish
        new Item(12696, 1500), //1500 Super combat potions
        new Item(13442, 1500), //1500 Anglerfish
        new Item(12696, 5000), //5000 Super combat potions
        new Item(13442, 5000), //5000 Anglerfish
        new Item(20008), // Fancy tiara
        new Item(20110), // Bowl wig
        new Item(20020), // Lesser demon mask
        new Item(20023), // Greater demon mask
        new Item(20026), // Black demon mask
        new Item(20029), // Old demon mask
        new Item(20032) // Jungle demon mask
    };

    private Item[] allRewardsCached;

    public Item[] allPossibleRewards() {
        if (allRewardsCached == null) {
            ArrayList<Item> Items = new ArrayList<>();
            Items.addAll(Arrays.asList(EXTREMELY_RARE));
            Items.addAll(Arrays.asList(RARE));
            Items.addAll(Arrays.asList(UNCOMMON));
            Items.addAll(Arrays.asList(COMMON));
            allRewardsCached = Items.toArray(new Item[0]);
        }
        return allRewardsCached;
    }
    
    public Item rollReward() {
        if (Utils.rollDie(EXTREME_ROLL, 1)) {
            return Utils.randomElement(EXTREMELY_RARE);
        } else if (Utils.rollDie(RARE_ROLL, 1)) {
            return Utils.randomElement(RARE);
        } else if (Utils.rollDie(UNCOMMON_ROLL, 1)) {
            return Utils.randomElement(UNCOMMON);
        } else {
            return Utils.randomElement(COMMON);
        }
    }
}
