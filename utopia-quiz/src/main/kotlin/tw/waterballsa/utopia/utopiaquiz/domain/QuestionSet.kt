package tw.waterballsa.utopia.utopiaquiz.domain

import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import tw.waterballsa.utopia.utopiaquiz.repositories.QuizRepository

@Component
class QuestionSet() {

    private val questions: List<Question> = listOf(
                Question(
                    1,
                    "以下關於水球軟體學院的願景何者正確？",
                    listOf(
                        "學院是「全民成長型社群」，旨在讓所有剛加入的夥伴立刻感受到成長的動力，並善用社團等設施帶動所有紳士能教學相長、一起成長。",
                        "水球希望召集眾人之力一同建立一個非常友善、充實又充滿熱忱的軟體創作之地，發展各式各樣的活動，來一起架空軟體業的負面氛圍，鼓勵彼此一起做個心裡富足的工程師",
                        "「技術為王」，為了能召集更多優秀的工程師夥伴，學院主張「技術力」是加入學院的基礎成員必須擁有良好技術力，在交流上才不好有太多落差。",
                        "學院中有各式各樣的學習聚會，鼓勵學院全民一起追求在軟實力（職涯、組織、表達力、演講）和硬實力（軟體造詣）的長期發展和說走就走的行動力。"
                    ), 2),
                Question(
                    2,
                    "請問以下何者不是學院「三大 BUFF」？",
                    listOf(
                        "拜大佬運動",
                        "學院三大自在",
                        "紳士文化",
                        "全民化機制"
                    ), 0),
                Question(
                    3,
                    "以下何者不屬於「紳士文化」提倡的原則？",
                    listOf(
                        "「休但幾咧」表情原則",
                        "據理力爭原則",
                        "思辨原則",
                        " 引導或離開原則 (Guide or Leave)"
                    ), 1),
                Question(
                    4,
                    "以下何者言論遵守「紳士文化」？",
                    listOf(
                        "『從那個培訓班出來的人真的是完全不能用，簡直是搞笑⋯⋯』",
                        "『照你這種說法，你心態有問題，還不如先照個鏡子想想自己有沒有幾把刷子，我已經算是很客氣了』",
                        "『也許這 YY 技術在許多應用下仍有價值，不過我想請教其原因。就我理解，我認為新的 XXX 技術在大多數情況下能表現得更好。』",
                        "『好啦，你都很厲害，大家都相信你，你最棒了。』"
                    ), 2),
                Question(
                    5,
                    "根據「紳士文化」，遇到不認同的言論時，該怎麼應對？",
                    listOf(
                        "據理力爭，要求對方面對所有的質問。",
                        "如果你不打算用友善的語氣引導對方說出更完整的言論的話，那麼對對方的訊息點一個「反對」表情即可。",
                        "隱射、嘲諷、挑釁等負面文字試圖讓他人知道自己言論中的缺陷。",
                        "組織小群體，排擠他人和聊他人的是非。"
                    ), 1),
                Question(
                    6,
                    "以下何者並非學院中的「三大自在」之一？",
                    listOf(
                        "隨筆分享自在：將自己隨手寫的技術心得/學習筆記分享到學院論壇中",
                        "發問自在：遇到難題時，只要具備發問素養，就算一天問 20 則問題也沒關係。",
                        "話題自在：能自在地邀請大家來一同討論各式各樣的話題，在文字頻道中和語音頻道都行。",
                        "交友自在：可以隨意私訊任何一位成員，要求對方與自己聊天"
                    ), 3),
                Question(
                    7,
                    "以下何者社團願景敘述有錯？",
                    listOf(
                        "遊戲微服務計畫：帶大家一起玩遊戲，用玩遊戲的方式來學電腦科學的奧秘。",
                        "技術演講吐司會：鼓勵大家以「學習導向」的方式培養自身的技術思考和表達力，並一起成為最能言善道、最有渲染力的技術演講者」",
                        "既然職場要求我們刷題，那我們不如帶著更全面的熱忱去「解決難題」，在解題的過程中精學電腦科學的奧秘！",
                        "讓台灣的工程師，用一步的距離，就能與國際上的朋友們接軌。"
                    ), 0)
                )

    fun getRandomQuestions(number: Int): List<Question> = questions.shuffled().take(number).toList()

    fun getQuestionsByIds(ids: List<Int>): List<Question> =
        ids.map { targetId -> questions.first{ it.id == targetId } }.toList()
}
