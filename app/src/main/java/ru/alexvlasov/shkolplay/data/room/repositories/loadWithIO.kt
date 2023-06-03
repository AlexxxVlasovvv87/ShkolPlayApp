package ru.alexvlasov.shkolplay.data.room.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

//Общая функция - обработчик ошибок

suspend fun loadWithIO(
    onError: suspend (() -> Unit) = {},
    onNetworkError: suspend ((msg: String) -> Unit) = {},
    onSuccess: suspend (() -> Unit) = {},
    onFinish: suspend (() -> Unit) = {},
    toDo: suspend () -> Unit,
) {
    withContext(Dispatchers.IO) {
        try {
            toDo()
            onSuccess()
        } catch (e: HttpException) {
            onNetworkError(e.errorText())
        } catch (e: UnknownHostException) {
            onNetworkError("Отсутствует интернет-соединение")
        } catch (ex: Exception) {
            onError()
        } finally {
            onFinish()
        }
    }
}


fun HttpException.errorText(): String {
    val body = response()?.errorBody()?.string()
    return if (body.toString() == "Bad credentials")
        "Неправильно введены данные"
    else if (code() in 400..401 && body != null)
        body.toString()
    else
        "Произошла ошибка. Попробуйте еще раз"
}