package com.valinor.entity;

import com.valinor.Client;
import com.valinor.cache.anim.Animation;
import com.valinor.cache.anim.Sequence;
import com.valinor.cache.anim.SpotAnimation;
import com.valinor.cache.def.ItemDefinition;
import com.valinor.cache.def.NpcDefinition;
import com.valinor.collection.TempCache;
import com.valinor.entity.model.IdentityKit;
import com.valinor.entity.model.Model;
import com.valinor.io.Buffer;

public final class Player extends Entity {

    public Model get_rotated_model() {
        if(!visible)
            return null;

        Model player = get_animated_model();
        if(player == null)
            return null;

        super.height = player.model_height;
        player.within_tile = true;
        if(reference_pose)
            return player;

        if(super.graphic_id != -1 && super.current_animation_id != -1) {
            SpotAnimation anim = SpotAnimation.cache[super.graphic_id];
            Model model = anim.get_model();
            /**
             * MAKE SURE WE'VE LOADED THE GRAPHIC BEFORE ATTEMPTING TO DO IT.
             * Fixes graphics flickering.
             */
            if (Animation.animationlist[anim.seq.primaryFrames[0] >> 16].length == 0) {
                model = null;
            }
            if(model != null) {
                Model graphic = new Model(false, Animation.noAnimationInProgress(super.current_animation_id), false, model);
                graphic.translate(0, -super.graphic_height, 0);
                graphic.skin();
                graphic.applyTransform(anim.seq.primaryFrames[super.current_animation_id]);
                graphic.face_skin = null;
                graphic.vertex_skin = null;
                if(anim.model_scale_x != 128 || anim.model_scale_y != 128)
                    graphic.scale(anim.model_scale_x, anim.model_scale_x, anim.model_scale_y);

                graphic.light(64 + anim.ambient, 850 + anim.contrast, -30, -50, -30, true, true);
                Model[] merged = {
                    player, graphic
                };
                player = new Model(merged);
            } else {
                return null;
            }
        }
        if(transformed_model != null) {
            if(Client.game_tick >= transform_duration)
                transformed_model = null;

            if(Client.game_tick >= transform_delay && Client.game_tick < transform_duration) {
                Model model = transformed_model;
                model.translate(x_offset - super.world_x, z_offset - height, y_offset - super.world_y);
                if(super.turn_direction == 512) {
                    model.rotate_90();
                    model.rotate_90();
                    model.rotate_90();
                } else
                if(super.turn_direction == 1024) {
                    model.rotate_90();
                    model.rotate_90();
                } else
                if(super.turn_direction == 1536)
                    model.rotate_90();

                Model[] merged = {
                    player, model
                };
                player = new Model(merged);
                if(super.turn_direction == 512)
                    model.rotate_90();
                else
                if(super.turn_direction == 1024) {
                    model.rotate_90();
                    model.rotate_90();
                } else
                if(super.turn_direction == 1536) {
                    model.rotate_90();
                    model.rotate_90();
                    model.rotate_90();
                }
                model.translate(super.world_x - x_offset, height - z_offset, super.world_y - y_offset);
            }
        }
        player.within_tile = true;
        return player;
    }

    public void update(Buffer buffer) {
        buffer.pos = 0;
        title = buffer.readString();
        titleColor = buffer.readString();
        gender = buffer.readUByte();
        overhead_icon = buffer.readUByte();
        skull_icon = buffer.readUByte();
        hint_arrow_icon = buffer.readUByte();
        desc = null;
        team_id = 0;
        for (int bodyPart = 0; bodyPart < 12; bodyPart++) {
            int reset = buffer.readUByte();
            if (reset == 0) {
                player_appearance[bodyPart] = 0;
                continue;
            }

            int id = buffer.readUByte();
            this.player_appearance[bodyPart] = (reset << 8) + id;

            if (bodyPart == 0 && this.player_appearance[0] == 65535) {
                desc = NpcDefinition.get(buffer.readUShort());
                break;
            }
            
            if (this.player_appearance[bodyPart] >= 512 && this.player_appearance[bodyPart] - 512 < ItemDefinition.length) {
                int team_cape = ItemDefinition.get(this.player_appearance[bodyPart] - 512).team;
                if (team_cape != 0) {
                    this.team_id = team_cape;
                }
            }
        }

        for (int index = 0; index < 5; index++) {
            int color = buffer.readUByte();
            if (color < 0 || color >= Client.APPEARANCE_COLORS[index].length) {
                color = 0;
            }
            appearance_colors[index] = color;
        }

        super.idle_animation_id = buffer.readUShort();
        if (super.idle_animation_id == 65535) {
            super.idle_animation_id = -1;
        }

        super.standing_turn_animation_id = buffer.readUShort();
        if (super.standing_turn_animation_id == 65535) {
            super.standing_turn_animation_id = -1;
        }

        super.walk_animation_id = buffer.readUShort();
        if (super.walk_animation_id == 65535) {
            super.walk_animation_id = -1;
        }

        super.turn_around_animation_id = buffer.readUShort();
        if (super.turn_around_animation_id == 65535) {
            super.turn_around_animation_id = -1;
        }

        super.pivot_right_animation_id = buffer.readUShort();
        if (super.pivot_right_animation_id == 65535) {
            super.pivot_right_animation_id = -1;
        }

        super.pivot_left_animation_id = buffer.readUShort();
        if (super.pivot_left_animation_id == 65535) {
            super.pivot_left_animation_id = -1;
        }

        super.running_animation_id = buffer.readUShort();
        if (super.running_animation_id == 65535) {
            super.running_animation_id = -1;
        }

        username = buffer.readString();
        combat_level = buffer.readUByte();
        rights = buffer.readUByte();
        donatorRights = buffer.readUByte();

        visible = true;
        appearance_offset = 0L;

        for (int index = 0; index < 12; index++) {
            appearance_offset <<= 4;
            if (player_appearance[index] >= 256) {
                appearance_offset += player_appearance[index] - 256;
            }
        }

        if (player_appearance[0] >= 256) {
            appearance_offset += player_appearance[0] - 256 >> 4;
        }

        if (player_appearance[1] >= 256) {
            appearance_offset += player_appearance[1] - 256 >> 8;
        }

        for (int index = 0; index < 5; index++) {
            appearance_offset <<= 3;
            appearance_offset += appearance_colors[index];
        }

        appearance_offset <<= 1;
        appearance_offset += gender;
    }

