package com.valinor.game.content.title;

import com.valinor.game.content.mechanics.Censor;
import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.content.title.req.TitleRequirement;
import com.valinor.game.content.title.req.impl.other.*;
import com.valinor.game.content.title.req.impl.pvm.BossRequirement;
import com.valinor.game.content.title.req.impl.pvm.SlayerTaskRequirement;
import com.valinor.game.content.title.req.impl.pvm.WildernessBossRequirement;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import static com.valinor.game.content.title.TitleCategory.*;

public class TitlePlugin extends Interaction {

    private static final Logger log = LoggerFactory.getLogger(TitlePlugin.class);

    public static final TitlePlugin SINGLETON = new TitlePlugin();

    private static final int COLOUR_INDEX_START = 61608;
    private static final int MAX_COLOUR_COUNT = 30;
    private static final int COLOUR_INDEX_END = COLOUR_INDEX_START + MAX_COLOUR_COUNT;

    private static final int TITLE_INDEX_START = 61641;
    private static final int MAX_TITLE_COUNT = 30;
    private static final int TITLE_INDEX_END = TITLE_INDEX_START + MAX_TITLE_COUNT;

    private final Set<CategorizedTitles> categorizedTitles = new HashSet<>();

    private final List<TitleColour> colours = new ArrayList<>();

    public TitlePlugin() {
        init();
    }

    @Override
    public void onLogin(Player player) {
        displayTitles(player, PVMING);
        displayColours(player);

        player.getPacketSender().sendString(61396, getPreview(player));

        if(player.<String>getAttribOr(AttributeKey.TITLE,"").contains("<")) {
            player.clearAttrib(AttributeKey.TITLE);
            player.clearAttrib(AttributeKey.TITLE_COLOR);
        }
    }

    @Override
    public boolean handleButtonInteraction(Player player, int button) {
        if (TitleCategory.perform(title -> title.getButtonId() == button, title -> open(player, title))) {
            return true;
        }

        if (button <= TITLE_INDEX_END && button >= TITLE_INDEX_START) {
            int index = button - TITLE_INDEX_START;
            List<AvailableTitle> titles = getCategory(player.getCurrentCategory());

            if (index < titles.size()) {
                AvailableTitle title = titles.get(index);
                if (player.getCurrentSelectedTitle() != null) {
                    AvailableTitle oldTitle = player.getCurrentSelectedTitle();
                    int oldIndex = TITLE_INDEX_START + titles.indexOf(oldTitle);
                    player.getPacketSender().sendString(oldIndex, oldTitle.getName());
                }

                player.getPacketSender().sendString(61399, title.getRequirementList().stream().map(TitleRequirement::getRequirementName).collect(Collectors.joining("<br>")));

                //Custom title
                if (title.getName().equalsIgnoreCase("Custom")) {
                    player.setEnterSyntax(new EnterSyntax() {
                        @Override
                        public void handleSyntax(Player player, @NotNull String input) {
                            List<String> wordsToBlock = Arrays.asList("billionaire", "mod", "moderator", "support", "admin", "administrator");
                            boolean illegalWords = wordsToBlock.stream().anyMatch(w -> w.toLowerCase().contains(input));
                            boolean illegal = Censor.containsBadWords(input) || Utils.containsMessageFormattingTag(input) || Utils.hasInvalidChars(input) || illegalWords;
                            if (input.length() >= 2 && input.length() <= 12 && !illegal) {
                                CustomRequirement customRequirement = new CustomRequirement();
                                AvailableTitle custom = new AvailableTitle(input, player.getCurrentCategory()).addRequirement(customRequirement);
                                player.setCurrentSelectedTitle(custom);
                                player.getPacketSender().sendString(61396, getPreview(player));
                            } else {
                                player.message("We were unable to set this title. The title is either to long or contains illegal words.");
                            }
                        }
                    });
                    player.getPacketSender().sendEnterInputPrompt("What would you like your custom title to be?");
                    return true;
                }

                player.setCurrentSelectedTitle(title);
                player.getPacketSender().sendString(button, "<col=ffffff>" + title.getName());
                player.getPacketSender().sendString(61396, getPreview(player));
            }

            return true;
        }

        if (button <= COLOUR_INDEX_END && button >= COLOUR_INDEX_START) {
            int index = button - COLOUR_INDEX_START;

            if (index < colours.size()) {
                TitleColour titleColour = colours.get(index);
                if (player.getCurrentSelectedColour() != null) {
                    TitleColour oldColour = player.getCurrentSelectedColour();
                    int oldIndex = COLOUR_INDEX_START + colours.indexOf(oldColour);
                    player.getPacketSender().sendString(oldIndex, oldColour.getName());
                }
                player.getPacketSender().sendString(button, "<col=ffffff>" + titleColour.getName());
                player.setCurrentSelectedColour(titleColour);
                player.getPacketSender().sendString(61396, getPreview(player));
            }

            return true;
        }

        if (button == 61402) { //Set title
            AvailableTitle title = player.getCurrentSelectedTitle();
            if (title == null) {
                player.message("You have not selected a title.");
                return true;
            }

            TitleColour colour = player.getCurrentSelectedColour();
            if (colour == null) {
                player.message("You have not selected a colour.");
                return true;
            }

            if (!title.satisfies(player)) {
                player.message("You do not meet all the requirements for this title.");
                return true;
            }
            player.putAttrib(AttributeKey.TITLE, title.getName());
            player.putAttrib(AttributeKey.TITLE_COLOR, colour.getFormat());
            player.message("You have updated your title to '" + title.getName() + "'");
            return true;
        }
        if (button == 61502) { //Clear title
            player.putAttrib(AttributeKey.TITLE, "");
            player.putAttrib(AttributeKey.TITLE_COLOR, "");
            player.message("You clear your title.");
            return true;
        }
        return false;
    }

