package tw.waterballsa.alpha.wsabot.bot.gamification.commands

import me.jakejmattson.discordkt.commands.commands

fun gamification() = commands("gamification") {
    slash("gamification", "A 'gamification' command.") {
        execute {
            respond("Start gamification!")
        }
    }
}
