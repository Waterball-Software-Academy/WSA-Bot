package tw.waterballsa.utopia.utopiagamificationquest.listeners

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.guild.GuildAuditLogEntryCreateEvent
import org.springframework.stereotype.Component
import tw.waterballsa.utopia.commons.config.WsaDiscordProperties
import tw.waterballsa.utopia.utopiagamificationquest.domain.actions.PostAction
import tw.waterballsa.utopia.utopiagamificationquest.repositories.PlayerRepository
import tw.waterballsa.utopia.utopiagamificationquest.service.PlayerFulfillMissionsService

@Component
class PostListener(
    guild: Guild,
    playerRepository: PlayerRepository,
    private val playerFulfillMissionsService: PlayerFulfillMissionsService
) : UtopiaGamificationListener(guild, playerRepository) {

    override fun onGuildAuditLogEntryCreate(event: GuildAuditLogEntryCreateEvent) {
        with(event) {
            val user = user ?: return
            val channel = guild.getThreadChannelById(entry.targetId)?.parentChannel ?: return
            val player = user.toPlayer() ?: return

            val action = PostAction(
                player,
                channel.id
            )

            playerFulfillMissionsService.execute(action, user.claimMissionRewardPresenter)
        }
    }

    private val GuildAuditLogEntryCreateEvent.user get() = entry.user ?: jda.retrieveUserById(entry.userId).complete()
}
