package de.westnordost.streetcomplete.quests.way_lit

import de.westnordost.streetcomplete.R
import de.westnordost.streetcomplete.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcomplete.data.osm.osmquests.Tags
import de.westnordost.streetcomplete.data.user.achievements.QuestTypeAchievement.PEDESTRIAN
import de.westnordost.streetcomplete.osm.MAXSPEED_TYPE_KEYS
import de.westnordost.streetcomplete.osm.updateWithCheckDate

class AddWayLit : OsmFilterQuestType<WayLitOrIsStepsAnswer>() {

    /* Using sidewalk, source:maxspeed=*urban etc and a urban-like maxspeed as tell-tale tags for
       (urban) streets which reached a certain level of development. I.e. non-urban streets will
       usually not even be lit in industrialized countries.

       Also, only include paths only for those which are equal to footway/cycleway to exclude
       most hike paths and trails.

        See #427 for discussion. */
    override val elementFilter = """
        ways with
        (
          highway ~ ${LIT_RESIDENTIAL_ROADS.joinToString("|")}
          or highway ~ ${LIT_NON_RESIDENTIAL_ROADS.joinToString("|")} and
          (
            sidewalk ~ both|left|right|yes|separate
            or ~${(MAXSPEED_TYPE_KEYS + "maxspeed").joinToString("|")} ~ .*urban|.*zone.*
            or maxspeed <= 60
            or maxspeed ~ "([1-9]|[1-2][0-9]|3[0-5]) mph"
          )
          or highway ~ ${LIT_WAYS.joinToString("|")}
          or highway = path and (foot = designated or bicycle = designated)
        )
        and
        (
          !lit
          or lit = no and lit older today -8 years
          or lit older today -16 years
        )
        and (access !~ private|no or (foot and foot !~ private|no))
        and indoor != yes
    """

    override val changesetComment = "Add whether way is lit"
    override val wikiLink = "Key:lit"
    override val icon = R.drawable.ic_quest_lantern
    override val isSplitWayEnabled = true
    override val questTypeAchievements = listOf(PEDESTRIAN)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_lit_title

    override fun createForm() = WayLitForm()

    override fun applyAnswerTo(answer: WayLitOrIsStepsAnswer, tags: Tags, timestampEdited: Long) {
        when (answer) {
            is IsActuallyStepsAnswer -> tags["highway"] = "steps"
            is WayLit -> tags.updateWithCheckDate("lit", answer.osmValue)
        }
    }

    companion object {
        private val LIT_RESIDENTIAL_ROADS = arrayOf("residential", "living_street", "pedestrian")

        private val LIT_NON_RESIDENTIAL_ROADS =
            arrayOf("primary", "primary_link", "secondary", "secondary_link",
                    "tertiary", "tertiary_link", "unclassified", "service")

        private val LIT_WAYS = arrayOf("footway", "cycleway", "steps")
    }
}