    public String getPreview(Player player) {
        if (player.getCurrentSelectedTitle() == null) {
            return "";
        }
        if (player.getCurrentSelectedColour() == null) {
            return player.getCurrentSelectedTitle().getName();
        }
        return player.getCurrentSelectedColour().getFormat() + player.getCurrentSelectedTitle().getName();
    }

    private void open(Player player, TitleCategory category) {
        displayTitles(player, category);
        player.getInterfaceManager().open(category.getInterfaceId());
    }

    private void displayColours(Player player) {
        for (int i = COLOUR_INDEX_START; i < COLOUR_INDEX_END; i++) {
            int index = i - COLOUR_INDEX_START;
            if (index >= colours.size()) break;
            player.getPacketSender().sendString(i, colours.get(index).getName());
        }
    }

    private void displayTitles(Player player, TitleCategory category) {
        player.setCurrentCategory(category);
        List<AvailableTitle> categorizedTitles = getCategory(category);

        for (int i = TITLE_INDEX_START; i < TITLE_INDEX_END; i++) {
            int index = i - TITLE_INDEX_START;
            if (index < categorizedTitles.size()) {
                AvailableTitle title = categorizedTitles.get(index);
                String col = player.getUnlockedTitles()
                    .stream()
                    .anyMatch(t -> t.getName().equalsIgnoreCase(title.name)) ? "<col=006000>" : "<col=ee9021>";
                player.getPacketSender().sendString(i, col + title.getName());
            } else {
                player.getPacketSender().sendString(i, "");
            }
        }
    }

    public void init() {
        categorizedTitles.add(new CategorizedTitles(PVMING, wildernessBosses(), slayerTasks(), bossKills()));
        categorizedTitles.add(new CategorizedTitles(OTHER, otherTitles()));
        colours.add(new TitleColour("Orange", "<col=ff7000>"));
        colours.add(new TitleColour("Red", "<col=ff0000>"));
        colours.add(new TitleColour("Green", "<col=006000>"));
        colours.add(new TitleColour("Blue", "<col=255>"));
    }

    public List<AvailableTitle> getCategory(TitleCategory category) {
        return categorizedTitles.stream().filter(cat -> cat.getCategory() == category).map(CategorizedTitles::getTitles).findAny().orElse(List.of());
    }

    private List<AvailableTitle> otherTitles() {
        List<AvailableTitle> titles = new ArrayList<>();
        TitleCategory category = OTHER;
        CustomRequirement customRequirement = new CustomRequirement();
        titles.add(create("Custom", category).addRequirement(customRequirement));
        MaxRequirement maxRequirement = new MaxRequirement();
        titles.add(create("Maxed", category).addRequirement(maxRequirement));
        titles.add(create("Completionist", category).addRequirement(maxRequirement).addRequirement(new AllAchievementsRequirement()));
        titles.add(create("The Skiller", category).addRequirement(new MasteryNonCombat()));

        titles.addAll(create(WinStakeRequirement.class, category,
            50, "The Staker",
            100, "Addict"));

        TitleUnlockRequirement.UnlockableTitle.SET.stream().map(title -> create(title.getName(), category).addRequirement(new TitleUnlockRequirement(title))).forEach(titles::add);
        return titles;
    }

    private List<AvailableTitle> bossKills() {
        return create(BossRequirement.class, PVMING,
            100, "The Victor",
            300, "Conqueror",
            500, "The Boss");
    }

    private List<AvailableTitle> slayerTasks() {
        return create(SlayerTaskRequirement.class, PVMING,
            50, "The Gem",
            100, "The Slayer",
            200, "The Butcher");
    }

    private List<AvailableTitle> wildernessBosses() {
        return create(WildernessBossRequirement.class, PVMING,
            25, "The Brave",
            50, "The Valiant",
            100, "Punisher",
            250, "The Butcher");
    }

    private AvailableTitle create(String name, TitleCategory category) {
        return new AvailableTitle(name, category);
    }


    /**
     * Creates a bunch of titles (nasty)
     */
    private List<AvailableTitle> create(Class<? extends TitleRequirement> type, TitleCategory category,
                                        Object... entries) {
        int lastNumber = -1;
        List<AvailableTitle> titles = new ArrayList<>();

        for (Object entry : entries) {
            if (lastNumber == -1) {
                lastNumber = (Integer) entry;
            } else {
                String name = (String) entry;
                try {
                    TitleRequirement requirement = type.getDeclaredConstructor(int.class).newInstance(lastNumber);
                    AvailableTitle title = new AvailableTitle(name, category);
                    title.addRequirement(requirement);
                    titles.add(title);
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    log.error("Failed to create titles.", e);
                }

                lastNumber = -1;
            }
        }

        return titles;
    }

}
