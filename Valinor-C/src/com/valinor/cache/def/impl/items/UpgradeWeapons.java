package com.valinor.cache.def.impl.items;

import com.valinor.cache.def.ItemDefinition;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | November, 21, 2020, 10:55
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class UpgradeWeapons {

    public static void unpack(int id) {
        ItemDefinition definition = ItemDefinition.get(id);
        switch (id) {
            case 15441:
                definition.name = "Abyssal whip tier 1";
                ItemDefinition.copyInventory(definition, ABYSSAL_WHIP);
                ItemDefinition.copyEquipment(definition, ABYSSAL_WHIP);
                definition.recolorTo = new int[]{127};
                definition.recolorFrom = new int[]{944};
                definition.modelCustomColor = 127;
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 15442:
                definition.name = "Abyssal whip tier 2";
                ItemDefinition.copyInventory(definition, ABYSSAL_WHIP);
                ItemDefinition.copyEquipment(definition, ABYSSAL_WHIP);
                definition.recolorFrom = new int[]{34770};
                definition.recolorFrom = new int[]{944};
                definition.modelCustomColor2 = 34770;
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 15443:
                definition.name = "Abyssal whip tier 3";
                ItemDefinition.copyInventory(definition, ABYSSAL_WHIP);
                ItemDefinition.copyEquipment(definition, ABYSSAL_WHIP);
                definition.recolorTo = new int[]{76770};
                definition.recolorFrom = new int[]{944};
                definition.modelCustomColor2 = 76770;
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 15444:
                definition.name = "Abyssal whip tier 4";
                ItemDefinition.copyInventory(definition, ABYSSAL_WHIP);
                ItemDefinition.copyEquipment(definition, ABYSSAL_WHIP);
                definition.recolorTo = new int[]{87770};
                definition.recolorFrom = new int[]{944};
                definition.modelCustomColor2 = 87770;
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 15445:
            case 15446:
            case 15447:
            case 15448:
            case 15449:
                definition.name = "Abyssal whip tier 5";
                ItemDefinition.copyInventory(definition, ABYSSAL_WHIP);
                ItemDefinition.copyEquipment(definition, ABYSSAL_WHIP);
                if (id != 15445) {
                    if (id != 15446) {
                        if (id != 15447) {
                            if (id != 15448) {
                                if (id == 15449) {
                                    definition.recolorTo = new int[]{87770};
                                    definition.recolorFrom = new int[]{944};
                                    definition.modelCustomColor2 = 87770;
                                }
                            } else {
                                definition.recolorTo = new int[]{76770};
                                definition.recolorFrom = new int[]{944};
                                definition.modelCustomColor2 = 76770;
                            }
                        } else {
                            definition.recolorTo = new int[]{34770};
                            definition.recolorFrom = new int[]{944};
                            definition.modelCustomColor2 = 34770;
                        }
                    } else {
                        definition.recolorTo = new int[]{127};
                        definition.recolorFrom = new int[]{944};
                        definition.modelCustomColor = 127;
                    }
                } else {
                    definition.recolorTo = new int[]{374770};
                    definition.recolorFrom = new int[]{944};
                    definition.modelCustomColor2 = 374770;
                }
                definition.inventoryActions = new String[]{null, "Wield", null, "Change-Color", "Destroy"};
                break;

            case 16209:
                definition.name = "Dragon dagger tier 1";
                ItemDefinition.copyInventory(definition, DRAGON_DAGGERP_5698);
                ItemDefinition.copyEquipment(definition, DRAGON_DAGGERP_5698);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                definition.recolorTo = new int[]{491770, 491770, 924};
                definition.recolorFrom = new int[]{6036, 22459, 924};
                break;

            case 16210:
                definition.name = "Dragon dagger tier 2";
                ItemDefinition.copyInventory(definition, DRAGON_DAGGERP_5698);
                ItemDefinition.copyEquipment(definition, DRAGON_DAGGERP_5698);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                definition.recolorTo = new int[]{296770, 296770, 924};
                definition.recolorFrom = new int[]{6036, 22459, 924};
                break;

            case 16211:
                definition.name = "Dragon dagger tier 3";
                ItemDefinition.copyInventory(definition, DRAGON_DAGGERP_5698);
                ItemDefinition.copyEquipment(definition, DRAGON_DAGGERP_5698);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                definition.recolorTo = new int[]{76770, 76770, 924};
                definition.recolorFrom = new int[]{6036, 22459, 924};
                break;

            case 16212:
                definition.name = "Dragon dagger tier 4";
                ItemDefinition.copyInventory(definition, DRAGON_DAGGERP_5698);
                ItemDefinition.copyEquipment(definition, DRAGON_DAGGERP_5698);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                definition.recolorTo = new int[]{933, 933, 1};
                definition.recolorFrom = new int[]{6036, 22459, 924};
                break;

            case 16213:
            case 16214:
            case 16215:
            case 16216:
            case 16217:
                definition.name = "Dragon dagger tier 5";
                ItemDefinition.copyInventory(definition, DRAGON_DAGGERP_5698);
                ItemDefinition.copyEquipment(definition, DRAGON_DAGGERP_5698);
                definition.groundActions = new String[]{null, null, "Take", null, null};
                definition.inventoryActions = new String[]{null, "Wield", null, "Change-Color", "Destroy"};
                if (id != 16213) {
                    if (id != 16214) {
                        if (id != 16215) {
                            if (id != 16216) {
                                if (id == 16217) {
                                    definition.recolorTo = new int[]{491770, 491770, 924};
                                    definition.recolorFrom = new int[]{6036, 22459, 924};
                                }
                            } else {
                                definition.recolorTo = new int[]{296770, 296770, 924};
                                definition.recolorFrom = new int[]{6036, 22459, 924};
                            }
                        } else {
                            definition.recolorTo = new int[]{76770, 76770, 924};
                            definition.recolorFrom = new int[]{6036, 22459, 924};
                        }
                    } else {
                        definition.recolorTo = new int[]{933, 933, 1};
                        definition.recolorFrom = new int[]{6036, 22459, 924};
                    }
                } else {
                    definition.recolorTo = new int[]{374770, 374770, 1};
                    definition.recolorFrom = new int[]{6036, 22459, 924};
                }
                break;

            case 16200:
                definition.name = "Granite maul tier 1";
                ItemDefinition.copyInventory(definition, GRANITE_MAUL_24225);
                ItemDefinition.copyEquipment(definition, GRANITE_MAUL_24225);
                definition.inventoryActions = new String[]{null, "Wield", null, "Change-Color", "Destroy"};
                definition.recolorTo = new int[]{227, 227, 290, 280, 270};
                definition.recolorFrom = new int[]{49, -32742, 10295, 7452, 10287};
                break;

            case 16201:
                definition.name = "Granite maul tier 2";
                ItemDefinition.copyInventory(definition, GRANITE_MAUL_24225);
                ItemDefinition.copyEquipment(definition, GRANITE_MAUL_24225);
                definition.inventoryActions = new String[]{null, "Wield", null, "Change-Color", "Destroy"};
                definition.recolorTo = new int[]{38449, 38349, 38349, 38349, 38349};
                definition.recolorFrom = new int[]{49, -32742, 10295, 7452, 10287};
                break;

            case 16202:
                definition.name = "Granite maul tier 3";
                ItemDefinition.copyInventory(definition, GRANITE_MAUL_24225);
                ItemDefinition.copyEquipment(definition, GRANITE_MAUL_24225);
                definition.inventoryActions = new String[]{null, "Wield", null, "Change-Color", "Destroy"};
                definition.recolorTo = new int[]{22872, 22872, 90, 87770, 87770};
                definition.recolorFrom = new int[]{49, -32742, 10295, 7452, 10287};
                break;

            case 16203:
                definition.name = "Granite maul tier 4";
                ItemDefinition.copyInventory(definition, GRANITE_MAUL_24225);
                ItemDefinition.copyEquipment(definition, GRANITE_MAUL_24225);
                definition.inventoryActions = new String[]{null, "Wield", null, "Change-Color", "Destroy"};
                definition.recolorTo = new int[]{933, 933, 1, 926, 926};
                definition.recolorFrom = new int[]{49, -32742, 10295, 7452, 10287};
                break;

            case 16204:
            case 16205:
            case 16206:
            case 16207:
            case 16208:
                ItemDefinition.copyInventory(definition, GRANITE_MAUL_24225);
                ItemDefinition.copyEquipment(definition, GRANITE_MAUL_24225);
                definition.name = "Granite maul tier 5";
                definition.inventoryActions = new String[]{null, "Wield", null, "Change-Color", "Destroy"};
                if (id != 16204) {
                    if (id != 16205) {
                        if (id != 16206) {
                            if (id != 16207) {
                                if (id == 16208) {
                                    definition.recolorTo = new int[]{227, 227, 290, 280, 270};
                                    definition.recolorFrom = new int[]{49, -32742, 10295, 7452, 10287};
                                }
                            } else {
                                definition.recolorTo = new int[]{38449, 38349, 38349, 38349, 38349};
                                definition.recolorFrom = new int[]{49, -32742, 10295, 7452, 10287};
                            }
                        } else {
                            definition.recolorTo = new int[]{22872, 22872, 90, 87770, 87770};
                            definition.recolorFrom = new int[]{49, -32742, 10295, 7452, 10287};
                        }
                    } else {
                        definition.recolorTo = new int[]{933, 933, 1, 926, 926};
                        definition.recolorFrom = new int[]{49, -32742, 10295, 7452, 10287};
                    }
                } else {
                    definition.recolorTo = new int[]{374770, 374770, 1, 374770, 374770};
                    definition.recolorFrom = new int[]{49, -32742, 10295, 7452, 10287};
                }
                break;

            case 16218:
                definition.modelCustomColor3 = 65535;
                definition.name = "Magic shortbow tier 1";
                ItemDefinition.copyInventory(definition, MAGIC_SHORTBOW);
                ItemDefinition.copyEquipment(definition, MAGIC_SHORTBOW);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16219:
                definition.modelCustomColor4 = 29667;
                definition.name = "Magic shortbow tier 2";
                ItemDefinition.copyInventory(definition, MAGIC_SHORTBOW);
                ItemDefinition.copyEquipment(definition, MAGIC_SHORTBOW);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16220:
                definition.modelCustomColor4 = 35667;
                definition.name = "Magic shortbow tier 3";
                ItemDefinition.copyInventory(definition, MAGIC_SHORTBOW);
                ItemDefinition.copyEquipment(definition, MAGIC_SHORTBOW);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16221:
                definition.modelCustomColor4 = 45667;
                definition.name = "Magic shortbow tier 4";
                ItemDefinition.copyInventory(definition, MAGIC_SHORTBOW);
                ItemDefinition.copyEquipment(definition, MAGIC_SHORTBOW);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;
            case 16222:
            case 16223:
            case 16224:
            case 16225:
            case 16226:
                if (id != 16222) {
                    if (id != 16223) {
                        if (id != 16224) {
                            if (id != 16225) {
                                if (id == 16226) {
                                    definition.modelCustomColor3 = 65535;
                                }
                            } else {
                                definition.modelCustomColor4 = 29667;
                            }
                        } else {
                            definition.modelCustomColor4 = 35667;
                        }
                    } else {
                        definition.modelCustomColor4 = 45667;
                    }
                } else {
                    definition.modelCustomColor4 = 56667;
                }
                definition.name = "Magic shortbow tier 5";
                ItemDefinition.copyInventory(definition, MAGIC_SHORTBOW);
                ItemDefinition.copyEquipment(definition, MAGIC_SHORTBOW);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16227:
                definition.modelCustomColor3 = 65535;
                definition.name = "Rune crossbow tier 1";
                ItemDefinition.copyInventory(definition, RUNE_CROSSBOW);
                ItemDefinition.copyEquipment(definition, RUNE_CROSSBOW);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16228:
                definition.modelCustomColor4 = 25966;
                definition.name = "Rune crossbow tier 2";
                ItemDefinition.copyInventory(definition, RUNE_CROSSBOW);
                ItemDefinition.copyEquipment(definition, RUNE_CROSSBOW);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16229:
                definition.modelCustomColor4 = 21532;
                definition.name = "Rune crossbow tier 3";
                ItemDefinition.copyInventory(definition, RUNE_CROSSBOW);
                ItemDefinition.copyEquipment(definition, RUNE_CROSSBOW);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16230:
                definition.modelCustomColor4 = 25325;
                definition.name = "Rune crossbow tier 4";
                ItemDefinition.copyInventory(definition, RUNE_CROSSBOW);
                ItemDefinition.copyEquipment(definition, RUNE_CROSSBOW);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16231:
            case 16232:
            case 16233:
            case 16234:
            case 16235:
                if (id != 16231) {
                    if (id != 16232) {
                        if (id != 16233) {
                            if (id != 16234) {
                                if (id == 16235) {
                                    definition.modelCustomColor3 = 65535;
                                }
                            } else {
                                definition.modelCustomColor4 = 25966;
                            }
                        } else {
                            definition.modelCustomColor4 = 21532;
                        }
                    } else {
                        definition.modelCustomColor4 = 25325;
                    }
                } else {
                    definition.modelCustomColor4 = 19667;
                }
                definition.name = "Rune crossbow tier 5";
                ItemDefinition.copyInventory(definition, RUNE_CROSSBOW);
                ItemDefinition.copyEquipment(definition, RUNE_CROSSBOW);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16236:
                definition.modelCustomColor4 = 52;
                definition.name = "Dragon scimitar tier 1";
                ItemDefinition.copyInventory(definition, DRAGON_SCIMITAR);
                ItemDefinition.copyEquipment(definition, DRAGON_SCIMITAR);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16237:
                definition.modelCustomColor3 = 296438;
                definition.name = "Dragon scimitar tier 2";
                ItemDefinition.copyInventory(definition, DRAGON_SCIMITAR);
                ItemDefinition.copyEquipment(definition, DRAGON_SCIMITAR);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16238:
                definition.modelCustomColor3 = 127752;
                definition.name = "Dragon scimitar tier 3";
                ItemDefinition.copyInventory(definition, DRAGON_SCIMITAR);
                ItemDefinition.copyEquipment(definition, DRAGON_SCIMITAR);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16239:
                definition.modelCustomColor4 = 15543;
                definition.name = "Dragon scimitar tier 4";
                ItemDefinition.copyInventory(definition, DRAGON_SCIMITAR);
                ItemDefinition.copyEquipment(definition, DRAGON_SCIMITAR);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16240:
            case 16241:
            case 16242:
            case 16243:
            case 16244:
                if (id != 16240) {
                    if (id != 16241) {
                        if (id != 16242) {
                            if (id != 16243) {
                                if (id == 16244) {
                                    definition.modelCustomColor4 = 52;
                                }
                            } else {
                                definition.modelCustomColor3 = 296438;
                            }
                        } else {
                            definition.modelCustomColor3 = 127752;
                        }
                    } else {
                        definition.modelCustomColor4 = 15543;
                    }
                } else {
                    definition.modelCustomColor3 = 73737;
                }
                definition.name = "Dragon scimitar tier 5";
                ItemDefinition.copyInventory(definition, DRAGON_SCIMITAR);
                ItemDefinition.copyEquipment(definition, DRAGON_SCIMITAR);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16245:
                definition.modelCustomColor4 = 81;
                definition.name = "Dragon longsword tier 1";
                ItemDefinition.copyInventory(definition, DRAGON_LONGSWORD);
                ItemDefinition.copyEquipment(definition, DRAGON_LONGSWORD);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16246:
                definition.modelCustomColor2 = 296444;
                definition.name = "Dragon longsword tier 2";
                ItemDefinition.copyInventory(definition, DRAGON_LONGSWORD);
                ItemDefinition.copyEquipment(definition, DRAGON_LONGSWORD);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16247:
                definition.modelCustomColor3 = 127752;
                definition.name = "Dragon longsword tier 3";
                ItemDefinition.copyInventory(definition, DRAGON_LONGSWORD);
                ItemDefinition.copyEquipment(definition, DRAGON_LONGSWORD);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16248:
                definition.modelCustomColor4 = 88881;
                definition.name = "Dragon longsword tier 4";
                ItemDefinition.copyInventory(definition, DRAGON_LONGSWORD);
                ItemDefinition.copyEquipment(definition, DRAGON_LONGSWORD);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16249:
            case 16250:
            case 16251:
            case 16252:
            case 16253:
                if (id != 16249) {
                    if (id != 16250) {
                        if (id != 16251) {
                            if (id != 16252) {
                                if (id == 16253) {
                                    definition.modelCustomColor4 = 81;
                                }
                            } else {
                                definition.modelCustomColor4 = 296438;
                            }
                        } else {
                            definition.modelCustomColor3 = 127752;
                        }
                    } else {
                        definition.modelCustomColor4 = 88881;
                    }
                } else {
                    definition.modelCustomColor4 = 19667;
                }
                definition.name = "Dragon longsword tier 5";
                ItemDefinition.copyInventory(definition, DRAGON_LONGSWORD);
                ItemDefinition.copyEquipment(definition, DRAGON_LONGSWORD);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16254:
                definition.modelCustomColor4 = 81;
                definition.name = "Dragon mace tier 1";
                ItemDefinition.copyInventory(definition, DRAGON_MACE);
                ItemDefinition.copyEquipment(definition, DRAGON_MACE);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16255:
                definition.modelCustomColor2 = 296444;
                definition.name = "Dragon mace tier 2";
                ItemDefinition.copyInventory(definition, DRAGON_MACE);
                ItemDefinition.copyEquipment(definition, DRAGON_MACE);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16256:
                definition.modelCustomColor3 = 127752;
                definition.name = "Dragon mace tier 3";
                ItemDefinition.copyInventory(definition, DRAGON_MACE);
                ItemDefinition.copyEquipment(definition, DRAGON_MACE);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16257:
                definition.modelCustomColor4 = 88881;
                definition.name = "Dragon mace tier 4";
                ItemDefinition.copyInventory(definition, DRAGON_MACE);
                ItemDefinition.copyEquipment(definition, DRAGON_MACE);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16258:
            case 16259:
            case 16260:
            case 16261:
            case 16262:
                if (id != 16258) {
                    if (id != 16259) {
                        if (id != 16260) {
                            if (id != 16261) {
                                if (id == 16262) {
                                    definition.modelCustomColor4 = 81;
                                }
                            } else {
                                definition.modelCustomColor4 = 296438;
                            }
                        } else {
                            definition.modelCustomColor3 = 127752;
                        }
                    } else {
                        definition.modelCustomColor4 = 88881;
                    }
                } else {
                    definition.modelCustomColor4 = 19667;
                }
                definition.name = "Dragon mace tier 5";
                ItemDefinition.copyInventory(definition, DRAGON_MACE);
                ItemDefinition.copyEquipment(definition, DRAGON_MACE);
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                break;

            case 16277:
                definition.name = "Staff of light tier 1";
                ItemDefinition.copyInventory(definition, STAFF_OF_LIGHT);
                ItemDefinition.copyEquipment(definition, STAFF_OF_LIGHT);
                definition.recolorFrom = new int[]{6864, 49, -27207, 6854, 103, 57, 4762, 78, 94};
                definition.recolorTo = new int[]{491770, 491770, 491770, 491770, 491770, 491770, 491770, 491770, 491770};
                break;

            case 16269:
                definition.name = "Staff of light tier 2";
                ItemDefinition.copyInventory(definition, STAFF_OF_LIGHT);
                ItemDefinition.copyEquipment(definition, STAFF_OF_LIGHT);
                definition.recolorFrom = new int[]{6864, 49, -27207, 6854, 103, 57, 4762, 78, 94};
                definition.recolorTo = new int[]{38357, 38341, 38365, 38349, 38333, 38337, 38353, 38345, 38350};
                break;

            case 16270:
                definition.name = "Staff of light tier 3";
                ItemDefinition.copyInventory(definition, STAFF_OF_LIGHT);
                ItemDefinition.copyEquipment(definition, STAFF_OF_LIGHT);
                definition.recolorFrom = new int[]{6864, 49, -27207, 6854, 103, 57, 4762, 78, 94};
                definition.recolorTo = new int[]{588, 572, 596, 580, 564, 568, 584, 576, 578};
                break;

            case 16271:
                definition.name = "Staff of light tier 4";
                ItemDefinition.copyInventory(definition, STAFF_OF_LIGHT);
                ItemDefinition.copyEquipment(definition, STAFF_OF_LIGHT);
                definition.recolorFrom = new int[]{6864, 49, -27207, 6854, 103, 57, 4762, 78, 94};
                definition.recolorTo = new int[]{22872, 22856, 22880, 22864, 22848, 22852, 22868, 22860, 22864};
                break;

            case 16272:
            case 16273:
            case 16274:
            case 16275:
            case 16276:
                definition.name = "Staff of light tier 5";
                ItemDefinition.copyInventory(definition, STAFF_OF_LIGHT);
                ItemDefinition.copyEquipment(definition, STAFF_OF_LIGHT);
                if (id != 16272) {
                    if (id != 16273) {
                        if (id != 16274) {
                            if (id != 16275) {
                                if (id == 16276) {
                                    definition.recolorFrom = new int[]{6864, 49, -27207, 6854, 103, 57, 4762, 78, 94};
                                    definition.recolorTo = new int[]{22872, 22856, 22880, 22864, 22848, 22852, 22868, 22860, 22864};
                                }
                            } else {
                                definition.recolorTo = new int[]{588, 572, 596, 580, 564, 568, 584, 576, 578};
                                definition.recolorFrom = new int[]{6864, 49, -27207, 6854, 103, 57, 4762, 78, 94};
                            }
                        } else {
                            definition.recolorTo = new int[]{38357, 38341, 38365, 38349, 38333, 38337, 38353, 38345, 38350};
                            definition.recolorFrom = new int[]{6864, 49, -27207, 6854, 103, 57, 4762, 78, 94};
                        }
                    } else {
                        definition.recolorFrom = new int[]{6864, 49, -27207, 6854, 103, 57, 4762, 78, 94};
                        definition.recolorTo = new int[]{491770, 491770, 491770, 491770, 491770, 491770, 491770, 491770, 491770};
                    }
                } else {
                    definition.recolorTo = new int[]{374770, 374770, 374770, 374770, 374770, 374770, 374770, 374770, 374770};
                    definition.recolorFrom = new int[]{6864, 49, -27207, 6854, 103, 57, 4762, 78, 94};
                }
                definition.inventoryActions = new String[]{null, "Wield", null, "Change-Color", "Destroy"};
                break;

            case 12765:
                definition.name = "Dark bow tier 1";
                break;

            case 12766:
                definition.name = "Dark bow tier 2";
                break;

            case 12767:
                definition.name = "Dark bow tier 3";
                break;

            case 12768:
                definition.name = "Dark bow tier 4";
                break;

            case 15706:
            case 15707:
            case 15708:
            case 15709:
            case 15710:
                definition.name = "Dark bow tier 5";
                definition.inventoryActions = new String[]{null, "Wield", null, null, "Destroy"};
                ItemDefinition.copyInventory(definition, DARK_BOW);
                ItemDefinition.copyEquipment(definition, DARK_BOW);
                if (id != 15706) {
                    if (id != 15707) {
                        if (id != 15708) {
                            if (id != 15709) {
                                if (id == 15710) {
                                    definition.recolorFrom = new int[]{1571, 1575, 1436, 2454, 2576};
                                    definition.recolorTo = new int[]{10417, 18983, 24988, 23958, 27144};
                                }
                            } else {
                                definition.recolorFrom = new int[]{1571, 1575, 1436, 2454, 2576};
                                definition.recolorTo = new int[]{-30173, -28121, -29284, -27116, -24048};
                            }
                        } else {
                            definition.recolorFrom = new int[]{1571, 1575, 1436, 2454, 2576};
                            definition.recolorTo = new int[]{10834, 10586, 12700, 8724, 5648};
                        }
                    } else {
                        definition.recolorFrom = new int[]{1571, 1575, 1436, 2454, 2576};
                        definition.recolorTo = new int[]{103, 90, 10324, 61, 28};
                    }
                } else {
                    definition.recolorFrom = new int[10];
                    definition.recolorTo = new int[10];
                    definition.inventoryModel = 26386;
                    definition.maleModel = 26279;
                    definition.femaleModel = 26279;
                    definition.recolorFrom[0] = 2576;
                    definition.recolorFrom[1] = 2454;
                    definition.recolorFrom[2] = 1571;
                    definition.recolorFrom[3] = 1575;
                    definition.recolorFrom[4] = 6808;
                    definition.recolorFrom[5] = 1436;
                    definition.recolorFrom[6] = 10417;
                    definition.recolorFrom[7] = 3974;
                    definition.recolorFrom[8] = 3594;
                    definition.recolorFrom[9] = 0;
                    definition.recolorTo[0] = 374500;
                    definition.recolorTo[1] = 374500;
                    definition.recolorTo[2] = 374770;
                    definition.recolorTo[3] = 374770;
                    definition.recolorTo[4] = 374770;
                    definition.recolorTo[5] = 374770;
                    definition.recolorTo[6] = 374450;
                    definition.recolorTo[7] = 374450;
                    definition.recolorTo[8] = 374450;
                    definition.recolorTo[9] = 374500;
                }
                break;
        }
    }
}
