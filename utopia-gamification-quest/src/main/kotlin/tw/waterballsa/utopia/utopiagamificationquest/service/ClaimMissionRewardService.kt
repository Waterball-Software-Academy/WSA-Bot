package tw.waterballsa.utopia.utopiagamificationquest.service

import org.springframework.stereotype.Component
import tw.waterballsa.utopia.utopiagamificationquest.domain.Mission
import tw.waterballsa.utopia.utopiagamificationquest.domain.Player
import tw.waterballsa.utopia.utopiagamificationquest.repositories.MissionRepository

@Component
class ClaimMissionRewardService(
    private val missionRepository: MissionRepository,
) {

    fun execute(request: Request, presenter: Presenter) {
        with(request) {
            val mission = missionRepository.findPlayerMissionByQuestId(player.id, questId.toInt()) ?: return

            if (!mission.isCompleted()) {
                presenter.presentRewardsNotAllowed(mission)
                return
            }

            mission.rewardPlayer()

            missionRepository.saveMission(mission)

            presenter.presentPlayerExpNotification(mission)

            mission.nextMission()?.let { nextMission ->

                missionRepository.saveMission(nextMission)
                presenter.presentNextMission(nextMission)
            }
        }
    }

    class Request(
        val player: Player,
        val questId: String
    )

    interface Presenter {
        fun presentPlayerExpNotification(mission: Mission)
        fun presentNextMission(mission: Mission)
        fun presentRewardsNotAllowed(mission: Mission)
    }
}
