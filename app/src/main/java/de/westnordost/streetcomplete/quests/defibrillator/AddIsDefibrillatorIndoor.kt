package de.westnordost.streetcomplete.quests.defibrillator

import de.westnordost.streetcomplete.R
import de.westnordost.streetcomplete.data.osm.mapdata.Element
import de.westnordost.streetcomplete.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcomplete.data.osm.mapdata.filter
import de.westnordost.streetcomplete.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcomplete.data.osm.osmquests.Tags
import de.westnordost.streetcomplete.data.user.achievements.QuestTypeAchievement.LIFESAVER
import de.westnordost.streetcomplete.quests.YesNoQuestAnswerFragment
import de.westnordost.streetcomplete.util.ktx.toYesNo

class AddIsDefibrillatorIndoor : OsmFilterQuestType<Boolean>() {

    override val elementFilter = """
        nodes with
         emergency = defibrillator
         and access !~ private|no
         and !indoor
    """
    override val changesetComment = "Add whether defibrillator is inside building"
    override val wikiLink = "Key:indoor"
    override val icon = R.drawable.ic_quest_defibrillator
    override val isDeleteElementEnabled = true
    override val questTypeAchievements = listOf(LIFESAVER)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_bicycleParkingCoveredStatus_title

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter("nodes, ways with amenity = bicycle_parking")

    override fun createForm() = YesNoQuestAnswerFragment()

    override fun applyAnswerTo(answer: Boolean, tags: Tags, timestampEdited: Long) {
        tags["covered"] = answer.toYesNo()
    }
}