    public Model get_animated_model() {
        if (desc != null) {
            int currentFrame = -1;
            int nextFrame = -1;
            int cycle1 = 0;
            int cycle2 = 0;
            if (super.animation >= 0 && super.animation_delay == 0) {
                Sequence seq = Sequence.cache[super.animation];
                currentFrame = seq.primaryFrames[super.current_animation_frame];
                if (Client.singleton.setting.enableTweening && super.next_animation_frame != -1) {
                    nextFrame = seq.primaryFrames[super.next_animation_frame];
                    cycle1 = seq.durations[super.current_animation_frame];
                    cycle2 = super.current_animation_duration;
                }
            } else if (super.queued_animation_id >= 0) {
                Sequence seq = Sequence.cache[super.queued_animation_id];
                currentFrame = seq.primaryFrames[super.queued_animation_frame];
                if (Client.singleton.setting.enableTweening && super.next_idle_frame != -1) {
                    nextFrame = seq.primaryFrames[super.next_idle_frame];
                    cycle1 = seq.durations[super.queued_animation_frame];
                    cycle2 = super.queued_animation_duration;
                }
            }
            Model model = desc.method164(-1, currentFrame, null, nextFrame, cycle1, cycle2);
            return model;
        }

        long l = appearance_offset;
        int currentFrame = -1;
        int nextFrame = -1;
        int cycle1 = 0;
        int cycle2 = 0;
        int i1 = -1;
        int j1 = -1;
        int k1 = -1;
        if (super.animation >= 0 && super.animation_delay == 0) {
            Sequence seq = Sequence.cache[super.animation];
            currentFrame = seq.primaryFrames[super.current_animation_frame];
            if (Client.singleton.setting.enableTweening && super.next_animation_frame != -1) {
                nextFrame = seq.primaryFrames[super.next_animation_frame];
                cycle1 = seq.durations[super.current_animation_frame];
                cycle2 = super.current_animation_duration;
            }
            if (super.queued_animation_id >= 0 && super.queued_animation_id != super.idle_animation_id)
                i1 = Sequence.cache[super.queued_animation_id].primaryFrames[super.queued_animation_frame];
            if (seq.playerOffhand >= 0) {
                j1 = seq.playerOffhand;
                l += j1 - player_appearance[5] << 40;
            }
            if (seq.playerMainhand >= 0) {
                k1 = seq.playerMainhand;
                l += k1 - player_appearance[3] << 48;
            }
        } else if (super.queued_animation_id >= 0) {
            Sequence sequence = Sequence.cache[super.queued_animation_id];
            currentFrame = sequence.primaryFrames[super.queued_animation_frame];

            /** DISABLED BECAUSE IT CAUSES FLICKERING WITH SOME GFXS **/
			/*if (Configuration.enableTweening && super.nextidle_animation_idFrame != -1) {
				nextFrame = animation.primaryFrames[super.nextidle_animation_idFrame];
				cycle1 = animation.durations[super.queued_animation_frame];
				cycle2 = super.anInt1519;
			}*/
        }

        Model model = (Model) model_cache.get(l);
        if(model == null) {
            boolean cached = false;
            for(int index = 0; index < 12; index++) {
                int appearance = player_appearance[index];
                if(k1 >= 0 && index == 3)
                    appearance = k1;

                if(j1 >= 0 && index == 5)
                    appearance = j1;

                if(appearance >= 256 && appearance < 512 && !IdentityKit.cache[appearance - 256].body_cached())
                    cached = true;

                if(appearance >= 512 && !ItemDefinition.get(appearance - 512).equipped_model_cached(gender))
                    cached = true;

            }
            if(cached) {
                if(key != -1L)
                    model = (Model) model_cache.get(key);

                if(model == null)
                    return null;
            }
        }
        if(model == null) {
            Model[] character = new Model[12];
            int equipped = 0;
            for(int index = 0; index < 12; index++) {
                int appearance = player_appearance[index];
                if(k1 >= 0 && index == 3)
                    appearance = k1;

                if(j1 >= 0 && index == 5)
                    appearance = j1;

                if(appearance >= 256 && appearance < 512) {
                    Model idk = IdentityKit.cache[appearance - 256].get_body();
                    if(idk != null)
                        character[equipped++] = idk;

                }
                if(appearance >= 512) {
                    Model items = ItemDefinition.get(appearance - 512).get_equipped_model(gender);
                    if(items != null)
                        character[equipped++] = items;

                }
            }
            model = new Model(equipped, character, true);
            for(int index = 0; index < 5; index++) {
                if(appearance_colors[index] != 0) {
                    model.recolor(Client.APPEARANCE_COLORS[index][0], Client.APPEARANCE_COLORS[index][appearance_colors[index]]);
                    if(index == 1)
                        model.recolor(Client.SHIRT_SECONDARY_COLORS[0], Client.SHIRT_SECONDARY_COLORS[appearance_colors[index]]);

                }
            }
            model.skin();
            model.light(64, 850, -30, -50, -30, true, true);
            model_cache.put(model, l);
            key = l;
        }
        if(reference_pose) {
            return model;
        }
        Model animated = Model.EMPTY_MODEL;
        animated.replace(model, Animation.noAnimationInProgress(currentFrame) & Animation.noAnimationInProgress(animation));
        if (currentFrame != -1 && i1 != -1) {
            animated.applyAnimationFrames(Sequence.cache[super.animation].interleaveOrder, i1, currentFrame);
        } else if(currentFrame != -1) {
            animated.applyAnimationFrame(currentFrame, nextFrame, cycle1, cycle2);
        }

        animated.calc_diagonals();
        animated.face_skin = null;
        animated.vertex_skin = null;
        return animated;
    }

