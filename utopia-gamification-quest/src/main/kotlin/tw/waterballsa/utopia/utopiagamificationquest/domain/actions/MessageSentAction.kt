package tw.waterballsa.utopia.utopiagamificationquest.domain.actions

import tw.waterballsa.utopia.utopiagamificationquest.domain.Action
import tw.waterballsa.utopia.utopiagamificationquest.domain.Player


class MessageSentAction(
    player: Player,
    val channelId: String,
    val context: String,
    val hasReplied: Boolean,
    val hasImage: Boolean,
    val numberOfVoiceChannelMembers: Int,
) : Action(player) {

    override fun match(criteria: Criteria): Boolean = criteria is MessageSentCriteria
}

class MessageSentCriteria(
    // 依照需求會有 "任意頻道" 和 "指定頻道" 的情況
    private val channelIdRule: ChannelIdRule,
    // 依照需求會有 "忽略此規則" 和 "訊息一定要回覆" 和 "訊息一定沒有回覆" 的情況
    private val hasRepliedRule: BooleanRule = BooleanRule.IGNORE,
    // 依照需求會有 "忽略此規則" 和 "訊息一定要有圖片"，和 "訊息一定沒有圖片" 的情況
    private val hasImageRule: BooleanRule = BooleanRule.IGNORE,
    // 依照需求會有 "使用regex匹配指定的訊息內容" 和 "忽略regex匹配指定的訊息內容" 的情況
    private val regexRule: RegexRule = RegexRule.IGNORE,
    private val numberOfVoiceChannelMembersRule: AtLeastRule = AtLeastRule.IGNORE,
) : Action.Criteria() {
    private val rules = listOf(channelIdRule, hasRepliedRule, hasImageRule, regexRule, numberOfVoiceChannelMembersRule)

    override fun meet(action: Action) =
        (action as? MessageSentAction)?.let { meetCriteria(it) } ?: false

    private fun meetCriteria(action: MessageSentAction): Boolean =
        channelIdRule.meet(action.channelId)
                && hasRepliedRule.meet(action.hasReplied)
                && hasImageRule.meet(action.hasImage)
                && regexRule.meet(action.context)
                && numberOfVoiceChannelMembersRule.meet(action.numberOfVoiceChannelMembers)
}

class ChannelIdRule(private val channelId: String) {
    companion object {
        val ANY_CHANNEL = ChannelIdRule("")
    }

    fun meet(actionChannelId: String): Boolean = actionChannelId.contains(channelId)
}

enum class BooleanRule(private val value: Boolean?) {
    IGNORE(null),
    TRUE(true),
    FALSE(false);

    fun meet(value: Boolean): Boolean = this.value?.let { it == value } ?: true
}

class RegexRule(private val regex: Regex) {
    companion object {
        val IGNORE = RegexRule(".*".toRegex())
    }

    fun meet(context: String): Boolean = context matches regex
}

class AtLeastRule(
    private val number: Int
) {
    companion object {
        val IGNORE = AtLeastRule(0)
    }

    fun meet(count: Int): Boolean = count >= number
}
