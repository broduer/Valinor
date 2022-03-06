package com.valinor.cache.anim.osrs;

import com.valinor.cache.anim.Sequence;
import com.valinor.cache.anim.SpotAnimation;

import static com.valinor.cache.anim.SpotAnimation.cache;

/**
 * @author Patrick van Elderen | May, 27, 2021, 21:58
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class OSRSGraphics {

    public static void unpack(int graphic) {

        if(graphic == 5000) {
            cache[graphic] = new SpotAnimation();
            cache[graphic].id = graphic;
            cache[graphic].animation_id = 6960;
            cache[graphic].seq = Sequence.cache[6960];
            cache[graphic].model_id = 58928;
            cache[graphic].model_scale_x = 180;
            cache[graphic].model_scale_y = 180;
        }

        if (graphic == 5001) {
            cache[graphic] = new SpotAnimation();
            cache[graphic].id = graphic;
            cache[graphic].animation_id = 465;
            cache[graphic].seq = Sequence.cache[465];
            cache[graphic].model_id = 58925;
            cache[graphic].model_scale_x = 80;
            cache[graphic].model_scale_y = 80;
        }

        if(graphic == 5002) {
            cache[graphic] = new SpotAnimation();
            cache[graphic].id = graphic;
            cache[graphic].animation_id = 7080;
            cache[graphic].seq = Sequence.cache[7080];
            cache[graphic].model_id = 58926;
        }

        if (graphic == 5004) {
            cache[graphic] = new SpotAnimation();
            cache[graphic].id = graphic;
            cache[graphic].animation_id = 8287;
            cache[graphic].seq = Sequence.cache[8287];
            cache[graphic].model_id = 58929;
        }

        if (graphic == 5005) {
            cache[graphic] = new SpotAnimation();
            cache[graphic].id = graphic;
            cache[graphic].animation_id = 5358;
            cache[graphic].seq = Sequence.cache[5358];
            cache[graphic].model_id = 58930;
        }

        if (graphic == 5006) {
            cache[graphic] = new SpotAnimation();
            cache[graphic].id = graphic;
            cache[graphic].model_id = 3479;
            cache[graphic].animation_id = 1061;
            cache[graphic].seq = Sequence.cache[1061];
            cache[graphic].ambient = 50;
            cache[graphic].contrast = 50;
            cache[graphic].src_color = new int[]{960};
            cache[graphic].dst_color = new int[]{-12535};
        }
        if (graphic == 5007) {
            cache[graphic] = new SpotAnimation();
            cache[graphic].id = graphic;
            cache[graphic].ambient = 100;
            cache[graphic].animation_id = 8545;
            cache[graphic].seq = Sequence.cache[8545];
            cache[graphic].contrast = 100;
            cache[graphic].model_id = 39171;
            cache[graphic].src_color = new int[]{7101, 0};
            cache[graphic].dst_color = new int[]{-12535, 0};
        }
        if (graphic == 5008) {
            cache[graphic] = new SpotAnimation();
            cache[graphic].id = graphic;
            cache[graphic].ambient = 100;
            cache[graphic].animation_id = 8546;
            cache[graphic].seq = Sequence.cache[8546];
            cache[graphic].contrast = 100;
            cache[graphic].model_id = 39168;
            cache[graphic].src_color = new int[]{7101, 0, 8146, 7104, 5056};
            cache[graphic].dst_color = new int[]{-12535, 0, -12535, -12535, -12535};
        }
    }
}
