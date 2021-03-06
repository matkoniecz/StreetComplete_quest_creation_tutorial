package de.westnordost.streetcomplete.quests.sport

import de.westnordost.streetcomplete.R
import de.westnordost.streetcomplete.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcomplete.data.osm.osmquests.Tags
import de.westnordost.streetcomplete.data.user.achievements.QuestTypeAchievement.OUTDOORS

class AddSport : OsmFilterQuestType<List<Sport>>() {

    override val elementFilter = """
        ways with
          leisure = pitch
          and (!sport or sport ~ football|skating|hockey|team_handball)
          and access !~ private|no
    """
    /* treat ambiguous values as if it is not set */
    override val changesetComment = "Add pitches sport"
    override val wikiLink = "Key:sport"
    override val icon = R.drawable.ic_quest_sport
    override val questTypeAchievements = listOf(OUTDOORS)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_sport_title

    override fun createForm() = AddSportForm()

    override fun applyAnswerTo(answer: List<Sport>, tags: Tags, timestampEdited: Long) {
        tags["sport"] = answer.joinToString(";") { it.osmValue }
    }
}
