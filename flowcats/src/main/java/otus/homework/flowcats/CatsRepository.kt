package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow<ApiResult<Fact>> {
        while (true) {
            val latestNews = catsService.getCatFact()
            emit(ApiResult.ApiSuccess(latestNews))
            delay(refreshIntervalMs)
        }
    }.catch {
            emit(ApiResult.ApiError(1,""))
        }
}