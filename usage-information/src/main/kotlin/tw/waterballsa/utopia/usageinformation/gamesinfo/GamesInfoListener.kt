package tw.waterballsa.utopia.usageinformation.gamesinfo

import dev.minn.jda.ktx.messages.Embed
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import org.springframework.stereotype.Component
import tw.waterballsa.utopia.jda.UtopiaListener
import tw.waterballsa.utopia.usageinformation.*
import tw.waterballsa.utopia.usageinformation.gamesinfo.domain.GamesInfo

private const val GAMES_INFO_COMMAND_NAME = "games"
private const val DICE_GAME_COMMAND_NAME = "dice"
private const val GUESS_NUMBER_COMMAND_NAME = "guess"
private const val ROCK_PAPER_SCISSORS_COMMAND_NAME = "rps"
private const val ROULETTE_GAME_COMMAND_NAME = "roulette"
private const val GUESS_1A2B_COMMAND_NAME = "1a2b"
private const val OPTION_NAME = "games-option"

@Component
class GamesInfoListener(guild: Guild) : UtopiaListener() {
    private val commandNameToId = hashMapOf<String, String>()

    init {
        val commandDataList = guild.retrieveCommands().complete()
        val commandNameSet = setOf(
            DICE_GAME_COMMAND_NAME,
            GUESS_NUMBER_COMMAND_NAME,
            ROCK_PAPER_SCISSORS_COMMAND_NAME,
            ROULETTE_GAME_COMMAND_NAME,
            GUESS_1A2B_COMMAND_NAME
        )
        commandDataList.filter {
            it.name in commandNameSet
        }.forEach {
            commandNameToId[it.name] = it.id
        }
    }

    override fun commands(): List<CommandData> {
        return listOf(
            Commands.slash(GAMES_INFO_COMMAND_NAME, "The mini games introduction.")
                .addOptions(
                    OptionData(OptionType.STRING, OPTION_NAME, "The mini games you want to know.", false)
                        .addChoice("Dice game", DICE_GAME_COMMAND_NAME)
                        .addChoice("Guess number game", GUESS_NUMBER_COMMAND_NAME)
                        .addChoice("Rock paper scissors game", ROCK_PAPER_SCISSORS_COMMAND_NAME)
                        .addChoice("Roulette game", ROULETTE_GAME_COMMAND_NAME)
                        .addChoice("Guess 1A2B game", GUESS_1A2B_COMMAND_NAME)
                )
        )
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        with(event) {
            if (fullCommandName != GAMES_INFO_COMMAND_NAME) {
                return
            }

            val optionName = getOption(OPTION_NAME)?.asString
            var gameInfoCommandId = ""
            val commandsDataList = guild?.retrieveCommands()?.complete()
            commandsDataList?.filter {
                it.name == GAMES_INFO_COMMAND_NAME
            }?.forEach {
                gameInfoCommandId = it.id
            }

            if (optionName == null) {
                replyEmbeds(Embed {
                    title = "遊戲資訊！"
                    description = """
                        每個遊戲最多都可以下 240 🪙 的賭注
                        （輸入 </$GAMES_INFO_COMMAND_NAME:${gameInfoCommandId}> `<$OPTION_NAME:>`會有更詳細的小遊戲資訊喔 ☺️）
                        """.trimIndent()
                }).setEphemeral(true).queue()
            }

            val commandId = commandNameToId[optionName] ?: return

            when (optionName) {
                DICE_GAME_COMMAND_NAME -> replyEmbeds(GamesInfo().diceGame(commandId))
                    .setEphemeral(true)
                    .queue()

                GUESS_NUMBER_COMMAND_NAME -> replyEmbeds(GamesInfo().guessNumberGame(commandId))
                    .setEphemeral(true)
                    .queue()

                ROCK_PAPER_SCISSORS_COMMAND_NAME -> replyEmbeds(GamesInfo().rockPaperScissorsGame(commandId))
                    .setEphemeral(true)
                    .queue()

                ROULETTE_GAME_COMMAND_NAME -> replyEmbeds(GamesInfo().rouletteGame(commandId))
                    .setEphemeral(true)
                    .queue()

                GUESS_1A2B_COMMAND_NAME -> replyEmbeds(GamesInfo().guess1a2bGame(commandId))
                    .setEphemeral(true)
                    .queue()

                else -> reply("請輸入目前有的小遊戲名稱").setEphemeral(true).queue()
            }
        }
    }
}
