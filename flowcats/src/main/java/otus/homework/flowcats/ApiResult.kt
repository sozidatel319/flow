package otus.homework.flowcats

sealed class ApiResult<T : Any> {

    class ApiSuccess<T : Any>(val data: T) : ApiResult<T>()
    class ApiError<T : Any>(val code: Int, val message: String?) : ApiResult<T>()
}