    public Model get_dialogue_model() {
        if (!visible)
            return null;

        if (desc != null)
            return desc.get_dialogue_model();

        boolean cached = false;
        for (int index = 0; index < 12; index++) {
            int appearance = player_appearance[index];
            if (appearance >= 256 && appearance < 512 && !IdentityKit.cache[appearance - 256].headLoaded())
                cached = true;

            if (appearance >= 512 && !ItemDefinition.get(appearance - 512).dialogue_model_cached(gender))
                cached = true;

        }
        if (cached)
            return null;

        Model[] character = new Model[12];
        int equipped = 0;
        for (int index = 0; index < 12; index++) {
            int appearance = player_appearance[index];
            if (appearance >= 256 && appearance < 512) {
                Model idk = IdentityKit.cache[appearance - 256].get_head();
                if (idk != null)
                    character[equipped++] = idk;

            }
            if (appearance >= 512) {
                Model items = ItemDefinition.get(appearance - 512).get_equipped_dialogue_model(gender);
                if (items != null)
                    character[equipped++] = items;

            }
        }
        Model model = new Model(equipped, character, true);
        for (int index = 0; index < 5; index++)
            if (appearance_colors[index] != 0) {
                model.recolor(Client.APPEARANCE_COLORS[index][0], Client.APPEARANCE_COLORS[index][appearance_colors[index]]);
                if (index == 1)
                    model.recolor(Client.SHIRT_SECONDARY_COLORS[0], Client.SHIRT_SECONDARY_COLORS[appearance_colors[index]]);
            }

        return model;
    }

    public boolean visible() {
        return visible;
    }

    public Player() {
        key = -1L;
        reference_pose = false;
        appearance_colors = new int[5];
        visible = false;
        player_appearance = new int[12];
    }

    public int rights, donatorRights;
    private long key;
    public NpcDefinition desc;
    public boolean reference_pose;
    public final int[] appearance_colors;
    public int team_id;
    private int gender;
    public String username;
    public static TempCache model_cache = new TempCache(260);
    public int combat_level;
    public int overhead_icon;
    public int skull_icon;
    public int hint_arrow_icon;
    public int transform_delay;
    public int transform_duration;
    public int height;
    public boolean visible;
    public int x_offset;
    public int z_offset;
    public int y_offset;
    public Model transformed_model;
    public final int[] player_appearance;
    private long appearance_offset;
    public int transform_width;
    public int transform_height;
    public int transform_width_offset;
    public int transform_height_offset;
    public int skill_level;
    public String title = "";
    public String titleColor = "";

    /**
     * Gets the players title
     * @return title
     */
    public String getTitle(boolean rightClick) {
        if (title.length() > 0) {
            if (rightClick) {
                return titleColor + title + " <col=ffffff>";
            } else {
                return titleColor + title + " <col=0>";
            }
        } else {
            return "";
        }
    }

}
