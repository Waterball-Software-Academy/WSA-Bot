package tw.waterballsa.utopia.utopiagamificationquest.service

import org.springframework.stereotype.Component
import tw.waterballsa.utopia.utopiagamificationquest.domain.Action
import tw.waterballsa.utopia.utopiagamificationquest.domain.Mission
import tw.waterballsa.utopia.utopiagamificationquest.repositories.MissionRepository

@Component
class PlayerFulfillMissionsService(
    private val missionRepository: MissionRepository
) {

    fun execute(action: Action, presenter: Presenter) {
        with(action) {
            val missions = missionRepository.findInProgressMissionsByPlayerId(player.id)

            fulfillMissions(missions, presenter)
        }
    }

    private fun Action.fulfillMissions(missions: List<Mission>, presenter: Presenter) {
        missions.filter { mission -> mission.match(this) }
            .onEach { mission -> mission.carryOut(this) }
            .filter { mission -> mission.isCompleted() }
            .onEach { mission ->
                missionRepository.saveMission(mission)
                presenter.presentClaimMissionReward(mission)
            }
    }

    interface Presenter {
        fun presentClaimMissionReward(mission: Mission)
    }
}
