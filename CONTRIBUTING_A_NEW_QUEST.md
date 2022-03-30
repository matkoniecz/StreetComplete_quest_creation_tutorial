This file describes in detail the process of creating a new quest.

For code style and more general info - see [CONTRIBUTING file](CONTRIBUTING.md#development).

If you want to contribute code to StreetComplete, then making a new quest is one of the easiest programming tasks. It is quite easy to implement a quest where layout design matches an existing quest. For example one more yes/no quest or where user taps one of displayed images.

Contributions like that are highly welcomed and you would make mapping one more thing in OSM much easier! You can implement it also if you never used Kotlin or implemented anything for Android. Being highly experienced programmer is not necessary here.

Reading the text below is not necessary to create a new quest. Duplicating existing quest and modifying its code may be sufficient. And people were creating new quests before this documentation existed.

But this materials may help or be quicker than trying to fully explore on your own how things work.

# Required

- programming ability - it is not a good task for someone who has never programmed anything.
  - Setup of environment takes a long time
  - Editing even the simplest quest requires edits to at least three different files
  - Kotlin familiarity is not required, but the ability to adjust to a new syntax is needed. If you are unfamiliar with Kotlin reading about [null safety related syntax](https://kotlinlang.org/docs/null-safety.html) is likely to be very useful.
  - It is expected that someone trying this can search for a solution to a typical problem on encountering an error message
- familiarity with StreetComplete as a user - it is highly recommended to be familiar with how StreetComplete works. Making hundreds of edits is not mandatory, but having a passing familiarity with how surveying with StreetComplete works is needed.
- time - Android Studio setup is sadly complicated
  - Multiple hours are typical, though mostly spent on waiting for various downloads
  - setting up Android Studio will likely be more complex than writing the code
- [basic ability to use git](https://git-scm.com/docs/user-manual) (but as mentioned, anything GitHub specific will be explained)
  - there is version control integration available in Android Studio
    - From the Android Studio **VCS** menu, click **Enable Version Control Integration** ([see official docs](https://developer.android.com/studio/intro#version_control_basics))
    - Go to File -> Settings -> Version control in Android Studio to optionally configure git integration
  - Using git from command line or terminal in Android Studio also works fine
- GitHub [account](https://github.com/signup)
- around 15GB of free space on disk
  - the initial setup will typically download half the Internet while getting emulators and dependencies

# Dependencies - initial setup

- install necessary software ([Android Studio](https://developer.android.com/studio) and [git](https://git-scm.com/downloads))
- create a GitHub [account](https://github.com/signup) if needed
- visit [https://github.com/streetcomplete/StreetComplete](https://github.com/streetcomplete/StreetComplete) and press the "fork" button on the top right
  - this creates a copy of StreetComplete repository that you control and can prepare code there
- clone your fork of a StreetComplete repository
  - in Android Studio it can be achieved without command line ( File -> New -> Project from Version Control... )
  - see [Github Documentation](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository) how repository can be cloned and where its URL can be found
- open StreetComplete in Android Studio
- [setup an emulator in Android Studio](https://developer.android.com/studio/run/emulator#install) (you can also [connect to a real device via USB](https://developer.android.com/studio/run/device), this can replace using the emulator)
- run StreetComplete in the emulator (to verify that everything was setup as required)

If you are doing it for the first time, don't worry if there is an error to solve along the way, this is typical for setting up an Android development environment. See [CONTRIBUTING file](CONTRIBUTING.md#development) which has some links to information about the setup.

# Invent a new quest

## Own ideas

To [repeat](CONTRIBUTING.md#developing-new-quests) from that documentation file:  [**open an issue** discussing the quest](CONTRIBUTING.md#developing-new-quests), before starting other work. This way it can be confirmed that such quest can be included. This can be skipped if you are an [experienced](https://github.com/streetcomplete/StreetComplete/discussions/3450) StreetComplete contributor.

## Existing proposals

You can also look at [quest proposals waiting for implementation](https://github.com/streetcomplete/StreetComplete/issues?q=is%3Aissue+is%3Aopen+label%3A%22new+quest%22+-label%3A%22blocked%22).

# Prepare repository for development

That is a good moment to create a branch and switch to it. 

It can be done using Android Studio GUI (Git -> New branch...), or in CLI with the following command:

```bash
git checkout -b short_branch_name_describing_planned_work
```

In general, [Intellij documentation](https://www.jetbrains.com/help/idea/using-git-integration.html) about git integration is applicable to Android Studio (which is rebranded Intellij with minor modifications).

# Learn from existing code

## Find a base

### Hints for looking around code

Full text search ([<kbd>ctrl</kbd>+<kbd>shift</kbd>+<kbd>f</kbd>](https://www.jetbrains.com/help/idea/finding-and-replacing-text-in-file.html) or [grepping](https://github.com/BurntSushi/ripgrep#ripgrep-rg)) remains useful and powerful.

But "Find usages" (<kbd>alt</kbd>+<kbd>F7</kbd>) is also a very powerful way to find where given function/constant/property/etc is appearing, classified by usages.

### Base on existing

Base the new quest on one that exists already.

Find one that has the same type of interface as the one that you are trying to implement.

Are you trying to implement a quest that will have simple yes/no answer? Take [`AddIsDefibrillatorIndoor`](app/src/main/java/de/westnordost/streetcomplete/quests/defibrillator/AddIsDefibrillatorIndoor.kt) quest as a base. Or [`AddTracktype`](app/src/main/java/de/westnordost/streetcomplete/quests/tracktype/AddTracktype.kt) where the mapper will be selecting one of presented images.

Is it going to be asked for POIs and should be disabled by default? [`AddWheelchairAccessBusiness`](app/src/main/java/de/westnordost/streetcomplete/quests/wheelchair_access/AddWheelchairAccessBusiness.kt) may be a good base.

Quests are grouped in [one folder](app/src/main/java/de/westnordost/streetcomplete/quests).

Implementing quest by duplicating and modifiying existing one is the recommended method.

### Locating a quest

Search across the code for part of a question or other text specific to this quest. For example "the name of this place?".

You will find an [XML file](app/src/main/res/values/strings.xml) with an entry looking like this:

```xml
    <string name="quest_placeName_title">"What’s the name of this place?"</string>
```

The identifier `quest_placeName_title` is a string reference, used in the code to allow translations.

Search for this identifier in `*.kt` files, it should appear in the quest file (in this case [AddPlaceName.kt](app/src/main/java/de/westnordost/streetcomplete/quests/place_name/AddPlaceName.kt)).

This method can often be used to locate relevant code.

## Pull Requests

One of the better ways to get around a codebase that is new to you is to look at recent accepted proposals to change the code (pull requests).

This is also likely useful here.

Find some [recent ones](https://github.com/streetcomplete/StreetComplete/pulls?q=is%3Apr+is%3Aclosed) adding a quest.

You can look at what was changed to achieve the goal, where relevant code is located. How much coding was needed to implement something? And what kind of comments are typical, how long one needs to wait for maintainers and so on.

This can be also used to locate relevant code, especially helpful if some change needs to be done in multiple files.

# Copying

Duplicate the relevant quest folder from [`app/src/main/java/de/westnordost/streetcomplete/quests`](app/src/main/java/de/westnordost/streetcomplete/quests). Some contain multiple quests, in such case delete unnecessary files.

Some quests are entirely defined in a single file, some have additional answer class, custom interface or utility classes.

For example, lets imagine that new quest will ask whether [AED](https://wiki.openstreetmap.org/wiki/Tag:emergency%3Ddefibrillator) is placed indoor or outdoor. A very similar in mechanics quest with simple yes/no question is for example [quest asking "Is this bicycle parking covered (protected from rain)?"](app/src/main/java/de/westnordost/streetcomplete/quests/bike_parking_cover/AddBikeParkingCover.kt).

So, as the first step: lets copy [`app/src/main/java/de/westnordost/streetcomplete/quests/bike_parking_cover/`](app/src/main/java/de/westnordost/streetcomplete/quests/bike_parking_cover) folder into `app/src/main/java/de/westnordost/streetcomplete/quests/defibrillator/`.

This is commit XXXXXXXXXXXXXXXXXXX TODO XXXXXXXXXXXXXXXXX in the demonstration repository.

# Adjust the copy

TODO: rename using refactor?
TODO: rename using file rename?
TODO: check for side efects

After the quest is copied it is necessary to adjust it a bit so it is not a duplicate anymore.

Change its class name and the file name to the new one.

In copied code change package info (things like `package de.westnordost.streetcomplete.quests.defibrillator` at the top) to match the new folder containing the quest.

# Add the quest to the list of active ones

Adjust [QuestsModule.kt](app/src/main/java/de/westnordost/streetcomplete/quests/QuestsModule.kt) file. It contains a big list of active quests, ordered by priority. Read [what governs their priority](https://github.com/streetcomplete/StreetComplete/blob/master/app/src/main/java/de/westnordost/streetcomplete/quests/QuestsModule.kt#L172-L195) but do not worry too much, it can be tweaked later.

Add your quest to the list so that it will be loaded by the app.

As this point you can run the app in emulator - everything should work and one of quests will appear twice.

# Modify the template

At this point prepared template can be modified to achieve the intended effect.

See for example [simple yes/no quest asking whether AED is indoor or outdoor](app/src/main/java/de/westnordost/streetcomplete/quests/defibrillator/AddIsDefibrillatorIndoor.kt).

TODO: maybe linking [app/src/main/java/de/westnordost/streetcomplete/data/osm/osmquests/OsmElementQuestType.kt](app/src/main/java/de/westnordost/streetcomplete/data/osm/osmquests/OsmElementQuestType.kt) would be better? And extend documentation there?

## Element selection

`elementFilter` property defines nodes, ways and relations which will be selected for a given quest. It is an element selection used by OsmFilterQuestType.

```kotlin
"""
    nodes with
     emergency = defibrillator
     and access !~ private|no
     and !indoor
"""
```

This query will be limited to object which fulfill some requirements.

- `nodes with`
  - nodes only, ways and relations are not eligible
- `emergency = defibrillator`
  - this tag must be present
- `and access !~ private|no`
  - and `access` tag must not have values `private` or `no`
  - `!~ private|no` is transformed into `^optionA|optionB$` [regexp](https://en.wikipedia.org/wiki/Regular_expression)
  - this filter excludes objects where mapper will likely has no access necessary for survey
- `and !indoor`
  - and `indoor` key must not be present at all, to show only ones where this tag is still missing

It is specified as a string, in syntax specific to StreetComplete. You can look around some quests to see how it works. If you are trying to implement a new quest and you got stuck here, [open a new issue](https://github.com/streetcomplete/StreetComplete/issues) to request more thorough documentation here.

### Resurvey

Some quests are asked not only when tag is missing but also when it is likely to be outdated.

Typical code is in [quest asking about motorcycle parking capacity](app/src/main/java/de/westnordost/streetcomplete/quests/motorcycle_parking_capacity/AddMotorcycleParkingCapacity.kt):

```kotlin
override val elementFilter = """
    nodes, ways with amenity = motorcycle_parking
      and access !~ private|no
      and (!capacity or capacity older today -4 years)
"""
```

This quest will be triggered when:

- `nodes, ways with`
  - on nodes or ways (relations not eligible)
- `amenity = motorcycle_parking`
  - where `amenity = motorcycle_parking` tag is present
- `and access !~ private|no`
  - and `access` tag does not have value `private` nor `no`
- `and (!capacity or capacity older today -4 years)`
  - and one of following is fullfilled:
    - `capacity` tag is not present at all (`!capacity`)
    - element was not edited for a long time (base time is 4 years, but it can be influenced by user changing settings)
    - `check_date:capacity` with a date indicating that it is outdated (the same as above applies)

### Prototyping

[Overpass Turbo](http://overpass-turbo.eu/) has own syntax but it is very useful tool for prototyping filters. It is very useful to verify own assumptions how things are tagged. Especially in more complex cases.

### Hints

The rules should generate as few false positives as possible. I.e. instead of asking for the surface of any way tagged with `highway=*`, the surface should instead only be asked for an inclusive list of roads.

In some cases it will be a good idea to [limit quests to certain countries](#enabledInCountries).

You can obtain more info about properties by placing the cursor in a property and pressing Ctrl+Q within Android Studio.

## changesetComment

```kotlin
override val changesetComment = "Add whether defibrillator is inside building"
```

message used as a changeset comment

## wikiLink

```kotlin
override val wikiLink = "Key:indoor"
```

points to the OSM Wiki page most relevant to the given quest, typically it is an added key. In this case, it is a page about [indoor=* tagging](https://wiki.openstreetmap.org/wiki/Key:indoor).

## icon

```kotlin
override val icon = R.drawable.ic_quest_defibrillator
```

icon drawable, you can initially use any icon.

Do not worry, submissions with placeholder icons are also welcomed! In many cases icon itself was not made by the PR author.

More info about icon handling [will be given later](#adding-quest-icon).

## questTypeAchievements

```kotlin
override val questTypeAchievements = listOf(LIFESAVER)
```

In quest achievements, list what is relevant to the given quest, see the full list of available ones in [AchievementsModule.kt](app/src/main/java/de/westnordost/streetcomplete/data/user/achievements/AchievementsModule.kt)

## getTitle

```kotlin
override fun getTitle(tags: Map<String, String>) = R.string.quest_is_defibrillator_inside_title
```

It is a message displayed to user, code here passes a [reference](https://developer.android.com/guide/topics/resources/string-resource) to the string. You can change it to the new, not yet existing one and use a built in tool to place text.

Actual strings sit in [app/src/main/res/values/strings.xml](app/src/main/res/values/strings.xml)

There are separate files with translated text, but do not worry about it - [translations are handled separately](CONTRIBUTING.md#translating-the-app).

## Form

```kotlin
override fun createForm() = YesNoQuestAnswerFragment()
```

Form defines interface used by mappers.

In this case, the simplest possible is used.

But sometimes more complex ones are needed, see for example [AddBridgeStructure.kt](https://github.com/streetcomplete/StreetComplete/blob/master/app/src/main/java/de/westnordost/streetcomplete/quests/bridge_structure/AddBridgeStructure.kt)

`override fun createForm() = AddBridgeStructureForm()`

With form defined in [AddBridgeStructureForm](app/src/main/java/de/westnordost/streetcomplete/quests/bridge_structure/AddBridgeStructureForm.kt)

## applyAnswerTo

```kotlin
override fun applyAnswerTo(answer: Boolean, tags: Tags, timestampEdited: Long) {
    tags["indoor"] = answer.toYesNo()
}
```

This code is responsible for modifying `tags` object (passed by reference).

In this case it would set `indoor` tag to have either `yes` or `no` answer, depending on selection in the quest interface.

Actions may include (examples from various quests):

- `tags["indoor"] = answer.toYesNo()`
  - set `indoor` tag to have either `yes` or `no` answer, based on provided data
- `tags.remove("amenity")`
  - remove key if it is present
- `tags.updateWithCheckDate("lit", answer.toYesNo())`
  - if the new value differs from existing tag value: new value will be applied
  - in case of value being the same: add survey date tag, it would be `check_date:lit=` in this case
    - so can be used even if value will stay the same - possible for resurvey quests
    - if the new value is the same as existing tag value: adds [survey date tag](app/src/main/java/de/westnordost/streetcomplete/data/meta/OsmTaggings.kt#L33) (`check_date:lit=` in this example)
  - always update survey tag if present already
  - necessary if given quest includes resurvey of old elements that are already tagged

## Extras

Info listed so far must be supplied by every quest. But there are also several optional fields. This specific quest has

```kotlin
override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
    getMapData().filter("nodes with emergency = defibrillator")
```

which causes nearby `emergency = defibrillator` nodes to be shown (icons used are defined in [PinIcons.kt](app/src/main/java/de/westnordost/streetcomplete/map/PinIcons.kt))

See [also other optional properties](app/src/main/java/de/westnordost/streetcomplete/data/osm/osmquests/OsmElementQuestType.kt).

# Adding quest icon
<!-- note that this section name is linked from section discussing val icon, in case of changing section name change also that link -->
It is OK to submit a quest without own icon, using any icon as a placeholder.

But it would be even better to include also icon.

Note that there are some graphics which haven't been used yet, created for proposed or expected quests; maybe you don't even need to create an icon!

A new icon can reuse the content of [other quest icons](res/graphics/quest), it can be based on openly licensed graphics. See [the attribution file](res/graphics/authors.txt) for what was used so far.

Keep similar style to existing ones and app in general. Once the quest icon is ready:

- save as "Plain SVG" or clean SVG file from unnecessary cruft in another way, like using [svgo](https://github.com/svg/svgo)
- Put SVG into [`res/graphics/quest`](res/graphics/quest) folder
  - SVG is a standard format editable in various software, unlike internal Android Studio XML that will be produced in the next steps.
- Open Android Studio
- Right click on the "app" folder in the Project tool window (top left)
- Select new → vector asset
- Select your SVG file
- Name with `ic_quest_` prefix (something like `ic_quest_traffic_calming`)
- Press "Finish" button to generate drawable
- Add an entry in the [attribution file](res/graphics/authors.txt)
- Modify `icon` property in the quest definition to use the new drawable
- Commit modified or created files
- Compile, test quest in the emulator

The same method applies also to other vector drawables, although they will be placed in other parts of [res/graphics/](res/graphics/)

Inkscape is a typical tool to create and edit SVG files, it is good, free and open-source so is available to all.

# Test

Obviously, testing can also be done earlier. But the quest should at least be tested before submitting for review.

Typically it is done using an emulator. Note that you can set location in emulator settings rather than scrolling within StreetComplete itself.

* Is quest listed?
  * Look at the quest list in settings - is your quest appearing there? If not - see [this step](#add-the-quest-to-the-list-of-active-ones).
  * While you are there you can disable all quests except yours for easier testing.
* Is it shown for expected elements?
  * Note that due to emulator peculiarities you may need to move map after quests are downloaded to see their markers ([gory details for curious](https://github.com/streetcomplete/StreetComplete/issues/2780))
* Is it selected for some unwanted elements?
* Can you tap on a quest marker to open quest form?
* Can you fill the answer as expected?
* Can you solve the quest?
* Is expected tagging being applied?
  * You can look at logs for info what was applied or use undo menu from bottom-left in the app itself.
  * You can freely answer - as long as you are not logged in, nothing will be submitted. Even after logging in you can disable uploading answers in settings.
* Is quest disappearing after being solved?
  * If no - then either tags are not being applied or element selection filter have some problems.

See "logcat" (bottom left area of the screen) to see stacktrace or logging messages.

## Adding logs

```kotlin
import android.util.Log

Log.w("Unique string for easy grepping in logcat", "Message with whatever you need like #${someVariable.itsProperty}")
```

# Open a pull request

[Pull request](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request) is a submission of proposed changes to the source code.

You can put into the description of PR something like "fixes #1234" to [mark it as fixing this issue](https://docs.github.com/en/issues/tracking-your-work-with-issues/linking-a-pull-request-to-an-issue#linking-a-pull-request-to-an-issue-using-a-keyword). It can go into a commit message or PR description or the tile.

If you are stuck on something, need some help or guidance and you are willing and able to continue after solving the problem - you can open a pull request in an incomplete state and mention the blocker.

You can see [already submitted pull requests](https://github.com/streetcomplete/StreetComplete/pulls?q=is%3Apr+) to see how this process works in practice.

# Future

After opening a pull request it will be reviewed and you will be likely asked to make some changes. This is normal and also happens with pull requests submitted by experienced contributors.

Changes typically include improving code style, tweaking phrasing and quest settings.

You are also welcome to help with reviewing other PRs - different people have different strengths, there are active reviewers who help with code style, there are some native speakers of English, some with deep knowledge of OSM tagging schemes or deep knowledge of how StreetComplete works.

In case that pull requests appears to be ready, it will be marked as approved and wait for merge.

After the PR is finished it will be merged before the beta release of the next version. This way it can be additionally tested with a wider audience before release to all and translators have time to [translate text into other languages](CONTRIBUTING.md#translating-the-app).

After full release it will reach the entire StreetComplete audience who now will be able to more easily contribute to OpenStreetMap. Thanks in advance for that!

Once your code is merged into StreetComplete you will be credited at [repository statistics](https://github.com/streetcomplete/StreetComplete/graphs/contributors).

# Bad documentation is a bug

Unclear documents, including this one, are a bug. Feel free to either submit a [pull request](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request) with a fix or [open an issue](https://github.com/streetcomplete/StreetComplete/issues/new) describing your confusion.

Note that not everything will be directly described. This document very intentionally doesn't include a step-by-step guide to installing Android Studio, [linking](CONTRIBUTING.md#development) to official docs instead.

# More complexity

What was described above is an attempt to cover all aspects of quest creation, without describing all the complexity.

Below is some additional info.

## Kotlin

Article about [null safety related syntax](https://kotlinlang.org/docs/null-safety.html) is likely to be very useful, especially if you are confused by `?:` [Elvis operator](https://kotlinlang.org/docs/null-safety.html#elvis-operator).

## Designing the form

As mentioned, the user interface must leave no space for misunderstandings, it must be concise and quick and easy to use. Also sounds obvious, but you will quickly find out that a balance must be found between covering all the edge cases and designing the form to be as straightforward and clutterless as possible.

- Design the main form clutter-free so that it is straightforward for the majority of use cases.
- Allow to answer popular edge cases, but don't clutter up the main form with that. A good pattern is to move such answers into the "Other answers..." menu. E.g. look at the [opening hours quest](app/src/main/java/de/westnordost/streetcomplete/quests/opening_hours/AddOpeningHoursForm.kt#L40-L48).
- Don't rely on the "leave a note" fallback too much. It is not intended and does not work as a regular answer but is designed to cover the case that the question was invalid itself because it was based on wrong data like i.e. the place does not exist anymore.
- The information the user should fill in should be as atomic as possible. Users are impatient, i.e. do not let them fill out a whole address with street name etc. when just the house number is fine too.
- "A picture is worth a thousand words": Often the term for certain things may not be enough to convey the meaning of certain predefined answers. Do you know what a wheelbender is? You will know if you see the photo.

Considerations about the edge cases to consider, how the design could look like and finding good representative photos or icons that match in style is also part of the preparational work that can be done without programming knowledge.

## New photos

Some quests will require photos for their interface.

Photos must be available on open license - so not every photo found on the Internet is usable. You can either take your own photo or find an existing freely licensed photo.

Good places to find freely licensed images are [Geograph](https://www.geograph.org.uk/) and [Wikimedia Commons](https://commons.wikimedia.org/).

You can also take your own photos, a standard smartphone camera is good enough. And sometimes a highly specific image is needed.

In StreetComplete many images have unusual composition. Often it is necessary to leave space for a label at the bottom.

Images should be free of visual debris, not misleading. Though it is fine to use an image not strictly matching what is depicted, as long as it is clear. For example, [a permanent pile of soil blocking road](https://wiki.openstreetmap.org/wiki/Tag:barrier%3Ddebris) is illustrated by a [temporary landslide](https://commons.wikimedia.org/wiki/File:Landslide_on_OR_42S_(46849629014).jpg). This is OK as images are illustrative.

Photos go to a different folder than SVGs: they can be used directly by the build process so put them into folders

- [mdpi](app/src/main/res/drawable-mdpi) - 384 pixels for images, with three square images in each row it would be 128 x 128 pixels for each)
- [hdpi](app/src/main/res/drawable-hdpi) - 576 pixels for images (192 x 192 pixels in case of three quare images in each row)
- [xhdpi](app/src/main/res/drawable-xhdpi) 768 pixels for images (256 x 256 pixels in case of three quare images in each row)
- [xxhdpi](app/src/main/res/drawable-xxhdpi) 1152 pixels (384 x 384 pixels in case of three quare images in each row)

Each of these folders should hold the same image resized to a different resolution. While testing various images it is enough to put one into any of the folders.

The [rescaling script](https://github.com/matkoniecz/rescaling_for_android) may be useful, but you can also do this manually with Gimp or similar software.

After adding a photo, remember to update [the credit file](app/src/main/res/authors.txt) (different to the one for icons).

## Custom filters

It is possible to use far more complex filters when querying for eligible elements.

Matches like `surface ~ earth|dirt|ground` are possible and are evaluated as "`surface` is either of `earth`, `dirt`, `ground`"

`access !~ private|no` will be evaluated to "`access` is neither `private` nor `no`"

Simple lists are [evaluated as sets](https://github.com/streetcomplete/StreetComplete/blob/2e812b9a3b5288983309a7edde6e8f9db05ad3f2/app/src/test/java/de/westnordost/streetcomplete/data/elementfilter/filters/ElementFilterOverpassKtTest.kt#L79-L88) - and for example `surface ~ earth|dirt|ground` will not match `surface=earther` tag. Basically, `surface ~ earth|dirt|ground` is treated as `surface ~ ^earth|dirt|ground$`

But using regexp like `surface ~ ^(.*)[0-9]$` is [also possible](https://github.com/streetcomplete/StreetComplete/blob/master/app/src/test/java/de/westnordost/streetcomplete/data/elementfilter/filters/ElementFilterOverpassKtTest.kt#L79-L88).

It is possible to check for [age of elements](https://github.com/streetcomplete/StreetComplete/blob/master/app/src/main/java/de/westnordost/streetcomplete/quests/construction/MarkCompletedHighwayConstruction.kt#L13-L17) or implement a [fully custom tag parsing](https://github.com/streetcomplete/StreetComplete/blob/master/app/src/main/java/de/westnordost/streetcomplete/quests/opening_hours/AddOpeningHours.kt#L137-L151), still combined with filter syntax.

It is possible to share and reuse [information about tagging schemes](https://github.com/streetcomplete/StreetComplete/blob/master/app/src/main/java/de/westnordost/streetcomplete/quests/surface/AddRoadSurface.kt#L18).

(this info is gathered [here](/app/src/main/java/de/westnordost/streetcomplete/data/meta/OsmTaggings.kt))

Even more complex ones using different class bases are possible. Such as what was needed by the [address quest](https://github.com/streetcomplete/StreetComplete/blob/master/app/src/main/java/de/westnordost/streetcomplete/quests/address/AddAddressStreet.kt) or the [crossing quest](https://github.com/streetcomplete/StreetComplete/blob/master/app/src/main/java/de/westnordost/streetcomplete/quests/crossing/AddCrossing.kt) but it is better to start from something simpler.

It allows it to make complex geometry checks, but writing them is also far more complex.

## `enabledInCountries`

Some quests should be enabled only in some countries or disabled in a specific countries.

[`override val enabledInCountries = NoCountriesExcept("SE")`](app/src/main/java/de/westnordost/streetcomplete/quests/accepts_cash/AddAcceptsCash.kt) - enabled only in Sweden.

[`override val enabledInCountries = AllCountriesExcept("US", "CA")`](app/src/main/java/de/westnordost/streetcomplete/quests/address/AddHousenumber.kt) - not enabled in USA and Canada

## `defaultDisabledMessage`

Some quests should be disabled by default, for example ones that may require going inside a shop. In such case setting `override val defaultDisabledMessage = R.string.default_disabled_msg_go_inside` will have two effects:

- quest will be disabled by default
- on attempting to enable it user will get message asking are they sure. Exact message in this case would be `default_disabled_msg_go_inside` - but some quests use more specific ones.

Note that quest may be both disabled by default and limited to some countries.
