package com.valinor.entity.model;

import com.valinor.cache.anim.Animation;
import com.valinor.cache.anim.Skins;
import com.valinor.draw.Rasterizer2D;
import com.valinor.draw.Rasterizer3D;
import com.valinor.entity.Renderable;
import com.valinor.io.Buffer;
import com.valinor.net.requester.Provider;
import com.valinor.scene.SceneGraph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Model extends Renderable {

    public static int anInt1620;
    public static Model EMPTY_MODEL = new Model(true);
    public static boolean obj_exists; //obj_exists
    public static int anInt1685;
    public static int anInt1686;
    public static int anInt1687;
    public static long anIntArray1688[] = new long[1000];
    public static int SINE[];
    public static int COSINE[];
    static ModelHeader aClass21Array1661[];
    static Provider resourceProvider;
    static boolean hasAnEdgeToRestrict[] = new boolean[6500];
    static boolean outOfReach[] = new boolean[6500];
    static int projected_vertex_x[] = new int[6500];
    static int projected_vertex_y[] = new int[6500];
    static int projected_vertex_z[] = new int[6500];
    static int camera_vertex_x[] = new int[6500];
    static int camera_vertex_y[] = new int[6500];
    static int camera_vertex_z[] = new int[6500];
    static int depthListIndices[] = new int[1600];
    static int faceLists[][] = new int[1600][512];
    static int anIntArray1673[] = new int[12];
    static int anIntArrayArray1674[][] = new int[12][2000];
    static int anIntArray1675[] = new int[2000];
    static int anIntArray1676[] = new int[2000];
    static int anIntArray1677[] = new int[12];
    static int anIntArray1678[] = new int[10];
    static int anIntArray1679[] = new int[10];
    static int anIntArray1680[] = new int[10];
    static int xAnimOffset;
    static int yAnimOffset;
    static int zAnimOffset;
    static int modelIntArray3[];
    static int modelIntArray4[];
    private static int anIntArray1622[] = new int[6500];
    private static int anIntArray1623[] = new int[6500];
    private static int anIntArray1624[] = new int[6500];
    private static int anIntArray1625[] = new int[6500];

    static {
        SINE = Rasterizer3D.SINE;
        COSINE = Rasterizer3D.COSINE;
        modelIntArray3 = Rasterizer3D.HSL_TO_RGB;
        modelIntArray4 = Rasterizer3D.anIntArray1469;
    }

    public short[] face_material;
    public byte[] face_texture;
    public byte[] texture_map;
    public int vertices;
    public int[] vertex_x;
    public int[] vertex_y;
    public int[] vertex_z;
    public int faces;
    public int[] triangle_edge_a;
    public int[] triangle_edge_b;
    public int[] triangle_edge_c;
    public int[] faceHslA;
    public int[] faceHslB;
    public int[] faceHslC;
    public int[] render_type;
    public byte[] face_render_priorities;
    public int[] face_alpha;
    public short[] face_color;
    public byte face_priority = 0;
    public int texture_faces;
    public short[] triangle_texture_edge_a;
    public short[] triangle_texture_edge_b;
    public short[] triangle_texture_edge_c;
    public int min_x;
    public int max_x;
    public int max_z;
    public int min_z;
    public int diagonal_2D;
    public int max_y;
    public int scene_depth;
    public int diagonal_3D;
    public int obj_height;
    public int[] bone_skin;
    public int[] muscle_skin;
    public int[][] vertex_skin;
    public int[][] face_skin;
    public boolean within_tile;
    public Vertex[] gouraud_vertex;
    private boolean aBoolean1618;
    public int[][] animayaGroups;
    public int[][] animayaScales;

    private static final Set<Integer> repeatedTextureModels = new HashSet<>();

    static {
        int[] array = {55555, 55556, 55557, 55558, 55559, 55560, 55561, 55562, 55563, 55564, 55565, 55566, 55567, 55568, 55569, 55570, 55571, 55572, 55573, 55574, 55575, 55576,
            55577, 55578, 55579, 55580, 55581, 55582, 55583, 55584, 55585, 55586, 55587, 55588, 55589, 55590, 55591, 55592, 55593, 55594, 55595, 55596, 55597, 55598, 55599, 55600,
            55601, 55602, 55603, 55604, 55605, 55606, 55608, 55609, 55610, 55611, 55612, 55613, 55614, 55615, 55616, 55617, 55618, 55619, 55620, 55621, 55622};
        for (int id : array) {
            addAll(id);
        }
    }

    private static void addAll(int... values) {
        for (int value : values) {
            repeatedTextureModels.add(value);
        }
    }

    public Model(int modelId) {
        byte[] is = aClass21Array1661[modelId].data;
        if (is[is.length - 1] == -3 && is[is.length - 2] == -1) {
            decodeType3(is);
        } else if (is[is.length - 1] == -2 && is[is.length - 2] == -1) {
            decodeType2(is);
        } else if (is[is.length - 1] == -1 && is[is.length - 2] == -1) {
            decodeType1(is);
        } else {
            decodeOldFormat(is);
        }

        repeatTexture = new boolean[faces];

        if (repeatedTextureModels.contains(modelId)) {
            Arrays.fill(repeatTexture, true);
        }
    }

    public boolean[] repeatTexture;

    private Model(boolean flag) {
        aBoolean1618 = true;
        within_tile = false;
        if (!flag)
            aBoolean1618 = !aBoolean1618;
    }

    public Model(int length, Model model_segments[], boolean preset) {
        try {
            aBoolean1618 = true;
            within_tile = false;
            anInt1620++;
            boolean render_type_flag = false;
            boolean priority_flag = false;
            boolean alpha_flag = false;
            boolean muscle_skin_flag = false;
            boolean color_flag = false;
            boolean texture_flag = false;
            boolean coordinate_flag = false;
            vertices = 0;
            faces = 0;
            texture_faces = 0;
            face_priority = -1;
            Model build;
            for (int segment_index = 0; segment_index < length; segment_index++) {
                build = model_segments[segment_index];
                if (build != null) {
                    vertices += build.vertices;
                    faces += build.faces;
                    texture_faces += build.texture_faces;
                    if (build.face_render_priorities != null) {
                        priority_flag = true;
                    } else {
                        if (face_priority == -1)
                            face_priority = build.face_priority;

                        if (face_priority != build.face_priority)
                            priority_flag = true;
                    }
                    render_type_flag |= build.render_type != null;
                    alpha_flag |= build.face_alpha != null;
                    muscle_skin_flag |= build.muscle_skin != null;
                    color_flag |= build.face_color != null;
                    texture_flag |= build.face_material != null;
                    coordinate_flag |= build.face_texture != null;
                }
            }
            vertex_x = new int[vertices];
            vertex_y = new int[vertices];
            vertex_z = new int[vertices];
            bone_skin = new int[vertices];
            triangle_edge_a = new int[faces];
            triangle_edge_b = new int[faces];
            triangle_edge_c = new int[faces];
            repeatTexture = new boolean[faces];
            if (color_flag) {
                face_color = new short[faces];
                repeatTexture = new boolean[faces];
            }

            if (render_type_flag)
                render_type = new int[faces];

            if (priority_flag)
                face_render_priorities = new byte[faces];

            if (alpha_flag)
                face_alpha = new int[faces];

            if (muscle_skin_flag)
                muscle_skin = new int[faces];

            if (texture_flag)
                face_material = new short[faces];

            if (coordinate_flag)
                face_texture = new byte[faces];

            if (texture_faces > 0) {
                texture_map = new byte[texture_faces];
                triangle_texture_edge_a = new short[texture_faces];
                triangle_texture_edge_b = new short[texture_faces];
                triangle_texture_edge_c = new short[texture_faces];
            }

            vertices = 0;
            faces = 0;
            texture_faces = 0;
            for (int segment_index = 0; segment_index < length; segment_index++) {
                build = model_segments[segment_index];
                if (build != null) {
                    for (int face = 0; face < build.faces; face++) {
                        if (render_type_flag && build.render_type != null)
                            render_type[faces] = build.render_type[face];

                        if (priority_flag)
                            if (build.face_render_priorities == null)
                                face_render_priorities[faces] = build.face_priority;
                            else
                                face_render_priorities[faces] = build.face_render_priorities[face];

                        if (alpha_flag && build.face_alpha != null)
                            face_alpha[faces] = build.face_alpha[face];

                        if (muscle_skin_flag && build.muscle_skin != null)
                            muscle_skin[faces] = build.muscle_skin[face];

                        if (texture_flag) {
                            if (build.face_material != null)
                                face_material[faces] = build.face_material[face];
                            else
                                face_material[faces] = -1;
                        }
                        if (coordinate_flag) {
                            if (build.face_texture != null && build.face_texture[face] != -1) {
                                face_texture[faces] = (byte) (build.face_texture[face] + texture_faces);
                            } else {
                                face_texture[faces] = -1;
                            }
                        }

                        if (color_flag && build.face_color != null)
                            face_color[faces] = build.face_color[face];
                        repeatTexture[faces] = build.repeatTexture[face];

                        triangle_edge_a[faces] = method465(build, build.triangle_edge_a[face]);
                        triangle_edge_b[faces] = method465(build, build.triangle_edge_b[face]);
                        triangle_edge_c[faces] = method465(build, build.triangle_edge_c[face]);
                        faces++;
                    }
                    for (int texture_edge = 0; texture_edge < build.texture_faces; texture_edge++) {
                        byte opcode = texture_map[texture_faces] = build.texture_map[texture_edge];
                        if (opcode == 0) {
                            triangle_texture_edge_a[texture_faces] = (short) method465(build, build.triangle_texture_edge_a[texture_edge]);
                            triangle_texture_edge_b[texture_faces] = (short) method465(build, build.triangle_texture_edge_b[texture_edge]);
                            triangle_texture_edge_c[texture_faces] = (short) method465(build, build.triangle_texture_edge_c[texture_edge]);
                        }
                        if (opcode >= 1 && opcode <= 3) {
                            triangle_texture_edge_a[texture_faces] = build.triangle_texture_edge_a[texture_edge];
                            triangle_texture_edge_b[texture_faces] = build.triangle_texture_edge_b[texture_edge];
                            triangle_texture_edge_c[texture_faces] = build.triangle_texture_edge_c[texture_edge];
                        }
                        if (opcode == 2) {

                        }
                        texture_faces++;
                    }
                    if (!preset) //for models that don't have preset textured_faces
                        texture_faces++;

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Model(Model amodel[]) {
        int i = 2;
        aBoolean1618 = true;
        within_tile = false;
        anInt1620++;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        boolean texture_flag = false;
        boolean coordinate_flag = false;
        vertices = 0;
        faces = 0;
        texture_faces = 0;
        face_priority = -1;
        for (int k = 0; k < i; k++) {
            Model model = amodel[k];
            if (model != null) {
                vertices += model.vertices;
                faces += model.faces;
                texture_faces += model.texture_faces;
                flag1 |= model.render_type != null;
                if (model.face_render_priorities != null) {
                    flag2 = true;
                } else {
                    if (face_priority == -1)
                        face_priority = model.face_priority;
                    if (face_priority != model.face_priority)
                        flag2 = true;
                }
                flag3 |= model.face_alpha != null;
                flag4 |= model.face_color != null;
                texture_flag |= model.face_material != null;
                coordinate_flag |= model.face_texture != null;
            }
        }

        vertex_x = new int[vertices];
        vertex_y = new int[vertices];
        vertex_z = new int[vertices];
        triangle_edge_a = new int[faces];
        triangle_edge_b = new int[faces];
        triangle_edge_c = new int[faces];
        faceHslA = new int[faces];
        faceHslB = new int[faces];
        faceHslC = new int[faces];
        triangle_texture_edge_a = new short[texture_faces];
        triangle_texture_edge_b = new short[texture_faces];
        triangle_texture_edge_c = new short[texture_faces];
        if (flag1)
            render_type = new int[faces];
        if (flag2)
            face_render_priorities = new byte[faces];
        if (flag3)
            face_alpha = new int[faces];
        if (flag4) {
            face_color = new short[faces];
            repeatTexture = new boolean[faces];
        }
        if (texture_flag)
            face_material = new short[faces];

        if (coordinate_flag)
            face_texture = new byte[faces];
        vertices = 0;
        faces = 0;
        texture_faces = 0;
        int i1 = 0;
        for (int j1 = 0; j1 < i; j1++) {
            Model model_1 = amodel[j1];
            if (model_1 != null) {
                int k1 = vertices;
                for (int l1 = 0; l1 < model_1.vertices; l1++) {
                    int x = model_1.vertex_x[l1];
                    int y = model_1.vertex_y[l1];
                    int z = model_1.vertex_z[l1];
                    vertex_x[vertices] = x;
                    vertex_y[vertices] = y;
                    vertex_z[vertices] = z;
                    ++vertices;
                }

                for (int uid = 0; uid < model_1.faces; uid++) {
                    triangle_edge_a[faces] = model_1.triangle_edge_a[uid] + k1;
                    triangle_edge_b[faces] = model_1.triangle_edge_b[uid] + k1;
                    triangle_edge_c[faces] = model_1.triangle_edge_c[uid] + k1;
                    faceHslA[faces] = model_1.faceHslA[uid];
                    faceHslB[faces] = model_1.faceHslB[uid];
                    faceHslC[faces] = model_1.faceHslC[uid];
                    if (flag1)
                        if (model_1.render_type == null) {
                            render_type[faces] = 0;
                        } else {
                            int j2 = model_1.render_type[uid];
                            if ((j2 & 2) == 2)
                                j2 += i1 << 2;
                            render_type[faces] = j2;
                        }
                    if (flag2)
                        if (model_1.face_render_priorities == null)
                            face_render_priorities[faces] = model_1.face_priority;
                        else
                            face_render_priorities[faces] = model_1.face_render_priorities[uid];
                    if (flag3) {
                        if (model_1.face_alpha == null)
                            face_alpha[faces] = 0;
                        else
                            face_alpha[faces] = model_1.face_alpha[uid];

                    }
                    if (flag4 && model_1.face_color != null) {
                        face_color[faces] = model_1.face_color[uid];
                        repeatTexture[faces] = model_1.repeatTexture[uid];
                    }

                    if (texture_flag) {
                        if (model_1.face_material != null) {
                            face_material[faces] = model_1.face_material[faces];
                        } else {
                            face_material[faces] = -1;
                        }
                    }

                    if (coordinate_flag) {
                        if (model_1.face_texture != null && model_1.face_texture[faces] != -1)
                            face_texture[faces] = (byte) (model_1.face_texture[faces] + texture_faces);
                        else
                            face_texture[faces] = -1;

                    }

                    faces++;
                }

                for (int k2 = 0; k2 < model_1.texture_faces; k2++) {
                    triangle_texture_edge_a[texture_faces] = (short) (model_1.triangle_texture_edge_a[k2] + k1);
                    triangle_texture_edge_b[texture_faces] = (short) (model_1.triangle_texture_edge_b[k2] + k1);
                    triangle_texture_edge_c[texture_faces] = (short) (model_1.triangle_texture_edge_c[k2] + k1);
                    texture_faces++;
                }

                i1 += model_1.texture_faces;
            }
        }

        calc_diagonals();
    }

    public Model(boolean color_flag, boolean alpha_flag, boolean animated, Model model) {
        this(color_flag, alpha_flag, animated, false, model);
    }

    public Model(boolean color_flag, boolean alpha_flag, boolean animated, boolean texture_flag, Model model) {
        aBoolean1618 = true;
        within_tile = false;
        anInt1620++;
        vertices = model.vertices;
        faces = model.faces;
        texture_faces = model.texture_faces;
        if (animated) {
            vertex_x = model.vertex_x;
            vertex_y = model.vertex_y;
            vertex_z = model.vertex_z;
        } else {
            vertex_x = new int[vertices];
            vertex_y = new int[vertices];
            vertex_z = new int[vertices];
            for (int point = 0; point < vertices; point++) {
                vertex_x[point] = model.vertex_x[point];
                vertex_y[point] = model.vertex_y[point];
                vertex_z[point] = model.vertex_z[point];
            }

        }

        if (color_flag) {
            face_color = model.face_color;
            repeatTexture = model.repeatTexture;
        } else {
            face_color = new short[faces];
            repeatTexture = new boolean[faces];
            for (int face = 0; face < faces; face++) {
                face_color[face] = model.face_color[face];
                repeatTexture[face] = model.repeatTexture[face];
            }

        }

        if(!texture_flag && model.face_material != null) {
            face_material = new short[faces];
            for(int face = 0; face < faces; face++) {
                face_material[face] = model.face_material[face];
            }
        } else {
            face_material = model.face_material;
        }

        if (alpha_flag) {
            face_alpha = model.face_alpha;
        } else {
            face_alpha = new int[faces];
            if (model.face_alpha == null) {
                for (int l = 0; l < faces; l++)
                    face_alpha[l] = 0;

            } else {
                for (int i1 = 0; i1 < faces; i1++)
                    face_alpha[i1] = model.face_alpha[i1];

            }
        }
        bone_skin = model.bone_skin;
        muscle_skin = model.muscle_skin;
        render_type = model.render_type;
        triangle_edge_a = model.triangle_edge_a;
        triangle_edge_b = model.triangle_edge_b;
        triangle_edge_c = model.triangle_edge_c;
        face_render_priorities = model.face_render_priorities;
        face_texture = model.face_texture;
        texture_map = model.texture_map;
        face_priority = model.face_priority;
        triangle_texture_edge_a = model.triangle_texture_edge_a;
        triangle_texture_edge_b = model.triangle_texture_edge_b;
        triangle_texture_edge_c = model.triangle_texture_edge_c;
    }

    public Model(boolean adjust_elevation, boolean gouraud_shading, Model model) {
        aBoolean1618 = true;
        within_tile = false;
        anInt1620++;
        vertices = model.vertices;
        faces = model.faces;
        texture_faces = model.texture_faces;
        if (adjust_elevation) {
            vertex_y = new int[vertices];
            for (int point = 0; point < vertices; point++)
                vertex_y[point] = model.vertex_y[point];

        } else {
            vertex_y = model.vertex_y;
        }
        if (gouraud_shading) {
            faceHslA = new int[faces];
            faceHslB = new int[faces];
            faceHslC = new int[faces];
            for (int face = 0; face < faces; face++) {
                faceHslA[face] = model.faceHslA[face];
                faceHslB[face] = model.faceHslB[face];
                faceHslC[face] = model.faceHslC[face];
            }

            render_type = new int[faces];
            if (model.render_type == null) {
                for (int face = 0; face < faces; face++)
                    render_type[face] = 0;

            } else {
                for (int face = 0; face < faces; face++)
                    render_type[face] = model.render_type[face];

            }
            super.normals = new Vertex[vertices];
            for (int point = 0; point < vertices; point++) {
                Vertex class33 = super.normals[point] = new Vertex();
                Vertex class33_1 = model.normals[point];
                class33.x = class33_1.x;
                class33.y = class33_1.y;
                class33.z = class33_1.z;
                class33.magnitude = class33_1.magnitude;
            }
            gouraud_vertex = model.gouraud_vertex;

        } else {
            faceHslA = model.faceHslA;
            faceHslB = model.faceHslB;
            faceHslC = model.faceHslC;
            render_type = model.render_type;
        }
        vertex_x = model.vertex_x;
        vertex_z = model.vertex_z;
        triangle_edge_a = model.triangle_edge_a;
        triangle_edge_b = model.triangle_edge_b;
        triangle_edge_c = model.triangle_edge_c;
        face_render_priorities = model.face_render_priorities;
        face_alpha = model.face_alpha;
        face_texture = model.face_texture;
        face_color = model.face_color;
        repeatTexture = model.repeatTexture;
        face_material = model.face_material;
        face_priority = model.face_priority;
        texture_map = model.texture_map;
        triangle_texture_edge_a = model.triangle_texture_edge_a;
        triangle_texture_edge_b = model.triangle_texture_edge_b;
        triangle_texture_edge_c = model.triangle_texture_edge_c;
        super.model_height = model.model_height;
        diagonal_2D = model.diagonal_2D;
        diagonal_3D = model.diagonal_3D;
        scene_depth = model.scene_depth;
        min_x = model.min_x;
        max_z = model.max_z;
        min_z = model.min_z;
        max_x = model.max_x;
    }

    public static void clear() {
        aClass21Array1661 = null;
        hasAnEdgeToRestrict = null;
        outOfReach = null;
        projected_vertex_y = null;
        projected_vertex_z = null;
        camera_vertex_x = null;
        camera_vertex_y = null;
        camera_vertex_z = null;
        depthListIndices = null;
        faceLists = null;
        anIntArray1673 = null;
        anIntArrayArray1674 = null;
        anIntArray1675 = null;
        anIntArray1676 = null;
        anIntArray1677 = null;
        SINE = null;
        COSINE = null;
        modelIntArray3 = null;
        modelIntArray4 = null;
    }

    public static void method460(byte abyte0[], int j) {
        try {
            if (abyte0 == null) {
                ModelHeader class21 = aClass21Array1661[j] = new ModelHeader();
                class21.vertices = 0;
                class21.faces = 0;
                class21.texture_faces = 0;
                return;
            }
            Buffer stream = new Buffer(abyte0);
            stream.pos = abyte0.length - 18;
            ModelHeader class21_1 = aClass21Array1661[j] = new ModelHeader();
            class21_1.data = abyte0;
            class21_1.vertices = stream.readUShort();
            class21_1.faces = stream.readUShort();
            class21_1.texture_faces = stream.readUByte();
            int k = stream.readUByte();
            int l = stream.readUByte();
            int i1 = stream.readUByte();
            int j1 = stream.readUByte();
            int k1 = stream.readUByte();
            int l1 = stream.readUShort();
            int uid = stream.readUShort();
            int j2 = stream.readUShort();
            int k2 = stream.readUShort();
            int l2 = 0;
            class21_1.vertex_offset = l2;
            l2 += class21_1.vertices;
            class21_1.face_offset = l2;
            l2 += class21_1.faces;
            class21_1.face_pri_offset = l2;
            if (l == 255)
                l2 += class21_1.faces;
            else
                class21_1.face_pri_offset = -l - 1;
            class21_1.muscle_offset = l2;
            if (j1 == 1)
                l2 += class21_1.faces;
            else
                class21_1.muscle_offset = -1;
            class21_1.render_type_offset = l2;
            if (k == 1)
                l2 += class21_1.faces;
            else
                class21_1.muscle_offset = -1;
            class21_1.bones_offset = l2;
            if (k1 == 1)
                l2 += class21_1.vertices;
            else
                class21_1.bones_offset = -1;
            class21_1.alpha_offset = l2;
            if (i1 == 1)
                l2 += class21_1.faces;
            else
                class21_1.alpha_offset = -1;
            class21_1.points_offset = l2;
            l2 += k2;
            class21_1.color_id = l2;
            l2 += class21_1.faces * 2;
            class21_1.texture_id = l2;
            l2 += class21_1.texture_faces * 6;
            class21_1.vertex_x_offset = l2;
            l2 += l1;
            class21_1.vertex_y_offset = l2;
            l2 += uid;
            class21_1.vertex_z_offset = l2;
            l2 += j2;
        } catch (Exception _ex) {
        }
    }

    public static void method459(int id, Provider onDemandFetcherParent) {
        aClass21Array1661 = new ModelHeader[80000]; //TODO: should this be id?
        resourceProvider = onDemandFetcherParent;
    }

    public static void method461(int file) {
        aClass21Array1661[file] = null;
    }

    public static Model get(int file) {
        if (aClass21Array1661 == null)
            return null;

        ModelHeader class21 = aClass21Array1661[file];
        if (class21 == null) {
            resourceProvider.provide(file);
            return null;
        } else {
            return new Model(file);
        }
    }

    public static boolean cached(int file) {
        if (aClass21Array1661 == null)
            return false;
        //This is great debugging, we can see where the file is set to 65535 in the stack trace when doing this.
        //if (file == 65535) throw new RuntimeException();
        ModelHeader class21 = aClass21Array1661[file];
        if (class21 == null) {
            resourceProvider.provide(file);
            return false;
        } else {
            return true;
        }
    }

    public void decodeType3(byte[] var1)
    {
        Buffer var2 = new Buffer(var1);
        Buffer var3 = new Buffer(var1);
        Buffer var4 = new Buffer(var1);
        Buffer var5 = new Buffer(var1);
        Buffer var6 = new Buffer(var1);
        Buffer var7 = new Buffer(var1);
        Buffer var8 = new Buffer(var1);
        var2.pos = var1.length - 26;
        int var9 = var2.readUShort();
        int var10 = var2.readUShort();
        int var11 = var2.readUnsignedByte();
        int var12 = var2.readUnsignedByte();
        int var13 = var2.readUnsignedByte();
        int var14 = var2.readUnsignedByte();
        int var15 = var2.readUnsignedByte();
        int var16 = var2.readUnsignedByte();
        int var17 = var2.readUnsignedByte();
        int var18 = var2.readUnsignedByte();
        int var19 = var2.readUShort();
        int var20 = var2.readUShort();
        int var21 = var2.readUShort();
        int var22 = var2.readUShort();
        int var23 = var2.readUShort();
        int var24 = var2.readUShort();
        int var25 = 0;
        int var26 = 0;
        int var27 = 0;
        int var28;


        if (var11 > 0)
        {
            texture_map = new byte[var11];
            var2.pos = 0;

            for (var28 = 0; var28 < var11; ++var28)
            {
                byte var29 = texture_map[var28] = var2.readSignedByte();
                if (var29 == 0)
                {
                    ++var25;
                }

                if (var29 >= 1 && var29 <= 3)
                {
                    ++var26;
                }

                if (var29 == 2)
                {
                    ++var27;
                }
            }
        }

        var28 = var11 + var9;
        int var58 = var28;
        if (var12 == 1)
        {
            var28 += var10;
        }

        int var30 = var28;
        var28 += var10;
        int var31 = var28;
        if (var13 == 255)
        {
            var28 += var10;
        }

        int var32 = var28;
        if (var15 == 1)
        {
            var28 += var10;
        }

        int var33 = var28;
        var28 += var24;
        int var34 = var28;
        if (var14 == 1)
        {
            var28 += var10;
        }

        int var35 = var28;
        var28 += var22;
        int var36 = var28;
        if (var16 == 1)
        {
            var28 += var10 * 2;
        }

        int var37 = var28;
        var28 += var23;
        int var38 = var28;
        var28 += var10 * 2;
        int var39 = var28;
        var28 += var19;
        int var40 = var28;
        var28 += var20;
        int var41 = var28;
        var28 += var21;
        int var42 = var28;
        var28 += var25 * 6;
        int var43 = var28;
        var28 += var26 * 6;
        int var44 = var28;
        var28 += var26 * 6;
        int var45 = var28;
        var28 += var26 * 2;
        int var46 = var28;
        var28 += var26;
        int var47 = var28;
        var28 = var28 + var26 * 2 + var27 * 2;


        vertices = var9;
        faces = var10;
        texture_faces = var11;
        vertex_x = new int[var9];
        vertex_y = new int[var9];
        vertex_z = new int[var9];
        triangle_edge_a = new int[var10];
        triangle_edge_b = new int[var10];
        triangle_edge_c = new int[var10];
        if (var17 == 1)
        {
            bone_skin = new int[var9];
        }

        if (var12 == 1)
        {
            render_type = new int[var10];
        }

        if (var13 == 255)
        {
            face_render_priorities = new byte[var10];
        }
        else
        {
            face_priority = (byte) var13;
        }

        if (var14 == 1)
        {
            face_alpha = new int[var10];
        }

        if (var15 == 1)
        {
            muscle_skin = new int[var10];
        }

        if (var16 == 1)
        {
            face_material = new short[var10];
        }

        if (var16 == 1 && var11 > 0)
        {
            face_texture = new byte[var10];
        }

        if (var18 == 1)
        {
            animayaGroups = new int[var9][];
            animayaScales = new int[var9][];
        }

        face_color = new short[var10];
        if (var11 > 0)
        {
            triangle_texture_edge_a = new short[var11];
            triangle_texture_edge_b = new short[var11];
            triangle_texture_edge_c = new short[var11];
        }

        var2.pos = var11;
        var3.pos = var39;
        var4.pos = var40;
        var5.pos = var41;
        var6.pos = var33;
        int var48 = 0;
        int var49 = 0;
        int var50 = 0;

        int var51;
        int var52;
        int var53;
        int var54;
        int var55;
        for (var51 = 0; var51 < var9; ++var51)
        {
            var52 = var2.readUnsignedByte();
            var53 = 0;
            if ((var52 & 1) != 0)
            {
                var53 = var3.readSignedSmart();
            }

            var54 = 0;
            if ((var52 & 2) != 0)
            {
                var54 = var4.readSignedSmart();
            }

            var55 = 0;
            if ((var52 & 4) != 0)
            {
                var55 = var5.readSignedSmart();
            }

            vertex_x[var51] = var48 + var53;
            vertex_y[var51] = var49 + var54;
            vertex_z[var51] = var50 + var55;
            var48 = vertex_x[var51];
            var49 = vertex_y[var51];
            var50 = vertex_z[var51];
            if (var17 == 1)
            {
                bone_skin[var51] = var6.readUnsignedByte();
            }
        }

        if (var18 == 1)
        {
            for (var51 = 0; var51 < var9; ++var51)
            {
                var52 = var6.readUnsignedByte();
                animayaGroups[var51] = new int[var52];
                animayaScales[var51] = new int[var52];

                for (var53 = 0; var53 < var52; ++var53)
                {
                    animayaGroups[var51][var53] = var6.readUnsignedByte();
                    animayaScales[var51][var53] = var6.readUnsignedByte();
                }
            }
        }

        var2.pos = var38;
        var3.pos = var58;
        var4.pos = var31;
        var5.pos = var34;
        var6.pos = var32;
        var7.pos = var36;
        var8.pos = var37;

        for (var51 = 0; var51 < var10; ++var51)
        {
            face_color[var51] = (short) var2.readUShort();
            if (var12 == 1)
            {
                render_type[var51] = var3.readSignedByte();
            }

            if (var13 == 255)
            {
                face_render_priorities[var51] = var4.readSignedByte();
            }

            if (var14 == 1)
            {
                face_alpha[var51] = var5.readSignedByte();
                if (face_alpha[var51] < 0) {
                    face_alpha[var51] = (256 + face_alpha[var51]);
                }
            }

            if (var15 == 1)
            {
                muscle_skin[var51] = var6.readUnsignedByte();
            }

            if (var16 == 1)
            {
                face_material[var51] = (short) (var7.readUShort() - 1);
            }

            if (face_texture != null && face_material[var51] != -1)
            {
                face_texture[var51] = (byte) (var8.readUnsignedByte() - 1);
            }
        }

        var2.pos = var35;
        var3.pos = var30;
        var51 = 0;
        var52 = 0;
        var53 = 0;
        var54 = 0;

        int var56;
        for (var55 = 0; var55 < var10; ++var55)
        {
            var56 = var3.readUnsignedByte();
            if (var56 == 1)
            {
                var51 = var2.readSignedSmart() + var54;
                var52 = var2.readSignedSmart() + var51;
                var53 = var2.readSignedSmart() + var52;
                var54 = var53;
                triangle_edge_a[var55] = var51;
                triangle_edge_b[var55] = var52;
                triangle_edge_c[var55] = var53;
            }

            if (var56 == 2)
            {
                var52 = var53;
                var53 = var2.readSignedSmart() + var54;
                var54 = var53;
                triangle_edge_a[var55] = var51;
                triangle_edge_b[var55] = var52;
                triangle_edge_c[var55] = var53;
            }

            if (var56 == 3)
            {
                var51 = var53;
                var53 = var2.readSignedSmart() + var54;
                var54 = var53;
                triangle_edge_a[var55] = var51;
                triangle_edge_b[var55] = var52;
                triangle_edge_c[var55] = var53;
            }

            if (var56 == 4)
            {
                int var57 = var51;
                var51 = var52;
                var52 = var57;
                var53 = var2.readSignedSmart() + var54;
                var54 = var53;
                triangle_edge_a[var55] = var51;
                triangle_edge_b[var55] = var57;
                triangle_edge_c[var55] = var53;
            }
        }

        var2.pos = var42;
        var3.pos = var43;
        var4.pos = var44;
        var5.pos = var45;
        var6.pos = var46;
        var7.pos = var47;

        for (var55 = 0; var55 < var11; ++var55)
        {
            var56 = texture_map[var55] & 255;
            if (var56 == 0)
            {
                triangle_texture_edge_a[var55] = (short) var2.readUShort();
                triangle_texture_edge_b[var55] = (short) var2.readUShort();
                triangle_texture_edge_c[var55] = (short) var2.readUShort();
            }
        }

        var2.pos = var28;
        var55 = var2.readUnsignedByte();
        if (var55 != 0)
        {
            var2.readUShort();
            var2.readUShort();
            var2.readUShort();
            var2.readInt();
        }

        repeatTexture = new boolean[var10];
    }

    public void decodeType2(byte[] var1)
    {
        boolean var2 = false;
        boolean var3 = false;
        Buffer var4 = new Buffer(var1);
        Buffer var5 = new Buffer(var1);
        Buffer var6 = new Buffer(var1);
        Buffer var7 = new Buffer(var1);
        Buffer var8 = new Buffer(var1);
        var4.pos = var1.length - 23;
        int var9 = var4.readUShort();
        int var10 = var4.readUShort();
        int var11 = var4.readUnsignedByte();
        int var12 = var4.readUnsignedByte();
        int var13 = var4.readUnsignedByte();
        int var14 = var4.readUnsignedByte();
        int var15 = var4.readUnsignedByte();
        int var16 = var4.readUnsignedByte();
        int var17 = var4.readUnsignedByte();
        int var18 = var4.readUShort();
        int var19 = var4.readUShort();
        int var20 = var4.readUShort();
        int var21 = var4.readUShort();
        int var22 = var4.readUShort();
        byte var23 = 0;
        int var24 = var23 + var9;
        int var25 = var24;
        var24 += var10;
        int var26 = var24;
        if (var13 == 255)
        {
            var24 += var10;
        }

        int var27 = var24;
        if (var15 == 1)
        {
            var24 += var10;
        }

        int var28 = var24;
        if (var12 == 1)
        {
            var24 += var10;
        }

        int var29 = var24;
        var24 += var22;
        int var30 = var24;
        if (var14 == 1)
        {
            var24 += var10;
        }

        int var31 = var24;
        var24 += var21;
        int var32 = var24;
        var24 += var10 * 2;
        int var33 = var24;
        var24 += var11 * 6;
        int var34 = var24;
        var24 += var18;
        int var35 = var24;
        var24 += var19;
        int var10000 = var24 + var20;
        vertices = var9;
        faces = var10;
        texture_faces = var11;
        vertex_x = new int[var9];
        vertex_y = new int[var9];
        vertex_z = new int[var9];
        triangle_edge_a = new int[var10];
        triangle_edge_b = new int[var10];
        triangle_edge_c = new int[var10];
        if (var11 > 0)
        {
            texture_map = new byte[var11];
            triangle_texture_edge_a = new short[var11];
            triangle_texture_edge_b = new short[var11];
            triangle_texture_edge_c = new short[var11];
        }

        if (var16 == 1)
        {
            bone_skin = new int[var9];
        }

        if (var12 == 1)
        {
            render_type = new int[var10];
            face_texture = new byte[var10];
            face_material = new short[var10];
        }

        if (var13 == 255)
        {
            face_render_priorities = new byte[var10];
        }
        else
        {
            face_priority = (byte) var13;
        }

        if (var14 == 1)
        {
            face_alpha = new int[var10];
        }

        if (var15 == 1)
        {
            muscle_skin = new int[var10];
        }

        if (var17 == 1)
        {
            animayaGroups = new int[var9][];
            animayaScales = new int[var9][];
        }

        face_color = new short[var10];
        var4.pos = var23;
        var5.pos = var34;
        var6.pos = var35;
        var7.pos = var24;
        var8.pos = var29;
        int var37 = 0;
        int var38 = 0;
        int var39 = 0;

        int var40;
        int var41;
        int var42;
        int var43;
        int var44;
        for (var40 = 0; var40 < var9; ++var40)
        {
            var41 = var4.readUnsignedByte();
            var42 = 0;
            if ((var41 & 1) != 0)
            {
                var42 = var5.readSignedSmart();
            }

            var43 = 0;
            if ((var41 & 2) != 0)
            {
                var43 = var6.readSignedSmart();
            }

            var44 = 0;
            if ((var41 & 4) != 0)
            {
                var44 = var7.readSignedSmart();
            }

            vertex_x[var40] = var37 + var42;
            vertex_y[var40] = var38 + var43;
            vertex_z[var40] = var39 + var44;
            var37 = vertex_x[var40];
            var38 = vertex_y[var40];
            var39 = vertex_z[var40];
            if (var16 == 1)
            {
                bone_skin[var40] = var8.readUnsignedByte();
            }
        }

        if (var17 == 1)
        {
            for (var40 = 0; var40 < var9; ++var40)
            {
                var41 = var8.readUnsignedByte();
                animayaGroups[var40] = new int[var41];
                animayaScales[var40] = new int[var41];

                for (var42 = 0; var42 < var41; ++var42)
                {
                    animayaGroups[var40][var42] = var8.readUnsignedByte();
                    animayaScales[var40][var42] = var8.readUnsignedByte();
                }
            }
        }

        var4.pos = var32;
        var5.pos = var28;
        var6.pos = var26;
        var7.pos = var30;
        var8.pos = var27;

        for (var40 = 0; var40 < var10; ++var40)
        {
            face_color[var40] = (short) var4.readUShort();
            if (var12 == 1)
            {
                var41 = var5.readUnsignedByte();
                if ((var41 & 1) == 1)
                {
                    render_type[var40] = 1;
                    var2 = true;
                }
                else
                {
                    render_type[var40] = 0;
                }

                if ((var41 & 2) == 2)
                {
                    face_texture[var40] = (byte) (var41 >> 2);
                    face_material[var40] = face_color[var40];
                    face_color[var40] = 127;
                    if (face_material[var40] != -1)
                    {
                        var3 = true;
                    }
                }
                else
                {
                    face_texture[var40] = -1;
                    face_material[var40] = -1;
                }
            }

            if (var13 == 255)
            {
                face_render_priorities[var40] = var6.readSignedByte();
            }

            if (var14 == 1)
            {
                face_alpha[var40] = var7.readSignedByte();
                if (face_alpha[var40] < 0) {
                    face_alpha[var40] = (256 + face_alpha[var40]);
                }
            }

            if (var15 == 1)
            {
                muscle_skin[var40] = var8.readUnsignedByte();
            }
        }

        var4.pos = var31;
        var5.pos = var25;
        var40 = 0;
        var41 = 0;
        var42 = 0;
        var43 = 0;

        int var45;
        int var46;
        for (var44 = 0; var44 < var10; ++var44)
        {
            var45 = var5.readUnsignedByte();
            if (var45 == 1)
            {
                var40 = var4.readSignedSmart() + var43;
                var41 = var4.readSignedSmart() + var40;
                var42 = var4.readSignedSmart() + var41;
                var43 = var42;
                triangle_edge_a[var44] = var40;
                triangle_edge_b[var44] = var41;
                triangle_edge_c[var44] = var42;
            }

            if (var45 == 2)
            {
                var41 = var42;
                var42 = var4.readSignedSmart() + var43;
                var43 = var42;
                triangle_edge_a[var44] = var40;
                triangle_edge_b[var44] = var41;
                triangle_edge_c[var44] = var42;
            }

            if (var45 == 3)
            {
                var40 = var42;
                var42 = var4.readSignedSmart() + var43;
                var43 = var42;
                triangle_edge_a[var44] = var40;
                triangle_edge_b[var44] = var41;
                triangle_edge_c[var44] = var42;
            }

            if (var45 == 4)
            {
                var46 = var40;
                var40 = var41;
                var41 = var46;
                var42 = var4.readSignedSmart() + var43;
                var43 = var42;
                triangle_edge_a[var44] = var40;
                triangle_edge_b[var44] = var46;
                triangle_edge_c[var44] = var42;
            }
        }

        var4.pos = var33;

        for (var44 = 0; var44 < var11; ++var44)
        {
            texture_map[var44] = 0;
            triangle_texture_edge_a[var44] = (short) var4.readUShort();
            triangle_texture_edge_b[var44] = (short) var4.readUShort();
            triangle_texture_edge_c[var44] = (short) var4.readUShort();
        }

        if (face_texture != null)
        {
            boolean var47 = false;

            for (var45 = 0; var45 < var10; ++var45)
            {
                var46 = face_texture[var45] & 255;
                if (var46 != 255)
                {
                    if (triangle_edge_a[var45] == (triangle_texture_edge_a[var46] & '\uffff') && triangle_edge_b[var45] == (triangle_texture_edge_b[var46] & '\uffff') && triangle_edge_c[var45] == (triangle_texture_edge_c[var46] & '\uffff'))
                    {
                        face_texture[var45] = -1;
                    }
                    else
                    {
                        var47 = true;
                    }
                }
            }

            if (!var47)
            {
                face_texture = null;
            }
        }

        if (!var3)
        {
            face_material = null;
        }

        if (!var2)
        {
            render_type = null;
        }

        repeatTexture = new boolean[var10];
    }

    public void decodeType1(byte[] var1)
    {
        Buffer var2 = new Buffer(var1);
        Buffer var3 = new Buffer(var1);
        Buffer var4 = new Buffer(var1);
        Buffer var5 = new Buffer(var1);
        Buffer var6 = new Buffer(var1);
        Buffer var7 = new Buffer(var1);
        Buffer var8 = new Buffer(var1);
        var2.pos = var1.length - 23;
        int var9 = var2.readUShort();
        int var10 = var2.readUShort();
        int var11 = var2.readUnsignedByte();
        int var12 = var2.readUnsignedByte();
        int var13 = var2.readUnsignedByte();
        int var14 = var2.readUnsignedByte();
        int var15 = var2.readUnsignedByte();
        int var16 = var2.readUnsignedByte();
        int var17 = var2.readUnsignedByte();
        int var18 = var2.readUShort();
        int var19 = var2.readUShort();
        int var20 = var2.readUShort();
        int var21 = var2.readUShort();
        int var22 = var2.readUShort();
        int var23 = 0;
        int var24 = 0;
        int var25 = 0;
        int var26;
        if (var11 > 0)
        {
            texture_map = new byte[var11];
            var2.pos = 0;

            for (var26 = 0; var26 < var11; ++var26)
            {
                byte var27 = texture_map[var26] = var2.readSignedByte();
                if (var27 == 0)
                {
                    ++var23;
                }

                if (var27 >= 1 && var27 <= 3)
                {
                    ++var24;
                }

                if (var27 == 2)
                {
                    ++var25;
                }
            }
        }

        var26 = var11 + var9;
        int var56 = var26;
        if (var12 == 1)
        {
            var26 += var10;
        }

        int var28 = var26;
        var26 += var10;
        int var29 = var26;
        if (var13 == 255)
        {
            var26 += var10;
        }

        int var30 = var26;
        if (var15 == 1)
        {
            var26 += var10;
        }

        int var31 = var26;
        if (var17 == 1)
        {
            var26 += var9;
        }

        int var32 = var26;
        if (var14 == 1)
        {
            var26 += var10;
        }

        int var33 = var26;
        var26 += var21;
        int var34 = var26;
        if (var16 == 1)
        {
            var26 += var10 * 2;
        }

        int var35 = var26;
        var26 += var22;
        int var36 = var26;
        var26 += var10 * 2;
        int var37 = var26;
        var26 += var18;
        int var38 = var26;
        var26 += var19;
        int var39 = var26;
        var26 += var20;
        int var40 = var26;
        var26 += var23 * 6;
        int var41 = var26;
        var26 += var24 * 6;
        int var42 = var26;
        var26 += var24 * 6;
        int var43 = var26;
        var26 += var24 * 2;
        int var44 = var26;
        var26 += var24;
        int var45 = var26;
        var26 = var26 + var24 * 2 + var25 * 2;
        vertices = var9;
        faces = var10;
        texture_faces = var11;
        vertex_x = new int[var9];
        vertex_y = new int[var9];
        vertex_z = new int[var9];
        triangle_edge_a = new int[var10];
        triangle_edge_b = new int[var10];
        triangle_edge_c = new int[var10];
        if (var17 == 1)
        {
            bone_skin = new int[var9];
        }

        if (var12 == 1)
        {
            render_type = new int[var10];
        }

        if (var13 == 255)
        {
            face_render_priorities = new byte[var10];
        }
        else
        {
            face_priority = (byte) var13;
        }

        if (var14 == 1)
        {
            face_alpha = new int[var10];
        }

        if (var15 == 1)
        {
            muscle_skin = new int[var10];
        }

        if (var16 == 1)
        {
            face_material = new short[var10];
        }

        if (var16 == 1 && var11 > 0)
        {
            face_texture = new byte[var10];
        }

        face_color = new short[var10];
        if (var11 > 0)
        {
            triangle_texture_edge_a = new short[var11];
            triangle_texture_edge_b = new short[var11];
            triangle_texture_edge_c = new short[var11];
        }

        var2.pos = var11;
        var3.pos = var37;
        var4.pos = var38;
        var5.pos = var39;
        var6.pos = var31;
        int var46 = 0;
        int var47 = 0;
        int var48 = 0;

        int var49;
        int var50;
        int var51;
        int var52;
        int var53;
        for (var49 = 0; var49 < var9; ++var49)
        {
            var50 = var2.readUnsignedByte();
            var51 = 0;
            if ((var50 & 1) != 0)
            {
                var51 = var3.readSignedSmart();
            }

            var52 = 0;
            if ((var50 & 2) != 0)
            {
                var52 = var4.readSignedSmart();
            }

            var53 = 0;
            if ((var50 & 4) != 0)
            {
                var53 = var5.readSignedSmart();
            }

            vertex_x[var49] = var46 + var51;
            vertex_y[var49] = var47 + var52;
            vertex_z[var49] = var48 + var53;
            var46 = vertex_x[var49];
            var47 = vertex_y[var49];
            var48 = vertex_z[var49];
            if (var17 == 1)
            {
                bone_skin[var49] = var6.readUnsignedByte();
            }
        }

        var2.pos = var36;
        var3.pos = var56;
        var4.pos = var29;
        var5.pos = var32;
        var6.pos = var30;
        var7.pos = var34;
        var8.pos = var35;

        for (var49 = 0; var49 < var10; ++var49)
        {
            face_color[var49] = (short) var2.readUShort();
            if (var12 == 1)
            {
                render_type[var49] = var3.readSignedByte();
            }

            if (var13 == 255)
            {
                face_render_priorities[var49] = var4.readSignedByte();
            }

            if (var14 == 1)
            {
                face_alpha[var49] = var5.readSignedByte();
                if (face_alpha[var49] < 0) {
                    face_alpha[var49] = (256 + face_alpha[var40]);
                }
            }

            if (var15 == 1)
            {
                muscle_skin[var49] = var6.readUnsignedByte();
            }

            if (var16 == 1)
            {
                face_material[var49] = (short) (var7.readUShort() - 1);
            }

            if (face_texture != null && face_material[var49] != -1)
            {
                face_texture[var49] = (byte) (var8.readUnsignedByte() - 1);
            }
        }

        var2.pos = var33;
        var3.pos = var28;
        var49 = 0;
        var50 = 0;
        var51 = 0;
        var52 = 0;

        int var54;
        for (var53 = 0; var53 < var10; ++var53)
        {
            var54 = var3.readUnsignedByte();
            if (var54 == 1)
            {
                var49 = var2.readSignedSmart() + var52;
                var50 = var2.readSignedSmart() + var49;
                var51 = var2.readSignedSmart() + var50;
                var52 = var51;
                triangle_edge_a[var53] = var49;
                triangle_edge_b[var53] = var50;
                triangle_edge_c[var53] = var51;
            }

            if (var54 == 2)
            {
                var50 = var51;
                var51 = var2.readSignedSmart() + var52;
                var52 = var51;
                triangle_edge_a[var53] = var49;
                triangle_edge_b[var53] = var50;
                triangle_edge_c[var53] = var51;
            }

            if (var54 == 3)
            {
                var49 = var51;
                var51 = var2.readSignedSmart() + var52;
                var52 = var51;
                triangle_edge_a[var53] = var49;
                triangle_edge_b[var53] = var50;
                triangle_edge_c[var53] = var51;
            }

            if (var54 == 4)
            {
                int var55 = var49;
                var49 = var50;
                var50 = var55;
                var51 = var2.readSignedSmart() + var52;
                var52 = var51;
                triangle_edge_a[var53] = var49;
                triangle_edge_b[var53] = var55;
                triangle_edge_c[var53] = var51;
            }
        }

        var2.pos = var40;
        var3.pos = var41;
        var4.pos = var42;
        var5.pos = var43;
        var6.pos = var44;
        var7.pos = var45;

        for (var53 = 0; var53 < var11; ++var53)
        {
            var54 = texture_map[var53] & 255;
            if (var54 == 0)
            {
                triangle_texture_edge_a[var53] = (short) var2.readUShort();
                triangle_texture_edge_b[var53] = (short) var2.readUShort();
                triangle_texture_edge_c[var53] = (short) var2.readUShort();
            }
        }

        var2.pos = var26;
        var53 = var2.readUnsignedByte();
        if (var53 != 0)
        {
            var2.readUShort();
            var2.readUShort();
            var2.readUShort();
            var2.readInt();
        }

        repeatTexture = new boolean[var10];
    }

    public void decodeOldFormat(byte[] var1)
    {
        boolean var2 = false;
        boolean var3 = false;
        Buffer var4 = new Buffer(var1);
        Buffer var5 = new Buffer(var1);
        Buffer var6 = new Buffer(var1);
        Buffer var7 = new Buffer(var1);
        Buffer var8 = new Buffer(var1);
        var4.pos = var1.length - 18;
        int var9 = var4.readUShort();
        int var10 = var4.readUShort();
        int var11 = var4.readUnsignedByte();
        int var12 = var4.readUnsignedByte();
        int var13 = var4.readUnsignedByte();
        int var14 = var4.readUnsignedByte();
        int var15 = var4.readUnsignedByte();
        int var16 = var4.readUnsignedByte();
        int var17 = var4.readUShort();
        int var18 = var4.readUShort();
        int var19 = var4.readUShort();
        int var20 = var4.readUShort();
        byte var21 = 0;
        int var22 = var21 + var9;
        int var23 = var22;
        var22 += var10;
        int var24 = var22;
        if (var13 == 255)
        {
            var22 += var10;
        }

        int var25 = var22;
        if (var15 == 1)
        {
            var22 += var10;
        }

        int var26 = var22;
        if (var12 == 1)
        {
            var22 += var10;
        }

        int var27 = var22;
        if (var16 == 1)
        {
            var22 += var9;
        }

        int var28 = var22;
        if (var14 == 1)
        {
            var22 += var10;
        }

        int var29 = var22;
        var22 += var20;
        int var30 = var22;
        var22 += var10 * 2;
        int var31 = var22;
        var22 += var11 * 6;
        int var32 = var22;
        var22 += var17;
        int var33 = var22;
        var22 += var18;
        int var10000 = var22 + var19;
        vertices = var9;
        faces = var10;
        texture_faces = var11;
        vertex_x = new int[var9];
        vertex_y = new int[var9];
        vertex_z = new int[var9];
        triangle_edge_a = new int[var10];
        triangle_edge_b = new int[var10];
        triangle_edge_c = new int[var10];
        if (var11 > 0)
        {
            texture_map = new byte[var11];
            triangle_texture_edge_a = new short[var11];
            triangle_texture_edge_b = new short[var11];
            triangle_texture_edge_c = new short[var11];
        }

        if (var16 == 1)
        {
            bone_skin = new int[var9];
        }

        if (var12 == 1)
        {
            render_type = new int[var10];
            face_texture = new byte[var10];
            face_material = new short[var10];
        }

        if (var13 == 255)
        {
            face_render_priorities = new byte[var10];
        }
        else
        {
            face_priority = (byte) var13;
        }

        if (var14 == 1)
        {
            face_alpha = new int[var10];
        }

        if (var15 == 1)
        {
            muscle_skin = new int[var10];
        }

        face_color = new short[var10];
        repeatTexture = new boolean[var10];
        var4.pos = var21;
        var5.pos = var32;
        var6.pos = var33;
        var7.pos = var22;
        var8.pos = var27;
        int var35 = 0;
        int var36 = 0;
        int var37 = 0;

        int var38;
        int var39;
        int var40;
        int var41;
        int var42;
        for (var38 = 0; var38 < var9; ++var38)
        {
            var39 = var4.readUnsignedByte();
            var40 = 0;
            if ((var39 & 1) != 0)
            {
                var40 = var5.readSignedSmart();
            }

            var41 = 0;
            if ((var39 & 2) != 0)
            {
                var41 = var6.readSignedSmart();
            }

            var42 = 0;
            if ((var39 & 4) != 0)
            {
                var42 = var7.readSignedSmart();
            }

            vertex_x[var38] = var35 + var40;
            vertex_y[var38] = var36 + var41;
            vertex_z[var38] = var37 + var42;
            var35 = vertex_x[var38];
            var36 = vertex_y[var38];
            var37 = vertex_z[var38];
            if (var16 == 1)
            {
                bone_skin[var38] = var8.readUnsignedByte();
            }
        }

        var4.pos = var30;
        var5.pos = var26;
        var6.pos = var24;
        var7.pos = var28;
        var8.pos = var25;

        for (var38 = 0; var38 < var10; ++var38)
        {
            face_color[var38] = (short) var4.readUShort();
            if (var12 == 1)
            {
                var39 = var5.readUnsignedByte();
                if ((var39 & 1) == 1)
                {
                    render_type[var38] = 1;
                    var2 = true;
                }
                else
                {
                    render_type[var38] = 0;
                }

                if ((var39 & 2) == 2)
                {
                    face_texture[var38] = (byte) (var39 >> 2);
                    face_material[var38] = face_color[var38];
                    face_color[var38] = 127;
                    if (face_material[var38] != -1)
                    {
                        var3 = true;
                    }
                }
                else
                {
                    face_texture[var38] = -1;
                    face_material[var38] = -1;
                }
            }

            if (var13 == 255)
            {
                face_render_priorities[var38] = var6.readSignedByte();
            }

            if (var14 == 1)
            {
                face_alpha[var38] = var7.readSignedByte();
                if (face_alpha[var38] < 0) {
                    face_alpha[var38] = (256 + face_alpha[var38]);
                }
            }

            if (var15 == 1)
            {
                muscle_skin[var38] = var8.readUnsignedByte();
            }
        }

        var4.pos = var29;
        var5.pos = var23;
        var38 = 0;
        var39 = 0;
        var40 = 0;
        var41 = 0;

        int var43;
        int var44;
        for (var42 = 0; var42 < var10; ++var42)
        {
            var43 = var5.readUnsignedByte();
            if (var43 == 1)
            {
                var38 = var4.readSignedSmart() + var41;
                var39 = var4.readSignedSmart() + var38;
                var40 = var4.readSignedSmart() + var39;
                var41 = var40;
                triangle_edge_a[var42] = var38;
                triangle_edge_b[var42] = var39;
                triangle_edge_c[var42] = var40;
            }

            if (var43 == 2)
            {
                var39 = var40;
                var40 = var4.readSignedSmart() + var41;
                var41 = var40;
                triangle_edge_a[var42] = var38;
                triangle_edge_b[var42] = var39;
                triangle_edge_c[var42] = var40;
            }

            if (var43 == 3)
            {
                var38 = var40;
                var40 = var4.readSignedSmart() + var41;
                var41 = var40;
                triangle_edge_a[var42] = var38;
                triangle_edge_b[var42] = var39;
                triangle_edge_c[var42] = var40;
            }

            if (var43 == 4)
            {
                var44 = var38;
                var38 = var39;
                var39 = var44;
                var40 = var4.readSignedSmart() + var41;
                var41 = var40;
                triangle_edge_a[var42] = var38;
                triangle_edge_b[var42] = var44;
                triangle_edge_c[var42] = var40;
            }
        }

        var4.pos = var31;

        for (var42 = 0; var42 < var11; ++var42)
        {
            texture_map[var42] = 0;
            triangle_texture_edge_a[var42] = (short) var4.readUShort();
            triangle_texture_edge_b[var42] = (short) var4.readUShort();
            triangle_texture_edge_c[var42] = (short) var4.readUShort();
        }

        if (face_texture != null)
        {
            boolean var45 = false;

            for (var43 = 0; var43 < var10; ++var43)
            {
                var44 = face_texture[var43] & 255;
                if (var44 != 255)
                {
                    if (triangle_edge_a[var43] == (triangle_texture_edge_a[var44] & '\uffff') && triangle_edge_b[var43] == (triangle_texture_edge_b[var44] & '\uffff') && triangle_edge_c[var43] == (triangle_texture_edge_c[var44] & '\uffff'))
                    {
                        face_texture[var43] = -1;
                    }
                    else
                    {
                        var45 = true;
                    }
                }
            }

            if (!var45)
            {
                face_texture = null;
            }
        }

        if (!var3) {
            face_material = null;
        }

        if (!var2) {
            render_type = null;
        }

        repeatTexture = new boolean[var10];
    }

    public void replace(Model model, boolean alpha_flag) {
        vertices = model.vertices;
        faces = model.faces;
        texture_faces = model.texture_faces;
        if (anIntArray1622.length < vertices) {
            anIntArray1622 = new int[vertices + 10000];
            anIntArray1623 = new int[vertices + 10000];
            anIntArray1624 = new int[vertices + 10000];
        }
        vertex_x = anIntArray1622;
        vertex_y = anIntArray1623;
        vertex_z = anIntArray1624;
        for (int point = 0; point < vertices; point++) {
            vertex_x[point] = model.vertex_x[point];
            vertex_y[point] = model.vertex_y[point];
            vertex_z[point] = model.vertex_z[point];
        }
        if (alpha_flag) {
            face_alpha = model.face_alpha;
        } else {
            if (anIntArray1625.length < faces)
                anIntArray1625 = new int[faces + 100];

            face_alpha = anIntArray1625;
            if (model.face_alpha == null) {
                for (int face = 0; face < faces; face++)
                    face_alpha[face] = 0;

            } else {
                for (int face = 0; face < faces; face++)
                    face_alpha[face] = model.face_alpha[face];

            }
        }
        render_type = model.render_type;
        face_color = model.face_color;
        repeatTexture = model.repeatTexture;
        face_render_priorities = model.face_render_priorities;
        face_priority = model.face_priority;
        face_skin = model.face_skin;
        vertex_skin = model.vertex_skin;
        triangle_edge_a = model.triangle_edge_a;
        triangle_edge_b = model.triangle_edge_b;
        triangle_edge_c = model.triangle_edge_c;
        faceHslA = model.faceHslA;
        faceHslB = model.faceHslB;
        faceHslC = model.faceHslC;
        triangle_texture_edge_a = model.triangle_texture_edge_a;
        triangle_texture_edge_b = model.triangle_texture_edge_b;
        triangle_texture_edge_c = model.triangle_texture_edge_c;
        face_texture = model.face_texture;
        texture_map = model.texture_map;
        face_material = model.face_material;
    }

    private int method465(Model model, int face) {
        int vertex = -1;
        int x = model.vertex_x[face];
        int y = model.vertex_y[face];
        int z = model.vertex_z[face];
        for (int index = 0; index < vertices; index++) {
            if (x != vertex_x[index] || y != vertex_y[index] || z != vertex_z[index])
                continue;
            vertex = index;
            break;
        }
        if (vertex == -1) {
            vertex_x[vertices] = x;
            vertex_y[vertices] = y;
            vertex_z[vertices] = z;
            if (model.bone_skin != null)
                bone_skin[vertices] = model.bone_skin[face];

            vertex = vertices++;
        }
        return vertex;
    }

    public void calc_diagonals() {
        super.model_height = 0;
        diagonal_2D = 0;
        max_y = 0;
        for (int i = 0; i < vertices; i++) {
            int j = vertex_x[i];
            int k = vertex_y[i];
            int l = vertex_z[i];
            if (-k > super.model_height)
                super.model_height = -k;
            if (k > max_y)
                max_y = k;
            int i1 = j * j + l * l;
            if (i1 > diagonal_2D)
                diagonal_2D = i1;
        }
        diagonal_2D = (int) (Math.sqrt(diagonal_2D) + 0.98999999999999999D);
        diagonal_3D = (int) (Math.sqrt(diagonal_2D * diagonal_2D + super.model_height
            * super.model_height) + 0.98999999999999999D);
        scene_depth = diagonal_3D
            + (int) (Math.sqrt(diagonal_2D * diagonal_2D + max_y
            * max_y) + 0.98999999999999999D);
    }

    public void computeSphericalBounds() {
        super.model_height = 0;
        max_y = 0;
        for (int i = 0; i < vertices; i++) {
            int j = vertex_y[i];
            if (-j > super.model_height)
                super.model_height = -j;
            if (j > max_y)
                max_y = j;
        }

        diagonal_3D = (int) (Math.sqrt(diagonal_2D * diagonal_2D + super.model_height
            * super.model_height) + 0.98999999999999999D);
        scene_depth = diagonal_3D
            + (int) (Math.sqrt(diagonal_2D * diagonal_2D + max_y
            * max_y) + 0.98999999999999999D);
    }

    public void calculatebone_skin(int i) {
        super.model_height = 0;
        diagonal_2D = 0;
        max_y = 0;
        min_x = 0xf423f;
        max_x = 0xfff0bdc1;
        max_z = 0xfffe7961;
        min_z = 0x1869f;
        for (int j = 0; j < vertices; j++) {
            int k = vertex_x[j];
            int l = vertex_y[j];
            int i1 = vertex_z[j];
            if (k < min_x)
                min_x = k;
            if (k > max_x)
                max_x = k;
            if (i1 < min_z)
                min_z = i1;
            if (i1 > max_z)
                max_z = i1;
            if (-l > super.model_height)
                super.model_height = -l;
            if (l > max_y)
                max_y = l;
            int j1 = k * k + i1 * i1;
            if (j1 > diagonal_2D)
                diagonal_2D = j1;
        }

        diagonal_2D = (int) Math.sqrt(diagonal_2D);
        diagonal_3D = (int) Math.sqrt(diagonal_2D * diagonal_2D + super.model_height * super.model_height);
        if (i != 21073) {
            return;
        } else {
            scene_depth = diagonal_3D + (int) Math.sqrt(diagonal_2D * diagonal_2D + max_y * max_y);
            return;
        }
    }

    public void scale2(int i) {
        for (int i1 = 0; i1 < vertices; i1++) {
            vertex_x[i1] = vertex_x[i1] / i;
            vertex_y[i1] = vertex_y[i1] / i;
            vertex_z[i1] = vertex_z[i1] / i;
        }
    }

    public void skin() {
        if (bone_skin != null) {
            int ai[] = new int[256];
            int j = 0;
            for (int l = 0; l < vertices; l++) {
                int j1 = bone_skin[l];
                ai[j1]++;
                if (j1 > j)
                    j = j1;
            }

            vertex_skin = new int[j + 1][];
            for (int k1 = 0; k1 <= j; k1++) {
                vertex_skin[k1] = new int[ai[k1]];
                ai[k1] = 0;
            }

            for (int j2 = 0; j2 < vertices; j2++) {
                int l2 = bone_skin[j2];
                vertex_skin[l2][ai[l2]++] = j2;
            }

            bone_skin = null;
        }
        if (muscle_skin != null) {
            int ai1[] = new int[256];
            int k = 0;
            for (int i1 = 0; i1 < faces; i1++) {
                int l1 = muscle_skin[i1];
                ai1[l1]++;
                if (l1 > k)
                    k = l1;
            }

            face_skin = new int[k + 1][];
            for (int i2 = 0; i2 <= k; i2++) {
                face_skin[i2] = new int[ai1[i2]];
                ai1[i2] = 0;
            }

            for (int k2 = 0; k2 < faces; k2++) {
                int i3 = muscle_skin[k2];
                face_skin[i3][ai1[i3]++] = k2;
            }

            muscle_skin = null;
        }
    }

    private void transform(int animationType, int[] skin, int x, int y, int z) {
        int i1 = skin.length;
        if (animationType == 0) {
            int j1 = 0;
            xAnimOffset = 0;
            yAnimOffset = 0;
            zAnimOffset = 0;
            for (int k2 = 0; k2 < i1; k2++) {
                int l3 = skin[k2];
                if (l3 < vertex_skin.length) {
                    int ai5[] = vertex_skin[l3];
                    for (int i5 = 0; i5 < ai5.length; i5++) {
                        int j6 = ai5[i5];
                        xAnimOffset += vertex_x[j6];
                        yAnimOffset += vertex_y[j6];
                        zAnimOffset += vertex_z[j6];
                        j1++;
                    }
                }
            }
            if (j1 > 0) {
                xAnimOffset = (int)(xAnimOffset / j1 + x);
                yAnimOffset = (int)(yAnimOffset / j1 + y);
                zAnimOffset = (int)(zAnimOffset / j1 + z);
                return;
            } else {
                xAnimOffset = (int)x;
                yAnimOffset = (int)y;
                zAnimOffset = (int)z;
                return;
            }
        }
        if (animationType == 1) {
            for (int k1 = 0; k1 < i1; k1++) {
                int l2 = skin[k1];
                if (l2 < vertex_skin.length) {
                    int ai1[] = vertex_skin[l2];
                    for (int i4 = 0; i4 < ai1.length; i4++) {
                        int j5 = ai1[i4];
                        vertex_x[j5] += x;
                        vertex_y[j5] += y;
                        vertex_z[j5] += z;
                    }
                }
            }
            return;
        }
        if (animationType == 2) {
            for (int l1 = 0; l1 < i1; l1++) {
                int i3 = skin[l1];
                if (i3 < vertex_skin.length) {
                    int ai2[] = vertex_skin[i3];
                    for (int j4 = 0; j4 < ai2.length; j4++) {
                        int k5 = ai2[j4];
                        vertex_x[k5] -= xAnimOffset;
                        vertex_y[k5] -= yAnimOffset;
                        vertex_z[k5] -= zAnimOffset;
                        int k6 = (x & 0xff) * 8;
                        int l6 = (y & 0xff) * 8;
                        int i7 = (z & 0xff) * 8;
                        if (i7 != 0) {
                            int j7 = SINE[i7];
                            int i8 = COSINE[i7];
                            int l8 = vertex_y[k5] * j7 + vertex_x[k5] * i8 >> 16;
                            vertex_y[k5] = vertex_y[k5] * i8 - vertex_x[k5] * j7 >> 16;
                            vertex_x[k5] = l8;
                        }
                        if (k6 != 0) {
                            int k7 = SINE[k6];
                            int j8 = COSINE[k6];
                            int i9 = vertex_y[k5] * j8 - vertex_z[k5] * k7 >> 16;
                            vertex_z[k5] = vertex_y[k5] * k7 + vertex_z[k5] * j8 >> 16;
                            vertex_y[k5] = i9;
                        }
                        if (l6 != 0) {
                            int l7 = SINE[l6];
                            int k8 = COSINE[l6];
                            int j9 = vertex_z[k5] * l7 + vertex_x[k5] * k8 >> 16;
                            vertex_z[k5] = vertex_z[k5] * k8 - vertex_x[k5] * l7 >> 16;
                            vertex_x[k5] = j9;
                        }
                        vertex_x[k5] += xAnimOffset;
                        vertex_y[k5] += yAnimOffset;
                        vertex_z[k5] += zAnimOffset;
                    }
                }
            }
            return;
        }
        if (animationType == 3) {
            for (int i2 = 0; i2 < i1; i2++) {
                int j3 = skin[i2];
                if (j3 < vertex_skin.length) {
                    int ai3[] = vertex_skin[j3];
                    for (int k4 = 0; k4 < ai3.length; k4++) {
                        int l5 = ai3[k4];
                        vertex_x[l5] -= xAnimOffset;
                        vertex_y[l5] -= yAnimOffset;
                        vertex_z[l5] -= zAnimOffset;
                        vertex_x[l5] = (int)((vertex_x[l5] * x) / 128);
                        vertex_y[l5] = (int)((vertex_y[l5] * y) / 128);
                        vertex_z[l5] = (int)((vertex_z[l5] * z) / 128);
                        vertex_x[l5] += xAnimOffset;
                        vertex_y[l5] += yAnimOffset;
                        vertex_z[l5] += zAnimOffset;
                    }
                }
            }
            return;
        }
        if (animationType == 5 && face_skin != null && face_alpha != null) {
            for (int j2 = 0; j2 < i1; j2++) {
                int k3 = skin[j2];
                if (k3 < face_skin.length) {
                    int ai4[] = face_skin[k3];
                    for (int l4 = 0; l4 < ai4.length; l4++) {
                        int i6 = ai4[l4];
                        face_alpha[i6] += x * 8;
                        if (face_alpha[i6] < 0)
                            face_alpha[i6] = 0;
                        if (face_alpha[i6] > 255)
                            face_alpha[i6] = 255;
                    }
                }
            }
        }
    }

    private void transformSkin(int animationType, int skin[], int x, int y, int z) {

        int i1 = skin.length;
        if (animationType == 0) {
            int j1 = 0;
            xAnimOffset = 0;
            yAnimOffset = 0;
            zAnimOffset = 0;
            for (int k2 = 0; k2 < i1; k2++) {
                int l3 = skin[k2];
                if (l3 < vertex_skin.length) {
                    int ai5[] = vertex_skin[l3];
                    for (int i5 = 0; i5 < ai5.length; i5++) {
                        int j6 = ai5[i5];
                        xAnimOffset += vertex_x[j6];
                        yAnimOffset += vertex_y[j6];
                        zAnimOffset += vertex_z[j6];
                        j1++;
                    }

                }
            }

            if (j1 > 0) {
                xAnimOffset = (int) (xAnimOffset / j1 + x);
                yAnimOffset = (int) (yAnimOffset / j1 + y);
                zAnimOffset = (int) (zAnimOffset / j1 + z);
                return;
            } else {
                xAnimOffset = (int) x;
                yAnimOffset = (int) y;
                zAnimOffset = (int) z;
                return;
            }
        }
        if (animationType == 1) {
            for (int k1 = 0; k1 < i1; k1++) {
                int l2 = skin[k1];
                if (l2 < vertex_skin.length) {
                    int ai1[] = vertex_skin[l2];
                    for (int i4 = 0; i4 < ai1.length; i4++) {
                        int j5 = ai1[i4];
                        vertex_x[j5] += x;
                        vertex_y[j5] += y;
                        vertex_z[j5] += z;
                    }

                }
            }

            return;
        }
        if (animationType == 2) {
            for (int l1 = 0; l1 < i1; l1++) {
                int i3 = skin[l1];
                if (i3 < vertex_skin.length) {
                    int auid[] = vertex_skin[i3];
                    for (int j4 = 0; j4 < auid.length; j4++) {
                        int k5 = auid[j4];
                        vertex_x[k5] -= xAnimOffset;
                        vertex_y[k5] -= yAnimOffset;
                        vertex_z[k5] -= zAnimOffset;
                        int k6 = (x & 0xff) * 8;
                        int l6 = (y & 0xff) * 8;
                        int i7 = (z & 0xff) * 8;
                        if (i7 != 0) {
                            int j7 = SINE[i7];
                            int i8 = COSINE[i7];
                            int l8 = vertex_y[k5] * j7 + vertex_x[k5] * i8 >> 16;
                            vertex_y[k5] = vertex_y[k5] * i8 - vertex_x[k5] * j7 >> 16;
                            vertex_x[k5] = l8;
                        }
                        if (k6 != 0) {
                            int k7 = SINE[k6];
                            int j8 = COSINE[k6];
                            int i9 = vertex_y[k5] * j8 - vertex_z[k5] * k7 >> 16;
                            vertex_z[k5] = vertex_y[k5] * k7 + vertex_z[k5] * j8 >> 16;
                            vertex_y[k5] = i9;
                        }
                        if (l6 != 0) {
                            int l7 = SINE[l6];
                            int k8 = COSINE[l6];
                            int j9 = vertex_z[k5] * l7 + vertex_x[k5] * k8 >> 16;
                            vertex_z[k5] = vertex_z[k5] * k8 - vertex_x[k5] * l7 >> 16;
                            vertex_x[k5] = j9;
                        }
                        vertex_x[k5] += xAnimOffset;
                        vertex_y[k5] += yAnimOffset;
                        vertex_z[k5] += zAnimOffset;
                    }

                }
            }

            return;
        }
        if (animationType == 3) {
            for (int uid = 0; uid < i1; uid++) {
                int j3 = skin[uid];
                if (j3 < vertex_skin.length) {
                    int ai3[] = vertex_skin[j3];
                    for (int k4 = 0; k4 < ai3.length; k4++) {
                        int l5 = ai3[k4];
                        vertex_x[l5] -= xAnimOffset;
                        vertex_y[l5] -= yAnimOffset;
                        vertex_z[l5] -= zAnimOffset;
                        vertex_x[l5] = (int) ((vertex_x[l5] * x) / 128);
                        vertex_y[l5] = (int) ((vertex_y[l5] * y) / 128);
                        vertex_z[l5] = (int) ((vertex_z[l5] * z) / 128);
                        vertex_x[l5] += xAnimOffset;
                        vertex_y[l5] += yAnimOffset;
                        vertex_z[l5] += zAnimOffset;
                    }

                }
            }

            return;
        }
        if (animationType == 5 && face_skin != null && face_alpha != null) {
            for (int j2 = 0; j2 < i1; j2++) {
                int k3 = skin[j2];
                if (k3 < face_skin.length) {
                    int ai4[] = face_skin[k3];
                    for (int l4 = 0; l4 < ai4.length; l4++) {
                        int i6 = ai4[l4];
                        face_alpha[i6] += x * 8;
                        if (face_alpha[i6] < 0)
                            face_alpha[i6] = 0;
                        if (face_alpha[i6] > 255)
                            face_alpha[i6] = 255;
                    }

                }
            }

        }
    }

    public void interpolate(int frameId) {
        if (vertex_skin == null)
            return;

        if (frameId == -1)
            return;

        Animation frame = Animation.get(frameId);
        if (frame == null)
            return;

        Skins skin = frame.base;
        xAnimOffset = 0;
        yAnimOffset = 0;
        zAnimOffset = 0;

        for (int index = 0; index < frame.transformationCount; index++) {
            int pos = frame.transformationIndices[index];
            //Change skin.cache[pos] to skin.cache[2] for funny animations
            transform(skin.transformationType[pos], skin.skinList[pos], frame.transformX[index], frame.transformY[index], frame.transformZ[index]);
        }
    }
    
    public void mix(int[] label, int idle, int current) {
        if (current == -1)
            return;

        if (label == null || idle == -1) {
            interpolate(current);
            return;
        }
        Animation anim = Animation.get(current);
        if (anim == null)
            return;

        Animation skin = Animation.get(idle);
        if (skin == null) {
            interpolate(current);
            return;
        }
        Skins list = anim.base;
        xAnimOffset = 0;
        yAnimOffset = 0;
        zAnimOffset = 0;
        int id = 0;
        int table = label[id++];
        for (int index = 0; index < anim.transformationCount; index++) {
            int condition;
            for (condition = anim.transformationIndices[index]; condition > table; table = label[id++])
                ;//empty
            if (condition != table || list.transformationType[condition] == 0)
                transform(list.transformationType[condition], list.skinList[condition], anim.transformX[index], anim.transformY[index], anim.transformZ[index]);
        }
        xAnimOffset = 0;
        yAnimOffset = 0;
        zAnimOffset = 0;
        id = 0;
        table = label[id++];
        for (int index = 0; index < skin.transformationCount; index++) {
            int condition;
            for (condition = skin.transformationIndices[index]; condition > table; table = label[id++])
                ;//empty
            if (condition == table || list.transformationType[condition] == 0)
                transform(list.transformationType[condition], list.skinList[condition], skin.transformX[index], skin.transformY[index], skin.transformZ[index]);

        }
    }

    public void rotate_90() {
        for (int point = 0; point < vertices; point++) {
            int k = vertex_x[point];
            vertex_x[point] = vertex_z[point];
            vertex_z[point] = -k;
        }
    }

    public void leanOverX(int i) {
        int k = SINE[i];
        int l = COSINE[i];
        for (int point = 0; point < vertices; point++) {
            int j1 = vertex_y[point] * l - vertex_z[point] * k >> 16;
            vertex_z[point] = vertex_y[point] * k + vertex_z[point] * l >> 16;
            vertex_y[point] = j1;
        }
    }

    public void translate(int x, int y, int z) {
        for (int point = 0; point < vertices; point++) {
            vertex_x[point] += x;
            vertex_y[point] += y;
            vertex_z[point] += z;
        }
    }

    public void recolor(int found, int replace) {
        if(face_color != null) {
            for (int face = 0; face < faces; face++) {
                if (face_color[face] == (short) found) {
                    face_color[face] = (short) replace;
                }
            }
        }
    }

    public void retexture(short found, short replace) {
        if(face_material != null) {
            for (int face = 0; face < faces; face++) {
                if (face_material[face] == found) {
                    face_material[face] = replace;
                }
            }
        }
    }

    public void invert() {
        for (int index = 0; index < vertices; index++)
            vertex_z[index] = -vertex_z[index];

        for (int face = 0; face < faces; face++) {
            int l = triangle_edge_a[face];
            triangle_edge_a[face] = triangle_edge_c[face];
            triangle_edge_c[face] = l;
        }
    }

    public void scale(int i, int j, int l) {
        for (int index = 0; index < vertices; index++) {
            vertex_x[index] = (vertex_x[index] * i) / 128;
            vertex_y[index] = (vertex_y[index] * l) / 128;
            vertex_z[index] = (vertex_z[index] * j) / 128;
        }
    }

    public void light(int i, int j, int k, int l, int i1, boolean flag) {
        light(i, j, k, l, i1, flag, false);
    }

    public void light(int i, int j, int k, int l, int i1, boolean flag, boolean player) {
        int j1 = (int) Math.sqrt(k * k + l * l + i1 * i1);
        int k1 = j * j1 >> 8;
        faceHslA = new int[faces];
        faceHslB = new int[faces];
        faceHslC = new int[faces];
        if (super.normals == null) {
            super.normals = new Vertex[vertices];
            for (int index = 0; index < vertices; index++)
                super.normals[index] = new Vertex();

        }
        for (int face = 0; face < faces; face++) {
            int j2 = triangle_edge_a[face];
            int l2 = triangle_edge_b[face];
            int i3 = triangle_edge_c[face];
            int j3 = vertex_x[l2] - vertex_x[j2];
            int k3 = vertex_y[l2] - vertex_y[j2];
            int l3 = vertex_z[l2] - vertex_z[j2];
            int i4 = vertex_x[i3] - vertex_x[j2];
            int j4 = vertex_y[i3] - vertex_y[j2];
            int k4 = vertex_z[i3] - vertex_z[j2];
            int l4 = k3 * k4 - j4 * l3;
            int i5 = l3 * i4 - k4 * j3;
            int j5;
            for (j5 = j3 * j4 - i4 * k3; l4 > 8192 || i5 > 8192 || j5 > 8192 || l4 < -8192 || i5 < -8192 || j5 < -8192; j5 >>= 1) {
                l4 >>= 1;
                i5 >>= 1;
            }
            int k5 = (int) Math.sqrt(l4 * l4 + i5 * i5 + j5 * j5);
            if (k5 <= 0)
                k5 = 1;

            l4 = (l4 * 256) / k5;
            i5 = (i5 * 256) / k5;
            j5 = (j5 * 256) / k5;

            int texture_id;
            int type;
            if (render_type != null)
                type = render_type[face];
            else
                type = 0;

            if (face_material == null) {
                texture_id = -1;
            } else {
                texture_id = face_material[face];
            }

            if (render_type == null || (render_type[face] & 1) == 0) {
                Vertex class33_2 = super.normals[j2];
                class33_2.x += l4;
                class33_2.y += i5;
                class33_2.z += j5;
                class33_2.magnitude++;
                class33_2 = super.normals[l2];
                class33_2.x += l4;
                class33_2.y += i5;
                class33_2.z += j5;
                class33_2.magnitude++;
                class33_2 = super.normals[i3];
                class33_2.x += l4;
                class33_2.y += i5;
                class33_2.z += j5;
                class33_2.magnitude++;
            } else {
                if (texture_id != -1) {
                    type = 2;
                }
                int light = i + (k * l4 + l * i5 + i1 * j5) / (k1 + k1 / 2);
                faceHslA[face] = method481(face_color[face], light, type);
            }
        }
        if (flag) {
            method480(i, k1, k, l, i1, player);
            calc_diagonals();//method466
        } else {
            gouraud_vertex = new Vertex[vertices];
            for (int point = 0; point < vertices; point++) {
                Vertex class33 = super.normals[point];
                Vertex class33_1 = gouraud_vertex[point] = new Vertex();
                class33_1.x = class33.x;
                class33_1.y = class33.y;
                class33_1.z = class33.z;
                class33_1.magnitude = class33.magnitude;
            }
            calculatebone_skin(21073);
        }
    }

    public final void doShading(int i, int j, int k, int l, int i1) {
        method480(i, j, k, l, i1, false);
    }

    public final void method480(int i, int j, int k, int l, int i1, boolean player) {
        for (int j1 = 0; j1 < faces; j1++) {
            int k1 = triangle_edge_a[j1];
            int i2 = triangle_edge_b[j1];
            int j2 = triangle_edge_c[j1];
            int texture_id;
            if(face_material == null) {
                texture_id = -1;
            } else {
                texture_id = face_material[j1];
                if (player) {
                    if(face_alpha != null && face_color != null) {
                        if(face_color[j1] == 0 && face_render_priorities[j1] == 0) {
                            if(render_type[j1] == 2 && face_material[j1] == -1) {
                                face_alpha[j1] = 255;
                            }
                        }
                    } else if(face_alpha == null) {
                        if(face_color[j1] == 0 && face_render_priorities[j1] == 0) {
                            if(face_material[j1] == -1) {
                                face_alpha = new int[faces];
                                if(render_type[j1] == 2) {
                                    face_alpha[j1] = 255;
                                }
                            }
                        }
                    }
                }
            }

            if (render_type == null) {
                int type;
                if(texture_id != -1) {
                    type = 2;
                } else {
                    type = 1;
                }
                int hsl = face_color[j1] & 0xffff;
                Vertex vertex = super.normals[k1];
                int light = i + (k * vertex.x + l * vertex.y + i1 * vertex.z) / (j * vertex.magnitude);
                faceHslA[j1] = method481(hsl, light, type);
                vertex = super.normals[i2];
                light = i + (k * vertex.x + l * vertex.y + i1 * vertex.z) / (j * vertex.magnitude);
                faceHslB[j1] = method481(hsl, light, type);
                vertex = super.normals[j2];
                light = i + (k * vertex.x + l * vertex.y + i1 * vertex.z) / (j * vertex.magnitude);
                faceHslC[j1] = method481(hsl, light, type);
            } else if ((render_type[j1] & 1) == 0) {
                int type = render_type[j1];
                if(texture_id != -1) {
                    type = 2;
                }
                int hsl = face_color[j1] & 0xffff;
                Vertex vertex = super.normals[k1];
                int light = i + (k * vertex.x + l * vertex.y + i1 * vertex.z) / (j * vertex.magnitude);
                faceHslA[j1] = method481(hsl, light, type);
                vertex = super.normals[i2];
                light = i + (k * vertex.x + l * vertex.y + i1 * vertex.z) / (j * vertex.magnitude);
                faceHslB[j1] = method481(hsl, light, type);
                vertex = super.normals[j2];
                light = i + (k * vertex.x + l * vertex.y + i1 * vertex.z) / (j * vertex.magnitude);
                faceHslC[j1] = method481(hsl, light, type);
            }
        }

        super.normals = null;
        gouraud_vertex = null;
        bone_skin = null;
        muscle_skin = null;
        face_color = null;
    }

    public static final int method481(int i, int j, int k) {
        if (i == 65535)
            return 0;

        if ((k & 2) == 2) {
            if (j < 0)
                j = 0;
            else if (j > 127)
                j = 127;

            j = 127 - j;
            return j;
        }

        j = j * (i & 0x7f) >> 7;
        if (j < 2)
            j = 2;
        else if (j > 126)
            j = 126;

        return (i & 0xff80) + j;
    }

    //inventory / widget model rendering (render_2D)
    public final void render_2D(int roll, int yaw, int pitch, int start_x, int start_y, int zoom) {
        int depth = 0;
        int center_x = Rasterizer3D.center_x;
        int center_y = Rasterizer3D.center_y;
        int depth_sin = SINE[depth];
        int depth_cos = COSINE[depth];
        int roll_sin = SINE[roll];
        int roll_cos = COSINE[roll];
        int yaw_sin = SINE[yaw];
        int yaw_cos = COSINE[yaw];
        int pitch_sin = SINE[pitch];
        int pitch_cos = COSINE[pitch];
        int position = start_y * pitch_sin + zoom * pitch_cos >> 16;
        for (int index = 0; index < vertices; index++) {
            int x = vertex_x[index];
            int y = vertex_y[index];
            int z = vertex_z[index];
            if (yaw != 0) {
                int rotated_x = y * yaw_sin + x * yaw_cos >> 16;
                y = y * yaw_cos - x * yaw_sin >> 16;
                x = rotated_x;
            }
            if (depth != 0) {
                int rotated_y = y * depth_cos - z * depth_sin >> 16;
                z = y * depth_sin + z * depth_cos >> 16;
                y = rotated_y;
            }
            if (roll != 0) {
                int rotated_z = z * roll_sin + x * roll_cos >> 16;
                z = z * roll_cos - x * roll_sin >> 16;
                x = rotated_z;
            }
            x += start_x;
            y += start_y;
            z += zoom;

            int y_offset = y * pitch_cos - z * pitch_sin >> 16;
            z = y * pitch_sin + z * pitch_cos >> 16;
            y = y_offset;

            projected_vertex_z[index] = z - position;
            projected_vertex_x[index] = center_x + (x * SceneGraph.focalLength) / z;
            projected_vertex_y[index] = center_y + (y * SceneGraph.focalLength) / z;
            if (texture_faces > 0) {
                camera_vertex_x[index] = x;
                camera_vertex_y[index] = y;
                camera_vertex_z[index] = z;
            }

        }
        try {
            method483(false, false,  0);
        } catch (Exception _ex) {
            _ex.printStackTrace();
            System.out.println("Could not rotate and project item!");
        }
    }
    public static final int VIEW_DISTANCE = 9500; //3500 or 4500, 3500 provides better performance.

    @Override
    public final void render_3D(int orientation, int cos_y, int sin_y, int sin_x, int cos_x, int start_x, int start_y, int depth, long uid) {
        int scene_x = depth * cos_x - start_x * sin_x >> 16;
        int scene_y = start_y * cos_y + scene_x * sin_y >> 16;
        int dimension_sin_y = diagonal_2D * sin_y >> 16;
        int pos = scene_y + dimension_sin_y;
        if (pos <= 50 || scene_y >= VIEW_DISTANCE)
            return;

        int x_rot = depth * sin_x + start_x * cos_x >> 16;
        int obj_x = (x_rot - diagonal_2D) * SceneGraph.focalLength;
        if (obj_x / pos >= Rasterizer2D.viewport_center_y)
            return;

        int obj_width = (x_rot + diagonal_2D) * SceneGraph.focalLength;
        if (obj_width / pos <= -Rasterizer2D.viewport_center_y)
            return;

        int y_rot = start_y * sin_y - scene_x * cos_y >> 16;
        int dimension_cos_y = diagonal_2D * cos_y >> 16;
        int obj_height = (y_rot + dimension_cos_y) * SceneGraph.focalLength;
        if (obj_height / pos <= -Rasterizer2D.viewport_center_x)
            return;


        int offset = dimension_cos_y + (super.model_height * sin_y >> 16);
        int obj_y = (y_rot - offset) * SceneGraph.focalLength;
        if (obj_y / pos >= Rasterizer2D.viewport_center_x)
            return;


        int size = dimension_sin_y + (super.model_height * cos_y >> 16);
        boolean flag = false;
        if (scene_y - size <= 50)
            flag = true;

        boolean flag1 = false;
        if (uid > 0 && obj_exists) {
            int obj_height_offset = scene_y - offset;
            if (obj_height_offset <= 50)
                obj_height_offset = 50;
            if (x_rot > 0) {
                obj_x /= pos;
                obj_width /= obj_height_offset;
            } else {
                obj_width /= pos;
                obj_x /= obj_height_offset;
            }
            if (y_rot > 0) {
                obj_y /= pos;
                obj_height /= obj_height_offset;
            } else {
                obj_height /= pos;
                obj_y /= obj_height_offset;
            }
            int mouse_x = anInt1685 - Rasterizer3D.center_x;
            int mouse_y = anInt1686 - Rasterizer3D.center_y;
            if (mouse_x > obj_x && mouse_x < obj_width && mouse_y > obj_y && mouse_y < obj_height)
                if (within_tile)
                    anIntArray1688[anInt1687++] = uid;
                else
                    flag1 = true;
        }
        int center_x = Rasterizer3D.center_x;
        int center_y = Rasterizer3D.center_y;
        int sine_x = 0;
        int cosine_x = 0;
        if (orientation != 0) {
            sine_x = SINE[orientation];
            cosine_x = COSINE[orientation];
        }
        for (int index = 0; index < vertices; index++) {
            int raster_x = vertex_x[index];
            int raster_y = vertex_y[index];
            int raster_z = vertex_z[index];

            if (orientation != 0) {
                int rotated_x = raster_z * sine_x + raster_x * cosine_x >> 16;
                raster_z = raster_z * cosine_x - raster_x * sine_x >> 16;
                raster_x = rotated_x;

            }
            raster_x += start_x;
            raster_y += start_y;
            raster_z += depth;

            int position = raster_z * sin_x + raster_x * cos_x >> 16;
            raster_z = raster_z * cos_x - raster_x * sin_x >> 16;
            raster_x = position;

            position = raster_y * sin_y - raster_z * cos_y >> 16;
            raster_z = raster_y * cos_y + raster_z * sin_y >> 16;
            raster_y = position;


            projected_vertex_z[index] = raster_z - scene_y;
            if (raster_z >= 50) {
                projected_vertex_x[index] = (center_x + (raster_x * SceneGraph.focalLength) / raster_z);
                projected_vertex_y[index] = (center_y + (raster_y * SceneGraph.focalLength) / raster_z);
            } else {
                projected_vertex_x[index] = -5000;
                flag = true;
            }
            if (flag || texture_faces > 0) {
                camera_vertex_x[index] = raster_x;
                camera_vertex_y[index] = raster_y;
                camera_vertex_z[index] = raster_z;
            }
        }
        try {
            method483(flag, flag1,  uid);
            return;
        } catch (Exception _ex) {
            return;
        }
    }

    private void method483(boolean flag, boolean flag1, long uid) {

        for (int j = 0; j < scene_depth; j++)
            depthListIndices[j] = 0;

        for (int face = 0; face < faces; face++) {
            if (render_type == null || render_type[face] != -1) {
                int a = triangle_edge_a[face];
                int b = triangle_edge_b[face];
                int c = triangle_edge_c[face];
                int x_a = projected_vertex_x[a];
                int x_b = projected_vertex_x[b];
                int x_c = projected_vertex_x[c];
                if (flag && (x_a == -5000 || x_b == -5000 || x_c == -5000)) {
                    outOfReach[face] = true;
                    int j5 = (projected_vertex_z[a] + projected_vertex_z[b] + projected_vertex_z[c]) / 3 + diagonal_3D;
                    faceLists[j5][depthListIndices[j5]++] = face;
                } else {
                    if (flag1 && entered_clickbox(anInt1685, anInt1686, projected_vertex_y[a], projected_vertex_y[b], projected_vertex_y[c], x_a, x_b, x_c)) {
                        anIntArray1688[anInt1687++] = uid;
                        flag1 = false;
                    }
                    if ((x_a - x_b) * (projected_vertex_y[c] - projected_vertex_y[b]) - (projected_vertex_y[a] - projected_vertex_y[b]) * (x_c - x_b) > 0) {
                        outOfReach[face] = false;
                        if (x_a < 0 || x_b < 0 || x_c < 0 || x_a > Rasterizer2D.center_x || x_b > Rasterizer2D.center_x || x_c > Rasterizer2D.center_x)
                            hasAnEdgeToRestrict[face] = true;
                        else
                            hasAnEdgeToRestrict[face] = false;

                        int k5 = (projected_vertex_z[a] + projected_vertex_z[b] + projected_vertex_z[c]) / 3 + diagonal_3D;
                        faceLists[k5][depthListIndices[k5]++] = face;
                    }
                }
            }
        }
        if (face_render_priorities == null) {
            for (int i1 = scene_depth - 1; i1 >= 0; i1--) {
                int l1 = depthListIndices[i1];
                if (l1 > 0) {
                    int ai[] = faceLists[i1];
                    for (int j3 = 0; j3 < l1; j3++)
                        rasterize(ai[j3]);

                }
            }
            return;
        }
        for (int j1 = 0; j1 < 12; j1++) {
            anIntArray1673[j1] = 0;
            anIntArray1677[j1] = 0;
        }
        for (int i2 = scene_depth - 1; i2 >= 0; i2--) {
            int k2 = depthListIndices[i2];
            if (k2 > 0) {
                int ai1[] = faceLists[i2];
                for (int i4 = 0; i4 < k2; i4++) {
                    int l4 = ai1[i4];
                    byte l5 = face_render_priorities[l4];
                    int j6 = anIntArray1673[l5]++;
                    anIntArrayArray1674[l5][j6] = l4;
                    if (l5 < 10)
                        anIntArray1677[l5] += i2;
                    else if (l5 == 10)
                        anIntArray1675[j6] = i2;
                    else
                        anIntArray1676[j6] = i2;
                }

            }
        }

        int l2 = 0;
        if (anIntArray1673[1] > 0 || anIntArray1673[2] > 0)
            l2 = (anIntArray1677[1] + anIntArray1677[2]) / (anIntArray1673[1] + anIntArray1673[2]);
        int k3 = 0;
        if (anIntArray1673[3] > 0 || anIntArray1673[4] > 0)
            k3 = (anIntArray1677[3] + anIntArray1677[4]) / (anIntArray1673[3] + anIntArray1673[4]);
        int j4 = 0;
        if (anIntArray1673[6] > 0 || anIntArray1673[8] > 0)
            j4 = (anIntArray1677[6] + anIntArray1677[8]) / (anIntArray1673[6] + anIntArray1673[8]);

        int i6 = 0;
        int k6 = anIntArray1673[10];
        int auid[] = anIntArrayArray1674[10];
        int ai3[] = anIntArray1675;
        if (i6 == k6) {
            i6 = 0;
            k6 = anIntArray1673[11];
            auid = anIntArrayArray1674[11];
            ai3 = anIntArray1676;
        }
        int i5;
        if (i6 < k6)
            i5 = ai3[i6];
        else
            i5 = -1000;

        for (int l6 = 0; l6 < 10; l6++) {
            while (l6 == 0 && i5 > l2) {
                rasterize(auid[i6++]);
                if (i6 == k6 && auid != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    auid = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            while (l6 == 3 && i5 > k3) {
                rasterize(auid[i6++]);
                if (i6 == k6 && auid != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    auid = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            while (l6 == 5 && i5 > j4) {
                rasterize(auid[i6++]);
                if (i6 == k6 && auid != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    auid = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            int i7 = anIntArray1673[l6];
            int ai4[] = anIntArrayArray1674[l6];
            for (int j7 = 0; j7 < i7; j7++)
                rasterize(ai4[j7]);

        }
        while (i5 != -1000) {
            rasterize(auid[i6++]);
            if (i6 == k6 && auid != anIntArrayArray1674[11]) {
                i6 = 0;
                auid = anIntArrayArray1674[11];
                k6 = anIntArray1673[11];
                ai3 = anIntArray1676;
            }
            if (i6 < k6)
                i5 = ai3[i6];
            else
                i5 = -1000;
        }
    }

    private final void rasterize(int face) {
        if (outOfReach[face]) {
            method485(face);
            return;
        }
        int j = triangle_edge_a[face];
        int k = triangle_edge_b[face];
        int l = triangle_edge_c[face];
        Rasterizer3D.testX = hasAnEdgeToRestrict[face];
        if (face_alpha == null)
            Rasterizer3D.alpha = 0;
        else
            Rasterizer3D.alpha = face_alpha[face] & 0xff;

        int type;
        if (render_type == null)
            type = 0;
        else
            type = render_type[face] & 3;

        if (!Rasterizer3D.forceRepeat) {
            if (repeatTexture == null) {
                Rasterizer3D.repeatTexture = false;
            } else {
                Rasterizer3D.repeatTexture = repeatTexture[face];
            }
        } else {
            Rasterizer3D.repeatTexture = true;
        }

        if (face_material != null && face_material[face] != -1) {
            int texture_a = j;
            int texture_b = k;
            int texture_c = l;
            if (face_texture != null && face_texture[face] != -1) {
                int coordinate = face_texture[face] & 0xff;
                texture_a = triangle_texture_edge_a[coordinate];
                texture_b = triangle_texture_edge_b[coordinate];
                texture_c = triangle_texture_edge_c[coordinate];
            }
            if (faceHslC[face] == -1 || type == 3) {
                Rasterizer3D.drawTexturedTriangle(
                    projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l],
                    projected_vertex_x[j], projected_vertex_x[k], projected_vertex_x[l],
                    faceHslA[face], faceHslA[face], faceHslA[face],
                    camera_vertex_x[texture_a], camera_vertex_x[texture_b], camera_vertex_x[texture_c],
                    camera_vertex_y[texture_a], camera_vertex_y[texture_b], camera_vertex_y[texture_c],
                    camera_vertex_z[texture_a], camera_vertex_z[texture_b], camera_vertex_z[texture_c],
                    face_material[face]);
            } else {
                Rasterizer3D.drawTexturedTriangle(
                    projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l],
                    projected_vertex_x[j], projected_vertex_x[k], projected_vertex_x[l],
                    faceHslA[face], faceHslB[face], faceHslC[face],
                    camera_vertex_x[texture_a], camera_vertex_x[texture_b], camera_vertex_x[texture_c],
                    camera_vertex_y[texture_a], camera_vertex_y[texture_b], camera_vertex_y[texture_c],
                    camera_vertex_z[texture_a], camera_vertex_z[texture_b], camera_vertex_z[texture_c],
                    face_material[face]);
            }
        } else {
            if (type == 0) {
                Rasterizer3D.drawShadedTriangle(projected_vertex_y[j], projected_vertex_y[k],
                    projected_vertex_y[l], projected_vertex_x[j], projected_vertex_x[k],
                    projected_vertex_x[l], faceHslA[face], faceHslB[face], faceHslC[face]);
                return;
            }
            if (type == 1) {
                Rasterizer3D.drawFlatTriangle(projected_vertex_y[j], projected_vertex_y[k], projected_vertex_y[l], projected_vertex_x[j], projected_vertex_x[k], projected_vertex_x[l], modelIntArray3[faceHslA[face]]);
                return;
            }
        }
    }

    private final void method485(int i) {
        int j = Rasterizer3D.center_x;
        int k = Rasterizer3D.center_y;
        int l = 0;
        int i1 = triangle_edge_a[i];
        int j1 = triangle_edge_b[i];
        int k1 = triangle_edge_c[i];
        int l1 = camera_vertex_z[i1];
        int uid = camera_vertex_z[j1];
        int j2 = camera_vertex_z[k1];
        if (l1 >= 50) {
            anIntArray1678[l] = projected_vertex_x[i1];
            anIntArray1679[l] = projected_vertex_y[i1];
            anIntArray1680[l++] = faceHslA[i];
        } else {
            int k2 = camera_vertex_x[i1];
            int k3 = camera_vertex_y[i1];
            int k4 = faceHslA[i];
            if (j2 >= 50) {
                int k5 = (50 - l1) * modelIntArray4[j2 - l1];
                anIntArray1678[l] = j + (k2 + ((camera_vertex_x[k1] - k2) * k5 >> 16) << SceneGraph.focalLength) / 50;
                anIntArray1679[l] = k + (k3 + ((camera_vertex_y[k1] - k3) * k5 >> 16) << SceneGraph.focalLength) / 50;
                anIntArray1680[l++] = k4 + ((faceHslC[i] - k4) * k5 >> 16);
            }
            if (uid >= 50) {
                int l5 = (50 - l1) * modelIntArray4[uid - l1];
                anIntArray1678[l] = j + (k2 + ((camera_vertex_x[j1] - k2) * l5 >> 16) << SceneGraph.focalLength) / 50;
                anIntArray1679[l] = k + (k3 + ((camera_vertex_y[j1] - k3) * l5 >> 16) << SceneGraph.focalLength) / 50;
                anIntArray1680[l++] = k4 + ((faceHslB[i] - k4) * l5 >> 16);
            }
        }
        if (uid >= 50) {
            anIntArray1678[l] = projected_vertex_x[j1];
            anIntArray1679[l] = projected_vertex_y[j1];
            anIntArray1680[l++] = faceHslB[i];
        } else {
            int l2 = camera_vertex_x[j1];
            int l3 = camera_vertex_y[j1];
            int l4 = faceHslB[i];
            if (l1 >= 50) {
                int i6 = (50 - uid) * modelIntArray4[l1 - uid];
                anIntArray1678[l] = j + (l2 + ((camera_vertex_x[i1] - l2) * i6 >> 16) << SceneGraph.focalLength) / 50;
                anIntArray1679[l] = k + (l3 + ((camera_vertex_y[i1] - l3) * i6 >> 16) << SceneGraph.focalLength) / 50;
                anIntArray1680[l++] = l4 + ((faceHslA[i] - l4) * i6 >> 16);
            }
            if (j2 >= 50) {
                int j6 = (50 - uid) * modelIntArray4[j2 - uid];
                anIntArray1678[l] = j + (l2 + ((camera_vertex_x[k1] - l2) * j6 >> 16) << SceneGraph.focalLength) / 50;
                anIntArray1679[l] = k + (l3 + ((camera_vertex_y[k1] - l3) * j6 >> 16) << SceneGraph.focalLength) / 50;
                anIntArray1680[l++] = l4 + ((faceHslC[i] - l4) * j6 >> 16);
            }
        }
        if (j2 >= 50) {
            anIntArray1678[l] = projected_vertex_x[k1];
            anIntArray1679[l] = projected_vertex_y[k1];
            anIntArray1680[l++] = faceHslC[i];
        } else {
            int i3 = camera_vertex_x[k1];
            int i4 = camera_vertex_y[k1];
            int i5 = faceHslC[i];
            if (uid >= 50) {
                int k6 = (50 - j2) * modelIntArray4[uid - j2];
                anIntArray1678[l] = j + (i3 + ((camera_vertex_x[j1] - i3) * k6 >> 16) << SceneGraph.focalLength) / 50;
                anIntArray1679[l] = k + (i4 + ((camera_vertex_y[j1] - i4) * k6 >> 16) << SceneGraph.focalLength) / 50;
                anIntArray1680[l++] = i5 + ((faceHslB[i] - i5) * k6 >> 16);
            }
            if (l1 >= 50) {
                int l6 = (50 - j2) * modelIntArray4[l1 - j2];
                anIntArray1678[l] = j + (i3 + ((camera_vertex_x[i1] - i3) * l6 >> 16) << SceneGraph.focalLength) / 50;
                anIntArray1679[l] = k + (i4 + ((camera_vertex_y[i1] - i4) * l6 >> 16) << SceneGraph.focalLength) / 50;
                anIntArray1680[l++] = i5 + ((faceHslA[i] - i5) * l6 >> 16);
            }
        }
        int j3 = anIntArray1678[0];
        int j4 = anIntArray1678[1];
        int j5 = anIntArray1678[2];
        int i7 = anIntArray1679[0];
        int j7 = anIntArray1679[1];
        int k7 = anIntArray1679[2];
        if ((j3 - j4) * (k7 - j7) - (i7 - j7) * (j5 - j4) > 0) {
            Rasterizer3D.testX = false;
            int texture_a = i1;
            int texture_b = j1;
            int texture_c = k1;
            if (l == 3) {
                if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Rasterizer2D.center_x || j4 > Rasterizer2D.center_x || j5 > Rasterizer2D.center_x)
                    Rasterizer3D.testX = true;

                int l7;
                if (render_type == null)
                    l7 = 0;
                else
                    l7 = render_type[i] & 3;

                if (face_material != null && face_material[i] != -1) {
                    if (face_texture != null && face_texture[i] != -1) {
                        int coordinate = face_texture[i] & 0xff;
                        texture_a = triangle_texture_edge_a[coordinate];
                        texture_b = triangle_texture_edge_b[coordinate];
                        texture_c = triangle_texture_edge_c[coordinate];
                    }
                    if (faceHslC[i] == -1) {
                        Rasterizer3D.drawTexturedTriangle(
                            i7, j7, k7,
                            j3, j4, j5,
                            faceHslA[i], faceHslA[i], faceHslA[i],
                            camera_vertex_x[texture_a], camera_vertex_x[texture_b], camera_vertex_x[texture_c],
                            camera_vertex_y[texture_a], camera_vertex_y[texture_b], camera_vertex_y[texture_c],
                            camera_vertex_z[texture_a], camera_vertex_z[texture_b], camera_vertex_z[texture_c],
                            face_material[i]);
                    } else {
                        Rasterizer3D.drawTexturedTriangle(
                            i7, j7, k7,
                            j3, j4, j5,
                            anIntArray1680[0], anIntArray1680[1], anIntArray1680[2],
                            camera_vertex_x[texture_a], camera_vertex_x[texture_b], camera_vertex_x[texture_c],
                            camera_vertex_y[texture_a], camera_vertex_y[texture_b], camera_vertex_y[texture_c],
                            camera_vertex_z[texture_a], camera_vertex_z[texture_b], camera_vertex_z[texture_c],
                            face_material[i]);
                    }
                } else {
                    if (l7 == 0)
                        Rasterizer3D.drawShadedTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1], anIntArray1680[2]);

                    else if (l7 == 1)
                        Rasterizer3D.drawFlatTriangle(i7, j7, k7, j3, j4, j5, modelIntArray3[faceHslA[i]]);
                }
            }
            if (l == 4) {
                if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Rasterizer2D.center_x || j4 > Rasterizer2D.center_x || j5 > Rasterizer2D.center_x || anIntArray1678[3] < 0 || anIntArray1678[3] > Rasterizer2D.center_x)
                    Rasterizer3D.testX = true;
                int type;
                if (render_type == null)
                    type = 0;
                else
                    type = render_type[i] & 3;

                if (face_material != null && face_material[i] != -1) {
                    if (face_texture != null && face_texture[i] != -1) {
                        int coordinate = face_texture[i] & 0xff;
                        texture_a = triangle_texture_edge_a[coordinate];
                        texture_b = triangle_texture_edge_b[coordinate];
                        texture_c = triangle_texture_edge_c[coordinate];
                    }
                    if (faceHslC[i] == -1) {
                        Rasterizer3D.drawTexturedTriangle(
                            i7, j7, k7,
                            j3, j4, j5,
                            faceHslA[i], faceHslA[i], faceHslA[i],
                            camera_vertex_x[texture_a], camera_vertex_x[texture_b], camera_vertex_x[texture_c],
                            camera_vertex_y[texture_a], camera_vertex_y[texture_b], camera_vertex_y[texture_c],
                            camera_vertex_z[texture_a], camera_vertex_z[texture_b], camera_vertex_z[texture_c],
                            face_material[i]);
                        Rasterizer3D.drawTexturedTriangle(
                            i7, k7, anIntArray1679[3],
                            j3, j5, anIntArray1678[3],
                            faceHslA[i], faceHslA[i], faceHslA[i],
                            camera_vertex_x[texture_a], camera_vertex_x[texture_b], camera_vertex_x[texture_c],
                            camera_vertex_y[texture_a], camera_vertex_y[texture_b], camera_vertex_y[texture_c],
                            camera_vertex_z[texture_a], camera_vertex_z[texture_b], camera_vertex_z[texture_c],
                            face_material[i]);
                    } else {
                        Rasterizer3D.drawTexturedTriangle(
                            i7, j7, k7,
                            j3, j4, j5,
                            anIntArray1680[0], anIntArray1680[1], anIntArray1680[2],
                            camera_vertex_x[texture_a], camera_vertex_x[texture_b], camera_vertex_x[texture_c],
                            camera_vertex_y[texture_a], camera_vertex_y[texture_b], camera_vertex_y[texture_c],
                            camera_vertex_z[texture_a], camera_vertex_z[texture_b], camera_vertex_z[texture_c],
                            face_material[i]);
                        Rasterizer3D.drawTexturedTriangle(
                            i7, k7, anIntArray1679[3],
                            j3, j5, anIntArray1678[3],
                            anIntArray1680[0], anIntArray1680[2], anIntArray1680[3],
                            camera_vertex_x[texture_a], camera_vertex_x[texture_b], camera_vertex_x[texture_c],
                            camera_vertex_y[texture_a], camera_vertex_y[texture_b], camera_vertex_y[texture_c],
                            camera_vertex_z[texture_a], camera_vertex_z[texture_b], camera_vertex_z[texture_c],
                            face_material[i]);
                        return;
                    }
                } else {
                    if (type == 0) {
                        Rasterizer3D.drawShadedTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1], anIntArray1680[2]);
                        Rasterizer3D.drawShadedTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], anIntArray1680[0], anIntArray1680[2], anIntArray1680[3]);
                        return;
                    }
                    if (type == 1) {
                        int l8 = modelIntArray3[faceHslA[i]];
                        Rasterizer3D.drawFlatTriangle(i7, j7, k7, j3, j4, j5, l8);
                        Rasterizer3D.drawFlatTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], l8);
                        return;
                    }
                }
            }
        }
    }

    private final boolean entered_clickbox(int mouse_x, int mouse_y, int y_a, int y_b, int y_c, int x_a, int x_b, int x_c) {
        if (mouse_y < y_a && mouse_y < y_b && mouse_y < y_c)
            return false;
        if (mouse_y > y_a && mouse_y > y_b && mouse_y > y_c)
            return false;
        if (mouse_x < x_a && mouse_x < x_b && mouse_x < x_c)
            return false;
        return mouse_x <= x_a || mouse_x <= x_b || mouse_x <= x_c;
    }

    public int bufferOffset;

    public int getBufferOffset() {
        return bufferOffset;
    }

    public void setBufferOffset(int bufferOffset) {
        this.bufferOffset = bufferOffset;
    }

    public void completelyRecolor(int j) {
        for (int k = 0; k < faces; k++) {
            face_color[k] = (short) j;
        }
    }

    public void shadingRecolor(int j) {
        j += 100;
        int kcolor = 0;
        for (int k = 0; k < faces; k++) {
            kcolor = face_color[k];
            if (k + j >= 0) {
                face_color[k] = (short) (kcolor + j);
            }
        }
    }

    public void shadingRecolor2(int j) {

        for (int k = 0; k < faces; k++) {
            if (k + j >= 0) {
                face_color[k] = (short) (k + j);
            }
        }
    }

    public void shadingRecolor4(int j) {

        for (int k = 0; k < faces; k++) {
            if (j == 222) {
                System.out.println("k = " + face_color[k]);
            }
            if ((face_color[k] != 65535) && (k + j >= 0)) {
                face_color[k] += j;
            }
        }
    }

    public void shadingRecolor3(int j) {

        for (int k = 0; k < faces; k++) {
            int lastcolor = 1;
            if ((face_color[k] + j >= 10000)
                && (face_color[k] + j <= 90000)) {
                face_color[k] = (short) (k + j + lastcolor);
            }
            lastcolor++;
        }
    }

    public void modelRecoloring(int i, int j) {
        for (int k = 0; k < faces; k++) {
            if (face_color[k] == i) {
                face_color[k] = (short) j;
            }
        }
    }

}
